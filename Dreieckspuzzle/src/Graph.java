public class Graph{
	
	// Adjazenzmatrix
	private int[][] matrix;
	
	// Array, der einfach alle Tiles die insgesamt vorhanden sind speichert
	public Tile[] tiles;
	
	// Array, der die Indizes von tiles auf die des Dreiecks mappt (Standardwert = -1)
	// Beispiel: Tile 4 liegt im Puzzle ganz oben: puzzle[3] = 0
	public int[] puzzle;
	
	public int[][] placedTiles = new int[9][2];
	
	public Graph(Tile[] tiles){
		matrix = new int[9][9];
		for(int x = 0; x < matrix.length; x++) {
			for(int y = 0; y < matrix.length; y++) {
				matrix[x][y] = -1;
			}
		}
		
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
			System.out.println("aöldskfj");
			resetPuzzle();
			puzzle[i] = 0;
			tiles[i].rotate();
			
			if(fillBorders(0)) {
				return true;
			}
			System.out.println("!fsdgdf");
			resetPuzzle();
			puzzle[i] = 0;
			tiles[i].rotate();
			
			if(fillBorders(0)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean fillBorders(int indexMatrix) {
		boolean[][] visited = new boolean[2][9];
		int link = -1;
		for(int j = 0; j < 2; j++) {
			for (int i = 0; i < tiles.length; i++) {
				if(puzzle[i] != -1) {
					visited[j][i] = true;
				}
				else {
					visited[j][i] = false;
				}
			}
		}
		for(int j = 0; j < matrix.length; j++) {
			
			if(matrix[indexMatrix][j] == 0) {
				link++;
				
				boolean tileFound = false;
				for(int k = 0; k < visited.length; k++) {
					if(!visited[link][k]) {
						visited[link][k] = true;
						
						for(int i = 0; i < 3; i++) {
							if(fit(k, j)) {
								tileFound = true;
								addPlacedTile(indexMatrix, j);
								
								
								System.out.print(getIndexTiles(0) + " ");
								System.out.print(getIndexTiles(2) + " ");
								System.out.print(getIndexTiles(1) + " ");
								System.out.print(getIndexTiles(5) + " ");
								System.out.print(getIndexTiles(4) + " ");
								System.out.print(getIndexTiles(6) + " ");
								System.out.print(getIndexTiles(7) + " ");
								System.out.print(getIndexTiles(3) + " ");
								System.out.print(getIndexTiles(8) + " ");
								
								System.out.println();
								
								if(fillBorders(j)) {
									k = visited.length;
									break;
								}
								else {
									removePlaced(indexMatrix, j);
									tileFound = false;
								}
							}
							tiles[k].rotate();
						}
					}
				}
				if(!tileFound) {
					if(link == 0) {
						removeAllPlaced(indexMatrix);
						return false;
					}
					else {
						link = -1;
						j = 0;
						visited[1] = new boolean[9];
						removeAllPlaced(indexMatrix);
					}
					
				}
			}
		}
		return true;
	}
	
	private boolean fit(int indexTiles, int indexMatrix) {
		if(indexMatrix == 2 || indexMatrix == 5 || indexMatrix == 7) {
			if(!tiles[indexTiles].flipped) {
				//System.out.println("Tile " + indexTiles + " an der Stelle " + indexMatrix + " wird geflippt");
				tiles[indexTiles].flip();
			}
		}
		else {
			if(tiles[indexTiles].flipped) {
				//System.out.println("Tile " + indexTiles + " an der Stelle " + indexMatrix + " wird zurück geflippt");
				tiles[indexTiles].flip();
			}
		}
		
		//System.out.print("Probiere: " + indexTiles + " an der Stelle " + indexMatrix + ": " + tiles[indexTiles].values[0] + " " + tiles[indexTiles].values[1] + " " + tiles[indexTiles].values[2]);
		
		// 0: left, 1: middle, 2: right
		
		boolean fits = true;
		for(int i = 0; i < matrix.length; i++) {
			if(matrix[indexMatrix][i] != -1 && getIndexTiles(i) != -1) {
				int side = indexMatrix - i;
				
				int side1 = 0;
				int side2 = 0;
				int tile2 = getIndexTiles(i);
				
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
				if(matrix[indexMatrix][i] == 0 && getIndexTiles(i) != -1) {
					updateTrueLink(indexMatrix, i);
				}
			}
			puzzle[indexTiles] = indexMatrix;
			//System.out.print(" true");
			//System.out.println();
			return true;
		}
		else {
			//System.out.print(" false");
			//System.out.println();
			//tiles[indexTiles].rotate();
			//System.out.println("Rotation");
			//return fit(indexTiles, indexMatrix, rotations + 1);
			return false;
		}
	}
	
	private void removeAllPlaced(int indexMatrix) {
		for(int x = 0; x < placedTiles[0].length; x++) {
			if(placedTiles[indexMatrix][x] != 0) {
				removePlaced(indexMatrix, placedTiles[indexMatrix][x]);
			}
		}
		placedTiles[indexMatrix] = new int[2];
	}
	
	private void removePlaced(int indexMatrix, int value) {
		for(int i=0; i < placedTiles[0].length; i++) {
			if(placedTiles[indexMatrix][i] == value) {
				removeAllPlaced(value);
				placedTiles[indexMatrix][i] = 0;
				resetTile(value);
				updateFalseLink(indexMatrix, value);
			}
		}
	}
	
	private int getIndexTiles(int indexMatrix) {
		int indexTiles = -1;
		for(int i=0; i < puzzle.length; i++) {
			if(puzzle[i] == indexMatrix) {
				indexTiles = i;
				break;
			}
		}
		return indexTiles;
	}
	
	private void resetTile(int value) {
		for(int i = 0; i < tiles.length; i++) {
			if(puzzle[i] == value) {
				puzzle[i] = -1;
			}
		}
	}
	
	private void updateTrueLink(int x, int y) {
		matrix[x][y] = 1;
		matrix[y][x] = 1;
	}
	
	private void updateFalseLink(int x, int y) {
		matrix[x][y] = 0;
		matrix[y][x] = 0;
	}
	
	private void addPlacedTile(int indexMatrix, int value) {
		for(int i = 0; i < placedTiles[0].length; i++) {
			if(placedTiles[indexMatrix][i] == 0) {
				placedTiles[indexMatrix][i] = value;
				break;
			}
		}
	}
	
	private void addLink(int x, int y) {
		matrix[x][y] = 0;
		matrix[y][x] = 0;
	}

	private void resetPuzzle() {
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
		
		placedTiles = new int[9][2];
	}
}