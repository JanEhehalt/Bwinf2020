import java.lang.Math;
import java.util.ArrayList;

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
	
	public boolean fillWithTiles(ArrayList<Tile> tiles) {
		return fillBorders(0, tiles);
	}

	public boolean fillBorders(int tile, ArrayList<Tile> tileBag) {
		for(int i = 0; i < tiles.length; i++) {
			if(matrix[tile][i] != null) {
				/**
				 * TODO: 	check direction of the link
				 * 			find fitting part
				 * 				do something?? if no part found
				 * 			put it there
				 * 			set visited
				 * 			return fillBorders(i,visited)
				*/
				if(Math.abs(tile-i) == 1) {
					if(tile > i) {	// also z.B. tile = 2 ... i = 1
						for(int n = 0; n < tileBag.size(); n++) {
							if(tileBag.get(n).fits(tiles[tile].left)) {	// check if part fits
								tileBag.get(n).rotateTillIsRight(-tiles[tile].left);
								tiles[i] = tileBag.get(n);
								tiles[i].placed = true;
								tileBag.remove(n);
								return fillBorders(i, tileBag);
							}
						}
						return false;
					}
					else {			// also z.B. tile = 2 ... i = 3
						for(int n = 0; n < tileBag.size(); n++) {
							if(tileBag.get(n).fits(tiles[tile].right)) {	// check if part fits
								tileBag.get(n).rotateTillIsLeft(-tiles[tile].right);
								tiles[i] = tileBag.get(n);
								tiles[i].placed = true;
								tileBag.remove(n);
								return fillBorders(i, tileBag);
							}
						}
						return false;
					}
				}
				else if(Math.abs(tile-i) > 1) {
					for(int n = 0; n < tileBag.size(); n++) {
						if(tileBag.get(n).fits(tiles[tile].middle)) {	// check if part fits
							tileBag.get(n).rotateTillIsLeft(-tiles[tile].middle);
							tiles[i] = tileBag.get(n);
							tiles[i].placed = true;
							tileBag.remove(n);
							return fillBorders(i, tileBag);
						}
					}
					return false;
					
				}
			}
		}
		return true;
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