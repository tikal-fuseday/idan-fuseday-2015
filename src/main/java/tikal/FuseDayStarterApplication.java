package tikal;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;import java.lang.String;

@SpringBootApplication
@ComponentScan
@ImportResource("context.xml")
public class FuseDayStarterApplication {

    @Autowired
    RabbitTemplate template;

    @PostConstruct
    public  void init() {

        ConnectionFactory connectionFactory = template.getConnectionFactory();
        CachingConnectionFactory cachingConnectionFactory = (CachingConnectionFactory)connectionFactory;
        cachingConnectionFactory.setHost("192.168.1.53");
    }


    public void sendMessage(String message){
        //logger.info("Message Received: " + message);
        template.convertAndSend(message);
    }



    public static void main(String[] args) {
        SpringApplication.run(FuseDayStarterApplication.class, args);
    }
}
