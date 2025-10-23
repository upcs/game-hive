package game.hive.State;

import android.view.View;

import java.util.ArrayList;

public class HGameState implements View.OnClickListener {
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
        WhitesHand.add(new QueenBee(Hex.Color.WHITE));
        WhitesHand.add(new Beetle(Hex.Color.WHITE));
        WhitesHand.add(new Beetle(Hex.Color.WHITE));
        WhitesHand.add(new Spider(Hex.Color.WHITE));
        WhitesHand.add(new Spider(Hex.Color.WHITE));
        WhitesHand.add(new Ant(Hex.Color.WHITE));
        WhitesHand.add(new Ant(Hex.Color.WHITE));
        WhitesHand.add(new Ant(Hex.Color.WHITE));
        WhitesHand.add(new Grasshopper(Hex.Color.WHITE));
        WhitesHand.add(new Grasshopper(Hex.Color.WHITE));
        WhitesHand.add(new Grasshopper(Hex.Color.WHITE));

        // Add black pieces to BlacksHand
        BlacksHand.add(new QueenBee(Hex.Color.BLACK));
        BlacksHand.add(new Beetle(Hex.Color.BLACK));
        BlacksHand.add(new Beetle(Hex.Color.BLACK));
        BlacksHand.add(new Spider(Hex.Color.BLACK));
        BlacksHand.add(new Spider(Hex.Color.BLACK));
        BlacksHand.add(new Ant(Hex.Color.BLACK));
        BlacksHand.add(new Ant(Hex.Color.BLACK));
        BlacksHand.add(new Ant(Hex.Color.BLACK));
        BlacksHand.add(new Grasshopper(Hex.Color.BLACK));
        BlacksHand.add(new Grasshopper(Hex.Color.BLACK));
        BlacksHand.add(new Grasshopper(Hex.Color.BLACK));
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
