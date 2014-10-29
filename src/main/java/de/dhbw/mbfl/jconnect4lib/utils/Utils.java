/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.utils;

import java.util.ArrayList;

/**
 *
 * @author florian
 */
public class Utils {
    public static String joinArrayList(ArrayList list, String delimiter) {
        return joinArray(list.toArray(), delimiter);
    }
    
    public static String joinArray(Object[] array, String delimiter) {
        StringBuilder s = new StringBuilder();
        
        for (Object item : array) {
            s.append(item.toString());
            s.append(delimiter);
        }
        return s.substring(0, s.length() - 1 - delimiter.length());
    }
}
