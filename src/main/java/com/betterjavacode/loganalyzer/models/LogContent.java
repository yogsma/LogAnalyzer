package com.betterjavacode.loganalyzer.models;

import java.io.Serializable;

public class LogContent implements Serializable
{
    private static final long serialVersionUID = 3477407586219074209L;
    private String message;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }
}
