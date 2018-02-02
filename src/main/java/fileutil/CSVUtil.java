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
import java.util.Arrays;

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
        row -= 1;
        column -= 1;
        int i = 0;
        for(CSVRecord cr : this.records){
            i++;
            String s = cr.get(column);
            //System.out.println("Row: " + (i + 1) + "==" + s);
            if(i == row){
                
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
            System.out.println("Row: " + (i + 2) + "==" + s);
            if(i == row ){
                
                return Double.parseDouble(s);
            }
        }

        return -1;
    }

    public double getColumnAverage(String column){
        ArrayList<Double> subAvg = new ArrayList<Double>();
        int i = 0;//manages how many have been processed
        double movingSum = 0;
        //this can be increased in order to increase accuracy
        int granularity = 10;
        //cycle through all of the records
        for(CSVRecord cr : this.records){
            i++;
            //get the value at this column
            String s = cr.get(column);
            //get value
            double temp = Double.parseDouble(s);
            //if granularity have been summed, add a local average
            if(i == granularity){
                movingSum += temp;
                subAvg.add(movingSum / i);
                //System.out.println("AVG : " + movingSum / i);
                i = 0;
                movingSum = 0;

            }
            else{
                movingSum += temp;     
            }
            
        }
        //don't leave out the firnge datapoints if row % granularity !=0
        if(movingSum != 0){
            subAvg.add(movingSum / i);
            i = 0;
            movingSum = 0;
        }

        for(Double d : subAvg){
            movingSum += d;
        }

        return movingSum / subAvg.size();
    }


    public double getNthPercentile(String column, double perc){
        ArrayList<Double> unsorted = new ArrayList<Double>();
        for(CSVRecord cr : this.records){
            unsorted.add(Double.parseDouble(cr.get(column)));
        }
        Double[] sorted = unsorted.toArray(new Double[unsorted.size()]);
        for (int i = 1; i < sorted.length; i++)
        {
            Double x = sorted[i];
 
            // Find location to insert using binary search
            int j = Math.abs(Arrays.binarySearch(sorted, 0, i, x) + 1);
 
            //Shifting array to one location right
            System.arraycopy(sorted, j, sorted, j+1, i-j);
 
            //Placing element at its correct location
            sorted[j] = x;
        }
        double fpIndex = sorted.length * perc; 
        return sorted[(int)fpIndex];
        

    }
}