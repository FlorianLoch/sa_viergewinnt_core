/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.exceptions;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class NoChangeException extends ValidationException
{
    public static final String MSG = "No changes have been made.";

    public NoChangeException()
    {
        super(MSG);
    }
    
}
