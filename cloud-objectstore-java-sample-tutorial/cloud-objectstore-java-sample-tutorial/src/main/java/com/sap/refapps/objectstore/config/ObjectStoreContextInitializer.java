package com.sap.refapps.objectstore.config;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectStoreContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectStoreContextInitializer.class);

    private static String activeProfile;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final ConfigurableEnvironment applicationEnvironment = applicationContext.getEnvironment();
        final String profileToActive = getActiveProfile();
        // Active profile is set based on the objectore service connected to.
        // (s3/GCS etc..)
        applicationEnvironment.addActiveProfile(profileToActive);
    }

    /**
     * This method is used to return the profile name to activate based on
     * service plans.
     *
     * @return profile name
     */
    public static String getActiveProfile() {
        final String servicePlan = getServicePlan();
        if (servicePlan.equals("s3-standard")) {
            activeProfile = "cloud-aws";
        } else if (servicePlan.equals("gcs-standard")) {
            activeProfile = "cloud-gcp";
        }
        return activeProfile;
    }

    /**
     * This method is used to parse the service plan name from VCAP_SERVICES
     *
     * @return service plan name
     */
    private static String getServicePlan() {
        Optional<String> servicePlan = Optional.empty();
        final String jsonString = System.getenv("VCAP_SERVICES");
        if (jsonString != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonString);
                JsonNode objectstoreNode = root.path("objectstore");

                for (JsonNode node : objectstoreNode) {
                    servicePlan = Optional.of(node.path("plan").asText());
                }

            } catch (IOException e) {
                logger.error("Exception occurred: " + e);
            }
        }
        return servicePlan.get();
    }

}
