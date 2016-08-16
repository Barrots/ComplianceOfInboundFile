package com.earlymorning;

import com.earlymorning.model.InboundFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dario on 11/08/2016.
 */
@Configuration
@PropertySource("classpath:META-INF.com.earlymorning.properties.InboundFile.properties")
public class ResequencerNShifterTest {

    private ResequencerNShifter controller;
    @Value("${path.file.test}")
    private String pathFileTest= "src/main/resources/input/test/";

    @org.junit.Before
    public void setUp() throws Exception {
        controller = new ResequencerNShifter();
    }

    @org.junit.Test
    public void insertTimeStampInNameTest() throws Exception {

        String nameOfTheFile="fileWithNoTS";
        InboundFile testFileWithNoTS= new InboundFile(1, new Date(), nameOfTheFile , new Date(), pathFileTest);
        File checker =new File( pathFileTest + nameOfTheFile );
        checker.createNewFile();
        controller.insertTimeStampInName(testFileWithNoTS);
        assertTrue("The file didn't changed his name", !checker.getName().equalsIgnoreCase(testFileWithNoTS.getNameOfFileWithoutExt()));
        File deleter =new File( pathFileTest + testFileWithNoTS.getNameOfFile() );
        deleter.delete();

    }

}