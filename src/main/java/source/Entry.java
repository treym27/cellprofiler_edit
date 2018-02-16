package source;
import fileutil.CSVUtil;
import fileutil.CommandUtil;
import java.io.IOException;
import java.lang.*;

import javax.swing.*;
import java.awt.*;

public class Entry{
    public static void main(String args[]){

      long start_time = System.nanoTime();
      boolean batch = Entry.shouldBatchProcess();
      CountingEngine ce = new CountingEngine(Entry.selectDirectory(), batch);

      long elapse_time = System.nanoTime() - start_time;
      System.out.println("elapse_time in seconds: " + elapse_time * 1e-9);

    }


    public static boolean shouldBatchProcess(){
      int reply = JOptionPane.showConfirmDialog(
        null, 
        "Is this a batch process?",
        "Cell Profiler Wrapper",
        JOptionPane.YES_NO_OPTION);
      return reply == JOptionPane.YES_OPTION;
    }



    public static String selectDirectory(){
      JFileChooser chooser = new JFileChooser();
      String sourceDirectory = "";//"C:/Users/Steven/Desktop/Research/FARSIGHTTESTS/WORKING/Test_Images_Profiler/Batch/Buyout";
      
      chooser.setDialogTitle("Select Stack Directory");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      if(chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION)
        sourceDirectory = chooser.getSelectedFile().getAbsolutePath();
      if(sourceDirectory.equals(""))
        System.out.println("No directory chosen...\nExitting");
      return sourceDirectory;

    }



}