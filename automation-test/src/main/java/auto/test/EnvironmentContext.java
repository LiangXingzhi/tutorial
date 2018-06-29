package auto.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 *
 */
@ConfigurationProperties(prefix = "nfvi")
@Component
public class EnvironmentContext {

	private String driver;

}
