package com.ksc.epc.model.transform;

import com.ksc.DefaultRequest;
import com.ksc.KscClientException;
import com.ksc.Request;
<#if haveList??>
	import com.ksc.internal.SdkInternalList;
</#if>
import com.ksc.epc.model.${requestType};
import com.ksc.http.HttpMethodName;
import com.ksc.transform.Marshaller;
import com.ksc.util.StringUtils;

public class ${requestType}Marshaller implements Marshaller<Request<${requestType}>, ${requestType}> {

	@Override
	public Request<${requestType}> marshall(${requestType} ${requestParam}) {
		if (opsEpcRequest == null) {
			throw new KscClientException("Invalid argument passed to marshall(...)");
		}
		Request<${requestType}> request = new DefaultRequest<${requestType}>(${requestParam}, "epc");
		request.addParameter("Action", ${action});
		String version = ${requestParam}.getVersion();
		if (org.apache.commons.lang.StringUtils.isBlank(version)) {
			version = "2015-11-01";
		}
		request.addParameter("Version", version);
		request.setHttpMethod(HttpMethodName.GET);
		<#-- 基本类型+String成员-->
		<#if members??>
		<#list members as member>
			if (${requestParam}.get${member.name}() != null) {
				request.addParameter("${member.name}", StringUtils.from${member.type}(${requestParam}.get${member.name}()));
			}
		</#list>
		</#if>
		<#-- filter成员-->
		<#if filter??>
		SdkInternalList<Filter> filtersList = ${requestParam}.getFilters();
		if (!filtersList.isEmpty() || !filtersList.isAutoConstruct()) {
			int filtersListIndex = 1;
			for (Filter filtersListValue : filtersList) {
				if (filtersListValue.getName() != null) {
					request.addParameter("Filter." + filtersListIndex + ".Name",
						StringUtils.fromString(filtersListValue.getName()));
				}
				SdkInternalList<String> valuesList = (SdkInternalList<String>) filtersListValue.getValues();
				if (!valuesList.isEmpty() || !valuesList.isAutoConstruct()) {
					int valuesListIndex = 1;
					for (String valuesListValue : valuesList) {
						if (valuesListValue != null) {
							request.addParameter("Filter." + filtersListIndex + ".Value." + valuesListIndex,
							StringUtils.fromString(valuesListValue));
						}
						valuesListIndex++;
					}
				}
				filtersListIndex++;
			}
		}
		</#if>
		<#-- list类型成员-->
		<#if members??>
		<#list listMembers as member>
            SdkInternalList<${member.genericsClassName}> ${member.filedName}List = ${requestParam}.get${member.name}();
            if (!${member.filedName}List.isEmpty() || !${member.filedName}List.isAutoConstruct()) {
                int index = 1;
                for (String value : ${member.filedName}List) {
                    if (value != null) {
                        request.addParameter("ListenerId." + index, StringUtils.from${member.genericsClassName}(value));
                    }
                index++;
                }
            }
		</#list>
		</#if>
		return request;
	}

}
