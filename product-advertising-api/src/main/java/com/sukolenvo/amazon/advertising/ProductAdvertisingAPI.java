package com.sukolenvo.amazon.advertising;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;


import com.amazon.wsdl.AWSECommerceService;
import com.amazon.wsdl.AWSECommerceServicePortType;
import com.google.common.base.Preconditions;
import com.sukolenvo.amazon.advertising.configuration.AmazonProductServiceConfig;
import com.sukolenvo.amazon.advertising.call.BrowseNodeLookupCall;
import com.sukolenvo.amazon.advertising.call.CartAddCall;
import com.sukolenvo.amazon.advertising.call.CartClearCall;
import com.sukolenvo.amazon.advertising.call.CartCreateCall;
import com.sukolenvo.amazon.advertising.call.CartGetCall;
import com.sukolenvo.amazon.advertising.call.CartModifyCall;
import com.sukolenvo.amazon.advertising.call.ItemLookupCall;
import com.sukolenvo.amazon.advertising.call.ItemSeachCall;
import com.sukolenvo.amazon.advertising.call.SimilarityLookupCall;
import com.sukolenvo.amazon.advertising.configuration.MarketplaceLocation;
import com.sukolenvo.amazon.advertising.throttle.ThrottledAWSECommerseServicePort;

/**
 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/Welcome.html
 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/APPNDX_SearchIndexValues.html
 */
public class ProductAdvertisingAPI {

    /**
     * Possible values for the parameter Condition
     */
    static final public class Condition {
        final public static String NEW = "New";
        final public static String USED = "Used";
        final public static String COLLECTIBLE = "Collectible";
        final public static String REFURBISHED = "Refurbished";
        final public static String ALL = "All";
    }

    /**
     * Possible values for the parameter IdType
     */
    static final public class IdType {
        final public static String ASIN = "ASIN";
        final public static String SKU = "SKU";
        final public static String UPC = "UPC";
        final public static String EAN = "EAN";
        final public static String ISBN = "ISBN";
    }

    /**
     * String representation for booleans
     */
    static final public class Boolean {
        final public static String TRUE = "True";
        final public static String FALSE = "False";
    }

    /**
     * Possible values for the parameter MerchantId
     */
    static final public class MerchantId {
        final public static String AMAZON = "Amazon";
    }

    /**
     * Possible values for the parameter MerchantId
     */
    static final public class ResponseGroup {

        /**
         * Possible values for the parameter MerchantId in browseNodeLookupup() requests
         */
        static final public class BrowseNode {
            final public static String BROWSE_NODE_INFO = "BrowseNodeInfo";
            final public static String MOST_GIFTED = "MostGifted";
            final public static String NEW_RELEASES = "NewReleases";
            final public static String MOST_WISHED_FOR = "MostWishedFor";
            final public static String TOP_SELLERS = "TopSellers";
        }

        static final public class Cart {
            final public static String Cart = "Cart";
        }

        final public static String ACCESSORIES = "Accessories";
        final public static String BROWSE_NODES = "BrowseNodes";
        final public static String EDITORTIAL_REVIEW = "EditorialReview";
        final public static String IMAGES = "Images";
        final public static String ITEM_ATTRIBUTES = "ItemAttributes";
        final public static String ITEM_IDS = "ItemIds";
        final public static String LARGE = "Large";
        final public static String MEDIUM = "Medium";
        final public static String OFFER_FULL = "OfferFull";
        final public static String OFFERS = "Offers";
        final public static String PROMOTION_SUMMARY = "PromotionSummary";
        final public static String OFFER_SUMMARY = "OfferSummary";
        final public static String OFFER_LISTING = "OfferListings";
        final public static String RELATED_ITEMS = "RelatedItems";
        final public static String REVIEWS = "Reviews";
        final public static String SALES_RANK = "SalesRank";
        final public static String SIMILARITIES = "Similarities";
        final public static String TRACKS = "Tracks";
        final public static String VARIATION_IMAGES = "VariationImages";
        final public static String VARIATIONS = "Variations";
        final public static String VARIATION_SUMMARY = "VariationSummary";
    }

    private AWSECommerceServicePortType port;
    private AmazonProductServiceConfig config;
    private ItemSeachCall itemSearch = new ItemSeachCall(this);
    private SimilarityLookupCall similarityLookup = new SimilarityLookupCall(this);
    private ItemLookupCall itemLookup = new ItemLookupCall(this);
    private BrowseNodeLookupCall browseNodeLookup = new BrowseNodeLookupCall(this);
    private CartCreateCall cartCreate = new CartCreateCall(this);
    private CartGetCall cartGet = new CartGetCall(this);
    private CartClearCall cartClear = new CartClearCall(this);
    private CartModifyCall cartModify = new CartModifyCall(this);
    private CartAddCall cartAdd = new CartAddCall(this);

    public ProductAdvertisingAPI(AmazonProductServiceConfig configuration)
            throws UnsupportedEncodingException {
        Preconditions.checkNotNull(configuration.getAccessKey(), "accessKey may not be null");
        Preconditions.checkNotNull(configuration.getAssociateTag(), "associate tag may not be null");
        Preconditions.checkNotNull(configuration.getSecretKey(), "secret key may not be null");
        this.config = configuration;
        port = createPort(configuration.getLocation());
        if (configuration.getThrottle() > 0) {
            port = new ThrottledAWSECommerseServicePort(port, configuration.getThrottle());
        }
        appendHandler(new SignatureHandler(configuration));
        appendHandler(new SOAPLoggingHandler());
    }

    private AWSECommerceServicePortType createPort(final MarketplaceLocation location) {
        AWSECommerceService service = new AWSECommerceService();
        switch (location) {
            case DEFAULT:
                return service.getAWSECommerceServicePort();
            case CA:
                return service.getAWSECommerceServicePortCA();
            case CN:
                return service.getAWSECommerceServicePortCN();
            case DE:
                return service.getAWSECommerceServicePortDE();
            case ES:
                return service.getAWSECommerceServicePortES();
            case FR:
                return service.getAWSECommerceServicePortFR();
            case IN:
                return service.getAWSECommerceServicePortIN();
            case IT:
                return service.getAWSECommerceServicePortIT();
            case JP:
                return service.getAWSECommerceServicePortJP();
            case US:
                return service.getAWSECommerceServicePortUS();
            case UK:
                return service.getAWSECommerceServicePortUK();
            default:
                throw new UnsupportedOperationException("Unknown location type");
        }
    }

    /**
     * Appends a soap handler
     */
    public void appendHandler(Handler<? extends MessageContext> handler) {
        addHandler(null, handler);
    }

    /**
     * Prepends a soap handler
     */
    public void prependHandler(Handler<? extends MessageContext> handler) {
        addHandler(0, handler);
    }

    /**
     * Prepends a soap handler
     */
    private void addHandler(Integer index, Handler<? extends MessageContext> handler) {
        Binding binding = ((BindingProvider) port).getBinding();
        @SuppressWarnings("rawtypes")
        List<Handler> handlerList = binding.getHandlerChain();
        if (index == null) {
            handlerList.add(handler);

        } else {
            handlerList.add(index, handler);

        }
        binding.setHandlerChain(handlerList);
    }

    /**
     * Returns the generated SOAP proxy of wsimport
     */
    public AWSECommerceServicePortType getPort() {
        return port;
    }

    /**
     * Returns the ApiCall object for add an item into an existing remote shopping cart.
     * <p>
     * <code>
     * CartAddRequest request = api.getCartAdd().buildRequest(existingCart);
     * request.setItems(new CartAddRequest.Items());
     * CartAddRequest.Items.Item item = new CartAddRequest.Items.Item();
     * item.setASIN("3831019592");
     * item.setQuantity(BigInteger.valueOf(1));
     * request.getItems().getItem().add(item);
     * Cart updatedCart = api.getCartAdd().call(request);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/CartAdd.html
     */
    public CartAddCall getCartAdd() {
        return cartAdd;
    }

    /**
     * That's just not working and I have no idea why.
     * AWS returns empty response - please consider using REST API as that is showing perfect
     * results on tests.
     *
     */
    public CartModifyCall getCartModify() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the ApiCall object for clearing an existing remote shopping cart.
     * <p>
     * Clearing means removing all items from a shopping cart. Amazon doesn't let
     * you delete a shopping cart. A shopping cart will be deleted automatically after
     * its life span of 7 days.
     * <p>
     * <code>
     * Cart updatedCart = api.getCartClear().call(existingCart);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/CartClear.html
     */
    public CartClearCall getCartClear() {
        return cartClear;
    }

    /**
     * Returns the ApiCall object for getting an existing remote shopping cart.
     * <p>
     * Because the contents of a cart can change for different reasons, such as availability,
     * you should not keep a copy of a cart locally. Instead, use CartGet to retrieve the items
     * in a remote shopping cart.
     * <p>
     * <code>
     * Cart updatedCart = api.getCartGet().call(existingCart);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/CartGet.html
     */
    public CartGetCall getCartGet() {
        return cartGet;
    }

    /**
     * Returns the ApiCall object for creating a remote shopping cart.
     * <p>
     * As required by Amazon, you can't create an empty shopping cart.
     * <p>
     * <code>
     * CartCreateRequest request = new CartCreateRequest();
     * CartCreateRequest.Items items = new CartCreateRequest.Items();
     * request.setItems(items);
     * Item item = new Item();
     * item.setASIN("383102037X");
     * item.setQuantity(BigInteger.valueOf(1));
     * items.getItem().add(item);
     * Cart cart = api.getCartCreate().call(request);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/CartCreate.html
     */
    public CartCreateCall getCartCreate() {
        return cartCreate;
    }

    /**
     * Returns the ApiCall object for doing a browse node lookup.
     * <p>
     * Amazon is structured in a hierarchy of browse nodes.
     * <p>
     * <code>
     * BrowseNodeLookupRequest request = new BrowseNodeLookupRequest();
     * request.getBrowseNodeId().add("78689031");
     * BrowseNodes nodes = api.getBrowseNodeLookup().call(request);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/BrowseNodeLookup.html
     */
    public BrowseNodeLookupCall getBrowseNodeLookup() {
        return browseNodeLookup;
    }

    /**
     * Returns the ApiCall object for doing an item lookup.
     * <p>
     * <code>
     * ItemLookupRequest request = new ItemLookupRequest();
     * request.getItemId().add("383102037X");
     * Items items = api.getItemLookup().call(request);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/ItemLookup.html
     */
    public ItemLookupCall getItemLookup() {
        return itemLookup;
    }

    /**
     * Returns the ApiCall object for finding similar items.
     * <p>
     * Similarity is a measurement of similar items purchased, that is, customers who bought X
     * also bought Y and Z. It is not a measure, for example, of items viewed, that is,
     * customers who viewed X also viewed Y and Z.
     * <p>
     * <code>
     * SimilarityLookupRequest request = new SimilarityLookupRequest();
     * request.getItemId().add("383102037X");
     * Items items = api.getSimilarityLookup().call(request);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/SimilarityLookup.html
     */
    public SimilarityLookupCall getSimilarityLookup() {
        return similarityLookup;
    }

    /**
     * Returns the ApiCall object for searching items
     * <p>
     * <code>
     * ItemSearchRequest request = new ItemSearchRequest();
     * request.setSearchIndex("Books");
     * request.setKeywords("Star Wars");
     * Items items = api.getItemSearch().call(request);
     * </code>
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/ItemSearch.html
     */
    public ItemSeachCall getItemSearch() {
        return itemSearch;
    }

    public String getAssociateTag() {
        return config.getAssociateTag();
    }

    public String getAccessKey() {
        return config.getAccessKey();
    }
}
