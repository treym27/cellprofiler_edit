package fileutil;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;

public class CSVUtil{
    private String fileName; 
    private Iterable<CSVRecord> records;
    public CSVUtil(String fileName){
        this.fileName = fileName;
        loadCSV();

    }


    public boolean loadCSV(){
        return loadCSV(this.fileName);
    }


    public boolean loadCSV(String fileName){
        try(
            Reader r = Files.newBufferedReader(Paths.get(fileName));
            CSVParser c = new CSVParser(r, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        ){
            this.records = c.getRecords();
            if(this.records != null)
                return true;
        }
        catch(IOException ioe){
            System.out.println(ioe);
        }
        return false;
    }
    //note these numbers are indexed to conform with 
    //excel numbering scheme i.e. the row value is the row value 
    //shown in excel
    public double getValue(int column, int row){
        row -= 2;
        column--;
        int i = -1;
        for(CSVRecord cr : this.records){
            i++;
            String s = cr.get(column);
            //System.out.println(s);
            if(i == row && StringUtils.isNumeric(s)){
                
                return Double.parseDouble(s);
            }
        }

        return -1;
    }

    //note these numbers are indexed to conform with 
    //excel numbering scheme i.e. the row value is the row value 
    //shown in excel
    public double getValue(String column, int row){
        row -= 2;
      
        int i = -1;
        for(CSVRecord cr : this.records){
            i++;
            String s = cr.get(column);
            //System.out.println(s);
            if(i == row && StringUtils.isNumeric(s)){
                
                return Double.parseDouble(s);
            }
        }

        return -1;
    }
}