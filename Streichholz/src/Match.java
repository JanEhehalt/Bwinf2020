import java.util.Objects;

public class Match {
	int x;
	int y;
	double vx;
	double vy;
	int rotation;
	int length;
	
	public Match(int x, int y, int rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation * 30;
		this.length = 5;
		this.vx = Math.cos(Math.toRadians(rotation))*length;
		this.vy = Math.sin(Math.toRadians(rotation))*length;
	}
}
