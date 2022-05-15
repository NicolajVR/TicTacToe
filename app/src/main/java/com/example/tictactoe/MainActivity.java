package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean activePlayer;
    //player 1 = 0
    //player 0 = 1
    //empty => 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //Tells that all Buttons are Empty
    //All winning positions
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}//Rows (Horizontal)
            , {0, 3, 6}, {1, 4, 7}, {2, 5, 8} //Columns (Vertical)
            , {0, 4, 8}, {2, 4, 6} //Cross
    };
    //variables
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;
    private int playerOneScoreCount, playerTwoScoreCount, roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        //Gives ID for the buttons.
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            //"converts" the buttonID
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            //set the R.id.resourceID (ID for the buttons)
            buttons[i] = (Button) findViewById(resourceID);
            //Sets on click listeners for all buttons
            buttons[i].setOnClickListener(this);
        }

        //Resets everything on start.
        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        //gets ID for the buttons of the button that is pressed.
        String buttonID = v.getResources().getResourceEntryName(v.getId()); // btn_2
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); // 2
        //PLayer One
        if (activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else { //Player Two
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#18de4d"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (roundCount == 9) {
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();

        } else {
            activePlayer = !activePlayer;
        }
        if (playerOneScoreCount > playerTwoScoreCount) {
            playerStatus.setText("Player One is winning!");
        } else if (playerOneScoreCount < playerTwoScoreCount) {
            playerStatus.setText("Player Two is winning!");
        } else {
            playerStatus.setText("");
        }

        //Reset the game
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });


    }

    public boolean checkWinner() {
        boolean winnerResult = false;
        //checking for winnerPositions defined in the Array of winningPositions.
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    //Updating playerScore
    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    //Reset game
    public void playAgain() {
        roundCount = 0;
        activePlayer = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}

//Tutorial Followed: https://www.youtube.com/watch?v=CCQTD7ptYqY