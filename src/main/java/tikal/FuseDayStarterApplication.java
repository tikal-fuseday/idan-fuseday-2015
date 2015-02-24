package tikal;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class FuseDayStarterApplication {
private static final Logger logger = Logger.getLogger(FuseDayStarterApplication.class);
    private RabbitTemplate template;

    public FuseDayStarterApplication() {
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext("context.xml");
        template = ctx.getBean(RabbitTemplate.class);
        ConnectionFactory connectionFactory = template.getConnectionFactory();
        CachingConnectionFactory cachingConnectionFactory = (CachingConnectionFactory)connectionFactory;
        cachingConnectionFactory.setHost("192.168.1.53");
    }

    public static void main(String[] args) {
        SpringApplication.run(FuseDayStarterApplication.class, args);

    }

    public void sendMessage(String message){
        //logger.info("Message Received: " + message);
        template.convertAndSend(message);
    }
}
