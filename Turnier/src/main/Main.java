package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    		int best = getBest(players);
    		System.out.println();
    		System.out.println("Best Player:    " + best);
    		int bestWinsLeague = 0;
    		int bestWinsKo = 0;
    		int bestWinsKox5 = 0;
    		
    		int iterations = 1000000;
    		
    		for(int j = 0; j<iterations; j++) {
    			Player winnerLeague = league(players);
    			Player winnerKo = ko(players);
    			Player winnerKox5 = kox5(players);
    			for(Player p : players) {
    				if(p.id == best && p == winnerLeague) {
    					bestWinsLeague++;
    				}
    				if(p.id == best && p == winnerKo) {
    					bestWinsKo++;
    				}
    				if(p.id == best && p == winnerKox5) {
    					bestWinsKox5++;
    				}
    			}
    		}

    		System.out.println();
    		System.out.println("Winrate League: "+(float)bestWinsLeague/(float)iterations);
    		System.out.println("Winrate Ko:     "+(float)bestWinsKo/(float)iterations);
    		System.out.println("Winrate Kox5:   "+(float)bestWinsKox5/(float)iterations);
    		
    		
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
			}i
			
			
			
		}while(!finished);
	
		int leading = 0;
		for(Player p : players) {
			if(p.wins > players.get(leading).wins) leading = p.id;
		}
		for(Player p : players) {
			p.wins = 0;
		}
		return players.get(leading);
	}

	static Player ko(ArrayList<Player> players) {
		Node root;
		root = new Node();
		//Collections.shuffle(players);
		root.create(players);
		Player winner = root.getWinner();
		for(Player p : players) {
			p.wins = 0;
		}
		return winner;
	}
	
	static Player kox5(ArrayList<Player> players) {
		Node root;
		root = new Node();
		//Collections.shuffle(players);
		root.create(players);
		Player winner = root.getx5Winner();
		for(Player p : players) {
			p.wins = 0;
		}
		return winner;
	}
	
	static int getBest(ArrayList<Player> players) {
		int highest = 0;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).strength > players.get(highest).strength) {
				highest = i;
			}
		}
		return highest;
	}

	
}
