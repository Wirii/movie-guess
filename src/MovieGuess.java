import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/* MovieGuess
by Wirii
Udacity Java Course Project
*/

public class MovieGuess {
    public static void main(String [] args) throws Exception {
        Scanner letter = new Scanner(System.in);
        boolean hasWon = false;
        String chosenMovie = chooseMovie();

        System.out.println("I randomly chose a movie title.");
        System.out.println("Try to guess the title while giving me one letter at a time. You have 10 chances.");

        guessLetter(chosenMovie, letter, hasWon);
        isWon(hasWon, chosenMovie);
       
        letter.close();
    }

    private static String chooseMovie() throws Exception {
        /**
            method for choosing one movie from text file
         */

        Path moviesPath = Paths.get("movies.txt");
        int fileLines = (int) Files.lines(moviesPath).count();
        int randomLine = (int) (Math.random() * fileLines) + 1;
        String chosenMovie = Files.readAllLines(moviesPath).get(randomLine);

        return chosenMovie;
    }

    public static void guessLetter(String chosenMovie, Scanner letter, boolean hasWon) {
        /**
            method that replaces letters with underscores
            and gets user input.
            user input is compared to letters in chosen movie.
            if it matches, letters are revealed
         */

        String hiddenMovie = chosenMovie.replaceAll("[a-zA-z]", "_");
        String nonMatches = "";
        String matches = "";
        char[] hiddenArray = hiddenMovie.toCharArray();
        for(int i = 10; i > 0; i--) {
            System.out.println("\nYou have " + i + " chance(s) left.");
            System.out.println("Movie to guess: " + hiddenMovie);
            System.out.print("Your letter: ");
            char guess = letter.next().charAt(0);

            // checks if guess was right
            if(chosenMovie.indexOf(guess) != -1) {
                matches += guess;
                hiddenArray[chosenMovie.indexOf(guess)] = guess;
                hiddenMovie = new String(hiddenArray);
                System.out.println("Nice! '" + guess + "' is one of the letters");
            } else {
                System.out.println("This wasn't a correct letter.\n");

                if(nonMatches.indexOf(guess) == -1) {
                    nonMatches += guess;
                } else if (nonMatches.indexOf(guess) != -1 || matches.indexOf(guess) != -1) {
                    System.out.println("You already tried that letter!");
                    i++;
                } 

                System.out.print("Letters that didn't match: ");
                for(int j = 0; j < nonMatches.toCharArray().length; j++) {
                    System.out.print(nonMatches.toCharArray()[j] + " ");
                }
            }

            // checks if title is guessed
            if(hiddenMovie.equalsIgnoreCase(chosenMovie)) {
                System.out.println("You won!");
                hasWon = true;
                break;
            }
        }
    }

    public static void isWon(boolean hasWon, String chosenMovie) {
        /**
         method that displays message which depends on the result of the game
         */
        
        if(hasWon) {
            System.out.println("\nMy movie title was " + chosenMovie.toUpperCase());
        } else {
            System.out.println("\nToo bad, you lose. My movie title was " + chosenMovie.toUpperCase());
        }
    }
}
