package com.sukolenvo.amazon.advertising.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.amazon.wsdl.Cart;
import com.amazon.wsdl.CartClear;
import com.amazon.wsdl.CartClearRequest;
import com.amazon.wsdl.OperationRequest;
import com.sukolenvo.amazon.advertising.ProductAdvertisingAPI;
import com.sukolenvo.amazon.advertising.exception.RequestException;

public class CartClearCall extends CartCall<CartClear, CartClearRequest> {

	public CartClearCall(ProductAdvertisingAPI api) {
		super(api, CartClear.class, CartClearRequest.class);
	}

	@Override
	protected void call(CartClear call,
						Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartClear(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

	public Cart call(Cart cart) throws RequestException {
		return call(buildRequest(cart));
	}

}
