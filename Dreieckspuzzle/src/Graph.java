public class Graph{
	
	Link[][] matrix;
	Tile[] tiles;
	
	Graph(){
		
		matrix = new Link[9][9];
		tiles = new Tile[9];
		
		addLink(0,2);
		addLink(1,2);
		addLink(1,5);
		addLink(2,3);
		addLink(3,7);
		addLink(4,5);
		addLink(5,6);
		addLink(6,7);
		addLink(7,8);
		
		
	}
	
	public Tile[] fillWithTiles() {
		return fillBorders();
	}

	public Tile[] fillBorders() {
		return null;
	}
	
	public void addLink(int i, int n) {
		matrix[i][n] = new Link();
		matrix[n][i] = new Link();
	}


	public void printMatrix() {
		for(int i = 0; i < matrix.length; i++) {
			for(int n = 0; n < matrix.length; n++) {
				if(matrix[i][n] == null) System.out.print("0 ");
				else System.out.print("1 ");
			}	
			System.out.println();
		}
	}

}