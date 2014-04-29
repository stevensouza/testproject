package com.stevesouza.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by stevesouza on 4/23/14.
 *
 * elasticsearch java api: http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/
 *
 * some sample code: http://java.dzone.com/articles/elasticsearch-java-api
 *
 * annotations: https://github.com/aloiscochard/elasticsearch-osem
 */
public class MyElasticSearchTest {
    private Client client;
    private Node node;
    @Before
    public void setUp() throws Exception {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("node.http.enabled", true)
                .put("path.logs","target/elasticsearch/logs")
                .put("path.data","target/elasticsearch/data")
                .put("gateway.type", "none")
                .put("index.store.type", "memory")
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1).build();

        node = NodeBuilder.nodeBuilder().local(true).settings(settings).node();
        client = node.client();
    }


    @Test
    public void testAddDoc1() {
        String json =
                "{\"user\":\"kimchy\"," +
                        "\"postDate\":\"2013-01-30\"," +
                        "\"message\":\"trying out Elastic Search\"}";

        IndexResponse response = client.prepareIndex("testindex", "article")
                .setSource("user", "steve", "age", 25, "career", "software")
                .execute()
                .actionGet();  // similar to Future.get - which waits for response.
        System.out.println("id="+response.getId()+", version="+response.getVersion());



        GetResponse getResponse = client.prepareGet("testindex", "article", response.getId())
                .execute()
                .actionGet();
        System.out.println(getResponse.getSourceAsMap());

        System.out.println(getResponse.getFields());
    }

    @Test
    public void testAddDoc2() {
        String json =
                "{\"user\":\"joel\"," +
                 "\"age\":\"26\"," +
                 "\"career\":\"artist\"}";

        IndexResponse response = client.prepareIndex("testindex", "article")
                .setSource(json)
                .execute()
                .actionGet();  // similar to Future.get - which waits for response.
        System.out.println("id="+response.getId()+", version="+response.getVersion());


    }
    @After
    public void tearDown() throws Exception {

        client.close();

    }
}
