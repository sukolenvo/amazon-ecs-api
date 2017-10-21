package com.sukolenvo.amazon.advertising.configuration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AmazonProductServiceConfig {

    private MarketplaceLocation location = MarketplaceLocation.DEFAULT;
    private String associateTag;
    private String accessKey;
    private String secretKey;
    /**
     * Throttle of requests in milliseconds. 0 for no delay.
     */
    private long throttle;

}
