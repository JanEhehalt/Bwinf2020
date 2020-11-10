public class Link{
	public boolean exists;
	private int value;
	
	public Link() {
		exists = false;
		value = 0;
		
	}
	
	public void calcNewValue(int valueA, int valueB) {
		this.value = valueA + valueB;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}