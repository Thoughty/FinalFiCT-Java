// Warat Phat-in 6188035
// Section 2

public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player
	private int numSpecialTurns;
	private int currentTurn =0;
	
	 private boolean isSleeping = false; // to check if is sleeping or not
	 private boolean isCursed = false; // to check if is cursing or not 
	 private boolean isTaunting = false;// to check if is taunting or not 
	 private boolean isAlive = true;// to check if is Alive or not 
	 private Player CursedTarget; // to store the target that has been cursed
	
	 
	 private Arena.Team team; // A or B
	 private Arena.Row row; // Back or Front
	 private int PositionNum; // to store the position in team 
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public void setPosition(int position, Arena.Row row, Arena.Team team) { // to set the postion in team
		this.PositionNum = position;	this.row = row;	this.team = team;
	}
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		this.type = _type;
		this.atk = 0;
		this.maxHP = 0;
		this.currentHP = 0;
		switch (_type)
		{
		case Healer :
			this.type = PlayerType.Healer; this.atk = 238; this.maxHP = 4790; this.currentHP = 4790; this.numSpecialTurns = 4; break;
		case Tank :
			this.type = PlayerType.Tank; this.atk = 255; this.maxHP = 5340; this.currentHP = 5340; this.numSpecialTurns = 4; break;
		case Samurai :
			this.type = PlayerType.Samurai; this.atk = 368; this.maxHP = 4005; this.currentHP = 4005; this.numSpecialTurns = 3; break;
		case BlackMage :
			this.type = PlayerType.BlackMage; this.atk = 303; this.maxHP = 4175; this.currentHP = 4175; this.numSpecialTurns = 4; break;
		case Phoenix :
			this.type = PlayerType.Phoenix; this.atk = 209; this.maxHP = 4175; this.currentHP = 4175; this.numSpecialTurns = 8; break;
		case Cherry :
			this.type = PlayerType.Cherry; this.atk = 198; this.maxHP = 3560; this.currentHP = 3560; this.numSpecialTurns = 4; break;
		}
	}
	
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return this.currentHP;
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return this.type;
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		
		return this.maxHP;
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		if(isSleeping)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		if(isCursed)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(isAlive)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		if(isTaunting)
		{
			return true;
		}
		return false;
	}
	
	
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		target.currentHP = target.currentHP - this.atk;
		if(target.currentHP <= 0)
		{
			target.currentHP = 0;
			target.isAlive = false;
			target.isCursed = false;
			target.isSleeping = false;
			target.isTaunting = false;
		}
	}
	
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		Player target = null;
		switch(this.type)
		{
			case Healer : target = FindLowHP(myTeam);
					if(target.isCursed == false && target.currentHP < target.maxHP && target != null)
					{
						double heal = (0.25) * target.maxHP;
						target.currentHP = target.currentHP + heal;
						if(target.currentHP > target.maxHP)
							{
								target.currentHP =  target.maxHP;
							}
						
						System.out.println("# "+this.playerInfo()+" Heals "+target.playerInfo());
					}
					break;
			case Phoenix : target = FindDeadMan(myTeam);
				if(target != null)
				{
					target.isAlive = true;
					target.currentHP = (0.30)* target.maxHP;
					target.currentTurn = 0;
					System.out.println("# "+this.playerInfo()+" Revieves "+target.playerInfo());

				}
					break;
			case Cherry : 
				for(int i = 0;i < 2;i++)
				{
					for(int g = 0;g < theirTeam[i].length;g++)
					{
						if(theirTeam[i][g].isAlive)
						{
							theirTeam[i][g].isSleeping = true;	
							System.out.println("# "+this.playerInfo()+" Feed a fortune cookie to "+theirTeam[i][g].playerInfo());
						}
					}
				}
			

				break;
			case Tank : this.isTaunting = true; 
				System.out.println("# "+this.playerInfo()+" is Taunting! ");
				break;
			case Samurai : target = FindTarget(theirTeam);
				if(target != null)
				{
					attack(target); attack(target);
					System.out.println("# "+this.playerInfo()+" Double Slashes! "+target.playerInfo());
				}
				break;
			case BlackMage : target = FindTarget(theirTeam);
				if(target != null)
				{
					this.CursedTarget = target;
					target.isCursed = true;
					System.out.println("# "+this.playerInfo()+" Curses "+target.playerInfo());

				}
				break;
		}
	}
	
	public static Player FindTarget(Player[][] TeamEnemy) // To find target for taking action
	{
		Player target = null;
		
				if(TauntEnemy(TeamEnemy) != null)
				{
					target = TauntEnemy(TeamEnemy);
				}
				else if (!FrontRowDead(TeamEnemy))
				{
					for(int i = 0; i<TeamEnemy[0].length; i ++) //Front Row man
					{
						if(TeamEnemy[0][i].isAlive() )
						{
							target = TeamEnemy[0][i];
							break;
						}
					}
					for(int i = 0; i<TeamEnemy[0].length; i++)//Front row find lowest HP
					{
						if(TeamEnemy[0][i].isAlive() && target.getCurrentHP() > TeamEnemy[0][i].getCurrentHP())
						{
							target = TeamEnemy[0][i];
						}
					}
				}
				else
				{
					for(int i = 0; i<TeamEnemy[1].length; i ++) //Back Row man
					{
						if(TeamEnemy[1][i].isAlive() )
						{
							target = TeamEnemy[1][i];
							break;
						}
					}
					for(int i = 0; i<TeamEnemy[1].length; i++)//Back row find lowest HP
					{
						if(TeamEnemy[1][i].isAlive() && target.getCurrentHP() > TeamEnemy[1][i].getCurrentHP())
						{
							target = TeamEnemy[1][i];
						}
					}
				}
				return target;
	}
	public static boolean FrontRowDead(Player [][] Team)
	{
				for(int i = 0; i <Team[0].length;i++)
				{
					if(Team[0][i].isAlive())
					{
						return false;
					}
				}
				
	
		return true;
	}
	public static Player TauntEnemy(Player[][] Team)// to check if enemy team has a tank with taunt 
	{
		Player target = null;
		boolean TauntManFound = false;
				for(int i = 0; i < 2; i++)
				{
					for(int l = 0; l < Team[i].length; l++)
					{
						if(Team[i][l].isTaunting() && Team[i][l].isAlive())
						{
							target = Team[i][l];
							TauntManFound = true;
							break;
						}
					}
					if(TauntManFound)
					{
						break;
					}
				}
		return target;		
	}
	
	public static Player FindLowHP(Player[][] team)// find lowest HP in ally team for healing
	{
		Player target = null;
		boolean InjuredManFound = false;
		
				for(int i = 0; i < 2; i++)
				{
					for(int g = 0; g<team[i].length; g++)
					{
						if(team[i][g].isAlive())
						{
							target = team[i][g];
							InjuredManFound = true;
							break;
						}
					}
					if(InjuredManFound)
					{
						break;
					}
				}
				//compare to find lowest HP for healing
				for(int i = 0; i < 2; i++)
				{
					for(int g = 0; g < team[i].length; g++)
					{
						if(target.getCurrentHP()/target.getMaxHP() > team[i][g].getCurrentHP()/team[i][g].getMaxHP() && team[i][g].isAlive() )
						{
							target = team[i][g];
						}
					}
				}
				
		return target;
	}
	public static Player FindDeadMan(Player[][] Team)// to help phoenix find the dead teammate to revive them
	{
		Player target = null;
		boolean DeadBodyFound = false;
				for(int i = 0; i < 2; i++)
				{
					for(int g = 0; g < Team[i].length ; g++)
					{
						if(!Team[i][g].isAlive())
						{
							target = Team[i][g];
							DeadBodyFound = true;
							break;
						}
					}
					if(DeadBodyFound)
					{
						break;
					}
				}
				return target;
	}
	
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		Player target;
		if(this.type == PlayerType.Tank)
		{
			this.isTaunting = false;
		}
		if(this.type == PlayerType.BlackMage && this.CursedTarget != null)
		{
			this.CursedTarget.isCursed = false;
		}
		if((!this.isSleeping)&& this.isAlive)
		{
			this.currentTurn++;
			switch(this.team)
			{
				case A : if(this.currentTurn == this.numSpecialTurns)
						{
							switch(this.type)
							{
								case Healer : useSpecialAbility(arena.getTeam(Arena.Team.A),arena.getTeam(Arena.Team.B));
											 break;
								case BlackMage : useSpecialAbility(arena.getTeam(Arena.Team.A),arena.getTeam(Arena.Team.B));
								 			break;
								case Cherry : useSpecialAbility(arena.getTeam(Arena.Team.A),arena.getTeam(Arena.Team.B));
					 						break;
								case Samurai : useSpecialAbility(arena.getTeam(Arena.Team.A),arena.getTeam(Arena.Team.B));
					 						break;
								case Tank : useSpecialAbility(arena.getTeam(Arena.Team.A),arena.getTeam(Arena.Team.B));
					 						break;
								case Phoenix : useSpecialAbility(arena.getTeam(Arena.Team.A),arena.getTeam(Arena.Team.B));
					 						break;
							}
							this.currentTurn = 0;
						}
				else
				{
					target = FindTarget(arena.getTeam(Arena.Team.B));
					if(target != null)
					{
						System.out.println("# "+this.playerInfo()+" Attack "+target.playerInfo());
						attack(target);
					}
				}
						break;
				case B : if(this.currentTurn == this.numSpecialTurns)
				{
					switch(this.type)
					{
						case Healer : useSpecialAbility(arena.getTeam(Arena.Team.B),arena.getTeam(Arena.Team.A));
									 break;
						case BlackMage : useSpecialAbility(arena.getTeam(Arena.Team.B),arena.getTeam(Arena.Team.A));
						 			break;
						case Cherry : useSpecialAbility(arena.getTeam(Arena.Team.B),arena.getTeam(Arena.Team.A));
			 						break;
						case Samurai : useSpecialAbility(arena.getTeam(Arena.Team.B),arena.getTeam(Arena.Team.A));
			 						break;
						case Tank : useSpecialAbility(arena.getTeam(Arena.Team.B),arena.getTeam(Arena.Team.A));
			 						break;
						case Phoenix : useSpecialAbility(arena.getTeam(Arena.Team.B),arena.getTeam(Arena.Team.A));
			 						break;
					}
					this.currentTurn = 0;
				}
				else
				{
					target = FindTarget(arena.getTeam(Arena.Team.A));
					if(target != null)
					{
						System.out.println("# "+this.playerInfo()+" Attack "+target.playerInfo());
						attack(target);
					}
				}
				break;
			}
		}
		if(this.isSleeping)
		{
			this.isSleeping = false;
		}
			
	}
	private String playerInfo() {
		return this.team+"["+this.row+"]["+this.PositionNum+"] {"+this.type+"}";
	}
	
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	
	
}
