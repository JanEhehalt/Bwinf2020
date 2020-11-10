import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main{
	
	
    public static void main(String[] args){
    	Tile[] tiles = new Tile[9];
    	try {
	    	File data = new File("src/puzzle0.txt");
	        //File data = new File(args[0]);
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
    	g.printMatrix();
    	System.out.println(g.fillWithTiles());
    	
    	for(int i = 0; i < g.tiles.length; i++) {
    		if(g.puzzle[i] < 0) {
    			System.out.println("Nichts zugeteilt");
    			continue;
    		}
    		Tile t = g.tiles[g.puzzle[i]];
    		System.out.println(i + ": " + t.values[0] + " " + t.values[1] + " " + t.values[2] + ": " + t.flipped);
    	}
    	for(int i=0; i< g.matrix.length; i++) {
    		for(int j=0; j<g.matrix[0].length; j++) {
    			if(g.matrix[i][j] != null && g.matrix[i][j].exists) {
    				System.out.print(g.matrix[i][j].getValue() + " ");
    			}
    		}
    		System.out.println();
    	}
    }
    
}