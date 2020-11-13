package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

class Main{
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
			System.out.println("");
			System.out.println("LEAGUE: ID: " + league(players).id);
			System.out.println("");
			System.out.println("KO Winner: " + ko(players).id);
			System.out.println("");
			System.out.println("KOx5 Winner: " + kox5(players).id);
			
    		
    	}
    	catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }

	public static Player match(Player p1, Player p2) {
		int i = p1.strength + p2.strength;
		int RNG = (int)(Math.random() * i);
		
		if(RNG < p1.strength) return p1;
		else return p2;
	}
	static Player league(ArrayList<Player> players) {
		
		boolean[][] played = new boolean[players.size()][players.size()];
		for(int i = 0; i < played.length; i++) {
	    	for(int j = 0; j < played.length; j++) {
	    		played[i][j] = false;
	    		played[j][i] = false;
	    		played[i][i] = true;
			}
		}
		
		boolean finished = true;
		do {
			finished = true;
			for(Player p1 : players) {
				for(Player p2 : players) {
					if(!played[p1.id][p2.id] && p1.id != p2.id) {
						played[p1.id][p2.id] = true;
						played[p2.id][p1.id] = true;
						players.get(match(p1,p2).id).wins++;
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
				for(Player l : leading) {
					if(l.id == i) {
						for(Player p : players) {
							p.wins = 0;
						}
						return l;
					}
				}
			}
		}
		System.out.print("NO WINNER?!?!?!??!?");
		return null;
	}

	static Player ko(ArrayList<Player> players) {
		Node root;
		if(players.size() % 2 == 0) {
			root = new Node();
			Collections.shuffle(players);
			root.create(players);
			
			return root.getWinner();
		}
		else System.out.println("NIX EHRE EHRE UNGERADE SPIELERZAHL");
		return null;
	}
	
	static Player kox5(ArrayList<Player> players) {
		Node root;
		if(players.size() % 2 == 0) {
			root = new Node();
			root.create(players);
			return root.getx5Winner();
		}
		else System.out.println("NIX EHRE EHRE UNGERADE SPIELERZAHL");
		return null;
	}

	
}
