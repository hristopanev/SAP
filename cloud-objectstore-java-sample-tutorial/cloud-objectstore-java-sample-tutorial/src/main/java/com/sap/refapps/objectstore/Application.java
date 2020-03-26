package com.sap.refapps.objectstore;

import com.sap.refapps.objectstore.config.ObjectStoreContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.initializers(new ObjectStoreContextInitializer()).run(args);
	}

}
