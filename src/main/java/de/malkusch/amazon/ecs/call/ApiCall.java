package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Errors;
import com.ECS.client.jax.Errors.Error;
import com.ECS.client.jax.OperationRequest;
import com.ECS.client.jax.Request;

import de.malkusch.amazon.ecs.InterfaceDecorator;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI;
import de.malkusch.amazon.ecs.ProductAdvertisingAPI.Boolean;
import de.malkusch.amazon.ecs.exception.RequestErrorException;
import de.malkusch.amazon.ecs.exception.RequestException;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
abstract public class ApiCall<CallType, RequestType, ResultType> {

	protected ProductAdvertisingAPI api;

	private Class<CallType> callClass;

	abstract protected void call(CallType call,
			Holder<OperationRequest> operationRequest,
			Holder<List<ResultType>> result);

	public ApiCall(ProductAdvertisingAPI api, Class<CallType> callClass) {
		this.api = api;
		this.callClass = callClass;
	}

	public List<ResultType> callRequests(CallType call) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<ResultType>> result = new Holder<List<ResultType>>();
		call(call, operationRequest, result);
		return result.value;
	}

	public ResultType call(RequestType request) throws RequestException {
		ResultType result = callRequests(buildCall(request)).get(0);
		ApiResponse response = InterfaceDecorator.getProxy(result,
				ApiResponse.class);
		validateResponse(response.getRequest());
		return result;
	}

	public CallType buildCall() {
		try {
			CallType instance = callClass.newInstance();
			ApiCallParameters<?> parameter = InterfaceDecorator.getProxy(
					instance, ApiCallParameters.class);
			parameter.setAssociateTag(api.getAssociateTag());
			parameter.setAWSAccessKeyId(api.getAccessKey());
			return instance;

		} catch (InstantiationException e) {
			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);

		}
	}

	public CallType buildCall(RequestType request) {
		CallType instance = buildCall();
		ApiCallParameters<RequestType> parameter = InterfaceDecorator.getProxy(
				instance, ApiCallParameters.class);
		parameter.getRequest().add(request);
		return instance;
	}

	private void validateResponse(Request request) throws RequestException {
		Errors errors = request.getErrors();
		if (errors != null && errors.getError() != null) {
			RequestErrorException exception = null;
			for (Error error : errors.getError()) {
				exception = new RequestErrorException(error, exception);

			}
			if (exception != null) {
				throw exception;
				
			}
		}
		if (request.getIsValid().equals(Boolean.FALSE)) {
			throw new RequestException("Request was invalid");

		}
	}

}
