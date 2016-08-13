package com.earlymorning;

import com.earlymorning.model.InboundFile;
import com.earlymorning.model.ItemAsRow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by Dario on 10/08/2016.
 */
@Configuration
@PropertySource("classpath:META-INF.com.earlymorning.properties.InboundFile.properties")
@Component
public class SplitterNChecker {
    private FileReader fstreamIn;
    private BufferedReader in;
    private FileWriter fstreamOutErr;
    private BufferedWriter outE;
    private FileWriter fstreamOutRig;
    private BufferedWriter outR;
    private String newNameErr;
    private String newNameRig;

    @Value("${path.file.temp}")
    private String pathTemp = "src/main/resources/input/temp/";
    @Value("${archive.failed.path}")
    private String pathErrors = "src/main/resources/output/errorsFile/";
    @Value("${archive.accepted.path}")
    private String pathAccepted= "src/main/resources/output/acceptedFile/";


// ASSUMPTION: There'll be at least a correct record in the InboundFile

    public boolean splitWithSuccess(InboundFile fileToOpen) {
        boolean successForWholeFile = true;
        newNameErr = fileToOpen.getNameOfFileWithoutExt() + "ErrorRows.csv";
        newNameRig = fileToOpen.getNameOfFileWithoutExt() + "ToProcess.csv";
        initReadNWrite(fileToOpen);
        try {
            cycleCheckWrite(fileToOpen, successForWholeFile);

            ContentBasedRouter mover = new ContentBasedRouter();
            if (!successForWholeFile) {
                outE.close();
                fstreamOutErr.close();

                mover.moveFile(pathTemp + newNameErr, pathErrors + newNameErr);
            }
            outR.close();
            fstreamOutRig.close();

            mover.moveFile(pathTemp + newNameRig, pathAccepted + newNameRig);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return successForWholeFile;
    }

    public void initReadNWrite(InboundFile fileToOpen) {
        try {
            fstreamIn = new FileReader(fileToOpen.getFilePath() + fileToOpen.getNameOfFile());
            in = new BufferedReader(fstreamIn);

            fstreamOutRig = new FileWriter(pathTemp + newNameRig);
            outR = new BufferedWriter(fstreamOutRig);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cycleCheckWrite(InboundFile fileToOpen, boolean successForWholeFile) {
        try {
            String line;
            while ((line= in.readLine())!= null) {
                ItemAsRow row = new ItemAsRow(line, ",");

                if (!checkItem(row)) {
                    if (successForWholeFile) {
                        fstreamOutErr = new FileWriter(pathTemp + newNameErr);
                        outE = new BufferedWriter(fstreamOutErr);
                    }
                    successForWholeFile = false;
                    outE.write(row.toString() + "\n");
                    outE.flush();
                    fstreamOutErr.flush();
                } else {
                    outR.write(row.toString() + "\n");
                    outR.flush();
                    fstreamOutRig.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkItem(ItemAsRow item) {

        return true;
    }

}
