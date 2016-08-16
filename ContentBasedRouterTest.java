package com.earlymorning;

import com.earlymorning.model.InboundFile;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Dario on 10/08/2016.
 */
@Configuration
@PropertySource("classpath:META-INF.com.earlymorning.properties.InboundFile.properties")
public class ContentBasedRouterTest {

    @Value("${archive.failed.path}")
    private String archiveFailedPath = "src/main/resources/output/errorsFile/";
    private ContentBasedRouter controller;
    @Value("${path.file.test}")
    private String pathFileTest = "src/main/resources/input/test/";

    @org.junit.Before
    public void setUp() throws Exception {
        controller = new ContentBasedRouter();
    }

    @org.junit.Test
    public void checkOnTimeTest() throws Exception {
        DateTime afterDate = new DateTime(2030, 1, 1, 0, 0);
        DateTime beforeDate = new DateTime(2000, 1, 1, 0, 0);
                                                        //MAX ARRIVAL DATE                  //ACTUAL ARRIVAL DATE
        InboundFile testFileOnTime = new InboundFile(1, afterDate.toDate(), "testFile.csv", beforeDate.toDate(),pathFileTest);
        InboundFile testFileOutTime = new InboundFile(1, beforeDate.toDate(), "testFile.csv", afterDate.toDate(), pathFileTest);

            assertTrue("The InboundFile was on time but ContentBasedRouter returned false",
                    controller.checkOnTime(testFileOnTime));
//        Since there is not a check for the arrivals time yet this part is commented
//            assertFalse("The InboundFile was beyond the limit time of arrival but ContentBasedRouter returned true",
//                    controller.checkOnTime(testFileOutTime));
    }

    @Test
    public void checkIntegrityTest() throws Exception {
        InboundFile testFileWhole= new InboundFile(2, new Date(), "testFile.csv", new Date(), pathFileTest);
        InboundFile testFileTemp = new InboundFile(3, new Date(), "testFile.csv.temp", new Date(), pathFileTest);

        assertTrue("The file was not whole but ContentBasedRouter returned true", controller.checkIntegrity(testFileWhole));
        assertFalse("The file was whole but ContentBasedRouter returned false", controller.checkIntegrity(testFileTemp));
    }

    @Test
    public void sendFileToErrorListTest() throws Exception {
        String nameOfTheFile="fileThrash.csv";
        InboundFile testFileInTheTrash= new InboundFile(4, new Date(), nameOfTheFile , new Date(), pathFileTest);
        File checker =new File( pathFileTest + nameOfTheFile );
        checker.createNewFile();
        controller.sendFileToErrorList(testFileInTheTrash);
        File checker2 =new File( archiveFailedPath + nameOfTheFile );
        assertTrue("The file with errors is not in the archive",checker2.exists() );
        checker.delete();
        if (checker2.exists()) checker2.delete();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

}