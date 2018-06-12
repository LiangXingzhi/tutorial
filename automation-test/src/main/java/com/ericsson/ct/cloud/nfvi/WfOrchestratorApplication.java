package com.ericsson.ct.cloud.nfvi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WfOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WfOrchestratorApplication.class, args);
	}
}
