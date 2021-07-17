package com.betterjavacode.loganalyzer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class LogCollectionService
{
    private static final Logger logger = LoggerFactory.getLogger(LogCollectionService.class);

    public List<String> getLogContentFromFile (int pageNumber, int pageSize,
                                                   String fileName)
    {
        File file = findFileFromDirectory(fileName);


        if(file != null)
        {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                List<String> list = new ArrayList<>();
                for(int i = pageNumber * pageSize; i < (1 + pageNumber) * pageSize; i++)
                {
                    String message = bufferedReader.readLine();
                    list.add(message);
                }
                bufferedReader.close();
                return list;
            }
            catch(IOException e)
            {
                logger.error("Error accessing file ", e);
            }
        }
        return new ArrayList<>();
    }


    private File findFileFromDirectory(String fileName)
    {
        File[] files = new File("C://var//log").listFiles();
        if(files != null)
        {
            return getFile(fileName, files);
        }
        return null;
    }

    private File getFile(String fileName, File[] files)
    {
        for(File file : files)
        {
            if(file.isDirectory())
            {
                getFile(fileName, file.listFiles());
            }
            else
            {
                if(fileName.equals(file.getName()))
                {
                    return file;
                }
            }
        }

        return null;
    }

    public List<String> getLogContentFromFile (String fileName, int topN)
    {
        return getLogContentFromFile(0, topN, fileName);
    }
}
