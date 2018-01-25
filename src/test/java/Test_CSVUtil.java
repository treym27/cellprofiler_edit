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
    public void testRetOne(){
        CSVUtil c = new CSVUtil("empty");
        assertEquals(1, c.retOne());
    }
}