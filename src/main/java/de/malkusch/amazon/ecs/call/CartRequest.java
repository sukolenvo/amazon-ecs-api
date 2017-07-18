package de.malkusch.amazon.ecs.call;

public interface CartRequest {

	String setHMAC(String hmac);

	String setCartId(String cartId);

}
