package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	

    public static void main(String[] args){
		try {
	    	File data = new File("src/raetsel4.txt");
	        //File data = new File(args[0]);
	        Scanner sc = new  Scanner(data);
	        
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
        	
        	System.out.println(fits("die", "__s"));

	        
	        for(String string : sentence) {
        		System.out.println(string);
        	}
    		System.out.println("");
	        for(String string : words) {
        		System.out.println(string);
        	}
    		System.out.println("");
    		//System.out.println(sentence.get(3));
    		//System.out.println(words.get(5));
    		//System.out.println(fits(words.get(5), sentence.get(3)));
    		System.out.println("");
        	
	        sc.close();
	        
	        boolean finished = true;
	        
	        boolean[] filled = new boolean[sentence.size()];
	        for(int i = 0; i < filled.length; i++){
	        	if(sentence.get(i).length() == 1) filled[i] = true;
	        	else filled[i] = false;
	        }
	        
	        do { 
	        	finished = true;
		        for(int i = 0; i < sentence.size(); i++) {
		        	if(!filled[i]) {
			        	int posWords = 0;
			        	ArrayList<Integer> positions = new ArrayList<>();
			        	for(int j = 0; j < words.size(); j++) {
			        		if(fits(words.get(j), sentence.get(i))) {
			        			boolean availableTwice = false;
			        			for(Integer o : positions) {
			        				if(words.get(o).equals(words.get(j))) {
			        					availableTwice = true;
			        					break;
			        				}
			        			}
			        			if(!availableTwice) {
					        		posWords++;
					        		positions.add(j);
				        		}
			        		}
			        	}
			        	if(posWords == 1) {
				        	for(int j = 0; j < words.size(); j++) {
				        		if(fits(words.get(j), sentence.get(i))) {
				        			sentence.set(i, words.get(j));
				        			words.remove(j);
				        			j--;
				        			filled[i] = true;
				        			posWords--;
				        			break;
				        		}
				        	}
			        	}
		        	}
		        }
	        	System.out.println("___");
		        for(int i = 0; i < sentence.size(); i++) {
		        	int posWords = 0;
		        	if(!filled[i]) {
			        	for(int j = 0; j < words.size(); j++) {
			        		if(fits(words.get(j), sentence.get(i))) {
				        		posWords++;
			        		}
			        	}
		        	}
		        	System.out.println(i+" "+posWords);
		        	if(posWords >= 1) finished = false;
		        }

        		//System.out.println("running");
        		//System.out.println("%");
        		//	for(String string : sentence) {
            	//	System.out.println(string);}
        		//System.out.println("%");
            	
	        }while(!finished);
	        
	        for(int i = 0; i < sentence.size(); i++) {
	        	if(!filled[i]) {
	        		for(int j = 0; j < words.size(); j++) {
	        			if(sentence.get(i).length() == words.get(j).length()) {
	        				sentence.set(i, words.get(j));
		        			words.remove(j);
		        			j--;
		        			filled[i] = true;
	        			}
	        		}
	        	}
	        }
	        
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
	    			break;
	    		}
	    	}
	    	if(givenPos == -1) return false;
	    	if( gap.length() == word.length() && gap.charAt(givenPos) == word.charAt(givenPos) ) {
	    		return true;
	    	}
	    	else return false;
	    	
    	}
    	System.out.println("comma");
    	return false;
    }
}
