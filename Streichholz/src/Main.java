import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<Match> matches = getExample();;
		
	}
	
	public static ArrayList<Match> getExample(){
		ArrayList<Match> matches = new ArrayList<>();
		matches.add(new Match(1,1,6));
		System.out.println(matches.get(0).x);
		System.out.println(matches.get(0).y);
		System.out.println(matches.get(0).vx);
		System.out.println(matches.get(0).vy);
		return matches;
	}
}
