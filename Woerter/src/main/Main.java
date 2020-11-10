package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	

    public static void main(String[] args){
		try {
	    	File data = new File("src/raetsel1.txt");
	        //File data = new File(args[0]);
	        Scanner sc = new Scanner(data);
	        
	        ArrayList<String> words = new ArrayList<>();
	        ArrayList<String> sentence = new ArrayList<>();
	
        	while(sc.hasNext()) {
        		String cw = sc.next();	// cW - currentWord
        		
        		char last = cw.charAt(cw.length()-1);
        		if(!cw.contains("_") && (last != '!' || last != '?' || last != ',')) { 
        			words.add(cw);
        			break;
        		}
        		if(last == '.' || last == '!' || last == '?' || last == ',') {
        			sentence.add(cw.substring(0, cw.length() - 1));	// remove ! from last Word
		        	sentence.add(new String() + last);	// still adding to the sentence
        		}
        		else {
        			sentence.add(cw.toString());
        		}
        	}
        	while(sc.hasNext()) {
        		words.add(sc.next());
        	}
        	

        	System.out.println(sentence.size() + " " + words.size());
	        for(String string : sentence) {
        		System.out.println(string);
        	}
    		System.out.println("");
	        for(String string : words) {
        		System.out.println(string);
        	}
    		//System.out.println("");
    		//System.out.println("");
    		//System.out.println("");
        	
	        sc.close();
	        
	        boolean finished = true;
	        
	        boolean[] filled = new boolean[sentence.size()];
	        for(int i = 0; i < filled.length; i++){
	        	filled[i] = false;
	        }
	        
	        do { 
	        	finished = true;
		        for(int i = 0; i < words.size(); i++) {
		        	int posGaps = 0;
		        	
		        	for(int m = 0; m < sentence.size(); m++) {
		        		if(sentence.get(m).length() == 1) {
	        				filled[m] = true;
			        		continue;
			        	}
		        		else if(fits(words.get(i), sentence.get(m))) {
		        			posGaps++;
		        		}
		        	}
		        	if(posGaps == 1) {
		        		for(int n = 0; n < sentence.size(); n++) {
		        			if(sentence.get(n).length() == 1) {
		        				filled[n] = true;
				        		continue;
				        	}
			        		if(fits(words.get(i), sentence.get(n))) {
			        			sentence.set(n, words.get(i));
			        			filled[n] = true;
			        			words.remove(i);
			        			i--;
			        			break;
			        		}
			        	}
		        	}
		        	else if(posGaps == 0) {
		        		for(int b = 0; b < sentence.size(); b++) {
		        			if(sentence.get(b).length() == 1) {
		        				filled[b] = true;
				        		continue;
				        	}
		        			else if(sentence.get(b).length() == words.get(i).length()) {
		        				filled[b] = true;
		        				sentence.set(b, words.get(i));
		        				words.remove(i);
		        				i--;
		        				break;
		        			}
			        	}
		        	}
		        	else if(posGaps > 1) {
	        		}
		        }
		        for(int i = 0; i < filled.length; i++) {
		        	if(!filled[i]) {
		        		finished = false;
		        	}
		        }
	        }while(!finished);
	        

    		System.out.println("!!!");
    		System.out.println(sentence.size() + " " + words.size());
	        
	        for(String string : sentence) {
        		System.out.println(string);
        	}
    		System.out.println("");
	        for(String string : words) {
        		System.out.println(string);
        	}
	        
	        
	        
	        
	        
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    
    public static boolean fits(String word, String gap) {
    	if(gap.length() != 1) {
	    	char given = ' ';
	    	int givenPos = -1;
	    	
	    	for(int i = 0; i < gap.length(); i++) {
	    		if(gap.charAt(i) != '_') {
	    			given = gap.charAt(i);
	    			givenPos = i;
	    			if(givenPos == -1) return false;
	    	    	if( gap.length() == word.length() && given == word.charAt(givenPos) ) {
	    	    		return true;
	    	    	}
	    		}
	    	}
	    	return false;
	    	
    	}
    	System.out.println("comma");
    	return false;
    }
}
