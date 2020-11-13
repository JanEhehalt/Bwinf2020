package main;

public class Player {
	public int id;
	public int strength;
	public int wins;
	public int leagueWins = 0;
	public int koWins = 0;
	public int kox5Wins = 0;
	Player(int id, int strength){
		this.strength = strength;
		this.wins = 0;
		this.id = id;
	}
}
