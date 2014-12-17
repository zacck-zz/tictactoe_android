package com.zacck.test.ttt;

import java.security.PublicKey;

import com.zacck.test.ttt.gamelogic.GameLogic;

import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	//vars
	GameLogic myGame;
	
	private Button BoardPositions[];
	
	//vars for the textviews 
	TextView tvnews, tvmachine, tvperson, tvdraws;
	
	//counters for the wins 
	private int personCount = 0;
	private int machineCount = 0;
	private int drawCount = 0;
	
	//to tell the human player to start
	private boolean PersonStart = true;
	
	//to tell the players the game is done 
	private boolean GameEnd  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
    	
    	tvnews = (TextView)findViewById(R.id.tvNews);
    	tvmachine = (TextView)findViewById(R.id.tvMachinewins);
    	tvperson = (TextView)findViewById(R.id.tvPersonWins);
    	tvdraws = (TextView)findViewById(R.id.tvDraws);
    	
    	//initialize the board positions as the buttons  
    	BoardPositions = new Button[myGame.BSPACE()];
    	
    	//initialize buttons
    	BoardPositions[0] = (Button)findViewById(R.id.button1);
    	BoardPositions[1] = (Button)findViewById(R.id.button2);
    	BoardPositions[2] = (Button)findViewById(R.id.button3);
    	BoardPositions[3] = (Button)findViewById(R.id.button4);
    	BoardPositions[4] = (Button)findViewById(R.id.button5);
    	BoardPositions[5] = (Button)findViewById(R.id.button6);
    	BoardPositions[6] = (Button)findViewById(R.id.button7);
    	BoardPositions[7] = (Button)findViewById(R.id.button8);
    	BoardPositions[8] = (Button)findViewById(R.id.button9);
    	
    	tvperson.setText(Integer.toString(personCount));
    	tvmachine.setText(Integer.toString(machineCount));
    	tvdraws.setText(Integer.toString(drawCount));
    	
    	myGame = new GameLogic();
    	
    	//after inits lets start the game
    	beginGame();
    	
		
	}
    
    private class GamePlayListener implements View.OnClickListener
    {
    	int gameposition;
    	
    	public GamePlayListener(int gp)
    	{
    		//the position for the play is what was passed in 
    		this.gameposition  = gp;
    	}
    	
    	//lets implement the clicklistener which in turn makes the move we tell it
    	public void onClick(View v)
    	{
    		//check if the game isnt over 
    		if(!GameEnd)
    		{
    			//make move if its not occupied
    			if(BoardPositions[gameposition].isEnabled())
    			{
    				setPosition(myGame.PERSON, gameposition);
    				
    				int victor = myGame.Win();
    				
    				if(victor ==0)
    				{
    					
    					tvnews.setText("Machine Turn ");
    					int gamepos = myGame.MachineMove();
    					setPosition(myGame.MACHINE, gamepos);
    					victor = myGame.Win();
    				}
    				
    				if(victor == 0)
    				{
    					tvnews.setText(R.string.turn_person);
    				}
    				else if(victor ==1)
    				{
    					tvnews.setText(R.string.drawresult);
    					drawCount++;
    					tvdraws.setText(Integer.toString(drawCount));
    					GameEnd = true;
    					
    				}
    				else if(victor == 2)
    				{
    					tvnews.setText(R.string.personwin);
    					personCount++;
    					tvperson.setText(Integer.toString(personCount));
    					GameEnd = true;
    				}
    				else 
    				{
    					tvnews.setText(R.string.machinewin);
    					machineCount++;
    					tvperson.setText(Integer.toString(machineCount));
    					GameEnd = true;
    					
    				}
    			}
    		}
    	}
    	
    }
    
    private void beginGame()
    {
    	//this just clears the board to prevent the weird things from occuring 
    	myGame.emptyBoard();
    	GameEnd = false;
    	
    	for(int i= 0; i<BoardPositions.length; i++)
    	{
    		//clear data on the board 
    		//add click listeners to the buttons 
    		BoardPositions[i].setText("");
    		BoardPositions[i].setEnabled(true);
    		BoardPositions[i].setOnClickListener(new GamePlayListener(i));
    	}
    	
    	//check who is going first and alert them 
    	if(PersonStart)
    	{
    		tvnews.setText(R.string.go_first);
    		PersonStart = false;
    		
    	}
    	else
    	{
    		tvnews.setText("Machine Will Begin");
    		//play machine game posirion
    		int gamemove = myGame.MachineMove();
    		setPosition(myGame.MACHINE, gamemove);
    		//set it so person can begin next game 
    		PersonStart = true;
    		
    	}
    }


	private void setPosition(char p, int gm) 
	{
		//call game logic and tell them what has happened 
		myGame.playerPlay(p, gm);
		//once a board position has been occupied we should set that to false so the board position cannot be played again 
		BoardPositions[gm].setEnabled(false);
		BoardPositions[gm].setText(String.valueOf(p));
		
		//change colors for players so the difference is more visible .... this is gamification 
		if(p == myGame.PERSON )
		{
			BoardPositions[gm].setTextColor(Color.BLUE);
		}
		else
		{
			BoardPositions[gm].setTextColor(Color.YELLOW);
		}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
        case R.id.action_new:
        	beginGame();
        	break;
        case R.id.action_exit:
        	MainActivity.this.finish();
        	break;
        }
        return true;
    }


	
}
