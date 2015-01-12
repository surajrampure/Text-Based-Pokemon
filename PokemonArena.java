/* PokemonArena.java
 * Suraj Rampure
 * Started on 2014-11-25 –– Finished on 2015-01-06
 * 
 * Pokemon Arena is a battle game in which the user chooses four Pokemon from a Pokedex
 * and has to use them to defeat the rest of the Pokemon available in game. 
 * If they do so, they win and are crowned Trainer Supreme.
 * The game follows a round based system, in which the user's Pokemon throws one attack (or passes/retreats)
 * and the enemy throws an attack. 
 */

import java.util.*;
import java.io.*;

public class PokemonArena {
	
	// All access specifiers are set to private as all methods and fields are used 
	// in this class only
	private static ArrayList <Pokemon> pokedex = new ArrayList <Pokemon> ();		// All Pokemon
	private static ArrayList <Pokemon> users = new ArrayList <Pokemon> ();			// User's 4 Pokemon
	private static ArrayList <Pokemon> enemies = new ArrayList <Pokemon> ();		// Enemy Pokemon
	
	// Index of the user's Pokemon
	private static int current;
	
	// Player's name
	private static String playername;	
	
	// ASCII art to display when the player wins or loses, along with the intro logo
	private static String logoString;
	private static String winString;
	private static String loseString;

	
	// Pauses the program for len milliseconds
	private static void sleep (int len) {
		
		try {
			Thread.sleep(len);
		}
		
		catch (InterruptedException ex){
			System.out.println(ex);
		}

	}
	
	// Reads from pokemon.txt, creating the Pokemon objects for the game and storing them in the Pokedex
	private static void loadObjects () {
		
		// Loading Pokemon objects and adding them to the pokedex
		Scanner inFile = null;
		
		try {
			inFile = new Scanner (new File ("src/pokemon.txt"));
		}
		
		catch (IOException ex) {
			System.out.println(ex);
		}
		
		int n;
		n = Integer.parseInt(inFile.nextLine());
		
		for (int i = 0; i < n; i ++) {
			String line = inFile.nextLine();
			pokedex.add(new Pokemon(line));		// Pokedex contains all Pokemon objects
		}
		/******/
		
	}

	// Also reads the three txt ascii art files
	private static void loadArt() {
		
		// Loading the logo, win and lose strings from the txt files
		Scanner inFile = null;
		
		ArrayList <String> asciis = new ArrayList <String> ();
		
		String [] paths = {"src/logo.txt", "src/win.txt", "src/lose.txt"};
		
		int n;
		
		for (int i = 0; i < 3; i ++) {
			try {
				inFile = new Scanner (new File (paths[i]));
			}
			
			catch (IOException ex) {
				System.out.println(ex);
			}
			
			// I've set it up so that the first line of each of my ASCII art text files contains
			// the number of lines following it
			n = Integer.parseInt(inFile.nextLine());
			
			String s = "";
			
			asciis.add(s);
			
			for (int j = 0; j < n; j ++) {
				s += inFile.nextLine() + "\n";
			}
			
			asciis.set(i, s);
		}
		
		logoString = asciis.get(0);
		winString = asciis.get(1);
		loseString = asciis.get(2);
				
	}
	
	// A quick introduction to the program before displaying the Pokemon
	private static void intro () {
		
		System.out.println(logoString); 
		
		System.out.println("\t\t\t\t\t\t\t\t\t"
				+ "© 2015 The Pulse");
		
		Scanner kb = new Scanner(System.in);
		
		sleep(1500);
		
		System.out.println("\n\nWelcome to Pokemon Arena!\n");
		
		sleep(1250);
		
		System.out.print("Enter your name: ");
		
		playername = kb.nextLine();
		
		sleep (1000);
		
		System.out.println("\nWelcome, TRAINER " + playername.toUpperCase() + " to the Arena.\nYou're going to choose four of my Pokemon and use them to battle the rest of my Pokedex.");
		
		sleep (2000);
		
		System.out.println("\nYou beat all of the Pokemon? You become the TRAINER SUPREME.\nOtherwise, let's just say... you're not taking the title away from me any time soon.\n");
		
		sleep(2500);
		
	}
	
	// Allows the user to choose the four Pokemon they want to use (not allowing any repeats)
	// All four Pokemon are stored in the ArrayList "users", while enemies are stored in "enemies"
	// Once they die, they are removed from users
	private static void choose4 () {
		
		Scanner kb = new Scanner (System.in);
		
		System.out.printf("I have %d Pokemon available for you to choose from...\n\n", pokedex.size());
		
		// Prints the name of each type
		for (int i = 0; i < pokedex.size(); i ++) {
			System.out.printf("%d. %s\n", i + 1, pokedex.get(i).getName());
		}
		
		System.out.println();
		
		int selection, left = 4;
		boolean flag = true;
		
		// All of the input in this program is in the form of a while loop which takes in integers
		// and catches the InputMismatchException in the case that the user didn't input an integer
		while (flag) {
			
			sleep(500);
			
			System.out.printf("Enter the number corresponding to the Pokemon you want (%d remaining):\n", left);
			
			
			try {
				selection = kb.nextInt();
			}
			
			catch (InputMismatchException ex) {
				selection = -1;
				kb.next();					// Flushes out the invalid input
			}
			
				
			if (selection <= pokedex.size() && selection >= 1) {
				if (! users.contains(pokedex.get(selection -1))) {
					users.add(pokedex.get(selection -1));
					left -= 1;
					System.out.printf("You chose %s!\n\n\n", users.get(4-left-1).getName());
					sleep(750);
				}
					
				else {
					System.out.printf("Sorry, you've already chosen %s. Please choose another Pokemon.\n\n\n", pokedex.get(selection-1).getName());
				}
			}
				
			else {
				System.out.printf("Sorry, that is not a valid Pokemon. Please enter a number between 1 and %d.\n\n\n", pokedex.size());
			}
					
			// If they've picked 4 then we stop looping
			if (left == 0) {
				flag = false;
			}

		}
		
		System.out.println("You have chosen:");
		for (int i = 0; i < 4; i ++) {
			System.out.printf("%s\n", users.get(i).getName());
		}
		// Adds the remaining Pokemon to the enemies ArrayList
		for (int i = 0; i < pokedex.size(); i ++) {
			if (! users.contains(pokedex.get(i))) {
				enemies.add(pokedex.get(i));
			}
		}
		
		// Randomizes the order of the enemies
		Collections.shuffle(enemies);
		
		sleep(1500);
		
		System.out.println("\nLet us begin!\n\n\n\n");
		
		sleep(2000);
		
	}
	
	// Lets the user choose the Pokemon they want to use first each round
	private static void choosePlayer () {
		Scanner kb = new Scanner (System.in);
		System.out.println("Which Pokemon would you like to use?");
		for (int i = 0; i < users.size(); i ++) {
			System.out.printf("%d. %s\n", i + 1, users.get(i).getName());
	
		}
		int selection = -1;
		
		boolean flag = true;
		
		while (flag) {
			
			try {
				selection = kb.nextInt();
			}
			
			catch (InputMismatchException ex) {
				selection = -1;
				kb.next();
			}
			
			
			if (selection >= 0 && selection <= users.size()) {
				flag = false;
			}
			
			else {
				System.out.printf("That is not a valid Pokemon. Please enter a number between 1 and %d.\n", users.size());
			}
		}
		
		current = selection - 1;
		
	}
	
	// Chooses either "attack", "pass" or "retreat" based on the current conditions and user input (for user Pokemon)
	private static String chooseMove (Pokemon p) {
		
		if (users.contains(p)) {
			
			// Number of possible attacks
			// An attack is "possible" if the Pokemon's energy level is greater than or equal to the 
			// energy cost of the attack
			int count = 0;
			for (int i = 0; i < p.getAttacks().size(); i ++) {
				if (p.getAttacks().get(i).getCost() <= p.getEnergy()) {
					count ++;
				}
			}
			
			// They need at least one other Pokemon to be able to retreat
			
			boolean isRetreatPossible = false;
			if (users.size() > 1) {
				isRetreatPossible = true;
			}
			
			System.out.printf("%s\n", p);
			System.out.printf("1. Attack (%d Possible)\n", count);
			System.out.println("2. Pass");
			
			if (isRetreatPossible) {
				System.out.println("3. Retreat");
			}
			
			System.out.println("Enter the number corresponding to the move you want.\n");
			
			Scanner kb = new Scanner (System.in);
			
			boolean flag = true;
			int selection;
			while (flag) {
				try {
					selection = kb.nextInt();
				}
				
				catch (InputMismatchException ex) {
					selection = -1;
					kb.next();
				}
				
				if (count > 0 && selection == 1) {
					return "attack";
				}
				
				else if (count == 0 && selection == 1) {
					System.out.printf("Sorry, %s is too tired to perform any attacks. Please choose another option.\n\n", p.getName());
				}
				
				else if (selection == 2) {
					return "pass";
				}
				
				else if (isRetreatPossible && selection == 3) {
					return "retreat";
				}
				
				else {
					System.out.println("That is not a valid option. Please try again.");
				}
			}
			
			return "";

		}
		
		else {
			
			// For enemy Pokemon, we only care if there is a possible attack (they don't get the option to retreat)
			boolean canAttack = false;
			
			for (int i = 0; i < p.getAttacks().size(); i ++) {
				if (p.getAttacks().get(i).getCost() <= p.getEnergy()) {
					canAttack = true;
					break;
				}
			}
			
			if (canAttack) {
				return "attack";
			}
			
			else {
				return "pass";
			}
		}

		
	}
	
	// Does nothing, other than print "Pokemon passed"
	private static void pass (Pokemon p) {
		System.out.printf("\n%s passed. Nothing happened.\n\n", p.getName());
	}
	
	// Allows the user to swap out their Pokemon for another one they have alive
	private static void retreat (Pokemon p) {
		
		// We put users.size() > 1, because for retreat to be possible there must be at least one other Pokemon in users,
		// therefore at least two total
		
		System.out.println();
		
		if (users.size() > 1) {
			for (int i = 0; i < users.size(); i ++) {
				System.out.printf("%d. %s", i + 1, users.get(i));
			}
			
			System.out.println();
			
			System.out.println("\nEnter the number corresponding to the Pokemon you want to switch to.");
			
			boolean flag = true;
			
			Scanner kb = new Scanner (System.in);
			int selection = -1;
			
			while (flag) {
				
				try {
					selection = kb.nextInt();
				}
				
				catch (InputMismatchException ex) {
					selection = -1;
					kb.next();
				}
				
				if (selection > 0 && selection <= users.size()) {
					current = selection -1;
					flag = false;
				}
				
				else {
					System.out.printf("Sorry, that is not a valid option. Please enter a number between 1 and %d.\n", users.size());
				}
			}
			
			System.out.printf("You switched to %s.\n\n", users.get(current).getName());
		}
		
		else {
			System.out.printf("Sorry, all of your other Pokemon are dead. I guess you're going to have to stick with %s.\n\n", users.get(current).getName());
		}
	
	}

	// Lets the user choose the attack they want and returns that object
	private static Attack chooseAttack(Pokemon p) {
		
		// ArrayList of the possible attacks for the Pokemon
		ArrayList <Attack> poss_attacks = new ArrayList <Attack> ();
		
		for (int i = 0; i < p.getAttacks().size(); i ++) {
			if (p.getAttacks().get(i).getCost() <= p.getEnergy()) {
				poss_attacks.add(p.getAttacks().get(i));
			}
			
		}
		
		// If it's a user Pokemon then we display the options to the user and let them pick
		if (users.contains(p)) {

			Scanner kb = new Scanner (System.in);
			
			
			
			// Unreachable, since chooseAttack is only called if there is a possible attack
			if (poss_attacks.size() == 0) {
				return null;
			}
			
			else {
				for (int i = 0; i < poss_attacks.size(); i ++) {
					System.out.printf("%d. %s (ENERGY COST = %d, DAMAGE = %d, SPECIAL = %s)\n", i+1, 
							poss_attacks.get(i).getName(), poss_attacks.get(i).getCost(), 
							poss_attacks.get(i).getDamage(), poss_attacks.get(i).getSpecial().toUpperCase());
				}
			}
			
			System.out.println("Enter the number corresponding to the attack you want.\n");
			
			int selection = -1;
			boolean flag = true;
			
			while (flag) {
				selection = kb.nextInt();
				
				if (selection > 0 && selection <= poss_attacks.size()) {
					flag = false;
				}
				
				else {
					System.out.println("That is not a valid attack. Please try again.");
				}
			}
			
			return poss_attacks.get(selection -1);

		}
		
		else {
			// A random attack is chosen if it is an enemy Pokemon
			if (poss_attacks.size() > 0) {
				int x = (int) (Math.random()*poss_attacks.size());
				return poss_attacks.get(x);
				
			}
			
			else {
				return null;
			}
				
		}
		
	}
	
	// Allows both Pokemon p1 and p2 to make one move each
	// One of p1 and p2 is a user Pokemon and the other is an enemy Pokemon,
	// p1 being the one that gets to go first (decided in battle)
	private static void round (Pokemon p1, Pokemon p2) {
		
		System.out.printf("------------------------------------\n\n"
				+ "------------------------------------\n\n");
		
		sleep(500);
		
		if (! p1.isStunned()) {
			String choice = chooseMove(p1);
			
			if (choice.equals("attack")) {
				p1.attack(chooseAttack(p1), p2);
			}
				
			else if (choice.equals("pass")) {
				pass(p1);
			}
				
			else if (choice.equals("retreat")) {
				// Retreat changes the value of current, so we have to reset it
				retreat(p1);
				p1 = users.get(current);
			}
		}
		
		else {
			System.out.printf("%s was stunned, and couldn't do anything.\n\n", p1.getName());
			p1.unStun();
		}
		
		
		sleep(1500);
		
		System.out.print("\n\n\n\n\n");
		
		// It is possible that p1 kills p2 during this round
		// However, "battle" only considers this at the end of the round
		if (! p2.isDead()) {
			if (! p2.isStunned()) {
				String choice = chooseMove(p2);
				if (choice.equals("attack")) {
					p2.attack(chooseAttack(p2), p1);
				}
				
				else if (choice.equals("pass")) {
					pass(p2);
				}
				
				else if (choice.equals("retreat")) {
					retreat(p2);
					p2 = users.get(current);
				}
			}
			
			else {
				System.out.printf("%s was stunned, and couldn't do anything.\n\n", p2.getName());
				p2.unStun();
			}
			
			
		}
		
		sleep(2000);
	
		// Doesn't matter if the energy is added and its dead, because HP is what determines death
		p1.increaseEnergy(10);
		p2.increaseEnergy(10);
		
		System.out.print("\n------------------------------------\n");
		
		System.out.println(p1);
		System.out.print(p2);
		
		System.out.print("------------------------------------\n\n\n\n\n\n");

		sleep(2000);
		
	}
	
	// Runs rounds with a user Pokemon (which is chosen inside the method)
	// and the next enemy Pokemon in the enemies ArrayList
	// until an enemy dies
	private static void battle () {
		int first = (int) (Math.random()*2);
		
		sleep(250);
		
		System.out.printf("A wild %s appeared!\n\n", enemies.get(0).getName());
		
		sleep(500);
		
		// User chooses the Pokemon they want to use
		choosePlayer();
		
		sleep(250);
		
		System.out.printf("\nYou chose %s!\n\n\n\n\n\n", users.get(current).getName());
		
		sleep(750);
		
		if (first == 0) {
			System.out.printf("%s gets to go first!\n\n", users.get(current).getName());
		}
		
		else {
			System.out.printf("%s gets to go first!\n\n", enemies.get(0).getName());
		}
		
		// At the start of each battle their energies are set to 50
		enemies.get(0).fullyEnergize();
		users.get(current).fullyEnergize();
		
		sleep(500);
		
		// True if we continue into the next round, false if not
		boolean flag = true;
		
		while (flag) {
			
			if (users.get(current).isDead()) {
				System.out.printf("%s killed your %s!\n\n\n\n\n", enemies.get(0).getName(), users.get(current).getName());
				users.remove(current);
				choosePlayer();		// Lets them pick a new Pokemon since their's died
									// This fixes the index of current

			}
			
			if (enemies.get(0).isDead()) {
				System.out.printf("Your %s killed %s!\n\n\n\n\n", users.get(current).getName(), enemies.get(0).getName());
				enemies.remove(0);
				flag = false;
				
				// If the user Pokemon doesn't die, then the value of current can't possibly be out of bounds
				
			}
			
			else {
				if (first == 0) {
					round(users.get(current), enemies.get(0));
				}
				
				else {
					round(enemies.get(0), users.get(current));
				}
			}

			
		}
		
		
		// Heals all remaining user Pokemon by 20 HP
		for (int i = 0; i < users.size(); i ++) {
			users.get(i).increaseHP(20);
			
			// Undisables them at the end of the round in case they were disabled
			if (users.get(i).isDisabled()) {
				users.get(i).unDisable();
			}
		}
		
		// Similarly undisables the one enemy if they were disabled during the battle
		// If the enemy died and this is a different enemy than that used during the previous battle,
		// it won't matter – they won't be disabled anyways
		
		if (enemies.get(0).isDisabled()) {
			enemies.get(0).unDisable();
		}

	}

	// handleBattles creates battles until either all user Pokemon are dead or enemy Pokemon are dead
	private static String handleBattles () {
		
		boolean running = true;
		String output = "";
		while (running) {
			battle();
			
			if (users.size() == 0) {
				output =  "lose";
				running = false;
			}
			
			if (enemies.size() == 0) {
				output = "win";
				running = false;
			}
		}
		
		return output;
	}
	
	// handleBattles returns "lose" or "win"
	// finish takes that String and tells the user in a nice message whether or not they have won or lost
	private static void finish (String output) {
		if (output.equals("lose")) {
			System.out.printf("TRAINER %s, you lose. You are not TRAINER SUPREME.\n\nThat's still me.\n\n", playername.toUpperCase());
			System.out.println(loseString);
		}
		
		if (output.equals("win")) {
			System.out.printf("Wow, I wasn't expecting that.\n\nI hereby declare you, TRAINER %s, TRAINER SUPREME.\n\n", playername.toUpperCase());
			System.out.println(winString);
		}
	}
	
	public static void main (String [] args) {
		
		loadObjects();
		
		loadArt();
		
		intro();
		
		choose4();

		String decision = handleBattles();
		
		finish(decision);
		
	}
	
}