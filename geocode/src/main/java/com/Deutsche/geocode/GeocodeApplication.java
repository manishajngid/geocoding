package com.Deutsche.geocode;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Deutsche.geocode.controller.GeocodeController;

@SpringBootApplication
public class GeocodeApplication {
	 private transient static final Logger LOG = Logger.getLogger(GeocodeApplication.class) ;
	public static void main(String[] args) {
		SpringApplication.run(GeocodeApplication.class, args);
		LOG.info("Geocode  Application is up now.  " + LOG.getName());
	}
}
