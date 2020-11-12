import java.util.ArrayList;

public class Graph{
	
	// Adjazenzmatrix
	Link[][] matrix;
	
	// Array, der einfach alle Tiles die insgesamt vorhanden sind speichert
	Tile[] tiles;
	
	// Array, der die Indizes von tiles auf die des Dreiecks mappt (Standardwert = -1)
	// Beispiel: Tile 4 liegt im Puzzle ganz oben: puzzle[3] = 0
	int[] puzzle;
	
	public Graph(Tile[] tiles){
		matrix = new Link[9][9];
		this.tiles = tiles;
		puzzle = new int[9];
		
		resetPuzzle();
	}
	
	public boolean fillWithTiles() {
		for(int i = 0; i < this.tiles.length; i++) {
			// puzzle-Array wird zurueckgesetzt
			resetPuzzle();
			puzzle[i] = 0;
			
			if(fillBorders(0)) {
				return true;
			}
			
			resetPuzzle();
			puzzle[i] = 0;
			System.out.println("					Starttile wird gedreht");
			tiles[i].rotate();
			
			if(fillBorders(0)) {
				return true;
			}
			
			resetPuzzle();
			puzzle[i] = 0;
			System.out.println("					Starttile wird gedreht");
			tiles[i].rotate();
			
			if(fillBorders(0)) {
				return true;
			}
		}
		return false;
	}

	public boolean fillBorders(int tile) {
		
		// Die Tiles, die zum Einfuegen nicht mehr zur Verfuegung stehen, werden als true markiert
		boolean[] visited = new boolean[9];
		visited[tile] = true;
		for (int j = 0; j < tiles.length; j++) {
			if(puzzle[j] != -1) {
				visited[j] = true;
			}
		}
		
		// Für jede anliegende Kante des Tiles wird versucht einen Partner zu finden
		ArrayList<Integer> placedTiles = new ArrayList<>();
		for(int j = 0; j < matrix.length; j++) {
			
			if(matrix[tile][j] != null && !matrix[tile][j].exists) {
				
				boolean tileFound = false;
				for(int k = 0; k < visited.length; k++) {
					if(!visited[k]) {
						visited[k] = true;
						
						// Das Tile wird probiert
						if(fit(k, j, 0)) {
							tileFound = true;
							resetTile(j);
							puzzle[k] = j;
							updateTrueLink(tile, j);
							placedTiles.add(j);
							
							if(fillBorders(puzzle[k])) {
								break;
							}
							else {
								//deleteAllCreated(puzzle[k], tile);
								resetTile(j);
								matrix[tile][j].exists = false;
								matrix[j][tile].exists = false;
								tileFound = false;
								continue;
							}
						}
					}
				}
				if(!tileFound) {
					for(int x = placedTiles.size() - 1; x >= 0; x--) {
						
						int temp = getIndexTiles(placedTiles.get(x));
						if(temp != -1) {
							matrix[tile][placedTiles.get(x)].exists = false;
							matrix[placedTiles.get(x)][tile].exists = false;
							puzzle[temp] = -1;
						}
						placedTiles.remove(x);
					}
					return false;
				}
			}
		}
		return true;
		
		// Jede Kante wurde belegt
	}
	
	public boolean fit(int indexTiles, int indexMatrix, int rotations) {
		if(indexMatrix == 2 || indexMatrix == 5 || indexMatrix == 7) {
			if(!tiles[indexTiles].flipped) {
				System.out.println("Tile " + indexTiles + " an der Stelle " + indexMatrix + " wird geflippt");
				tiles[indexTiles].flip();
			}
		}
		else {
			if(tiles[indexTiles].flipped) {
				System.out.println("Tile " + indexTiles + " an der Stelle " + indexMatrix + " wird zurück geflippt");
				tiles[indexTiles].flip();
			}
		}
		
		if(rotations > 2) {
			return false;
		}
		
		System.out.print("Probiere: " + indexTiles + " an der Stelle " + indexMatrix + ": " + tiles[indexTiles].values[0] + " " + tiles[indexTiles].values[1] + " " + tiles[indexTiles].values[2]);
		
		// 0: left, 1: middle, 2: right
		
		boolean fits = true;
		for(int i = 0; i < matrix.length; i++) {
			// TODO: des Ungleich -1 is neu, davor war da if des und des .exists halt, keine Ahnung es is halt sowas von zu spät für die
			// scheiße. Ich setz gleich Kaffee auf
			if(matrix[indexMatrix][i] != null && getIndexTiles(i) != -1) {
				int side = indexMatrix - i;
				
				int side1 = 0;
				int side2 = 0;
				
				int tile2 = 0;
				for(int j = 0; j < tiles.length; j++) {
					if(puzzle[j] == i) {
						tile2 = j;
					}
				}
				
				if(side == -1) {
					side1 = 2;
					side2 = 0;
				}
				else if(side == 1) {
					side1 = 0;
					side2 = 2;
				}
				else {
					side1 = 1;
					side2 = 1;
				}
				
				if(tiles[indexTiles].values[side1] + tiles[tile2].values[side2] != 0) {
					fits = false;
					break;
				}
			}
		}
		if(fits) {
			for(int i = 0; i < matrix.length; i++) {
				if(matrix[indexMatrix][i] != null && getIndexTiles(i) != -1) {
					updateTrueLink(indexMatrix, i);
				}
			}
			System.out.print(" true");
			System.out.println();
			return true;
		}
		else {
			System.out.print(" false");
			System.out.println();
			tiles[indexTiles].rotate();
			System.out.println("Rotation");
			return fit(indexTiles, indexMatrix, rotations + 1);
		}
	}
	
	public void deleteAllCreated(int indexMatrix, int creatorMatrix) {
		int indexTiles = -1;
		for(int i=0; i < puzzle.length; i++) {
			if(puzzle[i] == indexMatrix) {
				indexTiles = i;
			}
		}
		if(indexTiles != -1) {
			for(int x = matrix.length - 1; x >= 0; x--) {
				if(matrix[indexMatrix][x] != null && x != creatorMatrix) {
					deleteAllCreated(x, indexMatrix);
					int temp = getIndexTiles(x);
					if(temp != -1) {
						puzzle[temp] = -1;
					}
					matrix[indexMatrix][x].exists = false;
					matrix[x][indexMatrix].exists = false;
				}
			}
		}
	}
	
	public int getIndexTiles(int indexMatrix) {
		int indexTiles = -1;
		for(int i=0; i < puzzle.length; i++) {
			if(puzzle[i] == indexMatrix) {
				indexTiles = i;
			}
		}
		return indexTiles;
	}
	
	public void resetTile(int value) {
		for(int i = 0; i < tiles.length; i++) {
			if(puzzle[i] == value) {
				puzzle[i] = -1;
			}
		}
	}
	
	public void updateTrueLink(int x, int y) {
		matrix[x][y].exists = true;
		matrix[x][y].setValue(0);
		
		matrix[y][x].exists = true;
		matrix[y][x].setValue(0);
	}
	
	public void addLink(int i, int n) {
		matrix[i][n] = new Link();
		matrix[n][i] = new Link();
	}

	public void resetPuzzle() {
		for(int i = 0; i < puzzle.length; i++) {
			puzzle[i] = -1;
		}
		
		// Mögliche Kantenverbindungen werden angelegt (s. Skizze)
		addLink(0,2);
		addLink(1,2);
		addLink(1,5);
		addLink(2,3);
		addLink(3,7);
		addLink(4,5);
		addLink(5,6);
		addLink(6,7);
		addLink(7,8);
		
		for(Tile tile : tiles) {
			if(tile.flipped) {
				tile.flip();
			}
		}
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