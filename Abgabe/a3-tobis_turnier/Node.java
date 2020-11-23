import java.util.ArrayList;

class Node{
	public Node left;
	public Node right;
	public Player player;
	
	Node(Player player){
		this.player = player;
	}
	Node(){
		this.player = null;
	}
	
	Player getWinner() {
		if(this.player != null) return this.player;
		else return match(left.getWinner(), right.getWinner());
	}
	Player getx5Winner() {
		if(this.player != null) return this.player;
		else return matchx5(left.getWinner(), right.getWinner());
	}
	
	public void create(ArrayList<Player> players) {
		if(players.size() == 2) {
			left = new Node(players.get(0));
			right = new Node(players.get(1));
		}
		else {
			left = new Node();
			right = new Node();
			int split = players.size() / 2;
			ArrayList<Player> forLeft = new ArrayList<>();
			ArrayList<Player> forRight = new ArrayList<>();
			for(int i = 0; i < split; i++) {
				forLeft.add(players.get(i));
				forRight.add(players.get(i+split));
			}
			left.create(forLeft);
			right.create(forRight);
		}
	}
	
	public static Player match(Player p1, Player p2) {
		int i = p1.strength + p2.strength;
		int RNG = (int)(Math.random() * i);
		
		if(RNG < p1.strength) return p1;
		else return p2;
	}
	
	public static Player matchx5(Player p1, Player p2) {
		for(int j = 0; j < 5; j++) {
			int i = p1.strength + p2.strength;
			int RNG = (int)(Math.random() * i);
			
			if(RNG < p1.strength) p1.wins++;
			else p2.wins++;
		}
		if(p1.wins > p2.wins) return p1;
		else return p2;
		
	}
}
