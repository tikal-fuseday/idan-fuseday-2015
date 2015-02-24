package tikal.messaging;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import tikal.elastic.ESOperations;
import tikal.model.Checkin;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class Consumer {

    private static final Logger logger=Logger.getLogger(Consumer.class);

    Gson gson = new Gson();

    @Inject
    ESOperations esOperations;



    public void listen(String foo) {
        System.out.println(foo);
        Checkin checkin = gson.fromJson(foo, Checkin.class);
        checkin.setTimestamp(System.currentTimeMillis());
        try {
            esOperations.index(checkin);
        } catch (IOException e) {
            logger.error("error indexing",e);

        }
    }
}