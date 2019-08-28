/*
 * Author: Jeff Flanegan
 * Class: CSC 160 Combo
 * Project: Mancala
 * Due: 4/5
 */

/*
 * This is a game where each player is trying to get their beads into their goal
 * the players can only choose beads from  their side and dont put beads in the opponent's goal
 * when the last bead the player drops lands in a space that is not empty, the player picks up the beads in that bin and goes again.
 * The game is over when all the beads on one side are gone, the beads remaining on
 * 	 the other side are added to the players score who cleared their board first.
 * The largest score wins.
 * Player can play against a human or the computer.
 */
import java.util.Scanner;

public class Mancala_10_Jeff_Flanegan
{
	public static void main( String[ ] args )
	{
		Scanner input = new Scanner ( System.in );
		int player = 0;
		int beadAmount = 0;
		char play = 'y';
		int[ ] beadArray = startingArray ( ); // starting bead amounts in bins
		System.out.println ( "Do you wish to play against the computer?\ny/n" );
		char comp = input.next ( ).charAt ( 0 );
		System.out.println ( "\nWhich player is going first?\nEnter 1 for player 1 or 2 for player 2" );
		player = input.nextInt ( ); // determine player
		while ( play == 'y' )
		{ // M 9) make loop for gameplay
			showBoard ( beadArray );
			int binChoice = getStartingBin ( beadArray, player, comp ); // player chooses bin
			player = dropBeads ( beadArray, player, binChoice, beadAmount, comp ); // beads are collected and displaced
			int repeat = gameOverCheck ( beadArray ); // check for game ending conditions
			if ( repeat != -1 )
			{
				showBoard ( beadArray ); // show endgame board
				System.out.println ( "\nPlay again?\ny/n" );
				play = input.next ( ).charAt ( 0 );
				beadArray = startingArray ( ); // reset board for new game
			}
		}
		System.out.println ( "Thanks for playing!" ); // game over
		input.close ( );
	}

	// Mancala 1
	// output line of stars
	public static void makeSolidLine( int i )
	{
		while ( i > 0 )
		{
			System.out.print ( '*' );
			i--;
		}

	}

	// Mancala 2
	// output dotted line of stars
	public static void makeDottedLine( )
	{
		int i = 8;
		int j = 8;
		System.out.print ( '*' );
		while ( i == j && i >= 1 )
		{
			System.out.print ( "      " );
			j--;
			while ( j < i && j >= 0 )
			{
				System.out.print ( '*' );
				i--;
			}
		}
	}

	// Mancala 3
	// use solid and dotted lines to create board
	// output grid and starting goal numbers
	public static void showBoard( int[ ] beadArray )
	{
		makeSolidLine ( 57 );
		System.out.println ( "" );
		makeDottedLine ( );
		System.out.println ( "" );
		showTopRowNumbers ( ); // top row bin numbers
		System.out.println ( "" );
		makeDottedLine ( );
		System.out.println ( "" );
		showTopBins ( beadArray ); // top row array
		System.out.println ( "" );
		makeDottedLine ( );
		System.out.println ( "" );
		System.out.printf ( "*%4d  ", 13 ); // player 2 score bin (left)
		makeSolidLine ( 43 );
		System.out.printf ( "%4d %2s", 6, '*' ); // player 1 score bin (right)
		System.out.println ( "" );
		makeDottedLine ( );
		System.out.println ( "" );
		showBottomRowNumbers ( ); // bottom row bin numbers
		System.out.println ( "" );
		makeDottedLine ( );
		System.out.println ( "" );
		showBottomBins ( beadArray ); // bottom row array with score bins
		System.out.println ( "" );
		makeDottedLine ( );
		System.out.println ( "" );
		makeSolidLine ( 57 );
	}

	// output top row numbers
	public static void showTopRowNumbers( )
	{
		int i = 0;
		System.out.printf ( "%s %6s %3d", '*', '*', i );
		i++;
		while ( i < 6 )
		{
			System.out.printf ( "%3s %3d", '*', i );
			i++;
		}
		System.out.printf ( "%3s %6s", '*', '*' );
	}

	// output bottom row numbers
	public static void showBottomRowNumbers( )
	{
		int i = 12;
		System.out.printf ( "%s %6s %3d", '*', '*', i );
		i--;
		while ( i > 6 )
		{
			System.out.printf ( "%3s %3d", '*', i );
			i--;
		}
		System.out.printf ( "%3s %6s", '*', '*' );
	}

	// Mancala 4
	// make array for bead numbers
	public static int[ ] startingArray( )
	{
		int[ ] beadArray = { 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0 };
		return beadArray;

	}

	// output array
	public static void printArray( int[ ] beadArray )
	{
		for ( int i = 0; i < beadArray.length; i++ )
		{
			System.out.print ( beadArray[i] + " " );
		}
	}

	// Mancala 5
	// output beads at bin locations
	public static void showTopBins( int[ ] beadArray )
	{
		int i = 0;
		System.out.printf ( "%s %6s %3d", '*', '*', beadArray[i] );
		i++;
		while ( i < 6 )
		{
			System.out.printf ( "%3s %3d", '*', beadArray[i] );
			i++;
		}
		System.out.printf ( "%3s %6s", '*', '*' );

	}

	public static void showBottomBins( int[ ] beadArray )
	{
		int i = 12;
		System.out.printf ( "%s %3s %2s %3d", '*', beadArray[13], '*', beadArray[i] );
		i--;
		while ( i > 6 )
		{
			System.out.printf ( "%3s %3d", '*', beadArray[i] );
			i--;
		}
		System.out.printf ( "%3s %3s %2s", '*', beadArray[6], '*' );
	}

	// Mancala 6
	// determine game over, add loser side to winner score, output winner and score, return result
	public static int gameOverCheck( int[ ] beadArray )
	{
		int sum = 0;
		// if all bins are empty on players side, add total beads from opponents side to players score
		// player 1 cleared their side:
		if ( beadArray[0] == 0 && beadArray[1] == 0 && beadArray[2] == 0 && beadArray[3] == 0 && beadArray[4] == 0
				&& beadArray[5] == 0 )
		{
			for ( int i = 12; i > 6; i-- )
			{
				sum += beadArray[i];
				beadArray[i] = 0;
			}
			beadArray[6] += sum;
			if ( beadArray[6] > beadArray[13] )
			{ // determine highest score
				System.out.println ( "\n\nPlayer 1 wins. Score: " + beadArray[6] );
			}
			else if ( beadArray[6] == beadArray[13] )
			{
				System.out.println ( "\n\nTIE GAME!" );
			}
			else
			{
				System.out.println ( "\n\nPlayer 2 wins. Score: " + beadArray[13] );
			}
			return 1;
		}
		// player 2 cleared their side:
		else if ( beadArray[12] == 0 && beadArray[11] == 0 && beadArray[10] == 0 && beadArray[9] == 0 && beadArray[8] == 0
				&& beadArray[7] == 0 )
		{
			for ( int i = 0; i < 6; i++ )
			{
				sum += beadArray[i];
				beadArray[i] = 0;
			}
			beadArray[13] += sum;
			if ( beadArray[13] > beadArray[6] )
			{
				System.out.println ( "\n\nPlayer 2 wins. Score: " + beadArray[13] );
			}
			else if ( beadArray[6] == beadArray[13] )
			{
				System.out.println ( "\n\nTIE GAME!" );
			}
			else
			{
				System.out.println ( "\n\nPlayer 1 wins. Score: " + beadArray[6] );
			}
			return 2;
		}
		// game is over when a value not equal to -1 is returned
		else
		{
			return -1;
		}
	}

	// Mancala 7
	// ask user for starting bin, check validity for player side and bin not empty, return choice
	// initialize player number
	public static int getStartingBin( int[ ] beadArray, int player, int comp )
	{
		Scanner input = new Scanner ( System.in );
		int sBin = 99;
		if ( player == 1 )
		{
			do
			{
				System.out.println ( "\n\nplayer " + player + " choose a bin" );
				sBin = input.nextInt ( );
				if ( sBin > 5 || sBin < 0 )
				{
					System.out.println ( "you must choose between 0-5" );
				}
				if ( beadArray[sBin] == 0 )
				{
					System.out.println ( "You can't choose and empty bin, pick a different bin." );
				}
			} while ( sBin > 5 || sBin < 0 || beadArray[sBin] == 0 ); // check for correct range and bin content
		}
		else if ( comp != 'y' && player == 2 )
		{
			do
			{
				System.out.println ( "\n\nplayer " + player + " choose a bin" );
				sBin = input.nextInt ( );
				if ( sBin > 12 || sBin < 7 )
				{
					System.out.println ( "you must choose between 7-12" );
				}
				if ( beadArray[sBin] == 0 )
				{
					System.out.println ( "You can't choose an empty bin, pick a different bin." );
				}
			} while ( sBin > 12 || sBin < 7 || beadArray[sBin] == 0 );
		}
		// M 10) play against computer
		else if ( comp == 'y' && player == 2 )
		{
			// computer attempts to end in their own goal
			if ( beadArray[12] == 1 )
			{
				sBin = 12;
			}
			else if ( beadArray[11] == 2 )
			{
				sBin = 11;
			}
			else if ( beadArray[10] == 3 )
			{
				sBin = 10;
			}
			else if ( beadArray[9] == 4 )
			{
				sBin = 9;
			}
			else if ( beadArray[8] == 5 )
			{
				sBin = 8;
			}
			else if ( beadArray[7] == 6 )
			{
				sBin = 7;
			}
			else if ( beadArray[11] == 1 && beadArray[7] == 4 )
			{
				sBin = 7;
			}
			else if ( beadArray[11] == 1 && beadArray[8] == 3 )
			{
				sBin = 8;
			}
			else if ( beadArray[11] == 1 && beadArray[9] == 2 )
			{
				sBin = 9;
			}
			else if ( beadArray[11] == 1 && beadArray[10] == 1 )
			{
				sBin = 10;
			}
			else if ( beadArray[10] == 2 && beadArray[7] == 3 )
			{
				sBin = 7;
			}
			else if ( beadArray[10] == 2 && beadArray[8] == 2 )
			{
				sBin = 8;
			}
			else if ( beadArray[10] == 2 && beadArray[9] == 1 )
			{
				sBin = 9;
			}
			else if ( beadArray[9] == 3 && beadArray[7] == 2 )
			{
				sBin = 7;
			}
			else if ( beadArray[9] == 3 && beadArray[8] == 1 )
			{
				sBin = 8;
			}
			else if ( beadArray[8] == 4 && beadArray[7] == 1 )
			{
				sBin = 7;
			}
			// else, computer finds and picks largest amount on their side
			else
			{
				int max = 0;
				int maxIndex = 0;
				for ( int i = 7; i < 13; i++ )
				{
					if ( beadArray[i] > max )
					{
						max = beadArray[i];
						maxIndex = i;
					}
				}
				sBin = maxIndex;
			}
		}
		if ( comp == 'y' && player == 2 )
		{
			System.out.println ( "\nOpponent chose " + sBin );
		}
		else
		{
			System.out.println ( "You chose " + sBin );
		}
		return sBin;
	}

	// Mancala 8
	// drop beads in bins and setup next round by switching player
	// M-9) player continues if last bead is dropped in non-empty bin
	public static int dropBeads( int[ ] beadArray, int player, int binChoice, int beadAmount, int comp )
	{
		beadAmount = beadArray[binChoice]; // the number of beads stored from the array number of the bin
		System.out.println ( "Picked up " + beadAmount + " beads" );
		beadArray[binChoice] = 0; // contents of chosen bin taken
		int goalCount = 0; // temporary variable that marks if player ended in their goal
		while ( beadAmount > 0 )
		{
			goalCount = 0;
			binChoice++;
			if ( player == 1 && binChoice == 13 )
			{ // players don't drop beads in opponents score bin
				binChoice = 0;
			}
			else if ( player == 2 && binChoice == 6 )
			{
				binChoice = 7;
			}
			else if ( binChoice == 14 )
			{ // array can't register a number outside its range
				binChoice = 0;
			}
			beadArray[binChoice]++;
			beadAmount--;
			if ( player == 1 && binChoice == 6 && beadAmount == 0 )
			{
				System.out.println ( "You ended in your goal, choose any bin from your side" );
				player = 2; // initialize player switch to switch back to go again
				goalCount = 1;
			}
			else if ( player == 2 && binChoice == 13 && beadAmount == 0 )
			{
				if ( comp == 'y' )
				{
					System.out.println ( "Opponent ended in their goal, they will go again from their side\nPress Enter" );
					Scanner KeyIn = new Scanner ( System.in );
					KeyIn.nextLine ( );
				}
				else
				{
					System.out.println ( "You ended in your goal, choose any bin from your side" );
				}
				goalCount = 1;
				player = 1;
			}
			// if last bead was dropped in bin that was not empty, player keeps going
			else if ( beadArray[binChoice] > 1 && beadAmount == 0 )
			{
				System.out.println ( "Ended on bin " + binChoice + "\nGo again from this bin\n Press Enter" );
				Scanner KeyIn = new Scanner ( System.in );
				KeyIn.nextLine ( );
				beadAmount = beadArray[binChoice]; // picks up beads from last bin used
				System.out.println ( "\nPicked up " + beadAmount + " beads" );
				beadArray[binChoice] = 0; // beads depleted from bin
			}
		}
		if ( player == 1 )
		{ // switch players
			player = 2;
		}
		else if ( player == 2 )
		{
			player = 1;
		}
		if ( goalCount == 0 )
		{ // this text is not shown if player ended in their goal
			System.out.println ( "Ended on bin " + binChoice + "\n\nPlayer " + player + "'s turn." );
		}
		return player;
	}
}
// no problems