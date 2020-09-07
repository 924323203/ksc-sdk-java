package com.ksc.epc.generator;

import com.ksc.epc.generator.model.InterfaceInfo;
import com.ksc.epc.generator.model.Member;
import com.ksc.epc.model.OpsEpcRequest;
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
import java.lang.reflect.Field;
import java.net.FileNameMap;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-03 18:25
 */
public class TemplateUtils {

    /**
     * 生成接口和实现类
     *
     * @throws Exception e
     */
    public static void generateMethod() throws Exception {
        List<String> importList=new ArrayList<>();
        List<InterfaceInfo> interfaceInfos = formatInterfaceInfo();
        for(InterfaceInfo interfaceInfo:interfaceInfos){
            if(!importList.contains(interfaceInfo.getParamType())){
                importList.add(interfaceInfo.getParamType());
            }
            if(!importList.contains(interfaceInfo.getReturnType())){
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
            generateMarshaller(Class.forName("com.ksc.epc.model." + interfaceInfo.getParamType()),
                    interfaceInfo.getAction());
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
            interfaceInfo.setReturnType(infos[0]);
            interfaceInfo.setAction(infos[1]);
            interfaceInfo.setParamType(infos[2]);
            interfaceInfo.setJavadoc(infos[3]);
            interfaceInfos.add(interfaceInfo);
        }
        reader.close();
        return interfaceInfos;
    }

    private static void generateMarshaller(Class clazz,String action) throws Exception {
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
        //generateMarshaller(OpsEpcRequest.class,"RebootEpc");
        generateMethod();
    }
}
