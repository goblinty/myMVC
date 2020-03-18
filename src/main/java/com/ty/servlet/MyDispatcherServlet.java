package com.ty.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class MyDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String,Object> ioc = new HashMap<>();

    private Map<String, Method> handlerMapping = new HashMap<>();

    private Map<String,Object> controllerMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {

        //1.加载配置文件

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void doLoadConfig(String location){

        //将web.xml中的contextConfigLocation对应的value写入流
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);

        try {
            properties.load(resourceAsStream);
        }catch (IOException e){
            e.printStackTrace();
        }finally {

            //关流
            if (null != resourceAsStream){
                try {
                    resourceAsStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    private void doScanner(String packageName){

        // 将 .替换成 /
        URL url = this.getClass()
                .getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));

        File dir = new File(url.getFile());
        for (File file: dir.listFiles()
             ) {
            if (file.isDirectory()){
                //读取包
                doScanner(packageName+"."+file.getName());
            }else {
                String className = packageName +"." +file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

}
