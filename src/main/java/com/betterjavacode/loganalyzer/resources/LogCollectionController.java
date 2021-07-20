package com.betterjavacode.loganalyzer.resources;

import com.betterjavacode.loganalyzer.service.LogCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/loganalyzer/logs")
public class LogCollectionController
{
    @Autowired
    private LogCollectionService logCollectionService;
    private static String ROOT_DIR;
    static {
        if(System.getProperty("os.name").contains("Windows")) {
            ROOT_DIR = "\\var\\log";
        }
        else {
            ROOT_DIR = "/var/log";
        }
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getLogContent(@RequestParam("fileName") String fileName,
                                                            @RequestParam(name =
                                                                    "page", defaultValue = "0") int page,
                                                            @RequestParam(name= "size",
                                                                    defaultValue =
                                                                    "100") int size) throws Exception
    {
        if(fileName == null || fileName.isEmpty())
        {
            throw new Exception("Provide a file name");
        }


        List<String> logContents = logCollectionService.getLogContentFromFile(ROOT_DIR,
                fileName,
                page, size);

        if(logContents.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("logcontent", logContents);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<Map<String,Object>> getTopLogContent(@RequestParam("fileName") String fileName, @RequestParam("top")int topN) throws Exception
    {
        if(fileName == null || fileName.isEmpty())
        {
            throw new Exception("Provide a file name");
        }

        List<String> logContents = logCollectionService.getTopLogContentFromFile(ROOT_DIR, fileName,
                topN);

        if(logContents.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("logcontent", logContents);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchLogContent(@RequestParam("keyword") String keyword) throws UnsupportedEncodingException
    {
        String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8.toString());
        List<String> matchingContent =
                logCollectionService.searchForTextInLogFiles(ROOT_DIR, decodedKeyword);

        if(matchingContent.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("logcontent", matchingContent);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
