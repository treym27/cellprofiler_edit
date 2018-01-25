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
        CSVUtil c = new CSVUtil("src/test/resources/config/MacroCells.csv");
        assertTrue(c.loadCSV());
    }

    @Test
    public void testGetSingleValue(){
        CSVUtil c = new CSVUtil("src/test/resources/config/MacroCells.csv");
        double d = c.getValue(3,3);
        assertEquals(2694, d, 0.0);
    }
}