package de.malkusch.amazon.ecs.test;

import com.amazon.wsdl.*;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;
import de.malkusch.amazon.ecs.exception.RequestException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class TestAPI extends AbstractTest {

    public TestAPI() throws IOException {
        super();
    }

    @Test
    public void testItemSearch() throws RequestException {
        ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
        itemSearchRequest.setSearchIndex("Books");
        itemSearchRequest.setKeywords("Star Wars");

        Items items = api.getItemSearch().call(itemSearchRequest);

        assertTrue(items.getItem().size() > 0);
    }

    @Test(expected = RequestException.class)
    public void testFailItemSearch() throws RequestException {
        ItemSearchRequest itemSearchRequest = new ItemSearchRequest();

        api.getItemSearch().call(itemSearchRequest);
    }

    @Test
    public void testItemLookup() throws RequestException {
        ItemLookupRequest request = new ItemLookupRequest();
        request.getItemId().add("383102037X");

        Items items = api.getItemLookup().call(request);

        assertTrue(items.getItem().size() == 1);
    }

    @Test(expected = RequestException.class)
    public void testFailItemLookup() throws RequestException {
        ItemLookupRequest request = new ItemLookupRequest();

        api.getItemLookup().call(request);
    }

    @Test(expected = RequestException.class)
    public void testFailBrowseNodeLookup() throws RequestException {
        BrowseNodeLookupRequest request = new BrowseNodeLookupRequest();

        api.getBrowseNodeLookup().call(request);
    }

    @Test
    public void testBrowseNodeLookup() throws RequestException {
        BrowseNodeLookupRequest request = new BrowseNodeLookupRequest();
        request.getBrowseNodeId().add("2619526011");

        BrowseNodes nodes = api.getBrowseNodeLookup().call(request);

        assertTrue(nodes.getBrowseNode().size() == 1);
        assertEquals(request.getBrowseNodeId().get(0), nodes.getBrowseNode()
                .get(0).getBrowseNodeId());
    }

    @Test(expected = RequestException.class)
    public void testFailCartCreate() throws RequestException {
        CartCreateRequest request = new CartCreateRequest();

        api.getCartCreate().call(request);
    }

    @Test
    public void testCartCreate() throws RequestException {
        CartCreateRequest request = new CartCreateRequest();

        CartCreateRequest.Items items = new CartCreateRequest.Items();
        request.setItems(items);

        CartCreateRequest.Items.Item item = new CartCreateRequest.Items.Item();
        items.getItem().add(item);

        item.setASIN("B010S9N6OO");
        item.setQuantity(BigInteger.valueOf(1));

        Cart cart = api.getCartCreate().call(request);

        assertNotNull(cart.getPurchaseURL());
    }

    @Test(expected = RequestException.class)
    public void testFailCartGet() throws RequestException {
        CartGetRequest request = new CartGetRequest();

        api.getCartGet().call(request);
    }

    @Test
    public void testCartGet() throws RequestException {
        Cart createdCart = null;
        {
            CartCreateRequest request = new CartCreateRequest();

            CartCreateRequest.Items items = new CartCreateRequest.Items();
            request.setItems(items);

            CartCreateRequest.Items.Item item = new CartCreateRequest.Items.Item();
            items.getItem().add(item);

            item.setASIN("B010S9N6OO");
            item.setQuantity(BigInteger.valueOf(1));

            createdCart = api.getCartCreate().call(request);
        }

        Cart cart = api.getCartGet().call(createdCart);

        assertEquals(createdCart.getCartId(), cart.getCartId());
        assertTrue(cart.getCartItems().getCartItem().size() == 1);
        assertEquals(createdCart.getCartItems().getCartItem().get(0).getASIN(),
                cart.getCartItems().getCartItem().get(0).getASIN());
    }

    @Test(expected = RequestException.class)
    public void testFailCartModify() throws RequestException {
        CartModifyRequest request = new CartModifyRequest();

        api.getCartModify().call(request);
    }

    @Test
    @Ignore
    public void testCartModify() throws RequestException {
        Cart createdCart = null;
        {
            CartCreateRequest request = new CartCreateRequest();

            CartCreateRequest.Items items = new CartCreateRequest.Items();
            request.setItems(items);

            String[] asins = new String[]{"B01DX3DLPG", "B010S9N6OO", "B072TT78M5", "B06Y19T64R"};
            for (String asin : asins) {
                CartCreateRequest.Items.Item item = new CartCreateRequest.Items.Item();
                item.setASIN(asin);
                item.setQuantity(BigInteger.valueOf(3));
                items.getItem().add(item);

            }

            createdCart = api.getCartCreate().call(request);
        }

        CartModifyRequest request = api.getCartModify().buildRequest(
                createdCart);
        request.setItems(new CartModifyRequest.Items());

        // decrease
        CartModifyRequest.Items.Item decreasedItem = new CartModifyRequest.Items.Item();
        decreasedItem.setCartItemId(createdCart.getCartItems().getCartItem()
                .get(0).getCartItemId());
        decreasedItem.setQuantity(BigInteger.valueOf(2));
        request.getItems().getItem().add(decreasedItem);

        // increase
        CartModifyRequest.Items.Item increasedItem = new CartModifyRequest.Items.Item();
        increasedItem.setCartItemId(createdCart.getCartItems().getCartItem()
                .get(1).getCartItemId());
        increasedItem.setQuantity(BigInteger.valueOf(4));
        request.getItems().getItem().add(increasedItem);

        // remove
        CartModifyRequest.Items.Item removedItem = new CartModifyRequest.Items.Item();
        removedItem.setCartItemId(createdCart.getCartItems().getCartItem()
                .get(2).getCartItemId());
        removedItem.setQuantity(BigInteger.valueOf(0));
        request.getItems().getItem().add(removedItem);

        Cart modifiedCart = api.getCartModify().call(request);

        assertEquals("2", modifiedCart.getCartItems().getCartItem().get(0)
                .getQuantity());
        assertEquals("4", modifiedCart.getCartItems().getCartItem().get(1)
                .getQuantity());
        assertEquals(createdCart.getCartItems().getCartItem().get(3)
                .getCartItemId(), modifiedCart.getCartItems().getCartItem()
                .get(2).getCartItemId());
        assertEquals(createdCart.getCartItems().getCartItem().size() - 1,
                modifiedCart.getCartItems().getCartItem().size());
    }

    @Test(expected = RequestException.class)
    public void testFailCartAdd() throws RequestException {
        CartAddRequest request = new CartAddRequest();

        api.getCartAdd().call(request);
    }

    @Test
    public void testCartAdd() throws RequestException {
        Cart createdCart = createCart();

        CartAddRequest request = api.getCartAdd().buildRequest(createdCart);
        request.setItems(new CartAddRequest.Items());

        CartAddRequest.Items.Item item = new CartAddRequest.Items.Item();
        item.setASIN("B01DX3DLPG");
        item.setQuantity(BigInteger.valueOf(1));
        request.getItems().getItem().add(item);

        Cart addedCart = api.getCartAdd().call(request);

        assertEquals(2, addedCart.getCartItems().getCartItem().size());
        assertEquals("B01DX3DLPG", addedCart.getCartItems().getCartItem()
                .get(0).getASIN());
    }

    private Cart createCart() throws RequestException {
        Cart createdCart;CartCreateRequest request = new CartCreateRequest();

        CartCreateRequest.Items items = new CartCreateRequest.Items();
        request.setItems(items);

        CartCreateRequest.Items.Item item = new CartCreateRequest.Items.Item();
        item.setASIN("B010S9N6OO");
        item.setQuantity(BigInteger.valueOf(3));
        items.getItem().add(item);

        createdCart = api.getCartCreate().call(request);
        return createdCart;
    }

    @Test(expected = RequestException.class)
    public void testFailCartClear() throws RequestException {
        CartClearRequest request = new CartClearRequest();

        api.getCartClear().call(request);
    }

    @Test
    public void testCartClear() throws RequestException {
        Cart createdCart = createCart();

        Cart clearedCart = api.getCartClear().call(createdCart);

        assertNull(clearedCart.getCartItems());
    }

    @Test(expected = RequestException.class)
    public void testFailSimilarityLookup() throws RequestException {
        SimilarityLookupRequest lookup = new SimilarityLookupRequest();

        api.getSimilarityLookup().call(lookup);
    }

    @Test
    public void testSimilarityLookup() throws RequestException {
        SimilarityLookupRequest lookup = new SimilarityLookupRequest();
        lookup.getItemId().add("B010S9N6OO");

        Items items = api.getSimilarityLookup().call(lookup);

        assertTrue(!items.getItem().isEmpty());
    }

}
