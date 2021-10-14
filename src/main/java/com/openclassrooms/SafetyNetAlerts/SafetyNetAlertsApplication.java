package com.openclassrooms.SafetyNetAlerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication {

	private static final Logger LOGGER = LogManager
			.getLogger(SafetyNetAlertsApplication.class.getName());

	public static void main(String[] args) {
		LOGGER.debug("SafetyNet Alerts started");
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

}
