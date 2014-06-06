package com.stevesouza.elasticsearch;

import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by stevesouza on 4/23/14.
 * todo: try to save a pojo to elasticsearch
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

        // 1) note unless the node is closed the same instance will run for each test and so data added in tests is incremental.
        // also note our client application is a node of the cluster
        // 2) by default a node will store data in shards!  If you don't want this you have to be explicit
        //    by stating the node is a client: Node node = nodeBuilder().client(true).node();
        //    alternatively you could set *.data(false).*
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
         public void testMatch_AllQuery() throws InterruptedException {
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

    @Test
    public void testMatch_AllQuery2() throws InterruptedException {
        IndexResponse response = indexDocument();
        displayIndexResponse(response);
        // note when a document is indexed it can take a configurable amount of time before
        // it shows up to any queries.  I think this is by default 1 second which is why the following
        // sleep is in the code (to ensure the object exists).  The 'get' method of getting
        // a document by id doesn't have this problem.
        Thread.sleep(2000);

        // The following is the match_all query
        SearchResponse searchResponse =  client.prepareSearch().setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        displaySearchResponse(searchResponse);
        assertThat(searchResponse.getHits().totalHits()).isEqualTo(1);
    }

    @Test
    public void testMatch_AllQuery3_WithWrappedQuery() throws InterruptedException {
        IndexResponse response = indexDocument();
        displayIndexResponse(response);
        Thread.sleep(2000);

        // note there is no "query" outer wrappe below.  The following can take any json formatted query though.  handy!
        SearchResponse searchResponse =  client.prepareSearch()
                .setQuery(QueryBuilders.wrapperQuery("{\"match_all\": {}}"))
                .execute()
                .actionGet();
        displaySearchResponse(searchResponse);
        assertThat(searchResponse.getHits().totalHits()).isEqualTo(1);
    }

    /** some notes on filters:
     * Example: setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.termFilter("multi", "test")))
     *
     The filter (which should be cached) is executed first to find the documents for which the query is run on. Basically,
     the query should only be executed on a subset of the documents.

     In most cases you want to prefilter since querying is more expensive. However, sometimes filter can be expensive
     (geo, scripted) so it might be better to post filter. Also, facets  are calculated on the documents returned post filter.
     If you need to facet on the documents before any filtering, post filters are the way to go. Of course, you can combine the two types of filters.

     Also this could be used which responds with a constant score for all documents (based on the document boost if it exists)
     QueryBuilders.constantScoreQuery(FilterBuilder filterBuilder)

     * @throws InterruptedException
     */
    @Test
    public void testMatch_AllFilter() throws InterruptedException {
        IndexResponse response = indexDocument();

        displayIndexResponse(response);
        // note when a document is indexed it can take a configurable amount of time before
        // it shows up to any queries.  I think this is by default 1 second which is why the following
        // sleep is in the code (to ensure the object exists).  The 'get' method of getting
        // a document by id doesn't have this problem.
        Thread.sleep(2000);
        // I think filtered queries are the fast way to run a query by filtering first.  The filter is run and then the query is applied to the results.
        // In this example it doesn't make much sense as both return all matches, however if the matchAllFilter was changed to something that returns a subset of
        // records no scoring would be used.  The SearchRequestBuilder used to have a setFilter(..) but no longer does so this is a way to set the filter to run first
        // though ugly.
        // QueryBuilder qb = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.matchAllFilter());

        // constantScoreQueries also convert a query to a filter.  i.e. because no scoring is done that part doesn't need to be run.
        // Note you can add a constant boost(int) for all documents.  That doesn't help below,
        // but might if you did it as part of a union.  Note you can also pass it a query so it is more flexible than the above approach.
        QueryBuilder qb  = QueryBuilders.constantScoreQuery(FilterBuilders.matchAllFilter());
        // The following is the match_all query
        SearchResponse searchResponse =  client.prepareSearch().setQuery(qb).execute().actionGet();
        FilterBuilders.matchAllFilter();
        displaySearchResponse(searchResponse);
        assertThat(searchResponse.getHits().totalHits()).isEqualTo(1);
        // Note some filters can have caching disabled.  For example when you don't run them often enough to benefit.
        // FilterBuilders.boolFilter().cache(false);
    }

    @Test
    public void testIndexStats() throws InterruptedException {
        IndexResponse response = indexDocument();
        response = indexDocument();
        response = indexDocument();

        GetResponse getResponse = getDocument(response.getId());
        getResponse = getDocument(response.getId());
        getResponse = getDocument(response.getId());
        getResponse = getDocument(response.getId());

        SearchResponse searchResponse =  client.prepareSearch().execute().actionGet();
        searchResponse =  client.prepareSearch().execute().actionGet();
        searchResponse =  client.prepareSearch().execute().actionGet();
        searchResponse =  client.prepareSearch().execute().actionGet();
        searchResponse =  client.prepareSearch().execute().actionGet();

        IndicesStatsResponse statsResponse = client.admin().indices().prepareStats(INDEX).all().execute().actionGet();
        System.out.println("* IndicesStatsResponse: "+statsResponse);
    }

    @Test
    public void testBulkInsertAndMultiMatchQuery() throws IOException, InterruptedException {

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "steve", "age", 52, "career", "software"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "john", "age", 52, "career", "joel"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "joel", "age", 53, "career", "artist"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "jean", "age", 57, "career", "engineer"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "mom", "age", 77, "career", "retired"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "dad", "age", 78, "career", "retired"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "jeff", "age", 60, "career", "musician"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "mick", "age", 60, "career", "musician"));
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                .setSource("user", "keith", "age", 60, "career", "musician"));
        // showing how to add using the json builder.  note i think there is also a way to set the index and
        // type only once.
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                        .setSource(jsonBuilder()
                                        .startObject()
                                        .field("user", "jim")
                                        .field("age", 55)
                                        .field("career", "musician")
                                        .endObject()
                        )
        );
        bulkRequest.add(client.prepareIndex(INDEX, DOC_TYPE)
                        .setSource(jsonBuilder()
                                        .startObject()
                                        .field("user", "william")
                                        .field("age", 55)
                                        .field("career", "musician")
                                        .endObject()
                        )
        );

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        // Sleep to let data stick.
        Thread.sleep(2000);
        // use multi_match to search the data for any occurences in the user field as well
        // as a field that starts with car* (career).  Note the search string could
        // be more than one term.
        String SEARCH_STR = "joel";
        String WILDCARDED_FIELD_IS_ALLOWED = "car*";
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX)
                .setTypes(DOC_TYPE)
                .setQuery(QueryBuilders.multiMatchQuery(SEARCH_STR,DOC_TYPE+".user", WILDCARDED_FIELD_IS_ALLOWED));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        displaySearchResponse(response);
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
        System.out.println("* searchResponse.toString() (returns data as pretty print json):" + searchResponse.toString());
        System.out.println("   hits=" + searchResponse.getHits().totalHits());
        System.out.println("   time in ms.=" + searchResponse.getTookInMillis());

        for (SearchHit doc : searchResponse.getHits().getHits()) {
            System.out.println("   source as map=" + doc.sourceAsMap());
            System.out.println("   shardId=" + doc.shard().shardId());
        }
    }
}
