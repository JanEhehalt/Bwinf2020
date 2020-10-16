import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Main{
	
    public static void main(String[] args){
    	try {
	    	File data = new File("src/puzzle0.txt");
	        //File data = new File(args[0]);
	        Scanner sc = new  Scanner(data);
	        
	        ArrayList<Tile> tiles = new ArrayList<Tile>();
	        sc.nextLine();
	        sc.nextLine();
	        
	        while (sc.hasNextLine()) {
	        	int left = sc.nextInt();
	        	int middle = sc.nextInt();
	        	int right = sc.nextInt();
	        	
	            Tile temp = new Tile(left, middle, right);
	            tiles.add(temp);
	            sc.nextLine();
	        }
	        sc.close();
    	}
    	catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
}