package com.burke.cpwrapper;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fileutil.CSVUtil;


public class Test_CSVUtil {


    @Test
    public void testLoadCSV(){
        System.out.println("Starting testLoadCSV...");
        CSVUtil c = new CSVUtil("src/test/resources/config/MacroCells.csv");
        assertTrue(c.loadCSV());
    }

    @Test
    public void testGetSingleValueWithColIndex(){
        System.out.println("Starting testGetSingleValueWithColIndex...");

        double expected = 2694;
        int row = 3;
        int col = 3;

        CSVUtil c = new CSVUtil("src/test/resources/config/MacroCells.csv");
 
        System.out.println("Finding Value at ( Col: "+ col +", Row: " + row + ")..." );

        double d = c.getValue(col,row);

        System.out.println("\t( Col: "+ col +", Row: " + row + ") == " + d + ", expected: " + expected);
        assertEquals(expected, d, 0.0);
    }

    @Test
    public void testGetSingleValueWithColName(){
        System.out.println("Starting testGetSingleValueWithColName...");

        double expected = 2694;
        int row = 3;
        String col = "AreaShape_Area";
        CSVUtil c = new CSVUtil("src/test/resources/config/MacroCells.csv");

        System.out.println("Finding Value at ( Col: "+ col +", Row: " + row + ")..." );

        double d = c.getValue(col,row);

        System.out.println("\t( Col: "+ col +", Row: " + row + ") == " + d + ", expected: " + expected);
        assertEquals(expected, d, 0.0);

    }


    @After
    public void printSpacing(){
        System.out.println();
    }


}