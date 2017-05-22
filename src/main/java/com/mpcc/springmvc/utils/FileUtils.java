/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Huy_Nam
 */
public class FileUtils {

    private static final Properties prop = new Properties();
    private static final String pathFile = "config/config.properties";
    InputStream input = null;

    public FileUtils() {
        try {
            input = new FileInputStream(pathFile);
            prop.load(input);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    
    public static String getValue(String key, String valueDefault) {
        new FileUtils();
        String result = prop.getProperty(key);
        if (StringUtils.isNull(result)) {
            return valueDefault;
        }
        return result;
    }
}
