package com.earlymorning;

import com.earlymorning.model.InboundFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dario on 10/08/2016.
 */
@Component
public class ResequencerNShifter {

    public void insertTimeStampInName(InboundFile fileWithNameToChange){
        File toRename= new File(fileWithNameToChange.getFilePath() + fileWithNameToChange.getNameOfFile());
        fileWithNameToChange.setNameOfFile(fileWithNameToChange.getNameOfFile() + new SimpleDateFormat("yyyyMMddhhmm'.csv'").format(new Date()));
        File renamed = new File(fileWithNameToChange.getFilePath() + fileWithNameToChange.getNameOfFile());
        toRename.renameTo(renamed);
    }

}
