package game.hive.State;

import android.view.View;

import java.util.ArrayList;

import game.hive.GameFramework.infoMessage.GameState;

public class HGameState extends GameState implements View.OnClickListener {
    int activePlayer; //1 for White player, 2 for Black Player
    boolean[] isBeePlaced = new boolean[2];//move new to constructor
    int turnNumber;
    ArrayList<Hex> WhitesHand;
    ArrayList<Hex> BlacksHand;
    ArrayList<ArrayList<HexSpace>> Board;

    public HGameState(){
        activePlayer = 1;
        isBeePlaced[0]=false;
        isBeePlaced[1]=false;
        turnNumber=1;
        WhitesHand = new ArrayList<>();
        BlacksHand = new ArrayList<>();
        initializeHands();
        Board = new ArrayList<>();

    }
    private void initializeHands() {
        // Add white pieces to WhitesHand
        WhitesHand.add(new Hex(Hex.Color.WHITE,"QueenBee"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Beetle"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Beetle"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Spider"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Spider"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Ant"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Ant"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Ant"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Grasshopper"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Grasshopper"));
        WhitesHand.add(new Hex(Hex.Color.WHITE,"Grasshopper"));

        // Add black pieces to BlacksHand
        BlacksHand.add(new Hex(Hex.Color.BLACK,"QueenBee"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Beetle"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Beetle"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Spider"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Spider"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Ant"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Ant"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Ant"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Grasshopper"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Grasshopper"));
        BlacksHand.add(new Hex(Hex.Color.BLACK,"Grasshopper"));
    }
    @Override
    public void onClick(View v) {

        HGameState FirstInstance = new HGameState();
        FirstInstance.toString();


    }
    //deep copy constructor
    public HGameState(HGameState orig){
        this.activePlayer = orig.activePlayer;
        this.isBeePlaced[0] = orig.isBeePlaced[0];
        this.isBeePlaced[1] = orig.isBeePlaced[1];
        this.turnNumber = orig.turnNumber;
        this.WhitesHand = orig.WhitesHand;
        this.BlacksHand = orig.BlacksHand;
        this.Board = orig.Board;
    }

    public boolean placePiece(int X, int Y, String name, int playerId) {
        // if we're out of bounds or anything, return;
        if (Board == null || X < 0 || Y < 0) return false;
        //if (X >= Board.length || Y >= Board[X].length) return;

        // return the character that is in the proper position
        //Board[X][Y] = piece;

        //check location is empty
        //check valid move
        //place piece if valid and return true

        HexSpace SelectedHex = Board.get(X).get(Y);
        return false; //did not make a move
    }

    @Override
    public String toString() {
        return "HiveState{"
                + "turn=" + turnNumber
                + ", activePlayer=" + activePlayer
                + ", beePlaced=" + java.util.Arrays.toString(isBeePlaced)
                + ", WhitesHand=" + WhitesHand
                + ", BlacksHand=" + BlacksHand
                + "}";
    }


}
