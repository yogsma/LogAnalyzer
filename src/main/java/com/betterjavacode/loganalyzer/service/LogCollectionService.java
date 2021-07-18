package com.betterjavacode.loganalyzer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LogCollectionService
{
    private static final Logger logger = LoggerFactory.getLogger(LogCollectionService.class);
    private static final String ROOT_DIR = "\\var\\log";

    public List<String> getLogContentFromFile (String dir, String fileName, int pageNumber,
                                               int pageSize)
    {
        File file = findFileFromDirectory(dir, fileName);

        if(file != null)
        {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                List<String> list = bufferedReader.lines().skip(pageNumber * pageSize).limit(pageSize).collect(Collectors.toList());

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


    private File findFileFromDirectory(String dir, String fileName)
    {
        File[] files = new File(dir).listFiles();
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
                return getFile(fileName, file.listFiles());
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

    public List<String> getTopLogContentFromFile (String dir, String fileName, int topN)
    {
        return getLogContentFromFile(dir, fileName,0, topN);
    }

    public List<String> searchForTextInLogFiles (String keyword)
    {
        return searchMatchingTextFromFilesInDirectory(ROOT_DIR, keyword);
    }

    private List<String> searchMatchingTextFromFilesInDirectory(String dir, String keyword)
    {
        File[] files = new File(dir).listFiles();
        List<String> result = new ArrayList<>();

        for(File file : files)
        {
            if(file.isFile())
            {
                result.addAll(searchContent(file.toPath(), keyword));
            }
            else if(file.isDirectory())
            {
                result.addAll(searchMatchingTextFromFilesInDirectory(file.toString(), keyword));
            }
        }

        return result;
    }

    private List<String> searchContent (Path path, String keyword)
    {
        List<String> matchingContents = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(path)));
            String currentLine = null;
            while((currentLine = bufferedReader.readLine()) != null)
            {
                if(currentLine.contains(keyword))
                {
                    matchingContents.add(currentLine);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("Error accessing the file", e);
        }

        return matchingContents;
    }
}
