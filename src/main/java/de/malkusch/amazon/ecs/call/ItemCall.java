package de.malkusch.amazon.ecs.call;


import com.amazon.wsdl.Items;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public abstract class ItemCall<CallType, RequestType> extends
		ApiCall<CallType, RequestType, Items> {

	public ItemCall(ProductAdvertisingAPI api, Class<CallType> callClass) {
		super(api, callClass);
	}

}
