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

import pipelineauthor.PipelineAuthor;
import pipelineauthor.Replacement;
import pipelineauthor.PipelineFactory;

public class Test_PipelineFactory{

    @Test
    //@Ignore
    public void testFactory(){
        System.out.println("Starting testFactory...");
        PipelineFactory.constructPipeline("src/test/resources/config/MacroCells.csv", "");
        //44.959 , 63.99
    }



    @After
    public void printSpacing(){
        System.out.println();
    }
}