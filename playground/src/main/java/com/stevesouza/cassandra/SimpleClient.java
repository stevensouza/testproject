package com.stevesouza.cassandra;


import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * note on mac and jdk7 i had to add this to the this files jvm run options:
 * -Dorg.xerial.snappy.lib.path=/Applications/myapps/dsc-cassandra-2.0.7 -Dorg.xerial.snappy.lib.name=libsnappyjava.jnilib -Dorg.xerial.snappy.tempdir=/tmp
 * <p>
 * http://bigchunk.me/2013/01/12/fix-cassandra-missing-snappy-java-class-at-osx-10-8/
 * <p>
 * Note this code only works with the embedded cassandra and so cassandra need not be explicitly started.  However, the following
 * steps are useful to know to start up an external instance.
 * <p>
 * start cassandra:
 * cd /Applications/myapps/dsc-cassandra-2.0.7/bin
 * sudo ./cassandra
 * <p>
 * Created by stevesouza on 4/29/14.
 */
public class SimpleClient implements Closeable {

    private Cluster cluster;
    private Session session;

    public SimpleClient() throws ConfigurationException, IOException, TTransportException {
        // can do cassandra properties like this or in cassandra.yaml (i think)
        // System.setProperty("cassandra.start_native_transport", "true");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra("cassandra.yaml");

    }

    public void connect(String node) {
        cluster = Cluster.builder()
                .addContactPoint(node).build();
        Metadata metadata = cluster.getMetadata();
        session = cluster.connect();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
    }

    public void close() {
        System.out.println("shutting down cassandra client");
        stopCassandra();
        //  cluster.close();
    }

    public void createSchema() {
        session.execute("CREATE KEYSPACE simplex WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':3};");
        session.execute(
                "CREATE TABLE simplex.songs (" +
                        "id uuid PRIMARY KEY," +
                        "title text," +
                        "album text," +
                        "artist text," +
                        "tags set<text>," +
                        "data blob" +
                        ");");
        session.execute(
                "CREATE TABLE simplex.playlists (" +
                        "id uuid," +
                        "title text," +
                        "album text, " +
                        "artist text," +
                        "song_id uuid," +
                        "PRIMARY KEY (id, title, album, artist)" +
                        ");");
    }

    public void loadData() {
        session.execute(
                "INSERT INTO simplex.songs (id, title, album, artist, tags) " +
                        "VALUES (" +
                        "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                        "'La Petite Tonkinoise'," +
                        "'Bye Bye Blackbird'," +
                        "'Joséphine Baker'," +
                        "{'jazz', '2013'})" +
                        ";");
        session.execute(
                "INSERT INTO simplex.playlists (id, song_id, title, album, artist) " +
                        "VALUES (" +
                        "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d," +
                        "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                        "'La Petite Tonkinoise'," +
                        "'Bye Bye Blackbird'," +
                        "'Joséphine Baker'" +
                        ");");
    }

    public void loadDataBound() {
        PreparedStatement statement = session.prepare(
                "INSERT INTO simplex.songs " +
                        "(id, title, album, artist, tags) " +
                        "VALUES (?, ?, ?, ?, ?);"
        );

        BoundStatement boundStatement = new BoundStatement(statement);
        Set<String> tags = new HashSet<String>();
        tags.add("jazz");
        tags.add("2013");
        session.execute(boundStatement.bind(
                UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"),
                "La Petite Tonkinoise'",
                "Bye Bye Blackbird'",
                "Joséphine Baker",
                tags));

        statement = session.prepare(
                "INSERT INTO simplex.playlists " +
                        "(id, song_id, title, album, artist) " +
                        "VALUES (?, ?, ?, ?, ?);"
        );
        boundStatement = new BoundStatement(statement);
        session.execute(boundStatement.bind(
                UUID.fromString("2cc9ccb7-6221-4ccb-8387-f22b6a1b354d"),
                UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"),
                "La Petite Tonkinoise",
                "Bye Bye Blackbird",
                "Joséphine Baker again"));

    }


    public void querySchema() {
        ResultSet results = session.execute("SELECT * FROM simplex.playlists " +
                "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");
        iterate(results);

        Select select = QueryBuilder.select().all().from("simplex", "playlists");
        results = session.execute(select);
        iterate(results);

        select = QueryBuilder.select().column("title").column("album").column("artist").from("simplex", "playlists");
        results = session.execute(select);
        iterate(results);
    }

    private void iterate(ResultSet results) {
        System.out.println(String.format("%-30s\t%-20s\t%-20s\n%s", "title", "album", "artist",
                "-------------------------------+-----------------------+--------------------"));
        for (Row row : results) {
            System.out.println(String.format("%-30s\t%-20s\t%-20s", row.getString("title"),
                    row.getString("album"), row.getString("artist")));
        }
        System.out.println();
    }


    public static void main(String[] args) throws Exception {
        // try-with-resources - closes resources automatically.
        try (
                SimpleClient client = new SimpleClient();
        ) {
            client.connect("127.0.0.1");
            client.createSchema();
            client.loadData();
            client.loadDataBound();
            client.querySchema();
        }

    }

    private static void stopCassandra() {
        // Note this looks ugly, but was the way I found that worked for stopping the embedded server instance.  Calling
        // this deprecated method didn't work:  EmbeddedCassandraServerHelper.stopEmbeddedCassandra();
        try {
            EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
            ObjectName mxbeanName = new ObjectName("org.apache.cassandra.db:type=StorageService");
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.invoke(mxbeanName, "stopDaemon", null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
