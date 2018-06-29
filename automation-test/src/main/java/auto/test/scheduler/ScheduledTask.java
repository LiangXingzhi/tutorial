package auto.test.scheduler;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

	static final String POLICY_CONTINUE = "continue";

	@Value("${webdriver.gecko.driver}")
	private String geckDriverPath;

	@Value("${webdriver.chrome.driver}")
	private String chromeDriverPath;

	@Value("${webdriver.selected}")
	private String selectedDriver;

	@Value("${webdriver.timeout}")
	private int timeout;

	@Value("${webdriver.wait}")
	private int wait;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedDelayString = "${jobs.fixedDelay}")
	public void getTask1() {
		System.setProperty("webdriver.gecko.driver", geckDriverPath);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		logger.info("task end.");
	}

}