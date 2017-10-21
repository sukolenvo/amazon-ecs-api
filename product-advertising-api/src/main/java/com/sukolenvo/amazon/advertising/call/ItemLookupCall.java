package com.sukolenvo.amazon.advertising.call;

import java.util.List;

import javax.xml.ws.Holder;


import com.amazon.wsdl.ItemLookup;
import com.amazon.wsdl.ItemLookupRequest;
import com.amazon.wsdl.Items;
import com.amazon.wsdl.OperationRequest;
import com.sukolenvo.amazon.advertising.ProductAdvertisingAPI;

public class ItemLookupCall extends ItemCall<ItemLookup, ItemLookupRequest> {

	public ItemLookupCall(ProductAdvertisingAPI api) {
		super(api, ItemLookup.class);
	}

	@Override
	protected void call(ItemLookup call,
			Holder<OperationRequest> operationRequest,
			Holder<List<Items>> result) {

		api.getPort().itemLookup(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
