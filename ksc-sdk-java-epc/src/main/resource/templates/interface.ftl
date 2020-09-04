package com.ksc.epc;

<#list importList as import>
import com.ksc.epc.model.${import};
</#list>

public interface KSCEPC  {

<#list interfaceList as interface>
/**
* ${interface.javadoc}
* @param ${interface.param} ${interface.paramType}
* @return ${interface.returnType}
*/
${interface.returnType} ${interface.action}(${interface.paramType} ${interface.param});
</#list>
}