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


public class Test_PipelineAuthor{

    @Test
    public void testTemplateReplacement(){
        PipelineAuthor pa = new PipelineAuthor();
        pa.addReplacementRule(new Replacement("@minman", "10,70"));
        pa.buildPipeline("");
    }
}