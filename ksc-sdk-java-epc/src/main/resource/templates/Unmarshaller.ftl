package com.ksc.epc.model.transform;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

<#if haveList??>
import com.ksc.transform.ListUnmarshaller;
</#if>
<#if haveSimpleList??>
import com.ksc.transform.SimpleTypeJsonUnmarshallers;
</#if>
import com.fasterxml.jackson.core.JsonToken;
<#-- list类型中的泛型-->
<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
import com.ksc.epc.model.${resultType};
import com.ksc.transform.JsonUnmarshallerContext;
import com.ksc.transform.Unmarshaller;

/**
 * ${resultType} JSON Unmarshaller
 */
public class ${resultType}JsonUnmarshaller implements Unmarshaller<${resultType}, JsonUnmarshallerContext> {

	public ${resultType} unmarshall(JsonUnmarshallerContext context) throws Exception {
		${resultType} result = new ${resultType}();
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
				<#-- 非list类型类型成员-->
				<#if members??>
				<#list members as member>
				if (context.testExpression("${member.name?cap_first}", targetDepth)) {
					context.nextToken();
					result.set${member.name?cap_first}(context.getUnmarshaller(${member.type.simpleName}.class).unmarshall(context));
				}
				</#list>
				</#if>
				<#-- list类型成员,泛型不为javabean  取值范围见ClassUtils.classes-->
				<#if simpleListMembers??>
				<#list simpleListMembers as member>
				if (context.testExpression("${member.name?cap_first}", targetDepth)) {
					context.nextToken();
					result.set${member.name?cap_first}(new SdkInternalList<>(new ListUnmarshaller<${member.genericsClass.simpleName}>(
							SimpleTypeJsonUnmarshallers.${member.genericsClass.simpleName}JsonUnmarshaller.getInstance()).unmarshall(context)));
				}
				</#list>
				</#if>
				<#-- list类型成员,泛型为javabean-->
				<#if beanListMembers??>
				<#list beanListMembers as member>
				if (context.testExpression("${member.name?cap_first}", targetDepth)) {
					context.nextToken();
					result.set${member.name?cap_first}(
							new SdkInternalList<>(new ListUnmarshaller<${member.genericsClass.simpleName}>(${member.genericsClass.simpleName}JsonUnmarshaller.getInstance()).unmarshall(context)));
				}
				</#list>
				</#if>
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

	private static ${resultType}JsonUnmarshaller instance;

	public static ${resultType}JsonUnmarshaller getInstance() {
		if (instance == null)
			instance = new ${resultType}JsonUnmarshaller();
		return instance;
	}
}
