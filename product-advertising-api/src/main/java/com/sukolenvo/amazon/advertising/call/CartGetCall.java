package com.sukolenvo.amazon.advertising.call;


import com.amazon.wsdl.Cart;
import com.amazon.wsdl.CartGet;
import com.amazon.wsdl.CartGetRequest;
import com.amazon.wsdl.OperationRequest;
import com.sukolenvo.amazon.advertising.ProductAdvertisingAPI;
import com.sukolenvo.amazon.advertising.exception.RequestException;

import javax.xml.ws.Holder;
import java.util.List;

public class CartGetCall extends CartCall<CartGet, CartGetRequest> {

	public CartGetCall(ProductAdvertisingAPI api) {
		super(api, CartGet.class, CartGetRequest.class);
	}

	@Override
	protected void call(CartGet call,
						Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartGet(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

	public Cart call(Cart cart) throws RequestException {
		return call(buildRequest(cart));
	}

}
