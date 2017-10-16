package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;


import com.amazon.wsdl.Items;
import com.amazon.wsdl.OperationRequest;
import com.amazon.wsdl.SimilarityLookup;
import com.amazon.wsdl.SimilarityLookupRequest;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public class SimilarityLookupCall extends
		ItemCall<SimilarityLookup, SimilarityLookupRequest> {

	public SimilarityLookupCall(ProductAdvertisingAPI api) {
		super(api, SimilarityLookup.class);
	}

	@Override
	protected void call(SimilarityLookup call,
			Holder<OperationRequest> operationRequest,
			Holder<List<Items>> result) {

		api.getPort().similarityLookup(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
