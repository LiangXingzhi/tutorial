package auto.test.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InitData {

	Logger logger = LoggerFactory.getLogger(InitData.class);

	@PostConstruct
	public void init() {
	
	}
}