package com.earlymorning.model;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Dario on 11/08/2016.
 */
public class ItemAsRow {
    private long idRow;
    private DateTime possibleDate;
    private String possibleString;
    private int possibleNum;

    public ItemAsRow(long idRow, DateTime possibleDate, String possibleString, int possibleNum) {
        this.idRow = idRow;
        this.possibleDate = possibleDate;
        this.possibleString = possibleString;
        this.possibleNum = possibleNum;
    }
    
    public ItemAsRow(String rowOfFile, String splitRegex){
        String[] rowSplitted= rowOfFile.split(splitRegex);
        this.idRow = Long.parseLong(rowSplitted[0]);
//        this.possibleDate = DateTime.parse(rowSplitted[1]);
        this.possibleDate = new DateTime().toDateTimeISO();
        this.possibleString = rowSplitted[2];
        this.possibleNum = Integer.parseInt(rowSplitted[3]);

    }

    public long getIdRow() {
        return idRow;
    }

    public void setIdRow(long idRow) {
        this.idRow = idRow;
    }

    public DateTime getPossibleDate() {
        return possibleDate;
    }

    public void setPossibleDate(DateTime possibleDate) {
        this.possibleDate = possibleDate;
    }

    public String getPossibleString() {
        return possibleString;
    }

    public void setPossibleString(String possibleString) {
        this.possibleString = possibleString;
    }

    public int getPossibleNum() {
        return possibleNum;
    }

    public void setPossibleNum(int possibleNum) {
        this.possibleNum = possibleNum;
    }

    @Override
    public String toString() {
        return "id= " + idRow + ", possibleDate= " + possibleDate
                + ", possibleString= " + possibleString+ ", possibleNum= " + possibleNum ;
    }
}
