public class Tile{
	int left;
	int middle;
	int right;
	
	boolean flipped;
	boolean placed;
	
	public Tile(int left, int middle, int right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}
	
	public boolean fits(int counterPart) {
		if(counterPart + left == 0 || counterPart + right == 0 || counterPart + middle == 0)
			return true;
		else
			return false;
	}
	
	public void rotate() {	// clockwise
		int oldLeft = left;
		int oldRight = right;
		int oldMiddle = middle;
		
		right = oldLeft;
		middle = oldRight;
		left = oldMiddle;
	}
	
	public void flip() {	// 180°
		if(flipped) flipped = false;
		else flipped = true;
		
		int oldLeft = left;
		
		left = right;
		right = oldLeft;
	}
	
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
}