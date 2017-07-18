package de.malkusch.amazon.ecs;

import com.ECS.client.jax.*;
import com.google.common.util.concurrent.RateLimiter;
import lombok.experimental.Delegate;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;

public class ThrottledAWSECommerseServicePort implements AWSECommerceServicePortType, BindingProvider {

    private final AWSECommerceServicePortType wrapped;
    @Delegate
    private final BindingProvider provider;
    private final RateLimiter rateLimit;

    public ThrottledAWSECommerseServicePort(AWSECommerceServicePortType portType, long throttle) {
        wrapped = portType;
        provider = (BindingProvider) portType;
        rateLimit = RateLimiter.create(1000. / throttle);
    }

    public void itemSearch(String s, String s1, String s2, String s3, String s4,
                           ItemSearchRequest itemSearchRequest,
                           List<ItemSearchRequest> list,
                           Holder<OperationRequest> holder,
                           Holder<List<Items>> holder1) {
        rateLimit.acquire();
        wrapped.itemSearch(s, s1, s2, s3, s4, itemSearchRequest, list, holder, holder1);
    }

    public void itemLookup(String s, String s1, String s2, String s3, String s4,
                           ItemLookupRequest itemLookupRequest,
                           List<ItemLookupRequest> list,
                           Holder<OperationRequest> holder,
                           Holder<List<Items>> holder1) {
        rateLimit.acquire();
        wrapped.itemLookup(s, s1, s2, s3, s4, itemLookupRequest, list, holder, holder1);
    }

    public void browseNodeLookup(String s, String s1, String s2, String s3, String s4,
                                 BrowseNodeLookupRequest browseNodeLookupRequest,
                                 List<BrowseNodeLookupRequest> list,
                                 Holder<OperationRequest> holder,
                                 Holder<List<BrowseNodes>> holder1) {
        rateLimit.acquire();
        wrapped.browseNodeLookup(s, s1, s2, s3, s4, browseNodeLookupRequest, list, holder, holder1);
    }

    public void similarityLookup(String s, String s1, String s2, String s3, String s4,
                                 SimilarityLookupRequest similarityLookupRequest,
                                 List<SimilarityLookupRequest> list,
                                 Holder<OperationRequest> holder,
                                 Holder<List<Items>> holder1) {
        rateLimit.acquire();
        wrapped.similarityLookup(s, s1, s2, s3, s4, similarityLookupRequest, list, holder, holder1);
    }

    public void cartGet(String s, String s1, String s2, String s3, String s4,
                        CartGetRequest cartGetRequest,
                        List<CartGetRequest> list,
                        Holder<OperationRequest> holder,
                        Holder<List<Cart>> holder1) {
        rateLimit.acquire();
        wrapped.cartGet(s, s1, s2, s3, s4, cartGetRequest, list, holder, holder1);
    }

    public void cartCreate(String s, String s1, String s2, String s3, String s4,
                           CartCreateRequest cartCreateRequest,
                           List<CartCreateRequest> list,
                           Holder<OperationRequest> holder,
                           Holder<List<Cart>> holder1) {
        rateLimit.acquire();
        wrapped.cartCreate(s, s1, s2, s3, s4, cartCreateRequest, list, holder, holder1);
    }

    public void cartAdd(String s, String s1, String s2, String s3, String s4,
                        CartAddRequest cartAddRequest,
                        List<CartAddRequest> list,
                        Holder<OperationRequest> holder,
                        Holder<List<Cart>> holder1) {
        rateLimit.acquire();
        wrapped.cartAdd(s, s1, s2, s3, s4, cartAddRequest, list, holder, holder1);
    }

    public void cartModify(String s, String s1, String s2, String s3, String s4,
                           CartModifyRequest cartModifyRequest,
                           List<CartModifyRequest> list,
                           Holder<OperationRequest> holder,
                           Holder<List<Cart>> holder1) {
        rateLimit.acquire();
        wrapped.cartModify(s, s1, s2, s3, s4, cartModifyRequest, list, holder, holder1);
    }

    public void cartClear(String s, String s1, String s2, String s3, String s4,
                          CartClearRequest cartClearRequest,
                          List<CartClearRequest> list,
                          Holder<OperationRequest> holder,
                          Holder<List<Cart>> holder1) {
        rateLimit.acquire();
        wrapped.cartClear(s, s1, s2, s3, s4, cartClearRequest, list, holder, holder1);
    }
}
