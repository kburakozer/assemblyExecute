package com.executable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // creates a list to hold each line of binary codes created by assembler
        ArrayList<String> list = new ArrayList<>();
        //createa a new object from BufferedReader class and initilaize it to null.
        BufferedReader bReader = null;

        // creats a FileReader object named file.
        // args[0] usage allows users to enter argumnet in command line.
        FileReader file = new FileReader(args[0]);
        try{
            // initializes BufferedReader object with Filereader object.
            bReader = new BufferedReader(file);
            // new string variable is created and initalized to null
            String line = null;
            // it continues to get each line of as long as it is not null
            while((line = bReader.readLine()) != null){
                // adds the line to the list
                list.add(line);
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        // after reading the file, it closes the file
        bReader.close();
        // creates a new object from Executer class
        Executer executer = new Executer();
        // calls the method in the object executer and passes the list as an argument
        executer.execute(list);

    }

}
