package com.betterjavacode.loganalyzer.service;

import com.betterjavacode.loganalyzer.models.LogContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogCollectionService
{

    public Page<LogContent> getLogContentFromFile (Pageable pageable, String fileName)
    {
        // TO-DO
        return null;
    }
}
