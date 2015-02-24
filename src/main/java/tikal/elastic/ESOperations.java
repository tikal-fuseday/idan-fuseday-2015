package tikal.elastic;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tikal.model.Checkin;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by idanf on 10/07/2014.
 */

@Named
public class ESOperations {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Inject
    ESClient esClient;


    public void index(Checkin checkin) throws IOException {
        IndexResponse response = esClient.getClient().prepareIndex("tikal", "location", UUID.randomUUID().toString())
                .setSource(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("latitude", checkin.getLatitude())
                                .field("longitude", checkin.getLongitude())
                                .field("userId", checkin.getUserId())
                                .field("timestamp", checkin.getTimestamp())
                                .startArray("location").value(checkin.getLongitude()).value(checkin.getLatitude())
                                .endArray()
                                .endObject()
                )
                .execute()
                .actionGet();
    }


    public void bulkIndexing() {

    }

    public List<Object> searchTerm(String searchTerm) {
        SearchResponse response = esClient.getClient().prepareSearch("cellebrite", "website", "my_index")
                .setTypes("my_type", "transaction")
                .setSearchType(SearchType.DEFAULT)
                .setQuery(QueryBuilders.queryString(searchTerm))             // Query
                        // .setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))   // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        return null;
    }
}