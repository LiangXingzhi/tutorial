package auto.test.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import auto.test.Admin;
import auto.test.alarm.HandleAlarmBean;
import auto.test.mapper.AdminMapper;
import auto.test.mapper.HandleAlarmMapper;
import auto.test.selenium.ActionStep;
import auto.test.selenium.Browser;
import auto.test.selenium.Procedure;

@Component
public class ScheduledTask {

	Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

	static final String POLICY_CONTINUE = "continue";

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	HandleAlarmMapper aggregatedAlarmMapper;

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

	@Value("${nfvi.alarm.fixedDelay}")
	private int fixedDelay;

	@Value("${nfvi.alarm.ignoreEarlier}")
	private int ignoreEarlier;

	@Value("${nfvi.alarm.maxRetry}")
	private int maxRetry;

	@Value("${nfvi.alarm.stepMock}")
	private String stepMock;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedDelayString = "${jobs.fixedDelay}")
	public void getTask1() {
		System.setProperty("webdriver.gecko.driver", geckDriverPath);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		logger.info("task start...");
		Date now = new Date();
		Date startDate = DateUtils.addSeconds(now, -ignoreEarlier);
		Date endDate = DateUtils.addSeconds(now, -fixedDelay);
		List<HandleAlarmBean> handleAlarmBeanList = aggregatedAlarmMapper.selectAggregatedAlarmBetween(startDate,
				endDate, maxRetry);
		if (CollectionUtils.isNotEmpty(handleAlarmBeanList)) {
			HandleAlarmBean alarmBean = handleAlarmBeanList.get(0);
			String alarm = alarmBean.getType();
			List<Admin> admins = adminMapper.selectAll();
			String procedures = null;
			if (CollectionUtils.isNotEmpty(admins)) {
				procedures = admins.get(0).getProcedures();
				Yaml yaml = new Yaml();
				List<Procedure> procedureList = yaml.load(procedures);
				Optional<Procedure> procedureToExecuteOptional = Optional.ofNullable(procedureList.stream()
						.filter(procedure -> procedure.hasInterestIn(alarm)).findAny().orElse(null));
				if (procedureToExecuteOptional.isPresent()) {
					Procedure procedureToExecute = procedureToExecuteOptional.get();
					logger.info("procedure [" + procedureToExecute.getName() + "] start...");
					boolean procedureResult = false;
					Optional<List<ActionStep>> optional = Optional.ofNullable(procedureToExecute.getActionSteps());
					if (alarmBean.getRetry_time() == 0) {

					}
					alarmBean.setRetry_time(alarmBean.getRetry_time() + 1);
					alarmBean.setStart_time(new Date());
					// Init Start Success Fail
					alarmBean.setStatus("Start");
					aggregatedAlarmMapper.updateStatus(alarmBean);
					if (optional.isPresent() && CollectionUtils.isNotEmpty(optional.get())) {
						Browser browser = null;
						try {
							browser = new Browser(selectedDriver, wait, timeout);
							if (stepMock != null && "true".equals(stepMock)) {
								procedureResult = true;
							} else if (stepMock != null && "false".equals(stepMock)) {
								procedureResult = false;
							} else {
								for (ActionStep step : optional.get()) {
									boolean result = false;
									try {
										result = browser.execute(step);
									} catch (Exception e) {
										if (POLICY_CONTINUE.equals(step.getFailPolicy())) {
											result = true;
										} else {
											throw e;
										}
									}
									if (!result) {
										break;
									}
								}
								procedureResult = true;
							}
						} catch (Exception e) {
							procedureResult = false;
							logger.error("procedure [" + procedureToExecute.getName() + "] error", e);
						} finally {
							try {
								if (browser != null) {
									browser.close();
								}
							} catch (Exception e) {
								logger.error("close browser error, ", e);
							}
						}
						if (browser != null) {
							alarmBean.setWf_instance(browser.getContext().get("eval_instanceName"));
						}

						alarmBean.setStatus(procedureResult ? "Success" : "Fail");
					}
					alarmBean.setEnd_time(new Date());
					aggregatedAlarmMapper.updateStatus(alarmBean);
					logger.info("procedure [" + procedureToExecute.getName() + "] end, result is " + procedureResult);
				} else {
					logger.info("no procedure found for alarm[" + alarm + "]");
				}
			}

		}

		logger.info("task end.");
	}

}