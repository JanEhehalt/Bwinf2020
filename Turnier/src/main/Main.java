package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Main{
	class Player{
    	public int id;
    	public int strength;
    	public int wins;
    	Player(int id, int strength){
    		this.strength = strength;
    		this.wins = 0;
    		this.id = id;
    	}
    }
public static Player league(ArrayList<Player> players, int games) {
		
		boolean[][] played = new boolean[players.size()][players.size()];
		for(int i = 0; i < played.length; i++) {
	    	for(int j = 0; j < played.length; j++) {
	    		played[i][j] = false;
	    		played[j][i] = false;
			}
		}
		
		boolean finished = true;
		do {
			finished = true;
			for(Player p1 : players) {
				for(Player p2 : players) {
					if(!played[p1.id][p2.id]) {
						played[p1.id][p2.id] = true;
						played[p2.id][p1.id] = true;
						for(int i = games; i > 0; i--) {
							players.get(match(p1,p2).id).wins++;
						}
					}
				}
			}
	
			for(Player p1 : players) {
				for(Player p2 : players) {
					if(!played[p1.id][p2.id])
						finished = false;
				}
			}
			
			
		}while(!finished);
	
		ArrayList<Player> leading = new ArrayList<>();
		int mostWins = 0;
		for(Player p1 : players) {
			if(p1.wins > mostWins) mostWins = p1.wins;
		}
		for(Player p1 : players) {
			if(p1.wins == mostWins) leading.add(p1);
		}
		if(leading.size() == 1) return leading.get(0);
		else {
			for(int i = 0; i < players.size(); i++) {
				for(Player p1 : players) {
					if(p1.id == i) return p1;
				}
			}
		}
		System.out.print("NO WINNER?!?!?!??!?");
		return null;
	}

	public static Player ko(ArrayList<Player> players) {
		int playerAmount = players.size();
		node root;
		if(playerAmount % 2 == 0) {
			root = new node();
			root.create(playerAmount);
		}
		else System.out.println("NIX EHRE EHRE UNGERADE SPIELERZAHL");
		
		
		return null;
	}
    public static void main(String[] args){
    	try {
    		File nice = new File("src/spielstaerken1.txt");
    		Scanner sc = new Scanner(nice);
    		
    		ArrayList<Player> players = new ArrayList<>();
    		ArrayList<Player> forReset = new ArrayList<>();

    		int i = 0;
    		sc.next(); // PLAYER AMOUNT VARIABLE BY USING ARRAYLIST
    		while(sc.hasNextInt()) {
    			players.add(new Player(i, sc.nextInt()));
    			i++;
    		}
    		forReset = players;
    		sc.close();
    		
    		for(Player player : players) {
    			System.out.println(player.id + " " + player.strength);
    		}

			System.out.println("");
				System.out.println("LEAGUE: ID: " + league(players,1).id);
			System.out.println("");
			
    		
    	}
    	catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    
    
    

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
    	void create(int amount) {
    		
    	}
    }


	public static Player match(Player p1, Player p2) {
		int i = p1.strength + p2.strength;
		int RNG = (int)(Math.random() * i);
		
		if(RNG < p1.strength) return p1;
		else return p2;
	}

	
}
