package com.earlymorning.springUsage;

import com.earlymorning.model.InboundFile;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Dario on 12/08/2016.
 */
public class InboundFileSetMapper implements FieldSetMapper<InboundFile>{

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public InboundFile mapFieldSet(FieldSet fieldSet) throws BindException {

        InboundFile item = new InboundFile();
        item.setIdFile(fieldSet.readInt(0));
        item.setNameOfFile(fieldSet.readString(2));
        try {
            item.setMaxArrivalDate(dateFormat.parse(fieldSet.readString(1)));
            item.setActualArrivalDate(dateFormat.parse(fieldSet.readString(3)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return item;

    }

}