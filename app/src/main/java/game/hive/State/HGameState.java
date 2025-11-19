package game.hive.State;

import android.view.View;

import java.util.ArrayList;

import game.hive.GameFramework.infoMessage.GameState;
import game.hive.GameFramework.utilities.Logger;

public class HGameState extends GameState implements View.OnClickListener {
    int activePlayer; //0 for White player, 1 for Black Player
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
        for(int i=0;i<30;i++)
        {
            Board.add(new ArrayList<HexSpace>());
            for(int j=0;j<30;j++){
                Board.get(i).add( new HexSpace() );
            }
        }

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
        this.isBeePlaced = new boolean[2];
        this.isBeePlaced[0] = orig.isBeePlaced[0];
        this.isBeePlaced[1] = orig.isBeePlaced[1];
        this.turnNumber = orig.turnNumber;
        //this.WhitesHand = orig.WhitesHand;
        //this.BlacksHand = orig.BlacksHand;
        //this.Board = orig.Board;
        WhitesHand = new ArrayList<>();
        for(Hex hex : orig.WhitesHand){
            WhitesHand.add(new Hex(hex));
        }
        BlacksHand = new ArrayList<>();
        for(Hex hex : orig.BlacksHand) {
            BlacksHand.add(new Hex(hex));
        }
        Board = new ArrayList<>();
        for(int i=0;i<orig.Board.size();i++){
            Board.add(new ArrayList<>());
            for(int j=0;j<orig.Board.size();j++){
                Board.get(i).add(new HexSpace(orig.Board.get(i).get(j)));
            }
        }
    }

    /*public boolean placePiece(int X, int Y, String name, int playerId) {
        // if we're out of bounds or anything, return;
        if (Board == null || X < 0 || Y < 0) return false;
        //if (X >= Board.length || Y >= Board[X].length) return;

        // return the character that is in the proper position
        //Board[X][Y] = piece;

        if(playerId!=activePlayer){
            return false;
        }
        boolean valid = false;
        valid = true; // for testing

        if(turnNumber == 1){
            valid = true;
            X = 7;
            Y = 7;
        }


        HexSpace SelectedHex = Board.get(X).get(Y);
        HexSpace Side1 = Board.get(X-2).get(Y);
        HexSpace Side2 = Board.get(X-1).get(Y);
        HexSpace Side3 = Board.get(X+1).get(Y);
        HexSpace Side4 = Board.get(X+2).get(Y);
        HexSpace Side5 = Board.get(X+1).get(Y-1);
        HexSpace Side6 = Board.get(X-1).get(Y-1);

        //check location is empty
        if(SelectedHex.getHex()!=null){
            return false;
        }
         else if (turnNumber == 2) {
            if((Side1.getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side1.getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side2.getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side2.getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side3.getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side3.getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side4.getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side4.getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side5.getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side5.getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side6.getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side6.getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }

        } else if((Side1.getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side1.getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side2.getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side2.getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side3.getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side3.getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side4.getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side4.getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side5.getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side5.getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side6.getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side6.getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }

        //check valid move

        if(valid){
            if(activePlayer==0) {
                SelectedHex.setHex(new Hex(Hex.Color.WHITE, name));
                Logger.log("place piece","placed white piece");

            }
            else{
                SelectedHex.setHex(new Hex(Hex.Color.BLACK, name));
                Logger.log("place piece","placed black piece");
            }
            turnNumber++;
            activePlayer=1-activePlayer;
            return true;
        }
        //place piece if valid and return true


        return false; //did not make a move
    }*/

    public boolean isValidPlacement(int X, int Y, String name, int playerId) {
        // board must exist and coordinates must be in range
        if (Board == null || !isInBounds(X, Y)) {
            return false;
        }

        // must be this player's turn
        if (playerId != activePlayer) {
            return false;
        }

        HexSpace selected = getHexSpace(X, Y);
        if (selected == null) {
            return false;
        }

        // hex must be empty
        if (selected.getHex() != null) {
            return false;
        }

        // first turn: center placement always
        if (turnNumber == 1) {
            return true;
        }

        // neighbor spaces
        HexSpace side1 = getHexSpace(X - 2, Y);
        HexSpace side2 = getHexSpace(X - 1, Y);
        HexSpace side3 = getHexSpace(X + 1, Y);
        HexSpace side4 = getHexSpace(X + 2, Y);
        HexSpace side5 = getHexSpace(X + 1, Y - 1);
        HexSpace side6 = getHexSpace(X - 1, Y - 1);

        // Player color determination
        Hex.Color myColor;
        Hex.Color oppColor;

        if (activePlayer == 0) {
            // player 0 = White
            myColor  = Hex.Color.WHITE;
            oppColor = Hex.Color.BLACK;
        }
        else {
            // player 1 = Black
            myColor  = Hex.Color.BLACK;
            oppColor = Hex.Color.WHITE;
        }

        // turn 2: must be next to at least one opponent piece
        if (turnNumber == 2) {
            boolean touchesOpponent =
                    hasColor(side1, oppColor) ||
                            hasColor(side2, oppColor) ||
                            hasColor(side3, oppColor) ||
                            hasColor(side4, oppColor) ||
                            hasColor(side5, oppColor) ||
                            hasColor(side6, oppColor);

            return touchesOpponent;
        }

        // later turns: must be next to at least one of own pieces
        boolean touchesOwn =
                hasColor(side1, myColor) ||
                        hasColor(side2, myColor) ||
                        hasColor(side3, myColor) ||
                        hasColor(side4, myColor) ||
                        hasColor(side5, myColor) ||
                        hasColor(side6, myColor);

        return touchesOwn;
    }

    //place piece at (X, Y) if isValidPlacement
    //advance turn is in here
    public boolean placePiece(int X, int Y, String name, int playerId) {
        //first turn, force the piece to be placed at (7,7)
        if (turnNumber == 1) {
            X = 7;
            Y = 7;
        }

        // check for valid placement
        if (!isValidPlacement(X, Y, name, playerId)) {
            return false; // illegal placement, do nothing
        }

        // should never be null
        // extra check to avoid crash
        HexSpace selected = getHexSpace(X, Y);
        if (selected == null) {
            return false; // safety check; should not happen if isValidPlacement worked
        }

        // Decide the color based on activePlayer
        // 0 = WHITE, 1 = BLACK
        Hex.Color color = (activePlayer == 0) ? Hex.Color.WHITE : Hex.Color.BLACK;

        // Actually place the piece on the board
        selected.setHex(new Hex(color, name));
        Logger.log("place piece",
                "placed " + (color == Hex.Color.WHITE ? "white" : "black")
                        + " piece '" + name + "' at (" + X + ", " + Y + ")");

        // TODO: remove this piece (piece being placed)from the player's hand
        // (WhitesHand / BlacksHand) want hands to shrink

        // advance game state
        turnNumber++;
        activePlayer = 1 - activePlayer; // alternate players

        return true; // successfully placed
    }

    public boolean isValidMove(int Xloc, int Yloc, int Xdest, int Ydest, int playerId) {

        // board must exist
        if (Board == null) {
            return false;
        }

        // both curr dest and new destination must be on the board
        if (!isInBounds(Xloc, Yloc) || !isInBounds(Xdest, Ydest)) {
            return false;
        }

        // must be this player's turn
        if (playerId != activePlayer) {
            return false;
        }

        HexSpace from = getHexSpace(Xloc, Yloc);
        HexSpace to   = getHexSpace(Xdest, Ydest);

        // if somehow null, move is invalid
        if (from == null || to == null) {
            return false;
        }

        // there must be a piece to move
        if (from.getHex() == null) {
            return false;
        }

        // piece must belong to this player
        Hex.Color myColor;
        if (playerId == 0) {
            // player 0 = White
            myColor = Hex.Color.WHITE;
        } else {
            // player 1 = Black
            myColor = Hex.Color.BLACK;
        }

        if (from.getColor() != myColor) {
            return false;
        }

        // destination must be empty
        if (to.getHex() != null) {
            return false;
        }

        // neighbor spaces around the DESTINATION hex
        HexSpace side1 = getHexSpace(Xdest,     Ydest - 2);
        HexSpace side2 = getHexSpace(Xdest,     Ydest - 1);
        HexSpace side3 = getHexSpace(Xdest,     Ydest + 1);
        HexSpace side4 = getHexSpace(Xdest,     Ydest + 2);
        HexSpace side5 = getHexSpace(Xdest - 1, Ydest + 1);
        HexSpace side6 = getHexSpace(Xdest - 1, Ydest - 1);

        //TODO: Add piece specific movement
        // For now ... require that the destination touches at least one piece (any color)
        boolean hasNeighbor =
                hasAPiece(side1) ||
                        hasAPiece(side2) ||
                        hasAPiece(side3) ||
                        hasAPiece(side4) ||
                        hasAPiece(side5) ||
                        hasAPiece(side6);

        if (!hasNeighbor) {
            return false;
        }

        return true;
    }
    public boolean movePiece(int Xloc, int Yloc, int Xdest, int Ydest, int playerId) {
        // check valid move
        if (!isValidMove(Xloc, Yloc, Xdest, Ydest, playerId)) {
            return false;
        }

        HexSpace from = getHexSpace(Xloc, Yloc);
        HexSpace to   = getHexSpace(Xdest, Ydest);

        if (from == null || to == null) {
            return false; // safety check
        }

        // move the piece from curr to new destination
        to.setHex(from.getHex());
        from.setHex(null);

        // switch to the other player's turn
        activePlayer = 1 - activePlayer;

        return true;
    }

    /*public boolean movePiece(int Xloc, int Yloc, int Xdest, int Ydest, int playerId){
        // if we're out of bounds or anything, return;
        if (Board == null || Xloc < 0 || Yloc < 0 || Xdest < 0 || Ydest < 0) return false;
        //if (X >= Board.length || Y >= Board[X].length) return;

        // return the character that is in the proper position
        //Board[X][Y] = piece;

        if(playerId!=activePlayer){
            return false;
        }
        boolean valid = false;
        valid = true; // for testing

        HexSpace SelectedHex = Board.get(Xloc).get(Yloc);
        HexSpace DestinationHex = Board.get(Xdest).get(Ydest);
        HexSpace Side1 = Board.get(Xdest).get(Ydest-2);
        HexSpace Side2 = Board.get(Xdest).get(Ydest-1);
        HexSpace Side3 = Board.get(Xdest).get(Ydest+1);
        HexSpace Side4 = Board.get(Xdest).get(Ydest+2);
        HexSpace Side5 = Board.get(Xdest-1).get(Ydest+1);
        HexSpace Side6 = Board.get(Xdest-1).get(Ydest-1);
        if(DestinationHex.getHex()==null &&
                ((SelectedHex.getColor()== Hex.Color.WHITE && playerId==0)||
                (SelectedHex.getColor()== Hex.Color.BLACK && playerId==1))&&
                (Side1!=null||Side2!=null||Side3!=null||Side4!=null||Side5!=null||Side6!=null)){
            valid=true;
        }
        if(valid){
            DestinationHex.setHex(SelectedHex.getHex());
            SelectedHex.setHex(null);
            activePlayer=1-activePlayer;
            return true;
        }
        return false;
    }*/

    public ArrayList<ArrayList<HexSpace>> getBoard() {
        return Board;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean[] getIsBeePlaced() {
        return isBeePlaced;
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

    public ArrayList<Hex> getBlackHand() {return BlacksHand;}


    private boolean isInBounds(int x, int y) {

        // Board.get(x) gets a row (ArrayList<HexSpace>)
        // Board.get(x).get(y) gets single HexSpace
        return x >= 0 && x < Board.size() && y >= 0 && y < Board.get(x).size();
    }

    private HexSpace getHexSpace(int x, int y) {
        if (!isInBounds(x, y)) {
            return null;
        }

        return Board.get(x).get(y);
    }

    private boolean hasColor(HexSpace space, Hex.Color color) {

        if (space == null) return false;  // checks for off board or invalid
        if (space.getHex() == null) return false; // ignore empty space
        return space.getColor() == color;  // return true if color match
    }

    private boolean hasAPiece(HexSpace space) {
        return (space != null && space.getHex() != null); // return if space not empty & if space is valid
    }

}
