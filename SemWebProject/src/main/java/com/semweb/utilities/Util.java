package com.semweb.utilities;

import java.io.InputStream;
import java.util.Properties;

public class Util {

    public static String getProperty(String paramName) {
        String param = "";
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";
            InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            }
            param = prop.getProperty(paramName);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }
}
