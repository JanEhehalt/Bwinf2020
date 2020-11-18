public class Tile{
	
	int[] values;
	boolean flipped;
	
	public Tile(int left, int middle, int right) {
		this.values = new int[3];
		values[0] = left;
		values[1] = middle;
		values[2] = right;
	}
	
	public void rotate() {
		int oldLeft = values[0];
		int oldMiddle = values[1];
		int oldRight = values[2];
		
			values[2] = oldLeft;
			values[1] = oldRight;
			values[0] = oldMiddle;
	}
	
	public void flip() {
		flipped = !flipped;
		int temp = values[0];
		values[0] = values[2];
		values[2] = temp;
	}
}