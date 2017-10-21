package com.sukolenvo.amazon.advertising.call;

import java.util.List;

import javax.xml.ws.Holder;


import com.amazon.wsdl.BrowseNodeLookup;
import com.amazon.wsdl.BrowseNodeLookupRequest;
import com.amazon.wsdl.BrowseNodes;
import com.amazon.wsdl.OperationRequest;
import com.sukolenvo.amazon.advertising.ProductAdvertisingAPI;

public class BrowseNodeLookupCall extends
		ApiCall<BrowseNodeLookup, BrowseNodeLookupRequest, BrowseNodes> {

	public BrowseNodeLookupCall(ProductAdvertisingAPI api) {
		super(api, BrowseNodeLookup.class);
	}

	@Override
	protected void call(BrowseNodeLookup call,
			Holder<OperationRequest> operationRequest,
			Holder<List<BrowseNodes>> result) {

		api.getPort().browseNodeLookup(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
