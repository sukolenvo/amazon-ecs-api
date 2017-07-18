package de.malkusch.amazon.ecs.test;

import java.io.IOException;
import java.util.Properties;

import com.ECS.client.jax.AWSECommerceService;

import de.malkusch.amazon.ecs.ProductAdvertisingAPI;
import de.malkusch.amazon.ecs.configuration.AmazonProductServiceConfig;
import de.malkusch.amazon.ecs.configuration.MarketplaceLocation;
import de.malkusch.amazon.ecs.configuration.PropertiesConfiguration;

abstract public class AbstractTest {

    protected PropertiesConfiguration configuration;

    protected ProductAdvertisingAPI api;

    public AbstractTest() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("amazon.properties"));
        configuration = new PropertiesConfiguration(properties);

        api = new ProductAdvertisingAPI(AmazonProductServiceConfig.builder()
                                                                  .accessKey(configuration.getAccessKey())
                                                                  .associateTag(configuration.getAssociateTag())
                                                                  .secretKey(configuration.getSecretKey())
                                                                  .location(MarketplaceLocation.US)
                                                                  .throttle(2000).build());
    }

}
