package com.adinnet.web.api.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadTxt {

    public static class Logs {
        private String requestDate;
        private String url;
        private String parameter;

        public String getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }
    }


    private static String urlStart = "请求参数------------------------------------------------：";

    private static String logInfoFormat = "[tomcat-threads--31] INFO  com.bdb.web.filter.SessionFilter";

    private static String parameterStart = "[tomcat-threads--31] INFO  com.bdb.web.filter.SessionFilter - {";

    private static String parameterEnd = "}";

//    private String max =

    public static void main(String args[]) {
        try {
            String pathname = "/Users/liyueyou/Documents/downloadfile/catalina.txt";
            File filename;
            filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            //整个文件每一行存在list中。
            List<String> list = new ArrayList<String>();
            //读取一行。
            String line = "";
            //第一次读取
            line = br.readLine();
            while (line != null) {
                line = br.readLine();
                if(line!=null&&line.contains(logInfoFormat)){
                    list.add(line);
                }
            }
            List<Logs> logList = new ArrayList<Logs>();
            for (int i=1;i<list.size();i+=2){
                //匹配日志的行 第一行是请求的url 第二行是参数
                if(list.get(i).indexOf(logInfoFormat) != -1){
                    Logs log = new Logs();
                    String url = list.get(i-1).substring(list.get(i-1).indexOf(urlStart)+urlStart.length());
                    String parameter = list.get(i).substring(list.get(i).indexOf(parameterStart) + parameterStart.length(), list.get(i).indexOf(parameterEnd)==-1?list.get(i).indexOf(parameterStart) + parameterStart.length():list.get(i).indexOf(parameterEnd));
                    log.setRequestDate(list.get(i-1).substring(0,23));
                    log.setUrl(url);
                    log.setParameter(parameter);
                    logList.add(log);
                }
            }
            for(Logs log : logList){
                System.out.print(log.getRequestDate()+"     ");System.out.print(log.getUrl()+"      ");
                System.out.println(log.getParameter());
            }

            /* 写入Txt文件 */
//            File writename = new File("/Users/liyueyou/Desktop/log.txt");
//            writename.createNewFile(); // 创建新文件
//            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//            out.write(line+"\r\n");
//            out.flush();
//            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




