/* Attack.java
 * Suraj Rampure
 * Started on 2014/11/27 –– Finished on 2015-01-06
 * 
 * Attack objects each belong to a Pokemon object
 * Each Attack has a name, energy cost, damage and a special attack
 * The primary method of Attack is doSpecial(), which is called upon by
 * the attack method in Pokemon for the attack's special attack to be utilized
 */

class Attack {
	
	private String name, special;
	private int cost, damage;
	
	// Constructor method
	public Attack (String name, String cost, String damage, String special) {
		this.name = name;
		this.cost = Integer.parseInt(cost);		// We take in cost and damage as Strings because they are split up in the "data" String in the Pokemon constructor
		this.damage = Integer.parseInt(damage);
		
		if (special.equals(" ")) {
			this.special = "none";
		}
		
		else {
			this.special = special;
		}

	}
	
	// Getter methods
	public String getName () {
		return name;
	}
	
	public String getSpecial () {
		return special;
	}
	
	public int getCost () {
		return cost;
	}
	
	public int getDamage () {
		return damage;
	}
	/*****/
	
	// Attack SPECIAL methods
	
	// The attack method in the Pokemon class calls doSpecial
	// doSpecial returns an int which determines how it changes the attack damage
	// (if it returns 0, the attack is not done)
	public int doSpecial(Pokemon p, Pokemon enemy) {
		
		if (special.equals("stun")) {
			return special_stun(p, enemy);
		}
		
		else if (special.equals("wild card")) {
			return special_wildcard(p, enemy);
		}
		
		else if (special.equals("wild storm")) {
			return special_wildstorm(p, enemy);
		}
		
		else if (special.equals("disable")) {
			return special_disable(p, enemy);
		}
		
		else if (special.equals("recharge")) {
			return special_recharge(p, enemy);
		}
	
		return 1;
	}
	
	// Stun -- 50/50 chance of the enemy being stunned
	public int special_stun (Pokemon p, Pokemon enemy) {
		
		System.out.printf("%s used stun ", p.getName());
		
		int tostun = (int) (Math.random()*2);
		
		if (tostun == 1) {
			System.out.printf("and was successful! %s was stunned.\n\n", enemy.getName());
			enemy.stun();
		}
		
		else {
			System.out.print("and was unsuccesful. Nothing happened.\n\n");
		}
		
		return 1;
	}
	
	// Wild card -- 50/50 chance of the damage not happening
	public int special_wildcard (Pokemon p, Pokemon enemy) {
		
		System.out.printf("%s used wild card ", p.getName());
		
		int x = (int) (Math.random()*2);
		
		if (x == 1) {
			System.out.println("and the damage was unaffected by it.\n");
			return x;
		}
		
		else {
			System.out.println("and was unlucky – the damage was cancelled.\n");
			return x;
		}
	}
	
	// Wild storm – 50% chance of the initial attack working, but if it works
	// there's a 50% chance of the attack happening again – and again, and again...
	public int special_wildstorm (Pokemon p, Pokemon enemy) {
		
		int count = 0;
		
		System.out.printf("%s used wild storm ", p.getName());
		
		// This determines if we initiate the process
		// If loop is 0, wild storm never goes through
		int loop = (int) (Math.random()*2);
		
		if (loop == 1) {
			
			count ++;
			
			boolean flag = true;
			
			while (flag) {
				
				loop = (int) (Math.random()*2);
				
				// We stop if our variable "loop" is ever 0
				if (loop == 0) {
					flag = false;
				}
				
				count ++;
				
			}
		}
		
		System.out.printf("%d times! The damage dealt was increased %d times!\n\n", count, count);
		
		return count;
		
	}
	
	// Disable –– Enemy Pokemon's base attacks do 10 less damage 
	// (ie. 10 is subtracted from the damage independently of the 2x or 0.5x multipliers for weaknesses and resistance)
	
	public int special_disable (Pokemon p, Pokemon enemy) {
		if (! enemy.isDisabled()) {
			System.out.printf("%s used disable, and so %s was disabled!\n\n", p.getName(), enemy.getName());
			enemy.disable();
		}
		
		else {
			System.out.printf("%s used disable, but %s was already disabled so nothing happened.\n\n", p.getName(), enemy.getName());
		}
		
		return 1;
	}
	
	// Recharge –– Simply gives the attacking Pokemon 20 energy points
	public int special_recharge (Pokemon p, Pokemon enemy) {
		System.out.printf("%s used recharge! It gained 20 energy points.\n\n", p.getName());
		
		p.increaseEnergy(20);
		return 1;
	}

}