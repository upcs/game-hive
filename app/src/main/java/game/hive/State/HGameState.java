package game.hive.State;

import android.view.View;

import java.util.ArrayList;

import game.hive.GameFramework.infoMessage.GameState;
import game.hive.GameFramework.utilities.Logger;

public class HGameState extends GameState implements View.OnClickListener {
    int activePlayer; // 0 for White player, 1 for Black Player
    boolean[] isBeePlaced = new boolean[2]; // move new to constructor
    int turnNumber;
    ArrayList<Hex> WhitesHand;
    ArrayList<Hex> BlacksHand;
    ArrayList<ArrayList<HexSpace>> Board;

    private static final int FIRST_ROW = 16; // y-number you see on board
    private static final int FIRST_COL = 8;  // x-number you see on board


    public HGameState() {
        activePlayer = 1;
        isBeePlaced[0] = false;
        isBeePlaced[1] = false;
        turnNumber = 1;
        WhitesHand = new ArrayList<>();
        BlacksHand = new ArrayList<>();
        initializeHands();
        Board = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Board.add(new ArrayList<HexSpace>());
            for (int j = 0; j < 30; j++) {
                Board.get(i).add(new HexSpace());
            }
        }
    }

    private void initializeHands() {
        // Add white pieces to WhitesHand
        WhitesHand.add(new Hex(Hex.Color.WHITE, "QueenBee"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Beetle"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Beetle"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Spider"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Spider"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Ant"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Ant"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Ant"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Grasshopper"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Grasshopper"));
        WhitesHand.add(new Hex(Hex.Color.WHITE, "Grasshopper"));

        // Add black pieces to BlacksHand
        BlacksHand.add(new Hex(Hex.Color.BLACK, "QueenBee"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Beetle"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Beetle"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Spider"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Spider"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Ant"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Ant"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Ant"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Grasshopper"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Grasshopper"));
        BlacksHand.add(new Hex(Hex.Color.BLACK, "Grasshopper"));
    }

    @Override
    public void onClick(View v) {
        HGameState FirstInstance = new HGameState();
        FirstInstance.toString();
    }

    // deep copy constructor
    public HGameState(HGameState orig) {
        this.activePlayer = orig.activePlayer;
        this.isBeePlaced = new boolean[2];
        this.isBeePlaced[0] = orig.isBeePlaced[0];
        this.isBeePlaced[1] = orig.isBeePlaced[1];
        this.turnNumber = orig.turnNumber;

        WhitesHand = new ArrayList<>();
        for (Hex hex : orig.WhitesHand) {
            WhitesHand.add(new Hex(hex));
        }
        BlacksHand = new ArrayList<>();
        for (Hex hex : orig.BlacksHand) {
            BlacksHand.add(new Hex(hex));
        }

        Board = new ArrayList<ArrayList<HexSpace>>();
        for (int i = 0; i < orig.Board.size(); i++) {
            ArrayList<HexSpace> newRow = new ArrayList<HexSpace>();
            ArrayList<HexSpace> oldRow = orig.Board.get(i);

            for (int j = 0; j < oldRow.size(); j++) {
                HexSpace oldSpace = oldRow.get(j);
                HexSpace newSpace = new HexSpace(oldSpace);
                newRow.add(newSpace);
            }
            Board.add(newRow);
        }
    }

    public boolean isValidPlacement(int X, int Y, String name, int playerId) {
        // board must exist and coordinates must be in range
        if (Board == null || !isInBounds(X, Y)) {
            return false;
        }

        // must be this player's turn
        if (playerId != activePlayer) {
            return false;
        }

        // cannot place a piece that is no longer in this player's hand
        if (!playerHasPieceInHand(name, playerId)) {
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

        // if turn 4 and player still has NOT placed their queen
        // then only allow "QueenBee" to be placed
        if (turnNumber > 6 && !isBeePlaced[playerId] && !"QueenBee".equals(name)) {
            return false;
        }

        // first turn: always allowed (coords are forced in placePiece)
        if (turnNumber == 1) {
            return true;
        }

        // turn 2: must be next to the very first piece at (FIRST_COL, FIRST_ROW)
        if (turnNumber == 2) {
            return isAdjacentToFirstPiece(X, Y);
        }

        // ----- Normal placement rule from turn 3+ -----

        HexSpace[] neighbors = getNeighbors(X, Y);

        // determine my color and opponent color
        Hex.Color myColor = (activePlayer == 0) ? Hex.Color.WHITE : Hex.Color.BLACK;
        Hex.Color oppColor = (activePlayer == 0) ? Hex.Color.BLACK : Hex.Color.WHITE;

        // must touch at least ONE of your own pieces
        if (!anyNeighborHasColor(neighbors, myColor)) {
            return false;
        }

        // and must NOT touch ANY opponent pieces
        if (anyNeighborHasColor(neighbors, oppColor)) {
            return false;
        }

        return true;
    }


    // place piece at (X, Y) if isValidPlacement
    // advance turn is in here
    public boolean placePiece(int X, int Y, String name, int playerId) {
        // X = row, Y = col

        // first turn, force to (row=16, col=8) so board shows "8,16"
        if (turnNumber == 1) {
            X = FIRST_ROW;
            Y = FIRST_COL;
        }

        if (!isValidPlacement(X, Y, name, playerId)) {
            return false;
        }

        HexSpace selected = getHexSpace(X, Y);
        if (selected == null) {
            return false;
        }

        Hex.Color color = (activePlayer == 0) ? Hex.Color.WHITE : Hex.Color.BLACK;

        selected.setHex(new Hex(color, name));
        Logger.log("place piece", "placed " + (color == Hex.Color.WHITE ? "white" : "black") + " piece '" + name + "' at (" + X + ", " + Y + ")");

        if ("QueenBee".equals(name)) {
            isBeePlaced[activePlayer] = true;
        }

        removeFromHand(name, activePlayer);

        turnNumber++;
        activePlayer = 1 - activePlayer;

        return true;
    }


    public boolean isValidMove(int Xloc, int Yloc, int Xdest, int Ydest, int playerId) {

        // board must exist
        if (Board == null) {
            return false;
        }

        // both old and new destination must be on the board
        if (!isInBounds(Xloc, Yloc) || !isInBounds(Xdest, Ydest)) {
            return false;
        }

        // must be this player's turn
        if (playerId != activePlayer) {
            return false;
        }
        // players bee must be placed
        if (!isBeePlaced[playerId]){
            return false;
        }

        HexSpace from = getHexSpace(Xloc, Yloc);
        HexSpace to = getHexSpace(Xdest, Ydest);

        // safety: both spaces must exist
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

        //finds piece being moved
        Hex movingPiece = from.getHex();
        String pieceName = movingPiece.getName();

        // destination must be empty ( EXCEPT FOR BEETLE )
        if (to.getHex() != null) { //!"Beetle".equals(pieceName)
            return false;
        }

        // check if the remaining hive is still one connected group if we lift this piece
        if (!hiveStaysConnected(Xloc, Yloc)) {
            return false;
        }

        // simple hive rule... destination must touch at least one piece (any color)
        HexSpace[] destNeighbors = getNeighbors(Xdest, Ydest);
        if (!anyNeighborHasPiece(destNeighbors)) {
            return false;
        }


        boolean movementOK = false;

        if ("QueenBee".equals(pieceName)) {
            movementOK = canQueenMove(Xloc, Yloc, Xdest, Ydest);
        } else if ("Ant".equals(pieceName)) {
            movementOK = canAntMove(Xloc, Yloc, Xdest, Ydest);
        } else if ("Spider".equals(pieceName)) {
            movementOK = canSpiderMove(Xloc, Yloc, Xdest, Ydest);
        } else if ("Beetle".equals(pieceName)) {
            movementOK = canBeetleMove(Xloc, Yloc, Xdest, Ydest);
        } else if ("Grasshopper".equals(pieceName)) {
            movementOK = canGrasshopperMove(Xloc, Yloc, Xdest, Ydest);
        } else {
            movementOK = false; // unknown piece
        }

        if (!movementOK) {
            return false;
        }

        // make sure the hive is still one group AFTER the move
        // use copy of the state so we don't mess up the real board
        HGameState sim = new HGameState(this);  // deep copy current state

        HexSpace simFrom = sim.getHexSpace(Xloc, Yloc);
        HexSpace simTo   = sim.getHexSpace(Xdest, Ydest);

        // safety
        if (simFrom == null || simFrom.getHex() == null || simTo == null) {
            return false;
        }

        // simulate the move on the copy
        simTo.setHex(simFrom.getHex());
        simFrom.setHex(null);

        // (-1, -1), don't virtually remove anything, simply check for "ONE" hive
        if (!sim.hiveStaysConnected(-1, -1)) {
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
        HexSpace to = getHexSpace(Xdest, Ydest);

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
        return "HiveState{" + "turn=" + turnNumber + ", activePlayer=" + activePlayer + ", beePlaced=" + java.util.Arrays.toString(isBeePlaced) + ", WhitesHand=" + WhitesHand + ", BlacksHand=" + BlacksHand + "}";
    }

    public ArrayList<Hex> getBlackHand() {
        return BlacksHand;
    }

    public ArrayList<Hex> getWhiteHand() {
        return WhitesHand;
    }

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

    private void removeFromHand(String name, int playerId) {
        ArrayList<Hex> hand;

        // choose the correct hand
        if (playerId == 0) {
            hand = WhitesHand;
        } else {
            hand = BlacksHand;
        }

        boolean removedOne = false; // keep track if we already removed the piece

        // loop through whole hand
        for (int i = 0; i < hand.size(); i++) {

            // if we already removed a piece, don't remove more
            if (removedOne) {
                continue;
            }

            Hex h = hand.get(i);

            // make sure not null
            if (h != null) {

                // check name matches
                if (h.getName().equals(name)) {
                    hand.remove(i);
                    removedOne = true; // mark that we removed one
                }
            }
        }
    }

    // true if this player still has at least one copy of "name" in their hand
    private boolean playerHasPieceInHand(String name, int playerId) {

        ArrayList<Hex> hand;
        if (playerId == 0) {
            hand = WhitesHand;
        } else {
            hand = BlacksHand;
        }


        for (Hex h : hand) {
            if (h != null && name.equals(h.getName())) {
                return true;
            }
        }
        return false;
    }


    // ===== helpers for neighbor checks =====

    // Returns true ONLY if (row=X, col=Y) is one of the 6 hexes
    // that visually touch the first piece at (FIRST_ROW, FIRST_COL)
    private boolean isAdjacentToFirstPiece(int X, int Y) {
        // Center of the first piece in board coordinates (row, col)
        int centerRow = FIRST_ROW;
        int centerCol = FIRST_COL;

        // same tile as the center, false
        if (X == centerRow && Y == centerCol) {
            return false;
        }

    /*
        Because of how our hex grid is drawn, the 6 touching tiles around
        (centerRow, centerCol) are at these offsets:

            (centerRow - 2, centerCol)     // straight up
            (centerRow - 1, centerCol)     // upper-right
            (centerRow - 1, centerCol - 1) // upper-left
            (centerRow + 2, centerCol)     // straight down
            (centerRow + 1, centerCol)     // lower-right
            (centerRow + 1, centerCol - 1) // lower-left

        FIRST_ROW / FIRST_COL change,  offsets should still work relative to that center
    */

        // straight up
        if (X == centerRow - 2 && Y == centerCol) return true;

        // upper right
        if (X == centerRow - 1 && Y == centerCol) return true;

        // upper left
        if (X == centerRow - 1 && Y == centerCol - 1) return true;

        // straight down
        if (X == centerRow + 2 && Y == centerCol) return true;

        // lower right
        if (X == centerRow + 1 && Y == centerCol) return true;

        // lower left
        if (X == centerRow + 1 && Y == centerCol - 1) return true;

        // anything else is not adjacent to the first piece
        return false;
    }


    // returns true if ANY neighbor has a piece of the given color
    private boolean anyNeighborHasColor(HexSpace[] neighbors, Hex.Color color) {
        for (HexSpace n : neighbors) {
            if (hasColor(n, color)) {
                return true; // found one, done
            }
        }
        return false;
    }

    // returns true if ANY neighbor has a piece (any color)
    private boolean anyNeighborHasPiece(HexSpace[] neighbors) {
        for (HexSpace n : neighbors) {
            if (hasAPiece(n)) {
                return true;
            }
        }
        return false;
    }

    // returns true only if ALL neighbors have a piece
    private boolean allNeighborsHavePiece(HexSpace[] neighbors) {
        for (HexSpace n : neighbors) {
            if (!hasAPiece(n)) {
                return false;
            }
        }
        return true;
    }

    // return the 6 neighboring hexes around (row, col)
    //  HERE!!!! x=row, y=col in Board.get(x).get(y)
    private HexSpace[] getNeighbors(int x, int y) {
        int row = x;
        int col = y;
        boolean isEvenRow = (row % 2 == 0);

        if (isEvenRow) {
            return new HexSpace[]{getHexSpace(row - 2, col),       // up
                    getHexSpace(row - 1, col),       // up-right
                    getHexSpace(row - 1, col - 1),   // up-left
                    getHexSpace(row + 2, col),       // down
                    getHexSpace(row + 1, col),       // down-right
                    getHexSpace(row + 1, col - 1)    // down-left
            };
        } else {
            return new HexSpace[]{getHexSpace(row - 2, col),       // up
                    getHexSpace(row - 1, col + 1),   // up-right
                    getHexSpace(row - 1, col),       // up-left
                    getHexSpace(row + 2, col),       // down
                    getHexSpace(row + 1, col + 1),   // down-right
                    getHexSpace(row + 1, col)        // down-left
            };
        }
    }


    // true if (Xdest, Ydest) is one of the 6 neighbors of (Xloc, Yloc)
    // !!!! x = row index, y = col !!!!
    private boolean isNeighbor(int Xloc, int Yloc, int Xdest, int Ydest) {

        // same tile is never a neighbor
        if (Xloc == Xdest && Yloc == Ydest) return false;

        int row = Xloc;
        int col = Yloc;
        boolean isEvenRow = (row % 2 == 0);

        int dr = Xdest - Xloc;
        int dc = Ydest - Yloc;

        if (dr == -2 && dc == 0) return true; // up
        if (dr == +2 && dc == 0) return true; // down

        if (isEvenRow) {
            if (dr == -1 && dc == 0) return true; // up-right
            if (dr == -1 && dc == -1) return true; // up-left
            if (dr == +1 && dc == 0) return true; // down-right
            if (dr == +1 && dc == -1) return true; // down-left
        } else {
            if (dr == -1 && dc == +1) return true; // up-right
            if (dr == -1 && dc == 0) return true; // up-left
            if (dr == +1 && dc == +1) return true; // down-right
            if (dr == +1 && dc == 0) return true; // down-left
        }

        return false;
    }
    // ===== movement helpers =====

    private boolean canQueenMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
    }

    private boolean canAntMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return true;
    }

    private boolean canSpiderMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        if (Xloc == Xdest && Yloc == Ydest) {
            return false;
        }

        // dont allow spider move to adjacent hex
        if (isNeighbor(Xloc, Yloc, Xdest, Ydest)) {
            return false;
        }

        int rows = Board.size();
        int cols = Board.get(0).size();

        // visited[r][c]... already "passed" this hex space
        boolean[][] visited = new boolean[rows][cols];

        // mark starting hex as visited
        // dont allow to place at visted locations
        visited[Xloc][Yloc] = true;

        // try to find a path of 3 hexes through neighbors
        return spiderDFS(
                Xloc, Yloc,   // startX, startY
                Xloc, Yloc,   // curX, curY
                Xdest, Ydest,
                0,
                visited
        );

    }

    private boolean canBeetleMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
    }

    private boolean canGrasshopperMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        HexSpace[] N1 = getNeighbors(Xloc,Yloc);
        boolean dirXUp=false, dirYUp=false;
        for(int i=0;i<6;i++) {
            switch (i) {
                case 0:
                    dirXUp = true;
                    break;
                case 1:
                    dirXUp = true; //-1
                    dirYUp = false; // +1
                    break;
                case 2:
                    dirXUp = true;
                    dirYUp = true;
                    break;
                case 3:
                    dirXUp = false;
                    break;
                case 4:
                    dirXUp = false;
                    dirYUp = false;
                    break;
                case 5:
                    dirXUp = false;
                    dirYUp = true;
            }
            if (i == 0 || i == 3){
                if(N1[i].getColor()!= Hex.Color.NONE){
                    HexSpace up = N1[i];
                    int xStep = (dirXUp?-2:2);
                    int UpX = Xloc + xStep;
                    int UpY = Yloc;
                    while(up.getColor()!= Hex.Color.NONE){
                        UpX = UpX + xStep;
                        up = getHexSpace(UpX,UpY);

                    }
                    if(UpX==Xdest && UpY == Ydest) {
                        Logger.log("canGrasshopperMove","valid move at "+ UpX+","+ UpY);
                        return true;
                    }
                }
            }
            else{
                if(N1[i].getColor()!= Hex.Color.NONE){
                    HexSpace upRight = N1[i];
                    int xStep = dirXUp ? -1 : 1;
                    int yStep = dirYUp ? -1 : 1;
                    int UpRightX = Xloc + xStep;
                    int UpRightY = Yloc;
                    if ((Xloc % 2 == 0&&dirYUp)||(Xloc % 2 != 0&&!dirYUp)){
                        UpRightY = UpRightY + yStep;
                    }
                    while(upRight.getColor()!= Hex.Color.NONE){
                        if ((UpRightX % 2 == 0&&dirYUp)|| (UpRightX % 2 != 0&&!dirYUp)){
                            UpRightY = UpRightY + yStep;
                        }
                        UpRightX = UpRightX + xStep;
                        upRight = getHexSpace(UpRightX,UpRightY);
                    }
                    if(UpRightY==Ydest&&UpRightX==Xdest){
                        Logger.log("canGrasshopperMove","valid move at "+ UpRightX+","+ UpRightY);
                        return true;
                    }
                }

            }
        }
/*
        if(N1[0].getColor()!= Hex.Color.NONE){
            HexSpace up = N1[0];
            int xStep = (dirXUp?-2:2);
            int UpX = Xloc + xStep;
            int UpY = Yloc;
            while(up.getColor()!= Hex.Color.NONE){
                UpX = UpX + xStep;
                up = getHexSpace(UpX,UpY);

            }
            if(UpX==Xdest && UpY == Ydest) {
                Logger.log("canGrasshopperMove","valid move at "+ UpX+","+ UpY);
                return true;
            }
        }
        /*
        if(N1[1].getColor()!= Hex.Color.NONE){
            HexSpace upRight = N1[1];
            int UpRightX = Xloc -1;
            int UpRightY = Yloc;
            if (Xloc % 2 != 0){
                UpRightY = UpRightY +1;
            }
            while(upRight.getColor()!= Hex.Color.NONE){
                if (UpRightX % 2 != 0){
                    UpRightY = UpRightY +1;
                }
                UpRightX = UpRightX -1;
                upRight = getHexSpace(UpRightX,UpRightY);
            }
            if(UpRightY==Ydest&&UpRightX==Xdest) return true;
        }
        if(N1[2].getColor()!= Hex.Color.NONE){
            HexSpace upLeft = N1[2];
            int UpLeftX = Xloc -1;
            int UpLeftY = Yloc;
            if (Xloc % 2 ==0){
                UpLeftY = UpLeftY -1;
            }
            while(upLeft.getColor()!= Hex.Color.NONE){
                if(UpLeftX % 2 == 0){
                    UpLeftY = UpLeftY -1;
                }
                UpLeftX = UpLeftX -1;
                upLeft = getHexSpace(UpLeftX,UpLeftY);
            }
            if(UpLeftY==Ydest&&UpLeftX==Xdest) return true;
        }
        if(N1[3].getColor()!= Hex.Color.NONE){
            HexSpace down = N1[3];
            int DownX = Xloc +2;
            int DownY = Yloc;

        }
        //contiue this pattern for all 6
        */
        return false;
    }

    // find the queen on the board
    // return coord {x, y} or null if not found
    private int[] findQueen(Hex.Color color) {
        if (Board == null) return null;

        for (int x = 0; x < Board.size(); x++) {
            ArrayList<HexSpace> row = Board.get(x);

            for (int y = 0; y < row.size(); y++) {
                HexSpace space = row.get(y);

                if (space != null) {
                    Hex piece = space.getHex();

                    if (piece != null) {
                        boolean isQueen = "QueenBee".equals(piece.getName());
                        boolean sameColor = (piece.getColor() == color);

                        if (isQueen && sameColor) {
                            return new int[]{x, y};
                        }
                    }
                }
            }
        }

        return null;
    }

    // check if queen is completely surrounded
    // return true if all 6 surrounding hexes have pieces
    private boolean isQueenSurrounded(Hex.Color color) {
        // find where this color's queen is
        int[] qPos = findQueen(color);
        if (qPos == null) {
            // queen not on the board yet, so it can't be surrounded
            return false;
        }

        int x = qPos[0];
        int y = qPos[1];

        // get all 6 neighbors using the shared helper
        HexSpace[] neighbors = getNeighbors(x, y);

        // queen is "surrounded" only if all six neighbors have a piece
        return allNeighborsHavePiece(neighbors);
    }

    // Check that the hive stays in one connected group
    // if we remove the piece at (removeX, removeY)...
    // DON'T change the board here we just act hex is empty
    private boolean hiveStaysConnected(int removeX, int removeY) {
        if (Board == null) return true;

        // first, find all hexes that still have pieces on them
        ArrayList<int[]> occupied = new ArrayList<>();

        for (int row = 0; row < Board.size(); row++) {
            ArrayList<HexSpace> boardRow = Board.get(row);
            for (int col = 0; col < boardRow.size(); col++) {

                // act like this hex is empty
                if (row == removeX && col == removeY) {
                    continue;
                }

                HexSpace space = boardRow.get(col);
                if (space != null && space.getHex() != null) {
                    occupied.add(new int[]{row, col});
                }
            }
        }

        // if there are 0 or 1 pieces left on the board,
        // the hive can't be "split"
        if (occupied.size() <= 1) {
            return true;
        }

        // run BFS from one of the remaining pieces
        // visited[r][c] == true means we already counted that hex
        boolean[][] visited = new boolean[Board.size()][Board.get(0).size()];

        // start BFS from the first piece in our list
        int[] start = occupied.get(0);
        java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;

        int reached = 0; // pieces we can reach by walking along neighbors

        while (!queue.isEmpty()) {
            int[] cur = queue.remove();
            int r = cur[0];
            int c = cur[1];
            reached++;

            // figure out which 6 neighbors to use based on row connection
            // same pattern as getNeighbors / isNeighbor layout
            boolean isEvenRow = (r % 2 == 0);

            int[][] adjPositionsEvenRow = {
                    {-2, 0},  // up
                    {-1, 0},  // up-right
                    {-1, -1}, // up-left
                    { 2, 0},  // down
                    { 1, 0},  // down-right
                    { 1, -1}  // down-left
            };

            int[][] adjPositionsOddRow = {
                    {-2, 0},  // up
                    {-1, 1},  // up-right
                    {-1, 0},  // up-left
                    { 2, 0},  // down
                    { 1, 1},  // down-right
                    { 1, 0}   // down-left
            };

            int[][] adjPositions = isEvenRow ? adjPositionsEvenRow : adjPositionsOddRow;

            for (int[] adj : adjPositions) {
                int neighborRow = r + adj[0];
                int neighborCol = c + adj[1];

                // skip the "removed" hex we're moving
                if (neighborRow == removeX && neighborCol == removeY) {
                    continue;
                }

                // must be on the board
                if (!isInBounds(neighborRow, neighborCol)) {
                    continue;
                }

                // already visited this hex
                if (visited[neighborRow][neighborCol]) {
                    continue;
                }

                HexSpace neighbor = getHexSpace(neighborRow, neighborCol);

                // only move through hexes that have pieces
                if (neighbor == null || neighbor.getHex() == null) {
                    continue;
                }

                visited[neighborRow][neighborCol] = true;
                queue.add(new int[]{neighborRow, neighborCol});
            }
        }

        // if we can touch all pieces, the hive is still one
        // if not, move is not allowed.
        return (reached == occupied.size());
    }


    // DFS to see if the spider can reach (destX,destY)
    // 3 neighbor steps and only moving through empty hexes
    private boolean spiderDFS(int startX, int startY, int curX, int curY, int destX, int destY, int depth, boolean[][] visited) {

        // after 3 steps thats the destination
        if (depth == 3) {

            if (curX != destX || curY != destY) {
                return false;
            }

            // distance filtering conditions

            // 1 hex from start (adjacent) ... place NOT allowed
            if (isNeighbor(startX, startY, destX, destY)) {
                return false;
            }

            // 2 hex from start ... place NOT allowed
            for (int mx = 0; mx < Board.size(); mx++) {
                for (int my = 0; my < Board.get(mx).size(); my++) {

                    if (!isInBounds(mx, my)) continue;

                    // must be neighbor of start
                    if (!isNeighbor(startX, startY, mx, my)) continue;

                    // and neighbor of dest
                    if (!isNeighbor(mx, my, destX, destY)) continue;

                    return false;
                }
            }
            return true;
        }

        // continue to other hexes
        for (int r = 0; r < Board.size(); r++) {
            for (int c = 0; c < Board.get(r).size(); c++) {

                if (visited[r][c]) continue;
                if (!isNeighbor(curX, curY, r, c)) continue;
                if (!isInBounds(r, c)) continue;

                HexSpace space = getHexSpace(r, c);
                if (space == null) continue;

                // spider moves THROUGH empty spaces only
                if (space.getHex() != null) continue;

                // must "touch" hive
                HexSpace[] neigh = getNeighbors(r, c);
                if (!anyNeighborHasPiece(neigh)) continue;

                // "explore" placement
                visited[r][c] = true;
                if (spiderDFS(startX, startY, r, c, destX, destY, depth + 1, visited)) {
                    return true;
                }
                visited[r][c] = false;
            }
        }

        return false;
    }





    // return -1, no winner yet; 0, white wins; 1, black wins
    public int checkWinner() {
        boolean whiteSurrounded = isQueenSurrounded(Hex.Color.WHITE);
        boolean blackSurrounded = isQueenSurrounded(Hex.Color.BLACK);

        if (!whiteSurrounded && !blackSurrounded) {
            return -1; // game still going
        } else if (whiteSurrounded && !blackSurrounded) {
            return 1;  // white queen is dead, black wins
        } else if (!whiteSurrounded && blackSurrounded) {
            return 0;  // black queen is dead, white wins
        } else {
            return -1;
        }
    }
}
