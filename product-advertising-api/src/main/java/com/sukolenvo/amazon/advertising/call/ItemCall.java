package com.sukolenvo.amazon.advertising.call;


import com.amazon.wsdl.Items;
import com.sukolenvo.amazon.advertising.ProductAdvertisingAPI;

public abstract class ItemCall<CallType, RequestType> extends
		ApiCall<CallType, RequestType, Items> {

	public ItemCall(ProductAdvertisingAPI api, Class<CallType> callClass) {
		super(api, callClass);
	}

}
