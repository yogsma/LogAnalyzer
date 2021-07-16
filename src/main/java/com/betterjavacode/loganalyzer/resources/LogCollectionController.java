package com.betterjavacode.loganalyzer.resources;

import com.betterjavacode.loganalyzer.models.LogContent;
import com.betterjavacode.loganalyzer.service.LogCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/loganalyzer/logs")
public class LogCollectionController
{
    @Autowired
    private LogCollectionService logCollectionService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getLogContent(@RequestParam("fileName") String fileName,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "50") int size) throws Exception
    {
        if(fileName == null || fileName.isEmpty())
        {
            throw new Exception("Provide a file name");
        }
        List<LogContent> logContents = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);
        Page<LogContent> pageLogContent;
        pageLogContent = logCollectionService.getLogContentFromFile(pageable, fileName);
        logContents = pageLogContent.getContent();

        if(logContents.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("logcontent", logContents);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping
    public List<LogContent> getTopLogContent(@RequestParam("fileName") String fileName)
    {
        return null;
    }

    @GetMapping
    public List<LogContent> searchLogContent(@RequestParam("keyword") String keyword)
    {
        return null;
    }
}
