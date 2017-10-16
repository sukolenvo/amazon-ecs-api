package de.malkusch.amazon.ecs.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.amazon.wsdl.ItemSearchRequest;
import com.amazon.wsdl.Items;
import org.junit.Test;


import de.malkusch.amazon.ecs.exception.RequestException;

public class TestSignatureHandler extends AbstractTest {

	public TestSignatureHandler() throws IOException {
		super();
	}

	@Test
	public void testAuthenticatedCommunication()
			throws UnsupportedEncodingException, RequestException {
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setKeywords("Star Wars");

		Items items = api.getItemSearch().call(itemSearchRequest);

		assertEquals("True", items.getRequest().getIsValid());
	}

}
