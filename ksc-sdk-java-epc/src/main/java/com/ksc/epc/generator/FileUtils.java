package com.ksc.epc.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author create by WANGXIAOHAN
 * @Date 2020-09-08 11:03
 */
public class FileUtils {
    public static void generateCsv() throws Exception {
        File openApi = new File(TemplateUtils.class.getResource("/openapi.txt").getPath());
        File csv = new File("ksc-sdk-java-epc/src/main/resource/interface.csv");
        PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(csv, false), StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(openApi),
                StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            int count = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '.') {
                    count++;
                }
            }
            if (line.trim().startsWith("1.4.") && count == 3) {
                String action = line.substring(line.lastIndexOf(".") + 1, line.indexOf("(")).trim();
                String desc = line.substring(line.lastIndexOf("(") + 1, line.indexOf(")")).trim();
                writer.println(action + "," + desc + ",");
            }
        }
        writer.close();
        reader.close();
    }

    public static void main(String[] args) throws Exception {
        generateCsv();
    }
}
