//UIUC CS125 FALL 2013 MP. File: MovieSurvey.java, CS125 Project: Challenge2-Hollywood, Version: 2013-09-17T09:01:56-0500.998484625
/**
 * A program to run a simple survey and report the results. See MovieSurvey.txt
 * for more information. TODO: add your netid to the line below
 * 
 * @author dwoskin2
 */
public class MovieSurvey {
	public static void main(String[] arg) {
		/* First, some introductory lines are printed to the screen.
	    * Next, the program should prompt for the following three quantities, in the following order: the number of movies watched at a cinema, using a dvd player, using a computer.
	    * Next, the program should print out a summary of the input values.
	    * Next, the program should print out the total number of movies seen in one month.
	    * Next, the program should print out the percent of movies seen at the cinema to two decimal places.
	    * Finally, the program should print out the percent of movies seen outside of the cinema to two decimal places.*/
		int cinema = 0;
		int dvd = 0;
		int computer = 0;
		int total = 0;
		double cinemaPercentage;
		double dvdComputerPercentage;
		
		System.out.println("Welcome. We're interested in how people are watching movies this year.");
		System.out.println("Thanks for your time. - The WRITERS GUILD OF AMERICA.");
		System.out.println("Please tell us about how you've watched movies in the last month.");
		System.out.println("How many movies have you seen at the cinema?");
			cinema = TextIO.getlnInt();
		System.out.println("How many movies have you seen using a DVD or VHS player?");
			dvd = TextIO.getlnInt();
		System.out.println("How many movies have you seen using a Computer?");
			computer = TextIO.getlnInt();
		total = cinema + dvd + computer;
		System.out.println("Summary: " + cinema + " Cinema movies, " + dvd + " DVD/VHS movies, " + computer + " Computer movies");
		System.out.println("Total: " + total + " movies");
		
		cinemaPercentage = ((cinema / (double)total)* 100);
		dvdComputerPercentage = (((dvd + computer) / (double)total)*100);
		
		TextIO.putf("Fraction of movies seen at a cinema: " + "%.2f", cinemaPercentage);
		System.out.println("%");
		TextIO.putf("Fraction of movies seen outside of a cinema: " + "%.2f", dvdComputerPercentage);
		System.out.println("%");
	}
}
