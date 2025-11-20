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
        if (turnNumber > 4 && !isBeePlaced[playerId] && !"QueenBee".equals(name)) {
            return false;
        }

        // first turn: center placement always
        if (turnNumber == 1) {
            return true;
        }

        // SPECIAL CASE: turn 2
        // only requirement: must be next to the very first piece at (7,7)
        if (turnNumber == 2) {
            boolean nextToFirst = isAdjacentToFirstPiece(X, Y);
            if (!nextToFirst) {
                return false;
            }
            // we already checked in-bounds and that the hex is empty above
            return true;
        }

        // from here on, use normal neighbor rules
        HexSpace[] neighbors = getNeighbors(X, Y);

        // Player color determination
        Hex.Color myColor;
        Hex.Color oppColor;

        if (activePlayer == 0) {
            myColor = Hex.Color.WHITE;
            oppColor = Hex.Color.BLACK;
        } else {
            myColor = Hex.Color.BLACK;
            oppColor = Hex.Color.WHITE;
        }

        // later turns: must be next to at least one of own pieces
        return anyNeighborHasColor(neighbors, myColor);
    }

    // place piece at (X, Y) if isValidPlacement
    // advance turn is in here
    public boolean placePiece(int X, int Y, String name, int playerId) {
        // first turn, force the piece to be placed at (7,7)
        if (turnNumber == 1) {
            X = 7;
            Y = 7;
        }

        // check for valid placement
        if (!isValidPlacement(X, Y, name, playerId)) {
            return false; // illegal placement, do nothing
        }

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
        Logger.log(
                "place piece",
                "placed " + (color == Hex.Color.WHITE ? "white" : "black")
                        + " piece '" + name + "' at (" + X + ", " + Y + ")"
        );

        // if queen just placed for this player... remember queen placed
        if ("QueenBee".equals(name)) {
            isBeePlaced[activePlayer] = true;
        }

        // remove this piece from the player's hand
        removeFromHand(name, activePlayer);

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
        HexSpace to = getHexSpace(Xdest, Ydest);

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

        // neighbors around the DESTINATION hex
        HexSpace[] neighbors = getNeighbors(Xdest, Ydest);

        // destination must touch at least one piece (any color)
        // keeps the hive connected
        if (!anyNeighborHasPiece(neighbors)) {
            return false;
        }

        // piece-specific movement
        Hex movingPiece = from.getHex();
        String pieceName = movingPiece.getName();

        boolean movementOK = false;

        // QueenBee
        if (pieceName.equals("QueenBee")) {
            movementOK = canQueenMove(Xloc, Yloc, Xdest, Ydest);
        }

        // Ant
        else if (pieceName.equals("Ant")) {
            movementOK = canAntMove(Xloc, Yloc, Xdest, Ydest);
        }

        // Spider
        else if (pieceName.equals("Spider")) {
            movementOK = canSpiderMove(Xloc, Yloc, Xdest, Ydest);
        }

        // Beetle
        else if (pieceName.equals("Beetle")) {
            movementOK = canBeetleMove(Xloc, Yloc, Xdest, Ydest);
        }

        // Grasshopper
        else if (pieceName.equals("Grasshopper")) {
            movementOK = canGrasshopperMove(Xloc, Yloc, Xdest, Ydest);
        }

        // Unknown piece type
        else {
            movementOK = false;
        }

        if (!movementOK) {
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
        return "HiveState{"
                + "turn=" + turnNumber
                + ", activePlayer=" + activePlayer
                + ", beePlaced=" + java.util.Arrays.toString(isBeePlaced)
                + ", WhitesHand=" + WhitesHand
                + ", BlacksHand=" + BlacksHand
                + "}";
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

    // ===== helpers for neighbor checks =====

    // true if (X, Y) is directly next to the very first piece at (7,7)
    private boolean isAdjacentToFirstPiece(int X, int Y) {
        int dx = X - 7;
        int dy = Y - 7;

        // can't be on top of the first piece itself
        if (dx == 0 && dy == 0) {
            return false;
        }

        // neighbor if both dx and dy are in [-1, 1]
        if (dx >= -1 && dx <= 1 && dy >= -1 && dy <= 1) {
            return true;
        }

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

    // return the 6 neighboring hexes around (x, y)
    private HexSpace[] getNeighbors(int x, int y) {
        return new HexSpace[]{
                getHexSpace(x, y - 1),   // left
                getHexSpace(x, y + 1),   // right
                getHexSpace(x - 1, y),   // up
                getHexSpace(x + 1, y),   // down
                getHexSpace(x - 1, y + 1), // up-right
                getHexSpace(x + 1, y - 1)  // down-left
        };
    }

    // true if (Xdest, Ydest) is one of the 6 neighbors of (Xloc, Yloc)
    private boolean isNeighbor(int Xloc, int Yloc, int Xdest, int Ydest) {
        int dx = Xdest - Xloc;
        int dy = Ydest - Yloc;

        boolean neighbor = false;

        // left / right
        if (dx == 0 && (dy == -1 || dy == 1)) {
            neighbor = true;
        }

        // up / down
        if ((dx == -1 || dx == 1) && dy == 0) {
            neighbor = true;
        }

        // diagonals (up-right, down-left)
        if (dx == -1 && dy == 1) {
            neighbor = true;
        }
        if (dx == 1 && dy == -1) {
            neighbor = true;
        }

        return neighbor;
    }

    // ===== movement helpers (currently 1-step like Queen) =====

    private boolean canQueenMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
    }

    private boolean canAntMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
    }

    private boolean canSpiderMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
    }

    private boolean canBeetleMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
    }

    private boolean canGrasshopperMove(int Xloc, int Yloc, int Xdest, int Ydest) {
        return isNeighbor(Xloc, Yloc, Xdest, Ydest);
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
