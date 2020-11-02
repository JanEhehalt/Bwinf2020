package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	

    public static void main(String[] args){
		try {
	    	File data = new File("src/raetsel0.txt");
	        //File data = new File(args[0]);
	        Scanner sc = new  Scanner(data);
	
	        ArrayList<String> words;
	        ArrayList<String> sentence;
	        
	        int currentChar;
	        
	        sc.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
