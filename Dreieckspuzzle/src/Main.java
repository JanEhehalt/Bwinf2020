import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main{
	
	
    public static void main(String[] args){
    	Tile[] tiles = new Tile[9];
    	try {
    		File data;
    		if(args.length == 0) {
        		System.out.println("Ungültiger Dateiname: Automatische Einlesung der Datei: puzzle0.txt");
        		System.out.println();
        		data = new File("puzzle0.txt");
        	}
        	else {
        		data = new File(args[0]);
        	}
	        Scanner sc = new  Scanner(data);
	           
	        sc.nextLine();
	        sc.nextLine();
	        
	        while (sc.hasNextLine()) {
	        	int left = sc.nextInt();
	        	int middle = sc.nextInt();
	        	int right = sc.nextInt();
	        	
	            for(int i = 0; i < tiles.length; i++) {
	            	if(tiles[i] == null) {
	            		tiles[i] = new Tile(left, middle, right);
	            		break;
	            	}
	            }
	            
	            sc.nextLine();
	        }
	        sc.close();
    	}
    	catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	Graph g = new Graph(tiles);
    	System.out.println("Lösbar: "+ g.fillWithTiles());
    	System.out.println();
    	
    	for(int i = 0; i < g.tiles.length; i++) {
    		if(g.puzzle[i] < 0) {
    			System.out.println("Puzzle unmöglich");
    			continue;
    		}
    		
    		boolean tileExists = false;
    		int indexTiles = -1;
    		for(int j = 0; j < g.puzzle.length; j++) {
    			if(g.puzzle[j] == i) {
    				indexTiles = j;
    				tileExists = true;
    				break;
    			}
    		}
    		
    		if(tileExists) {
    			Tile t = g.tiles[indexTiles];
    			System.out.println("Tile an Stelle " + i + ": " + t.values[0] + " " + t.values[1] + " " + t.values[2] + "   flipped: " + t.flipped);
    		}
    		else {
    			System.out.println("Puzzle unmöglich");
    		}
    	}
    }
    
}
