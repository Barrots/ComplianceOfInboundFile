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
    private String pathFileTest="src/main/resources/input/test/";

    @org.junit.Before
    public void setUp() throws Exception {
        controller = new SplitterNChecker();
    }


    @Test
    public void splitWithSuccessTest() throws Exception {
        DateTime dateFile = new DateTime(2030, 1, 1, 0, 0);

        InboundFile testFileR = new InboundFile(1, dateFile.toDate(), "reportTest.csv", dateFile.toDate(), pathFileTest);
        InboundFile testFileW = new InboundFile(2, dateFile.toDate(), "reportTestWrong.csv", dateFile.toDate(), pathFileTest);


        assertTrue("The file was whole correct", controller.splitWithSuccess(testFileR));
//        assertFalse("The file was not whole correct but returned true", controller.splitWithSuccess(testFileW));
    }
}
