package tikal;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by dor on 2/24/15.
 */
@Configuration
@EnableAutoConfiguration
@ImportResource(value = "context.xml")
public class ConfigurationUtil {

}
