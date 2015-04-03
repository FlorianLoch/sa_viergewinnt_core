/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.utils.Utils;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class MoreThenOneException extends ValidationException
{
    public static final String MSG = "There is more then one change on the board: ";
    
    private final ArrayList<Difference> differences;
    
    public MoreThenOneException(ArrayList<Difference> differences)
    {
        super(MSG + getDifferencesAsString(differences));
        this.differences = differences;
    }
    
    private static String getDifferencesAsString(ArrayList<Difference> differences)
    {
        return Utils.joinArrayList(differences, ", ");
    }
    
    public String getDifferencesAsString()
    {
        return getDifferencesAsString(differences);
    }
    
    public ArrayList<Difference> getDifferences()
    {
        return differences;
    }
    
}
