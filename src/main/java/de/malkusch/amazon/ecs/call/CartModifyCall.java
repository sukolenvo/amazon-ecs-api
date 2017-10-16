package de.malkusch.amazon.ecs.call;

import com.amazon.wsdl.Cart;
import com.amazon.wsdl.CartModify;
import com.amazon.wsdl.CartModifyRequest;
import com.amazon.wsdl.OperationRequest;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

import javax.xml.ws.Holder;
import java.util.List;

public class CartModifyCall extends CartCall<CartModify, CartModifyRequest> {

	public CartModifyCall(ProductAdvertisingAPI api) {
		super(api, CartModify.class, CartModifyRequest.class);
	}

	@Override
	protected void call(CartModify call,
						Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartModify(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
