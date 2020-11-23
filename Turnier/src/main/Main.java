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
    		File data;
    		if(args.length == 0) {
        		System.out.println("Ungültiger Dateiname: Automatische Einlesung der Datei: spielstaerken1.txt");
        		System.out.println();
        		data = new File("spielstaerken1.txt");
        	}
        	else {
        		data = new File(args[0]);
        	}
    		Scanner sc = new Scanner(data);
    		
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
    			System.out.println("Spieler: " + player.id + " Spielstärke: " + player.strength);
    		}

    		int best = getBest(players);
    		System.out.println();
    		System.out.println("Best Player:    " + best);
    		System.out.println();
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

    		float winrateLeague = (float)bestWinsLeague/(float)iterations;
    		float winrateKo = (float)bestWinsKo/(float)iterations;
    		float winrateKox5 = (float)bestWinsKox5/(float)iterations;
    		
    		System.out.println("Ligasiege: " + bestWinsLeague);
    		System.out.println("Ko-Siege: " + bestWinsKo);
    		System.out.println("Kox5-Siege: " + bestWinsKox5);

    		System.out.println();
    		
    		if(winrateLeague > winrateKo && winrateLeague > winrateKox5){
    		System.out.println("Das beste Turnierformat ist Liga, da der beste Spieler hier " + winrateLeague*100 + " Prozent aller Ligen gewonnen hat");
    		}
    		else if(winrateKo > winrateKox5){
    		System.out.println("Das beste Turnierformat ist Ko, da der beste Spieler hier " + winrateKo*100 + " Prozent aller Ligen gewonnen hat");
    		}
    		else{
    		System.out.println("Das beste Turnierformat ist Kox5, da der beste Spieler hier " + winrateKox5*100 + " Prozent aller Ligen gewonnen hat");}
    		
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
		Collections.shuffle(players);
		root.create(players);
		Player winner = root.getWinner();
		for(Player p : players) {
			p.wins = 0;
		}
		sortById(players);
		return winner;
	}
	
	static Player kox5(ArrayList<Player> players) {
		Node root;
		root = new Node();
		Collections.shuffle(players);
		root.create(players);
		Player winner = root.getx5Winner();
		for(Player p : players) {
			p.wins = 0;
		}
		sortById(players);
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
	
	static void sortById(ArrayList<Player> players) {
		Player[] sortedPlayers = new Player[players.size()];
		for(Player p : players) {
			sortedPlayers[p.id] = p;
		}
		for(int i = 0; i < sortedPlayers.length; i++){
			players.set(i, sortedPlayers[i]);
		}
	}

	
}
