package com.ksc.epc.generator;

import com.ksc.epc.generator.model.InterfaceInfo;
import com.ksc.epc.generator.model.Member;
import com.ksc.epc.model.DescribeEpcResult;
import com.ksc.util.CollectionUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-03 18:25
 * <p>
 * list类型的入参名称必须是***s
 */
public class TemplateUtils {

    /**
     * 生成接口和实现类
     *
     * @throws Exception e
     */
    public static void generateMethod() throws Exception {
        List<String> importList = new ArrayList<>();
        List<InterfaceInfo> interfaceInfos = formatInterfaceInfo();
        for (InterfaceInfo interfaceInfo : interfaceInfos) {
            if (!importList.contains(interfaceInfo.getParamType())) {
                importList.add(interfaceInfo.getParamType());
            }
            if (!importList.contains(interfaceInfo.getReturnType())) {
                importList.add(interfaceInfo.getReturnType());
            }
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("interfaceList", interfaceInfos);
        dataMap.put("importList", importList);

        //生成接口实现java文件
        File interfaceFile = new File("ksc-sdk-java-epc/src/main/java/com/ksc/epc/KSCEPC.java");
        createFileFromTemplate(interfaceFile, dataMap, "interface.ftl");

        //生成接口实现类java文件
        File implFile = new File("ksc-sdk-java-epc/src/main/java/com/ksc/epc/KSCEPCClient.java");
        createFileFromTemplate(implFile, dataMap, "interfaceImpl.ftl");

    }

    /**
     * 生成所有Marshaller
     */
    public static void generateAllMarshaller() throws Exception {
        //获取接口信息
        List<InterfaceInfo> interfaceInfos = formatInterfaceInfo();
        for (InterfaceInfo interfaceInfo : interfaceInfos) {
            Class clazz;
            try {
                clazz=Class.forName("com.ksc.epc.model." + interfaceInfo.getParamType());
            } catch (Exception e){
                continue;
            }
            generateMarshaller(clazz, interfaceInfo.getAction());
        }
    }

    /**
     * 生成所有Marshaller
     *
     * @param classes class
     */
    public static void generateAllUnmarshaller(List<Class> classes) throws Exception {
        //获取接口信息
        for (Class clazz : classes) {
            if (!clazz.getSimpleName().endsWith("Result")) {
                continue;
            }
            generateUnmarshaller(clazz);
        }
    }

    private static List<InterfaceInfo> formatInterfaceInfo() throws Exception {
        List<InterfaceInfo> interfaceInfos = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(TemplateUtils.class.getResource("/interface.csv").getPath())),
                StandardCharsets.UTF_8));
        String line = null;
        //接口配置
        while ((line = reader.readLine()) != null) {
            String[] infos = line.split(",");
            InterfaceInfo interfaceInfo = new InterfaceInfo();
            interfaceInfo.setReturnType(infos[0]+"Result");
            interfaceInfo.setAction(infos[0]);
            interfaceInfo.setParamType(infos[0] + "Request");
            interfaceInfo.setJavadoc(infos[1]);
            interfaceInfos.add(interfaceInfo);
        }
        reader.close();
        return interfaceInfos;
    }

    private static void generateMarshaller(Class clazz, String action) throws Exception {
        //获取所有成员
        List<Member> members = ClassUtils.findMembers(clazz);
        List<Member> simpleMembers = new ArrayList<>();
        Member filter = null;
        List<Member> listMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.isIfList()) {
                if (member.isIfFilter()) {
                    filter = member;
                } else {
                    listMembers.add(member);
                }
            } else {
                simpleMembers.add(member);
            }
        }
        //模板参数
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("action", action);
        dataMap.put("requestType", clazz.getSimpleName());
        if (!CollectionUtils.isNullOrEmpty(simpleMembers)) {
            dataMap.put("members", simpleMembers);
        }
        if (!CollectionUtils.isNullOrEmpty(listMembers)) {
            dataMap.put("listMembers", listMembers);
            dataMap.put("haveList", true);
        }
        if (filter != null) {
            dataMap.put("filter", filter);
            dataMap.put("haveList", true);
            dataMap.put("haveFilter", true);
        }
        //输出java文件
        File marshaller = new File(
                "ksc-sdk-java-epc/src/main/java/com/ksc/epc/test/" + clazz.getSimpleName() + "Marshaller.java");
        createFileFromTemplate(marshaller, dataMap, "Marshaller.ftl");
    }

    private static void generateUnmarshaller(Class clazz) throws Exception {
        //获取所有成员
        List<Member> members = ClassUtils.findMembers(clazz);
        List<Member> simpleMembers = new ArrayList<>();
        List<Member> simpleListMembers = new ArrayList<>();
        List<Member> beanListMembers = new ArrayList<>();
        Set<String> otherImport = new HashSet<>();
        //javabean类型和集合类的泛型
        Set<String> beanTypes = new HashSet<>();
        for (Member member : members) {
            if (!member.getType().getName().startsWith("java.lang")) {
                otherImport.add("import " + member.getType().getName());
            }

            if (member.isIfList()) {
                //是否为javabean类型的list
                if (member.isGenericsIfBean()) {
                    beanListMembers.add(member);
                    beanTypes.add(member.getGenericsClass().getSimpleName());
                } else {
                    if (!member.getGenericsClass().getName().startsWith("java.lang")) {
                        otherImport.add("import " + member.getType().getName());
                    }
                    simpleListMembers.add(member);
                }
            } else if (member.isIfBean()) {
                simpleMembers.add(member);
                beanTypes.add(member.getType().getSimpleName());
            } else {
                simpleMembers.add(member);
            }
        }
        //模板参数
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("resultType", clazz.getSimpleName());
        if (!CollectionUtils.isNullOrEmpty(simpleMembers)) {
            dataMap.put("members", simpleMembers);
        }
        if (!CollectionUtils.isNullOrEmpty(beanTypes)) {
            dataMap.put("beanTypes", beanTypes);
        }
        if (!CollectionUtils.isNullOrEmpty(beanListMembers)) {
            dataMap.put("beanListMembers", beanListMembers);
            dataMap.put("haveList", true);
        }
        if (!CollectionUtils.isNullOrEmpty(simpleListMembers)) {
            dataMap.put("simpleListMembers", simpleListMembers);
            dataMap.put("haveList", true);
            dataMap.put("haveSimpleList", true);
        }
        if (!CollectionUtils.isNullOrEmpty(otherImport)) {
            dataMap.put("otherImport", otherImport);
        }
        //输出java文件
        File marshaller = new File(
                "ksc-sdk-java-epc/src/main/java/com/ksc/epc/model/transform/" + clazz.getSimpleName() + "JsonUnmarshaller.java");
        createFileFromTemplate(marshaller, dataMap, "Unmarshaller.ftl");
    }

    private static void createFileFromTemplate(File file, Map<String, Object> dataMap,
                                               String templateName) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(
                new File(TemplateUtils.class.getResource("/templates").getPath()));
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        PrintWriter writer = null;
        //输出java文件
        try {
            writer = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            Template implTemplate = configuration.getTemplate(templateName);
            implTemplate.process(dataMap, writer);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //generateUnmarshaller(BaseResult.class);
        //generateMarshaller(OpsEpcRequest.class,"RebootEpc");
        //generateMethod();

        //生成所有
        generateAllUnmarshaller(Collections.singletonList(DescribeEpcResult.class));
    }
}
