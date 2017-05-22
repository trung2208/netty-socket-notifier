/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author NAMPH1
 */
public class ResourceBunde {

//    public static void main(String[] args) throws IOException {
//
//        ResourceBunde a = new ResourceBunde();
//        //System.out.println(a.getPropValues());
//        //System.out.println(a.getConfigPath("pathExtension"));
//        System.out.println("-------**********-------------");
//        System.out.println(a.getValue("pathExtension"));
//
//    }

  

    public  String getValue(String value) {
        ResourceBundle rs = ResourceBundle.getBundle("config");
        return rs.getString(value);
    }

    String result = "";
    InputStream inputStream;

    public String getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

    public String getConfigPath(String key) {
        InputStream inputStream = null;
        Properties props = new Properties();

        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return props.getProperty(key);
    }

}
