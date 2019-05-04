package com.shop.uiils;

/**
 * @author Hpsyche
 */
public class MoneyUtils {
    public static String addPoint(String name){
        if(name.contains(".")) {
            String head = name.substring(0, name.length() - 2);
            String tail = name.substring(name.length() - 1);
            return head + "." + tail;
        }
        return name;
    }
}
