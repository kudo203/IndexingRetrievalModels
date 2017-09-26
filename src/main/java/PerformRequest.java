
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by koosh on 21/5/17.
 */
public class PerformRequest {

    private RestClient restClient;
    private File folder;

    public PerformRequest(RestClient r,File fold){
        this.restClient = r;
        this.folder = fold;
    }

    public void performBulkRequest() throws IOException{
        File[] listOfFiles = this.folder.listFiles();
        for(File file:listOfFiles){
            Document doc = Jsoup.parse(new
                            File(file.toString()),
                    "utf-8");
            requestSingleFile(doc);
        }

    }

    public void requestSingleFile(Document file)throws IOException{
        Elements docs = file.getElementsByTag("DOC");
        beanClass reuseBean = new beanClass();
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder bulkRequestBody = new StringBuilder();
        for(Element doc : docs){
            String docID = doc.getElementsByTag("DOCNO").text();

            String actionMetaData = String.format(
                    "{ \"index\" : { \"_index\" : \"%s\", \"_type\" : \"%s\",\"_id\" : \"%s\" } }%n",
                    "ap_dataset", "IR", docID);

            String tex = doc.getElementsByTag("TEXT").text();

            reuseBean.setID(docID);
            reuseBean.setText(tex);
            reuseBean.setLen(tex.split(" ").length);

            bulkRequestBody.append(actionMetaData);
            String json = mapper.writeValueAsString(reuseBean);
            bulkRequestBody.append(json);
            bulkRequestBody.append("\n");
        }

        HttpEntity entity = new NStringEntity(bulkRequestBody.toString(), ContentType.APPLICATION_JSON);
        Response response = this.restClient.performRequest("POST","/ap_dataset/ap_dataset/_bulk", Collections.<String, String>emptyMap(), entity);
        System.out.println(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    }
}
