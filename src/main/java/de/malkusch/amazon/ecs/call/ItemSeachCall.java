package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;


import com.amazon.wsdl.ItemSearch;
import com.amazon.wsdl.ItemSearchRequest;
import com.amazon.wsdl.Items;
import com.amazon.wsdl.OperationRequest;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public class ItemSeachCall extends ItemCall<ItemSearch, ItemSearchRequest> {

	public ItemSeachCall(ProductAdvertisingAPI api) {
		super(api, ItemSearch.class);
	}

	@Override
	protected void call(ItemSearch call,
			Holder<OperationRequest> operationRequest,
			Holder<List<Items>> result) {

		api.getPort().itemSearch(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
