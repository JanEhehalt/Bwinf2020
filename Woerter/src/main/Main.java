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
	        Scanner sc = new Scanner(data);
	        
	        /**
	         * The array words is storing the words which aren't added to the sentence yet
	         * The array sentence is storing all the gaps and the syntax of the sentence in the right order
	         */
	        ArrayList<String> words = new ArrayList<>();
	        ArrayList<String> sentence = new ArrayList<>();
	
	        /**
	         * with the scanner we can go over the file
	         * first we add the gaps to the sentence
	         * first we save the last char of the current String
	         * we differentiate between different cases
	         * 
	         * First we check whether there is no more gap
	         * This is the case as soon as our word does not have any '_'
	         * 		in the case there are no more gaps we still add our current String to the words array
	         * 		because we can't go back with the scanner
	         * Then we check if the last char of the String is syntax. 
	         * 		If it is we have to store the syntax in a separate String
	         * 		the rest of the String is also added to the sentence array separate
	         * 		! first the word then the syntax !
	         * If there's no syntax at the end and it has '_' we can jusst add the String as a gap
	         */
        	while(sc.hasNext()) {
        		String cw = sc.next();	// cW - currentWord
        		
        		char last = cw.charAt(cw.length()-1);
        		
        		// removed here checking for last being on syntax
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
        	

        	System.out.println("*****");
        	System.out.println("Satzl�nge: " + sentence.size() + " Anzahl der W�rter: " + words.size());
        	System.out.println("*****");
        	System.out.println("");
        	System.out.println("Satzteile: ");
        	System.out.println("*****");
	        for(String string : sentence) {
        		System.out.println(string);
        	}
	        System.out.println("*****");
	        System.out.println("");
	        System.out.println("W�rter:");
	        System.out.println("*****");
	        for(String string : words) {
        		System.out.println(string);
        	}
	        System.out.println("*****");
        	
	        sc.close();
	        
	        boolean finished = true;
	        
	        /**
	         * filled array
	         * helpful for saving which position in the sentence is already filled
	         * is always synchronized with the 'sentence' array list
	         * 
	         * directly making syntax positions true so that we won't look for a fitting word for those gaps  
	         */
	        boolean[] filled = new boolean[sentence.size()];
	        for(int i = 0; i < filled.length; i++){
	        	if(sentence.get(i).length() == 1) filled[i] = true;
	        	else filled[i] = false;
	        }
	        
	        do {
	        	/**
	        	 * finished will stay true as soon as there is no gap left which is containing an char that is not '_'
	        	 */
	        	finished = true;
	        	/**
	        	 * looping over the sentence
	        	 * 	looping over the words array if the current gap is not filled yet
	        	 */
		        for(int i = 0; i < sentence.size(); i++) {
		        	if(!filled[i]) {
		        		/**
		        		 * counting how many words would fit inside each gap
		        		 * we don't count the words twice, which come up twice, because then the program would think it's not distinct
		        		 */
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
			        	/**
			        	 * if there's only word fitting inside a gap it is distinct
			        	 * so we add it to the sentence
			        	 * 
			        	 * here we are looping over the words again in order to find the fitting word for the gap
			        	 * since there's only one posWord we can only find the right one
			        	 * 
			        	 * we add the word to the sentence
			        	 * we remove it from the left words array
			        	 * we synchronize the filled array
			        	 * 
			        	 * although we remove an element we don't have to look backwards because we break the loop as soon as we have found the right word,
			        	 * so we won't reach an index which is out of bounds
			        	 */
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
		        
		        /**
		         * here we check whether all the gaps with given char are filled
		         * we loop over the sentence and look at all the gaps which aren't filled
		         * as soon as there is one gap where 'fits' returns true we can break the current loop and 
		         * do the whole doWhile loop again
		         */
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
	        
	        /**
	         * After the doWhile loop has finished there can only be gaps left which don't have any chars given
	         * Because the given .txt's have to give a distinct answer we would think
	         * that there is only one word left for each gap
	         * 
	         * so we loop over the sentence and look at the gaps which aren't filled yet
	         * the last property of the Strings we can check is the length, so we check
	         * if the length of the gap is the same as the word's
	         * if it is we can say that the word has to fit inside the gap
	         * so we add it and again synchronize the filled array
	         * 
	         * We subtract one from j, because we remove one element from the words array. 
	         * If we wouldn't subtract one we would skip one element or get an index out of bounds exception
	         */
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
	        
	        // Printing the final result:
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
    
    /**
     * The method 'fits' is given two Strings. 'true' is being return if 'word' fits into 'gap'
     * The gap is a string containing only '_' and up to one normal letter
     * the method now first checks, so that the gap it's trying to fill is no syntax
     * 		if it was it would print out an error
     * after this is checked the method if looping over the gap char and is looking for an char inside it that is not '_'
     *		this one has to be a normal letter
     * then it saves it and its position inside the String
     * 
     * then it takes the normal word and compares the given char to the word's char at the same position
     * if it's the same it checks whether the length of the String is equal.
     * if it is it can return true, because the word would fit inside the gap
     * @param word
     * @param gap
     * @return
     */
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
    	System.out.println("** Comma given to 'fits' method **");
    	return false;
    }
}
