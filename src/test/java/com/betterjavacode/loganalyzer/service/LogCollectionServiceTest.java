package com.betterjavacode.loganalyzer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogCollectionServiceTest
{
    @InjectMocks
    LogCollectionService logCollectionService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLogContentFromFile() throws URISyntaxException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("controller.log").getFile());
        String absolutePath = file.getAbsolutePath();
        List<String> logContent =
                logCollectionService.getLogContentFromFile(file.getAbsolutePath().replace(file.getName(), ""),
                        "controller.log", 0, 10);

        assertNotNull(logContent);
        assertEquals(logContent.get(7), "[2021-07-10 11:42:34,121] INFO [Controller id=0] Deleting log dir event notifications (kafka.controller.KafkaController)");

    }
}
