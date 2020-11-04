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
	        Scanner sc = new  Scanner(data);
	        
	        ArrayList<String> words = new ArrayList<>();
	        ArrayList<String> sentence = new ArrayList<>();
	        boolean[] filled;
	
        	while(sc.hasNext()) {
        		String cw = sc.next();	// cW - currentWord
        		
        		char last = cw.charAt(cw.length()-1);
        		if(last == '.' || last == '!' || last == '?') {
        			sentence.add(cw.substring(0, cw.length() - 1));	// remove ! from last Word
		        	sentence.add(new String() + last);	// still adding to the sentence
		        	
		        	String next = sc.next();
		        	char nextLast = next.charAt(next.length()-1);
		        	if(next.contains("_") && (nextLast != '.' || nextLast != '!' || nextLast != '?')) {
		        		sentence.add(next);
		        	}
		        	else if(next.contains("_") && (last == '.' || last == '!' || last == '?')) {
		        		sentence.add(cw.substring(0, cw.length() - 1));
			        	sentence.add(new String() + last);
		        	}
		        	else
	        		break;
        		}
        		else if(last == ',') {
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
        	
        	filled = new boolean[sentence.size()];
        	for(int i = 0; i >= filled.length; i++) {
        		filled[i] = false;
        	}
	        sc.close();
	        
	        for(int i = 0; i < sentence.size(); i++) {
	        	if(!(sentence.get(i).length() > 1)) continue;
	        	for(int n = 0; n < words.size(); n++) {
	        		if(fits(words.get(n), sentence.get(i))) {
	        			sentence.set(i, words.get(n));
	        			words.remove(n);
	        			n--;
	        			filled[i] = true;
	        			break;
	        		}
	        		else if(sentence.get(i).length() == 1){
	        			filled[i] = true;
	        		}
	        	}
	        }
	        
	        for(int i = 0; i < sentence.size(); i++) {
	        	if(!filled[i]) {
	        		for(int n = 0; n < words.size(); n++) {
	        			if(sentence.get(i).length() == words.get(n).length()) {
	        				sentence.set(i, words.get(n));
	        				words.remove(n);
	        				n--;
	        				break;
	        			}
	        		}
	        	}
	        }
	        
	        for(String string : sentence) {
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
	    			break;
	    		}
	    	}
	    	if(givenPos == -1) return false;
	    	if( gap.length() == word.length() && given == word.charAt(givenPos) ) {
	    		return true;
	    	}
	    	else return false;
	    	
    	}
    	System.out.println("comma");
    	return false;
    }
}
