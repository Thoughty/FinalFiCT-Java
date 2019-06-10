import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;



public class Arena {

	public enum Row {Front, Back};	//enum for specifying the front or back row
	public enum Team {A, B};		//enum for specifying team A or B
	
	private Player[][] teamA = null;	//two dimensional array representing the players of Team A
	private Player[][] teamB = null;	//two dimensional array representing the players of Team B
	private int numRowPlayers = 0;		//number of players in each row
	
	public static final int MAXROUNDS = 100;	//Max number of turn
	public static final int MAXEACHTYPE = 3;	//Max number of players of each type, in each team.
	private final Path logFile = Paths.get("battle_log.txt");
	
	private int numRounds = 0;	//keep track of the number of rounds so far
	/**
	 * Constructor. 
	 * @param _numRowPlayers is the number of players in each row.
	 */
	public Arena(int _numRowPlayers)
	{	
		//INSERT YOUR CODE HERE
		this.numRowPlayers = _numRowPlayers;
		teamA = new Player[2][_numRowPlayers];
		teamB = new Player[2][_numRowPlayers];
		
		////Keep this block of code. You need it for initialize the log file. 
		////(You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////
		
	}
	
	/**
	 * Returns true if "player" is a member of "team", false otherwise.
	 * Assumption: team can be either Team.A or Team.B
	 * @param player
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team)
	{
		//INSERT YOUR CODE HERE
		switch(team)
		{
			case A:
				for(int i = 0; i<2; i++)
				{
					for(int g = 0; g<numRowPlayers; g++)
					{
						if(player.equals(teamA[i][g]))
						{
							return true;
						}
					}
				}
				break;
			case B:
				for(int i = 0; i<2; i++)
				{
					for(int g = 0; g<numRowPlayers; g++)
					{
						if(player.equals(teamB[i][g]))
						{
							return true;
						}
					}
				}
				break;
				
		
		
	}
		return false;
	}
	
	
	/**
	 * This methods receives a player configuration (i.e., team, type, row, and position), 
	 * creates a new player instance, and places him at the specified position.
	 * @param team is either Team.A or Team.B
	 * @param pType is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
	 * @param row	either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
	 */
	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position)
	{	
		//INSERT YOUR CODE HERE
		Player player = new Player(pType);
		switch(team)
		{
			case A:
				switch(row)
				{
					case Front: 
						teamA[0][position-1] = player;
						teamA[0][position-1].setPosition(position, row, team);
						break;
					case Back:
						teamA[1][position-1] = player;
						teamA[1][position-1].setPosition(position, row, team);
						break;
				}
				break;
			case B :
				switch(row)
				{
					case Front: 
						teamB[0][position-1] = player;
						teamB[0][position-1].setPosition(position, row, team);
						break;
					case Back:
						teamB[1][position-1] = player;
						teamB[1][position-1].setPosition(position, row, team);
						break;
				}
				break;
		}
	}
	public Player[][] getTeam(Team x)
	{
		if(x.equals(Team.A))
		{
			return teamA;
		}
		else if (x.equals(Team.B))
		{
			return teamB;
		}
		return null;
	}
	
	/**
	 * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
	 * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
	 * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * @return
	 */
	public boolean validatePlayers()
	{
		//INSERT YOUR CODE HERE
		int[] ValidTeamA = {0,0,0,0,0,0};
		int[] ValidTeamB = {0,0,0,0,0,0};
		for(int i = 0; i<2;i++)
		{
			for(int g = 0; g < numRowPlayers; g++)
			{
				if(teamA[i][g] == null)
				{
					return false;
				}
				else 
				{
					switch(teamA[i][g].getType())
					{
						case Healer:
							ValidTeamA[0]++;
							break;
						case BlackMage:
							ValidTeamA[1]++;
							break;
						case Tank:
							ValidTeamA[2]++;
							break;
						case Cherry:
							ValidTeamA[3]++;
							break;
						case Phoenix:
							ValidTeamA[4]++;
							break;
						case Samurai:
							ValidTeamA[5]++;
							break;
					}
				}
			}
		}
		for(int i = 0; i<2;i++)
		{
			for(int g = 0; g < numRowPlayers; g++)
			{
				if(teamB[i][g] == null)
				{
					return false;
				}
				else 
				{
					switch(teamB[i][g].getType())
					{
						case Healer:
							ValidTeamB[0]++;
							break;
						case BlackMage:
							ValidTeamB[1]++;
							break;
						case Tank:
							ValidTeamB[2]++;
							break;
						case Cherry:
							ValidTeamB[3]++;
							break;
						case Phoenix:
							ValidTeamB[4]++;
							break;
						case Samurai:
							ValidTeamB[5]++;
							break;
					}
				}
			}
		}
		for(int i=0; i < 6; i++)
		{
			if(ValidTeamA[i] > 3 || ValidTeamB[i] > 3)
			{
				return false;
			}

		}
		return true;
		
		
	}
	
	
	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team)
	{
		//INSERT YOUR CODE HERE
		double sum = 0;
		for(int i = 0; i<2; i++)
		{
			for(int g = 0; g < team[i].length; g++)
			{
				sum = sum + team[i][g].getCurrentHP();
			}
		}
		return sum;
		
	}
	
	/**
	 * Return the team (either teamA or teamB) whose number of alive players is higher than the other. 
	 * 
	 * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
	 * players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * @return
	 */
	public Player[][] getWinningTeam()
	{
		//INSERT YOUR CODE HERE	
		int AliveTeamA = 0;
		int AliveTeamB = 0;
		double sumA = 0;
		double sumB = 0;
		for(int i = 0;i < 2; i++)
		{
			for(int g = 0; g < numRowPlayers; g++)
			{
				if(teamA[i][g].isAlive())
				{
					AliveTeamA++;
				}
				if(teamB[i][g].isAlive())
				{
					AliveTeamB++;
				}
			}
		}
		if(AliveTeamA == AliveTeamB)
		{
			for(int i = 0; i<2; i++)
			{
				for(int g = 0; g < numRowPlayers; g++)
				{
					sumA = sumA + teamA[i][g].getCurrentHP();
				}
			}
			for(int i = 0; i<2; i++)
			{
				for(int g = 0; g < numRowPlayers; g++)
				{
					sumB = sumB + teamB[i][g].getCurrentHP();
				}
			}
			if(sumA > sumB)
			{
				System.out.println("Team A Won!!!!!");
				return teamA;
			}
			else if(sumA == sumB)
			{
				System.out.println("Team A Won!!!!!");
				return teamA;
			}
			else
			{
				System.out.println("Team B Won!!!!!");
				return teamB;
			}
		}
		else if (AliveTeamA > AliveTeamB)
		{
			System.out.println("Team A Won!!!!!");
			return teamA;
		}
		else
		{
			System.out.println("Team B Won!!!!!");
			return teamB;
		}
	}
	
	/**
	 * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
	 * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
	 * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
	 * not it is teamB's turn to do the same. 
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated.
	 * 2. The number of rounds exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by getWinningTeam().
	 */
	public void startBattle()
	{
		//INSERT YOUR CODE HERE
		while(TeamIsAlive(Arena.Team.A) && TeamIsAlive(Arena.Team.B) && this.numRounds <= MAXROUNDS)
		{
			this.numRounds++;
			System.out.println("Round ["+this.numRounds+"] !");
			
			//TeamA 
			for(int i=0; i < 2; i++)
			{
				for(int g = 0; g < numRowPlayers; g++)
				{
					teamA[i][g].takeAction(this);
				}
			}
			for(int i=0; i < 2; i++)
			{
				for(int g = 0; g < numRowPlayers; g++)
				{
					teamB[i][g].takeAction(this);
				}
			}
			displayArea(this, true);
			logAfterEachRound();
		}
		
	}
	
	public boolean TeamIsAlive(Team x) // to check some member in which team is alive
	{
		switch(x)
		{
			case A :
				for(int i = 0; i < 2; i++)
				{
					for(int g = 0; g < numRowPlayers; g++)
					{
						if(teamA[i][g].isAlive())
						{
							return true;
						}
					}
				}
				break;
			case B :
				for(int i = 0; i < 2; i++)
				{
					for(int g = 0; g < numRowPlayers; g++)
					{
						if(teamB[i][g].isAlive())
						{
							return true;
						}
					}
				}
				break;
		}
		return false;
	}
	
	/**
	 * This method displays the current area state, and is already implemented for you.
	 * In startBattle(), you should call this method once before the battle starts, and 
	 * after each round ends. 
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose)
	{
		StringBuilder str = new StringBuilder();
		if(verbose)
		{
			str.append(String.format("%43s   %40s","Team A","")+"\t\t"+String.format("%-38s%-40s","","Team B")+"\n");
			str.append(String.format("%43s","BACK ROW")+String.format("%43s","FRONT ROW")+"  |  "+String.format("%-43s","FRONT ROW")+"\t"+String.format("%-43s","BACK ROW")+"\n");
			for(int i = 0; i < arena.numRowPlayers; i++)
			{
				str.append(String.format("%43s",arena.teamA[1][i])+String.format("%43s",arena.teamA[0][i])+"  |  "+String.format("%-43s",arena.teamB[0][i])+String.format("%-43s",arena.teamB[1][i])+"\n");
			}
		}
	
		str.append("@ Total HP of Team A = "+getSumHP(arena.teamA)+". @ Total HP of Team B = "+getSumHP(arena.teamB)+"\n\n");
		System.out.print(str.toString());
		
		
	}
	
	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
	 * You are not to modify this method, however, this method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the output look something like:
	 * 
	 * 1	47415.0	49923.0
	 * 2	44977.0	46990.0
	 * 3	42092.0	43525.0
	 * 4	44408.0	43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively. 
	 */
	private void logAfterEachRound()
	{
		try {
			Files.write(logFile, Arrays.asList(new String[]{numRounds+"\t"+getSumHP(teamA)+"\t"+getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
