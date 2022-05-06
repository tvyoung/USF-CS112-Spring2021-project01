/**
 *
 * @author Vicki Young
 * @version 1.0
 * @since 2021.03.15
 * Hangman.java
 *
 * This program allows a user to play Hangman with the computer.
 * --EXPLAIN THE RULES--
 * 
 */
import java.util.*;
import java.lang.*;

public class WheelOfFortune
{
	
	private static String[] movieList = {"Star Wars: The Last Jedi", "Rogue One: A Star Wars Story", "Little Women", "The Half of It", "The Old Guard", "Detective Pikachu", "Dead Poets Society", "Coco", "The Shape of Water", "Hidden Figures", "Black Panther", "Soul", "Jojo Rabbit", "Monty Python and the Holy Grail", "Parasite"};

	private static int[] wheelValues = {100, 200, 300, 400, 500};

	private static StringBuilder movieShadowName = new StringBuilder(); 
	private static String movieName;
	private static int movieIndex;
	private static int cashValue = 0;

	private static Scanner guess = new Scanner(System.in);

	private static int numMistakes = 0;
	private static int playerTotal = 0;
	private static int gameStatus = 0;
	//if gameStatus == 0, CONTINUING
	//if gameStatus == 1, WIN
	//if gameStatus == 2, LOSE

	//takes in selected movieList index number
	//converts currently selected movie title into its shadow, in asterisks
	//reveals only spaces + characters that are not letters
	public static void movieShadow(int selectedMovieIndex) {

		//sets movieName to selected movie index
		movieName = movieList[selectedMovieIndex];
		movieShadowName.setLength(movieName.length());

		//FOR TESTING
		//System.out.println(movie);
		
		char c;
		for (int i = 0; i < movieName.length(); i++) {
			//c goes through each index character in movie
			c = movieName.charAt(i);
			//if c is a letter, adds asterisk * to currentGuess
			if (Character.isLetter(c)) {
				movieShadowName.setCharAt(i,'*');
			//if c is not a letter, adds whatever c is to currentGuess
			} else {
				movieShadowName.setCharAt(i, c);
			}			
		}
	}

	//assigns movieIndex a randomly selected film number index (0-14) 
	public static void randomMovieIndex() {
		Random randGen = new Random();
		movieIndex = randGen.nextInt(15);
	}

	//returns a random cash value from wheelValues list 
	public static void spinWheel() {
		Random randGen = new Random();
		cashValue = wheelValues[randGen.nextInt(5)];
	}

	//returns movieShadowName
	public static StringBuilder getMovieShadow() {
		return movieShadowName;
	}

	//returns movieName
	public static String getMovieName() {
		return movieName;
	}

	//returns movieIndex
	public static int getMovieIndex() {
		return movieIndex;
	}

	//returns cashValue
	public static int getCashValue() {
		return cashValue;
	}

	//returns numMistakes
	public static int getNumMistakes() {
		return numMistakes;
	}

	//returns playerTotal
	public static int getPlayerTotal() {
		return playerTotal;
	}

	//reveals letters R S T L N E	
	//this took SOOOOOOO long to figure out oh my god
	public static void revealLetters() {
		char c;
		for (int i = 0; i < movieName.length(); i++) {
			c = movieName.charAt(i);
			if (c == 'R' || c == 'r' ||
				c == 'S' || c == 's' ||
				c == 'T' || c == 't' ||
				c == 'L' || c == 'l' ||
				c == 'N' || c == 'n' ||
				c == 'E' || c == 'e' ) {
				movieShadowName.setCharAt(i, c);
			} 
		}
	}

	//player chooses to guess the movie title
	public static void guessMovieTitle() {
		System.out.println("Enter your movie title guess:");
		String titleGuess = guess.nextLine();
		//if player guess equals the movie name (case sensitive), game is won
		if (movieName.equals(titleGuess)) {
			System.out.println("Correct.");
			gameStatus = 1;
		//if player guesses incorrectly, increments numMistakes
		} else {
			System.out.println("Incorrect.");
			numMistakes++;
		}
	}

	//player chooses to guess by letter
	public static void guessMovieLetter() {
		System.out.print("Enter your letter guess: ");
		char letterGuess = guess.nextLine().charAt(0);

		char c;
		int numGuesses = 0;
		//if guessChecker is true, prevents numMistakes from incrementing
		boolean guessChecker = false;
		//goes through movieName
		for (int i = 0; i < movieName.length(); i++) {
			c = movieName.charAt(i); 
				//player input is not case sensitive
				//if there is at least one correct guess (one instance of userGuess in movieName) then guessChecker becomes true
			if (c == Character.toLowerCase(letterGuess) || c == Character.toUpperCase(letterGuess)) {
				//checks if the letter has been guessed yet or not
				//if letter has not been guessed yet, proceeds
				if (movieShadowName.charAt(i) == '*') {
					//the player letter is revealed in movieShadowName 
					movieShadowName.setCharAt(i, c);
					numGuesses++;
					guessChecker = true;
				//if letter has already been guessed, does not award points + does not increment numMistakes, simply tells player
				} else {
					System.out.println("This letter has already been guessed.");
					guessChecker = true;
					break;
				}
			}
		}
		//if the player guessed a letter that wasnt in movieName, increments numMistakes
		if (!guessChecker) {
			System.out.println("This letter is not in the movie title.");
			numMistakes++;
		}
		//adds money won per instances of correct letter to playerTotal
		playerTotal += (numGuesses * cashValue);
	}

	//checks if all letters in the movie name have been guessed
	public static void checkMovieShadow() {
		boolean status = true;
		//if there any remaining asterisk in movieShadowName (meaning not all the letters have been guessed), then no change to game status yet
		for (int i = 0; i < movieName.length(); i ++) {
			if (movieShadowName.charAt(i) == '*') {
				status = false;
			} 
		}
		//if all letters in the movie name have been correctly guessed, game is won
		if (status) {
			gameStatus = 1;
		}
	}

	//checks game status
	public static void checkGameStatus() {
		//if number of mistakes reaches 3, game is lost
		if (numMistakes == 3) {
			gameStatus = 2;
			System.out.println("\nFilm was: " + movieName);
			System.out.println("You have made 3 mistakes. Game lost.");
		//if player has fully guessed the movie title, game is won
		} else if (gameStatus == 1) {
			System.out.println("\nFilm was: " + movieName);
			System.out.println("Congratulations. Game won.");
		}
	}

	//prints game instructions
	public static void gameInstructions() {
		System.out.println("INSTRUCTIONS");
		System.out.println("Welcome to the Wheel of Fortune program. The program will randomly select a movie title from 15 preset films and display a shadow of the movie name in asterisks.\n");
		System.out.println("You, the player, will then \"spin the wheel\" to see how much you can win per letter. The possible cash values are $100, $200, $300, $400 and $500.\n");
		System.out.println("Afterward, the program will provide a hint by revealing the following letters if they exist in the movie title:\nR S T L N E\n");
		System.out.println("You will then have two options:\n1) Guess the movie title (This earns no reward money).\n2) Guess a letter in the movie that has not been revealed. For each correct guess, you will earn however much cash value that you spun earlier.\n");
		System.out.println("You will repeat this process until the game ends.");
		System.out.println("The game ends by:\n1) LOSE: You make 3 mistakes (guess a letter that does not exist in the movie title, or guess the movie title wrong).\n2) WIN: You correctly guess all the letters in the movie title.\n3) WIN: You correctly guess the entire movie title.\n");
		System.out.println("Let's begin.");
	}

	public static void main(String[] args) 
	{
		/*String movie = movieList[0];
		StringBuilder currentGuess = new StringBuilder(movie.length());
		System.out.println(movie);

		char c = movie.charAt(0);
		if (Character.isLetter(c)) {
			System.out.println(c + " is a letter");
		}*/

		WheelOfFortune game = new WheelOfFortune();
		Scanner enter = new Scanner(System.in);
		int userOption;

		//print game instructions
		gameInstructions();
		//randomly select a film 
		System.out.println("A film has been randomly selected:");
		//randomly selects movie index number from movieList
		randomMovieIndex();
		movieShadow(game.getMovieIndex());
		System.out.println(game.getMovieShadow());

		//FOR TESTING
		//System.out.println(movieName);

		//spin wheel to get cashValue
		System.out.println("\nNow, we will \"spin the wheel.\"");
		spinWheel();
		System.out.println("The wheel landed on $" + game.getCashValue());

		//reveal any letters R S T L N E in movie title
		System.out.println("\nWe now reveal the hint letters in the movie title:");
		revealLetters();

		//loops while gameStatus = 0 (CONTINUING)
		do {
			
			System.out.println();
			System.out.println(game.getMovieShadow());
			System.out.println("\nCurrent total money: $" + game.getPlayerTotal());
			System.out.println("Numbers of mistakes made: " + game.getNumMistakes());
			System.out.println("1: Guess the movie");
			System.out.println("2: Guess a letter");
			System.out.println("3: Quit");
			System.out.println("Choose (1, 2, or 3):");
			userOption = enter.nextInt();

			if (userOption == 1) {
				guessMovieTitle();
			} else if (userOption == 2) {
				guessMovieLetter();
			} else if (userOption == 3) {
				System.out.println("Thanks for playing.");
				break;
			}

			//checks movieShadow to see if film has been fully guessed
			checkMovieShadow();
			//checks gameStatus to see if player has won or lost yet
			checkGameStatus();

		} while (gameStatus == 0);
		
		//prints total amount of money player has won
		System.out.println("Total money won: $" + game.getPlayerTotal());

	}
}