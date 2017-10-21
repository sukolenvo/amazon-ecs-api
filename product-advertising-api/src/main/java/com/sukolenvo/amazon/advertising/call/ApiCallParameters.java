package com.sukolenvo.amazon.advertising.call;

import java.util.List;

public interface ApiCallParameters<RequestType> {

	void setAWSAccessKeyId(String awsAccessKeyId);

	void setAssociateTag(String associateTag);

	List<RequestType> getRequest();

}
