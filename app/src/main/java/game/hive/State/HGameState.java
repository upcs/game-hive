package game.hive.State;

import android.view.View;

import java.util.ArrayList;

import game.hive.GameFramework.infoMessage.GameState;

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

        if(playerId!=activePlayer){
            return false;
        }
        boolean valid = false;
        if(turnNumber == 1){
            valid = true;
            X = 15;
            Y = 15;
        }


        HexSpace SelectedHex = Board.get(X).get(Y);
        HexSpace Side1 = Board.get(X).get(Y-1);
        HexSpace Side2 = Board.get(X+1).get(Y-1);
        HexSpace Side3 = Board.get(X-1).get(Y);
        HexSpace Side4 = Board.get(X+1).get(Y);
        HexSpace Side5 = Board.get(X).get(Y+1);
        HexSpace Side6 = Board.get(X+1).get(Y+1);

        //check location is empty
        if(SelectedHex.getHex()!=null){
            return false;
        }
         else if (turnNumber == 2) {
            if((Side1.getHex().getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side1.getHex().getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side2.getHex().getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side2.getHex().getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side3.getHex().getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side3.getHex().getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side4.getHex().getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side4.getHex().getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side5.getHex().getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side5.getHex().getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }
            else if((Side6.getHex().getColor()== Hex.Color.WHITE && activePlayer==1)||
                    (Side6.getHex().getColor()== Hex.Color.BLACK && activePlayer==0)){
                valid = true;
            }

        } else if((Side1.getHex().getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side1.getHex().getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side2.getHex().getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side2.getHex().getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side3.getHex().getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side3.getHex().getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side4.getHex().getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side4.getHex().getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side5.getHex().getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side5.getHex().getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }
        else if((Side6.getHex().getColor()== Hex.Color.WHITE && activePlayer==0)||
                (Side6.getHex().getColor()== Hex.Color.BLACK && activePlayer==1)){
            valid = true;
        }

        //check valid move

        if(valid){
            if(activePlayer==1) {
                SelectedHex.setHex(new Hex(Hex.Color.WHITE, name));
            }
            else{
                SelectedHex.setHex(new Hex(Hex.Color.BLACK, name));
            }
            activePlayer=1-activePlayer;
            return true;
        }
        //place piece if valid and return true


        return false; //did not make a move
    }
    public boolean movePiece(int Xloc, int Yloc, int Xdest, int Ydest, int playerId){
        // if we're out of bounds or anything, return;
        if (Board == null || Xloc < 0 || Yloc < 0 || Xdest < 0 || Ydest < 0) return false;
        //if (X >= Board.length || Y >= Board[X].length) return;

        // return the character that is in the proper position
        //Board[X][Y] = piece;

        if(playerId!=activePlayer){
            return false;
        }
        boolean valid = false;
        HexSpace SelectedHex = Board.get(Xloc).get(Yloc);
        HexSpace DestinationHex = Board.get(Xdest).get(Ydest);
        HexSpace Side1 = Board.get(Xdest).get(Ydest-1);
        HexSpace Side2 = Board.get(Xdest+1).get(Ydest-1);
        HexSpace Side3 = Board.get(Xdest-1).get(Ydest);
        HexSpace Side4 = Board.get(Xdest+1).get(Ydest);
        HexSpace Side5 = Board.get(Xdest).get(Ydest+1);
        HexSpace Side6 = Board.get(Xdest+1).get(Ydest+1);
        if(DestinationHex.getHex()==null &&
                ((SelectedHex.getHex().getColor()== Hex.Color.WHITE && playerId==0)||
                (SelectedHex.getHex().getColor()== Hex.Color.BLACK && playerId==1))&&
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
