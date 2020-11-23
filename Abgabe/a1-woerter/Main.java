package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	

    public static void main(String[] args){
		try {
			File data;
    		if(args.length == 0) {
        		System.out.println("Ungültiger Dateiname: Automatische Einlesung der Datei: raetsel0.txt");
        		System.out.println();
        		data = new File("raetsel0.txt");
        	}
        	else {
        		data = new File(args[0]);
        	};
	        Scanner sc = new Scanner(data);
	        
	        ArrayList<String> words = new ArrayList<>();
	        ArrayList<String> sentence = new ArrayList<>();
	
        	while(sc.hasNext()) {
        		String cw = sc.next();	// cW - currentWord
        		
        		char last = cw.charAt(cw.length()-1);
        		
        		if(!cw.contains("_")) { 
        			words.add(cw);
        			break;
        		}
        		if(last == '.' || last == '!' || last == '?' || last == ',') {
        			sentence.add(cw.substring(0, cw.length() - 1));
        			sentence.add(new String() + last);
        		}
        		else {
        			sentence.add(cw.toString());
        		}
        	}
        	while(sc.hasNext()) {
        		words.add(sc.next());
        	}
	        sc.close();
	        
	        
	        
	        boolean finished = true;
	        
	        boolean[] filled = new boolean[sentence.size()];
	        for(int i = 0; i < filled.length; i++){
	        	if(isPunctuation(sentence.get(i))) filled[i] = true;
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
				        			filled[i] = true;
				        			posWords--;
				        			break;
				        		}
				        	}
			        	}
		        	}
		        }
		        
		        
		        for(int i = 0; i < sentence.size(); i++) {
		        	int posWords = 0;
		        	if(!filled[i]) {
			        	for(int j = 0; j < words.size(); j++) {
			        		if(fits(words.get(j), sentence.get(i))) {
			        			finished = false;
				        		break;
			        		}
			        	}
		        	}
		        }
		        
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
	        
	        
	        System.out.println("");
	        System.out.println("final order of the sentence: ");
	        System.out.println("*****");
	        for(String string : sentence) {
        		System.out.println(string);
        	}
	        System.out.println("*****");
    		System.out.println("");
	        System.out.println("final sentence: ");
	        System.out.println("*****");
	        for(String string : sentence) {
        		System.out.print(string + " ");
        	}
	        System.out.println();
	        System.out.println("*****");
	        
	    }
		
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    
   
    public static boolean fits(String word, String gap) {
    	if(!isPunctuation(gap)) {
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
    	System.out.println("** Comma given to 'fits' method **");
    	return false;
    }
    
    public static boolean isPunctuation(String string) {
    	if(string.contains("!") || string.contains(",") || string.contains("?") || string.contains(".")) return true;
    	else return false;
    }
}
