package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.amazon.wsdl.Cart;
import com.amazon.wsdl.CartAdd;
import com.amazon.wsdl.CartAddRequest;
import com.amazon.wsdl.OperationRequest;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public class CartAddCall extends CartCall<CartAdd, CartAddRequest> {

	public CartAddCall(ProductAdvertisingAPI api) {
		super(api, CartAdd.class, CartAddRequest.class);
	}

	@Override
	protected void call(CartAdd call,
						Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartAdd(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
