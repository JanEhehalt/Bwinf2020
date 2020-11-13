package main;

class node{
	public node left;
	public node right;
	public Player player;
	
	node(Player player){
		this.player = player;
	}
	node(){
		this.player = null;
	}
	
	Player getWinner() {
		if(left == null || right == null) return this.player;
		else return match(left.getWinner(), right.getWinner());
	}
	void create(ArrayList<Player> players) {
		if(players.size() == 2) {
			left = new node(players.get(0));
			right = new node(players.get(1));
		}
		else {
			left = new node();
			right = new node();
			
		}
	}
}
