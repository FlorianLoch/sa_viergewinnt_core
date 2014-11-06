package de.dhbw.mbfl.jconnect4lib.utils;

import java.util.ArrayList;

/**
 * Converts Lists and Arrays to String.
 * @author florian & Maurice Busch
 */
public class Utils
{
    
    /**
     * adds the elements one after another with the delimiter
     * @param list
     * @param delimiter
     * @return one string
     */
    public static String joinArrayList(ArrayList list, String delimiter)
    {
        return joinArray(list.toArray(), delimiter);
    }

    /**
     * adds the elements one after another with the delimiter
     * @param array
     * @param delimiter
     * @return one string
     */
    public static String joinArray(Object[] array, String delimiter)
    {
        StringBuilder s = new StringBuilder();

        for(Object item : array)
        {
            s.append(item.toString());
            s.append(delimiter);
        }
        return s.substring(0, s.length() - 1 - delimiter.length());
    }
}
