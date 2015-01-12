/* Pokemon.java
 * Suraj Rampure
 * Started on 2014-11-25 –– Finished on 2015-01-06
 * 
 * Pokemon objects are used in Pokemon Arena. They hold the attributes of Pokemon, 
 * including name, type, resistance, weakness, hp, enery and attacks.
 * The key method in this class is attack(a, enemy), where a Pokemon deals damage
 * 
 */

import java.util.*;
import java.io.*;

class Pokemon {
	
	private String name, type, resistance, weakness;
	private int hp, starthp, energy = 50;
	private ArrayList <Attack> attacks = new ArrayList <Attack> ();
	private boolean stunned = false, disabled = false;
	private int numofattacks;
	
	// Constructor method
	// The "data' String is split up and used to create Pokemon attributes
	public Pokemon (String data) {
		
		String [] inArray = data.split(",");
		
		name = inArray[0];
		hp = Integer.parseInt(inArray[1]);
		starthp = hp;
		type = inArray[2];
		resistance = inArray[3];
		weakness = inArray[4];
		
		// Spot 5 contains the number of attacks the Pokemon has
		// "attacks" is an attribute in the form of an ArrayList that contains all Attack objects belonging to 
		numofattacks = Integer.parseInt(inArray[5]);
		for (int i = 0; i < numofattacks; i ++) {
			attacks.add(new Attack (inArray[5 + 4*i + 1], inArray[5 + 4*i + 2], inArray[5 + 4*i + 3], inArray[5 + 4*i + 4]));
		}
	}
	
	// Getter methods
	public int numofattacks () {
		return numofattacks;
	}
	
	public int getEnergy () {
		return energy;
	}
	
	public String getName () {
		return name;
	}
	
	public ArrayList <Attack> getAttacks () {
		return attacks;
	}
	
	public String getType () {
		return type;
	}
	
	public boolean isStunned () {
		return stunned;
	}
	/******/
	
	
	// Attack method that takes in a Pokemon's attack,
	// considers if the enemy Pokemon is resistant or especially weak to the Pokemon object,
	// does the special attack and deals damage to the Pokemon
	public void attack (Attack a, Pokemon enemy) {
		// If enemy Pokemon is resistant to local Pokemon - damage is halved (compare types)
		// If enemy Pokemon's weakness type == the local Pokemon's type - damage is doubled
		
		System.out.printf("\n\n%s used %s against %s!\n\n", this.name, a.getName(), enemy.name);

		
		double damage = 1;
		
		// Checks resistance and weakness
		if (enemy.resistance.equals(this.type)) {
			System.out.printf("%s is resistant to %s types, so the damage dealt by %s was halved.\n\n", enemy.name, this.type, this.name);
			damage = 0.5;
		}
			
		else if (enemy.weakness.equals(this.type)) {
			//
			System.out.printf("%s's weakness is %s types, so the damage dealt by %s was doubled.\n\n", enemy.name, this.type, this.name);
			damage = 2;
		}
		
		// This causes the attack class to perform the special attack
		// If the special attack changes the exerted damage, it is reflected here
		damage *= a.doSpecial(this, enemy);
		
		if (isDisabled()) {
			// The BASE attacks do 10 less damage if they are disabled
			// However some attacks only do 0 damage, so we have to enforce that minimum
			damage *= Math.max(a.getDamage() - 10, 0);
		}
		
		else {
			damage *= a.getDamage();
		}
		
		// Subtracts the energy cost of the attack
		energy -= a.getCost();

		enemy.loseHP((int) damage);
		
		System.out.printf("%s lost %d HP!\n\n", enemy.getName(), (int) damage);

	}
	
	// Disabled methods
	// According to the rules, a Pokemon is only disabled until the end of a battle
	// However, if it survives the battle it must be undisabled
	public void disable() {
		disabled = true;
	}
	
	public void unDisable() {
		disabled = false;
	}
	
	public boolean isDisabled () {
		return disabled;
	}
	/******/

	// HP and energy methods
	public void increaseHP (int x) {
		// starthp is the starting and maximum hp for a Pokemon
		this.hp = Math.min(this.hp + x, starthp);
	}
	
	public void loseHP (int x) {
		// Not like this really matters, since in either the 0 or negative case a Pokemon dies
		// But it doesn't look good to show negative health when the Pokemon are printed before showing they are dead
		hp = Math.max(hp - x, 0);
	}
	
	// At the start of each battle energy is set to 50
	public void fullyEnergize() {
		energy = 50;
	}
	
	// Max energy is 50
	public void increaseEnergy (int x) {
		energy = Math.min(this.energy + x, 50);
	}
	
	public boolean isDead () {
		if (hp <= 0) {
			return true;
		}
		
		else {
			return false;
		}
	}
	/******/
		
	
	// Stun and unstun methods
	public void stun () {
		stunned = true;
	}
	
	public void unStun () {
		stunned = false;
		
	}
	/******/
	
	
	// toString method
	// At the end of each round we "print" each Pokemon showing its stats
	public String toString () {
		return String.format("%s: %d/%d HP, %d/%d Energy\n", name, hp, starthp, energy, 50);
	}

	
}