package com.earlymorning;

import com.earlymorning.model.InboundFile;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.junit.Assert.*;

/**
 * Created by Dario on 12/08/2016.
 */
@Configuration
@PropertySource("classpath:META-INF.com.earlymorning.properties.InboundFile.properties")
public class SplitterNCheckerTest {

    private SplitterNChecker controller;

    @Value("${path.file.test}")
    private String pathFileTest;

    @org.junit.Before
    public void setUp() throws Exception {
        controller = new SplitterNChecker();
    }


    @Test
    public void splitWithSuccessTest() throws Exception {
        DateTime dateFile = new DateTime(2030, 1, 1, 0, 0);

        InboundFile testFileR = new InboundFile(1, dateFile.toDate(), "reportTest.csv", dateFile.toDate(), "D:/Programmi/IntelliJ IDEA Community Edition 2016.2.1/Projects/Compliance of inbound data files/src/main/resources/input/test/");
        InboundFile testFileW = new InboundFile(2, dateFile.toDate(), "reportTestWrong.csv", dateFile.toDate(), "D:/Programmi/IntelliJ IDEA Community Edition 2016.2.1/Projects/Compliance of inbound data files/src/main/resources/input/test/");


        assertTrue("The file was whole correct", controller.splitWithSuccess(testFileR));
//        assertFalse("The file was not whole correct but returned true", controller.splitWithSuccess(testFileW));
    }
}
