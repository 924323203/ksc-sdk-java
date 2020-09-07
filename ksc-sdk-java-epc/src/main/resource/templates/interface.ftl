package com.ksc.epc;

<#list importList as import>
import com.ksc.epc.model.${import};
</#list>

public interface KSCEPC {

<#list interfaceList as interface>
    /**
    * ${interface.javadoc}
    * @param ${interface.paramType?uncap_first} ${interface.paramType}
    * @return ${interface.returnType}
    */
    ${interface.returnType} ${interface.action?uncap_first}(${interface.paramType} ${interface.paramType?uncap_first});
</#list>
}