package com.earlymorning;

import com.earlymorning.model.InboundFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;

/**
 * Created by Dario on 10/08/2016.
 */


@Component
public class ContentBasedRouter {

    @Value("${archive.failed.path}")
    private String archiveFailedPath = "src/main/resources/output/errorsFile/";

    public boolean checkOnTime(InboundFile fileJustArrived) {
        File getterOfLastProcessing = new File(fileJustArrived.getFilePath() + fileJustArrived.getNameOfFile());
        // My idea: the method to get the last Process Date of a file just arrived is to open it without doing changes
        // and then use:
        long timeOfLastMod= getterOfLastProcessing.lastModified();
        //Than check if time is acceptable
        return true;
    }

    public boolean checkIntegrity(InboundFile fileJustArrived) {
        return !fileJustArrived.getNameOfFile().endsWith("temp");
    }

    public void sendFileToErrorList(InboundFile fileWithMistakes) {
        moveFile(fileWithMistakes.getFilePath() + fileWithMistakes.getNameOfFile(), archiveFailedPath + fileWithMistakes.getNameOfFile());
    }

    public void moveFile(String pathFileWithName, String pathDestination) {
        try {
            File fileIn = new File(pathFileWithName);
            File fileOut = new File(pathDestination);

            FileInputStream fIn = new FileInputStream(fileIn);

            FileOutputStream fOut = new FileOutputStream(fileOut);

            byte[] buf = new byte[1024];
            int len;

            while ((len = fIn.read(buf)) > 0) {
                fOut.write(buf, 0, len);
            }

            fIn.close();
            fOut.close();

            fileIn.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
