public class Tile{
	
	int[] values;
	
	boolean flipped;
	boolean placed;
	
	public Tile(int left, int middle, int right) {
		this.values = new int[3];
		values[0] = left;
		values[1] = middle;
		values[2] = right;
	}
	
	/*
	public boolean fits(int counterPart) {
		if(counterPart + left == 0 || counterPart + right == 0 || counterPart + middle == 0)
			return true;
		else
			return false;
	}
	*/
	
	public void rotate() {
		int oldLeft = values[0];
		int oldMiddle = values[1];
		int oldRight = values[2];
		
		if(flipped) {
			values[2] = oldLeft;
			values[1] = oldRight;
			values[0] = oldMiddle;
		}
		else {
			values[2] = oldMiddle;
			values[1] = oldLeft;
			values[0] = oldRight;
		}
	}
	
	public void flip() {
		flipped = !flipped;
	}
	
	/*
	public boolean rotateTillIsLeft(int toBeLeft) {	// rotates until toBeLeft is on the left side
		if(left != toBeLeft) rotate();
		else return true;
		if(left != toBeLeft) rotate();
		else return true;
		if(left != toBeLeft) rotate();
		else return true;
		flip();
		if(left != toBeLeft) rotate();
		else return true;
		if(left != toBeLeft) rotate();
		else return true;
		if(left != toBeLeft) rotate();
		else return true;
		return false;
	}

	public boolean rotateTillIsRight(int toBeRight) {	// rotates until toBeRight is on the right side
		if(right != toBeRight) rotate();
		else return true;
		if(right != toBeRight) rotate();
		else return true;
		if(right != toBeRight) rotate();
		else return true;
		flip();
		if(right != toBeRight) rotate();
		else return true;
		if(right != toBeRight) rotate();
		else return true;
		if(right != toBeRight) rotate();
		else return true;
		return false;
	}
	
	public boolean rotateTillIsMiddle(int toBeMiddle) {	// rotates until toBeMiddle is in the middle
		if(middle != toBeMiddle) rotate();
		else return true;
		if(middle != toBeMiddle) rotate();
		else return true;
		if(middle != toBeMiddle) rotate();
		else return true;
		flip();
		if(middle != toBeMiddle) rotate();
		else return true;
		if(middle != toBeMiddle) rotate();
		else return true;
		if(middle != toBeMiddle) rotate();
		else return true;
		return false;
	}
	*/
}