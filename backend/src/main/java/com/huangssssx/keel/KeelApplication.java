package com.huangssssx.keel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class KeelApplication {

	private static final Logger logger = LoggerFactory.getLogger(KeelApplication.class);

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(KeelApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void printPort() {
		String port = environment.getProperty("server.port");
		logger.info("应用程序已启动，监听端口: {}", port);
	}

}
