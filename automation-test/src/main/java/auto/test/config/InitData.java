package com.ericsson.ct.cloud.nfvi.config;

import com.ericsson.ct.cloud.nfvi.mapper.AdminMapper;

import auto.test.Admin;
import auto.test.EnvironmentContext;
import auto.test.mapper.HandleAlarmMapper;
import auto.test.mapper.OriginAlarmMapper;
import auto.test.snmp.MultiThreadTrapReceiver;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

@Component
public class InitData {

	Logger logger = LoggerFactory.getLogger(InitData.class);

	@Autowired
    OriginAlarmMapper originAlarmMapper;

	@Autowired
	HandleAlarmMapper handleAlarmMapper;

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	EnvironmentContext context;

    @Autowired
	MultiThreadTrapReceiver multiThreadTrapReceiver;


    @PostConstruct
	public void initHandleAlarmTable(){
    	try{
    		handleAlarmMapper.selectMaxId();
		}catch (Exception e){
    		if(e instanceof BadSqlGrammarException){
    			try{
    				handleAlarmMapper.create();
				}catch (Exception e1){
					logger.error("create handle alarm table error,", e1);
				}
			}
			logger.info("init hand alarm table completed");
		}
	}
	@PostConstruct
	public void init() {
		try {
			originAlarmMapper.selectMaxId();
		} catch (Exception e) {
			if (e instanceof BadSqlGrammarException) {
				try {
					originAlarmMapper.create();
					originAlarmMapper.createAlarmIndex();
				} catch (Exception e1) {
					logger.error("create origin alarm table error,", e1);
				}
				try {
					adminMapper.create();
					DumperOptions options = new DumperOptions();
					options.setDefaultFlowStyle(FlowStyle.BLOCK);
					options.setPrettyFlow(true);
					Yaml yaml = new Yaml();
					Admin admin = new Admin();
					admin.setProcedures(yaml.dump(context.getProcedures()));
					adminMapper.insert(admin);
				} catch (Exception e2) {
					logger.error("create admin table error,", e2);
				}
				logger.info("init database tables completed");
			}
		}
        multiThreadTrapReceiver.run();
	}
}