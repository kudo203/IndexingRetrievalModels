import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by koosh on 19/5/17.
 */
public class timePass {
    public static void main(String[ ] args){
        Properties properties = new Properties();
        try {
            FileReader reader = new FileReader("conf.properties");
            //noinspection Since15
            properties.load(reader);
            reader.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        try{
            File folder = new File("C:\\Users\\Koosh_20\\Desktop\\AP_DATA\\ap89_collection");
            HttpHost htp = new HttpHost(
                    properties.getProperty("hostName"),
                    Integer.parseInt(properties.getProperty("port")),
                    properties.getProperty("scheme"));
            RestClient client = RestClient.builder(htp).build();

            PerformRequest pr = new PerformRequest(client,folder);
            pr.performBulkRequest();
            client.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }



    }
}
