package com.sukolenvo.amazon.advertising.configuration;

import java.util.Properties;

/**
 * Provides configuration by Properties
 */
public class PropertiesConfiguration {

	private Properties properties;

	public PropertiesConfiguration(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Reads amazon.accessKey
	 */
	public String getAccessKey() {
		return properties.getProperty("amazon.accessKey");
	}

	/**
	 * Reads amazon.secretKey
	 */
	public String getSecretKey() {
		return properties.getProperty("amazon.secretKey");
	}

	/**
	 * Reads amazon.associateTag
	 */
	public String getAssociateTag() {
		return properties.getProperty("amazon.associateTag");
	}

}
