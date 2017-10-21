package com.sukolenvo.amazon.advertising.test;

import java.io.IOException;
import java.util.Properties;

import com.sukolenvo.amazon.advertising.configuration.AmazonProductServiceConfig;
import com.sukolenvo.amazon.advertising.ProductAdvertisingAPI;
import com.sukolenvo.amazon.advertising.configuration.MarketplaceLocation;
import com.sukolenvo.amazon.advertising.configuration.PropertiesConfiguration;

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
