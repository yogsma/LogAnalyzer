package com.betterjavacode.loganalyzer.controller;

import com.betterjavacode.loganalyzer.utils.ServletUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    RestOperations restOperations;
    public MainController(RestOperations restOperations)
    {
        this.restOperations = restOperations;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home()
    {
        return "home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(HttpServletRequest request, Model model)
    {
        String searchKey = ServletUtil.getAttribute(request, "searchterm");
        String isFile = ServletUtil.getAttribute(request, "file");

        logger.info("searchKey is = {}", searchKey);
        logger.info("File boolean = {}", isFile);

        String url = "";
        if(Boolean.valueOf(isFile))
        {
            url = buildUrlForFile(searchKey);
            
        }
        else
        {
            try
            {
                url = buildUrlForSearchKeyword(searchKey);
            }
            catch(UnsupportedEncodingException e)
            {
                logger.debug("Unable to encode the keyword", e);
                url = "";
            }

        }
        logger.info("Url is {}", url);
        String adminUserCredentials = "adminuser:adminpassword";
        String encodedCredentials =
                new String(Base64.encodeBase64(adminUserCredentials.getBytes()));

        if(!url.isEmpty())
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Basic " + encodedCredentials);
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("parameters",httpHeaders);
            ResponseEntity<Map> responseEntity = restOperations.exchange(url,
                    HttpMethod.GET,
                    entity,
                    Map.class);
            List<String> result = (List<String>) responseEntity.getBody().get("logcontent");

            model.addAttribute("results", result);
        }
        else
        {
            List<String> result = new ArrayList<>();
            model.addAttribute("results", result);
        }

        return "searchResults";

    }

    private String buildUrlForSearchKeyword (String searchKey) throws UnsupportedEncodingException
    {
        return "http://localhost:8080/v1/loganalyzer/logs/search?keyword=" + URLEncoder.encode(searchKey, "UTF-8").replaceAll("\\+", "%20");
    }

    private String buildUrlForFile (String searchKey)
    {
        return "http://localhost:8080/v1/loganalyzer/logs?fileName=" + searchKey;
    }
}
