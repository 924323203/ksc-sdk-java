package com.ksc.epc.model.transform;

import com.fasterxml.jackson.core.JsonToken;
import com.ksc.epc.model.BaseResult;
import com.ksc.epc.model.EpcHost;
import com.ksc.epc.model.ListEpcsResult;
import com.ksc.transform.JsonUnmarshallerContext;
import com.ksc.transform.ListUnmarshaller;
import com.ksc.transform.Unmarshaller;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

/**
 * ListEpcResult JSON Unmarshaller
 */
public class BaseResultJsonUnmarshaller implements Unmarshaller<BaseResult, JsonUnmarshallerContext> {

	public BaseResult unmarshall(JsonUnmarshallerContext context) throws Exception {
		BaseResult result = new BaseResult();
		int originalDepth = context.getCurrentDepth();
		String currentParentElement = context.getCurrentParentElement();
		int targetDepth = originalDepth + 1;

		JsonToken token = context.getCurrentToken();
		if (token == null)
			token = context.nextToken();
		if (token == VALUE_NULL)
			return null;
		while (true) {
			if (token == null)
				break;

			if (token == FIELD_NAME || token == START_OBJECT) {
				if (context.testExpression("RequestId", targetDepth)) {
					context.nextToken();
					result.setRequestId(context.getUnmarshaller(String.class).unmarshall(context));
				} else if (context.testExpression("Result", targetDepth)) {
					context.nextToken();
					result.setResult(context.getUnmarshaller(Boolean.class).unmarshall(context));
				}
			} else if (token == END_ARRAY || token == END_OBJECT) {
				if (context.getLastParsedParentElement() == null
						|| context.getLastParsedParentElement().equals(currentParentElement)) {
					if (context.getCurrentDepth() <= originalDepth)
						break;
				}
			}
			token = context.nextToken();
		}
		return result;
	}

	private static BaseResultJsonUnmarshaller instance;

	public static BaseResultJsonUnmarshaller getInstance() {
		if (instance == null)
			instance = new BaseResultJsonUnmarshaller();
		return instance;
	}
}
