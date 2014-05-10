package com.stevesouza.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.assertThat;

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
    private static final String json =
            "{\"user\":\"joel\"," +
             "\"age\":\"26\"," +
             "\"career\":\"artist\"}";
    private static final String INDEX = "testindex";
    private static final String DOC_TYPE = "document_type_or_name";

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

        // note unless the node is closed the same instance will run for each test and so data added in tests is incremental.
        // also note our client application is a node of the cluster
        node = NodeBuilder.nodeBuilder().local(true).settings(settings).node();
        client = node.client();
    }

    @Test
    public void testIndexDoc_withExplicitSource() {
        IndexResponse response = client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "steve", "age", 25, "career", "software")
                .execute()
                .actionGet();  // similar to Future.get - which waits for response.
        displayIndexResponse(response);
        GetResponse getResponse = getDocument(response.getId());
        displayGetResponse(getResponse);
    }

    @Test
    public void testIndexDoc_withJson() {
        IndexResponse response = indexDocument();
        displayIndexResponse(response);
        // note for 'get' we don't have a time delay before object is available (unlike a query)
        GetResponse getResponse = getDocument(response.getId());
        displayGetResponse(getResponse);
    }

    @Test
    public void testIndexDoc_withExplicitId()  {
        String ID = "1";
        IndexResponse response = client.prepareIndex()
                .setIndex(INDEX)
                .setType(DOC_TYPE)
                .setId(ID)
                .setSource(json)
                .execute()
                .actionGet();  // similar to Future.get - which waits for response.

        displayIndexResponse(response);
        GetResponse getResponse = getDocument(ID);
        displayGetResponse(getResponse);
        assertThat(getResponse.getId()).isEqualTo(ID);
    }

    @Test
    public void testMatchAllQuery() throws InterruptedException {
        IndexResponse response = indexDocument();
        displayIndexResponse(response);
        // note when a document is indexed it can take a configurable amount of time before
        // it shows up to any queries.  I think this is by default 1 second which is why the following
        // sleep is in the code (to ensure the object exists).  The 'get' method of getting
        // a document by id doesn't have this problem.
        Thread.sleep(2000);

        // The following is the match_all query
        SearchResponse searchResponse =  client.prepareSearch().execute().actionGet();
        displaySearchResponse(searchResponse);
        assertThat(searchResponse.getHits().totalHits()).isEqualTo(1);
    }

    @After
    public void tearDown() throws Exception {
        client.close();
        node.close(); // if you don't close node then data stays even if you close client.
    }

    private IndexResponse indexDocument() {
        return client.prepareIndex(INDEX, DOC_TYPE)
                .setSource(json)
                .execute()
                .actionGet();
    }

    private GetResponse getDocument(String id) {
        // note for 'get' we don't have a time delay before object is available (unlike a query)
        return client.prepareGet()
                .setIndex(INDEX)
                .setType(DOC_TYPE)
                .setId(id)
                .execute()
                .actionGet();
    }

    private void displayIndexResponse(IndexResponse response) {
        System.out.println("* indexResponse:");
        System.out.println("   id="+response.getId()+", version="+response.getVersion()+", type="+response.getType()+", index="+response.getType());
        System.out.println("   returns true if document was created, false if updated: "+response.isCreated());
    }

    private void displayGetResponse(GetResponse getResponse) {
        System.out.println("* getResponse: ");
        System.out.println("   getSourceAsMap()="+getResponse.getSourceAsMap());
        System.out.println("   getSource()="+getResponse.getSource());
        System.out.println("   getSourceAsString()="+getResponse.getSourceAsString());
    }

    private void displaySearchResponse(SearchResponse searchResponse) {
        // note toString builds the full response as html. Also good to look at for code that uses XContentBuilder to build json version of results.
        System.out.println("* searchResponse.toString() (returns data as pretty print json):"+searchResponse.toString());
        System.out.println("   hits=" + searchResponse.getHits().totalHits());
        System.out.println("   time in ms.=" + searchResponse.getTookInMillis());

        for (SearchHit doc : searchResponse.getHits().getHits()) {
            System.out.println("   source as map=" + doc.sourceAsMap());
            System.out.println("   shardId=" + doc.shard().shardId());
        }
    }
}
