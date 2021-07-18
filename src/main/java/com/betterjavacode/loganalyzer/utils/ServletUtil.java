package com.betterjavacode.loganalyzer.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ServletUtil
{
    public static String getAttribute(HttpServletRequest request, String param)
    {
        String attribute = (String) request.getAttribute(param);
        if(attribute == null)
        {
            attribute = getStringParameter(request, param);
        }
        if(attribute == null || attribute.trim().equals(""))
        {
            return null;
        }
        else
        {
            attribute = attribute.trim();
        }
        return attribute;
    }

    private static String getStringParameter(HttpServletRequest request, String paramName)
    {
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
        return wrapper.getParameter(paramName);
    }
}
