package com.zacck.test.ttt.gamelogic;

import java.util.Random;

public class GameLogic {

	//this contains the game elements and game logic 
	//this is the whole board
	char tttBoard [];
	final static int mBoardSize = 9;
	//human and machine game signs 
	public static final char PERSON = 'X';
	public static final char MACHINE = 'O';
	//unplayed location of the board 
	public static final char POSITION = ' ';
	
	private Random Randy;
	
	//set up a constructor to init the game and its elements 
	public GameLogic()
	{
		tttBoard = new char[mBoardSize];
		//empty the board and set all its positions as clear 
		for(int i=0; i < mBoardSize; i++)
		{
			tttBoard[i] = POSITION;
			
			Randy = new Random();
		}
	} 
	
	//this is the getter for the total number of spaces on the board
	public static int BSPACE()
	{
		return mBoardSize;
	}
	

	
	//this clears the values on the board
	//gives us a new board with nothing 
	public void emptyBoard()
	
	{
		for(int i=0; i < mBoardSize; i++)
		{
			tttBoard[i] = POSITION;
			
		}
	}
	
	//lets define what a win is so that we can check for it 
	public int Win()
	{
		/*
		 * if a draw occurs we shall give a 1
		 * if the person wins we shall give a 2 
		 * if machine wins we shall give a 3
		 * if there is a move open we give a 0
		 */
		//lets define and check the horizontal wins 
		for(int i =0; i<= 6; i+=3)
		{
			//does human win?
			if(tttBoard[i] == PERSON && tttBoard[i+1] == PERSON && tttBoard[i+2] == PERSON)
			{
				return 2;
			}
			//does machine win 
			if(tttBoard[i] == MACHINE && tttBoard[i+1] == MACHINE && tttBoard[i+2] == MACHINE)
			{
				return 3;
			}
				
		}
		
		//lets do the same for vertical wins
		
		for(int i = 0; i <=2; i++)
		{
			if(tttBoard[i] == PERSON && tttBoard[i+3] == PERSON && tttBoard[i+6] == PERSON)
			{
				return 2;
			}
			if(tttBoard[i] == MACHINE && tttBoard[i+3] == MACHINE && tttBoard[i+6] == MACHINE)
			{
				return 3;
			}
		}
		
		//lets check the diagonals 
		
		if ((tttBoard[0] == PERSON &&  tttBoard[4] == PERSON && tttBoard[8] == PERSON) 
				||tttBoard[2] == PERSON && tttBoard[4] == PERSON && tttBoard[6] == PERSON)
		{
			return 2;
		}
		if ((tttBoard[0] == MACHINE &&  tttBoard[4] == MACHINE && tttBoard[8] == MACHINE) 
				||tttBoard[2] == MACHINE && tttBoard[4] == MACHINE && tttBoard[6] == MACHINE)
		{
			return 3;
		}
		
		//lets check for a draw 
		for(int i = 0; i<BSPACE(); i++)
		{
			if(tttBoard[i] != PERSON && tttBoard[i] != MACHINE)
			{
				return 0;
			}
		}
		
		//if there are no more moves to play and a win has not been taken 
		// a draw has occured 
		return 1;
	}
	
	//setter for when a player moves machine or human 
	public void playerPlay(char player, int positionTO)
	{
		//record which player moved where
		tttBoard[positionTO] = player;
		
	}
	
	public int MachineMove()
	{
		int play;
		
		//try and win
		for(int move =0; move<BSPACE(); move++)
		{
			//check if each position of the board is occupied 
			if(tttBoard[move] !=PERSON && tttBoard[move] != MACHINE)
			{
				//if its not occupied assume machine can play there
				char curr = tttBoard[move];
				tttBoard[move] = MACHINE;
				//if the machine plays this move will it win the game ?
				//if that will be achieved then play this move 
				if(Win() == 3)
				{
					playerPlay(MACHINE, move);
					return move;
				}
				else
					tttBoard[move] = curr;
			}
		}
		
		//if we cant win do we need to prevent  human from winning ?
		for (int block=0; block<BSPACE(); block++)
		{
			//check if each position of the board is occupied 
			if(tttBoard[block] !=PERSON && tttBoard[block] != MACHINE)
			{
				//if its not occupied assume person can play there
				char curr = tttBoard[block];
				tttBoard[block] = PERSON;
				//if the person plays this move will person win the game ?
				//if that will be achieved then block this move 
				if(Win() == 2)
				{
					playerPlay(MACHINE, block);
					return block;
				}
				else
					tttBoard[block] = curr;
			}
			
		}
		
		do 
		{
			play = Randy.nextInt(BSPACE());
		}
		//the play we generated is it to a position that is occupied if it is lets get a random move again
		while(tttBoard[play] == PERSON || tttBoard[play] == MACHINE);
		
			playerPlay(MACHINE, play);
		
		return play;
	}
	
	
	
	
	

}
