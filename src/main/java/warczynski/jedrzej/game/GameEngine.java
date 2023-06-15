package warczynski.jedrzej.game;

import warczynski.jedrzej.constants.Constants;
import warczynski.jedrzej.constants.PieceCodes;
import warczynski.jedrzej.pieces.Piece;
import warczynski.jedrzej.pieces.Queen;


import static warczynski.jedrzej.constants.Constants.*;

public class GameEngine {
    private Piece selectedPiece = null;
    private int TURN = 0;
    private int currentX = 0;
    private int currentY = 0;
    private int morphingPawnX = -1;
    private int morphingPawnY = -1;
    private boolean isChoosingMorphedPiece = false;
    boolean isEnd = false;

    private Board board;
    private Player[] players;

    public GameEngine() {
        initGame();
    }

    private void initGame() {
        initGameObjects();
        computeInitialMoves();
    }

    private void initGameObjects() {
        board = new Board();

        players = new Player[2];
        players[0] = new Player(Constants.WHITE, KING_START_X, WHITE_KING_START_Y);
        players[1] = new Player(Constants.BLACK, KING_START_X, BLACK_KING_START_Y);
    }

    public void handleMouseClick(int mouseX, int mouseY) {
        if (isChoosingMorphedPiece) {
            selectNewQueenIfValidMouseClick(mouseX, mouseY);
            nextTurnIfMorphPieceSelected();
        } else {
            selectPieceIfCanMove(mouseX, mouseY);
        }
    }

    public void handleMouseRelease() {
        if (selectedPiece != null) {
            int targetX = (selectedPiece.getX() + SQUARE_SIZE / 2) / SQUARE_SIZE;
            int targetY = (selectedPiece.getY() + SQUARE_SIZE / 2) / SQUARE_SIZE;
            if (players[TURN].canMoveFromCurrentToTarget(currentX, currentY, targetX, targetY)) {
                executeMove(targetX, targetY);
            } else {
                uncheckPieceSelection();
            }
        }
    }

    public void handleMouseDrag(int x, int y) {
        if (selectedPiece != null) {
            selectedPiece.setXY(x, y);
        }
    }


    public boolean isEnd() {
        return isEnd;
    }

    public int getMorphingPawnX() {
        return morphingPawnX;
    }

    public Piece getPiece(int x, int y) {
        return board.getOccupyingPiece(x, y);
    }

    private void computeInitialMoves() {
        players[0].computePossibleMoves(board);
    }

    public boolean blackPawnReachBoardEnd() {
        return morphingPawnY == 0;
    }


    private boolean pawnReachedMorphSquare(int y) {
        if (selectedPiece.getType() == 2 && y == 7) return true;
        return selectedPiece.getType() == 3 && y == 0;
    }

    private void executeMove(int targetX, int targetY) {
        selectedPiece.setXY(targetX * SQUARE_SIZE, targetY * SQUARE_SIZE);
        board.executeMove(currentX, currentY, targetX, targetY, 0, 0);

        if (pawnReachedMorphSquare(targetY)) {
            morphingPawnX = targetX;
            morphingPawnY = targetY;
            isChoosingMorphedPiece = true;
            selectedPiece = null;
        } else {
            updateKingPositionIfNeeded(targetX, targetY);
            prepareNextTurn();
        }
    }

    private void selectPieceIfCanMove(int mouseX, int mouseY) {
        currentX = mouseX;
        currentY = mouseY;
        if (players[TURN].anyMovesFromSquareExist(mouseX, mouseY)) {
            selectedPiece = board.getOccupyingPiece(mouseX, mouseY);
        }
    }

    private void nextTurnIfMorphPieceSelected() {
        if (!isChoosingMorphedPiece) {
            morphingPawnX = -1;
            morphingPawnY = -1;
            prepareNextTurn();
        }
    }

    private void selectNewQueenIfValidMouseClick(int mouseX, int mouseY) {
        if (mouseX == morphingPawnX) { // clicked on the square with new piece menu
            if (morphingPawnY == 0) {
                chooseBlackMorphedPiece(mouseY);
            } else {
                chooseWhiteMorphedPiece(mouseY);
            }
        }
    }

    private void chooseWhiteMorphedPiece(int y) {
        switch (y) {
            case 7 -> placeNewWhiteQueen(PieceCodes.WHITE_QUEEN);
            case 6 -> placeNewWhiteQueen(PieceCodes.WHITE_ROOK);
            case 5 -> placeNewWhiteQueen(PieceCodes.WHITE_BISHOP);
            case 4 -> placeNewWhiteQueen(PieceCodes.WHITE_KNIGHT);
            default -> isChoosingMorphedPiece = true;
        }
    }

    private void chooseBlackMorphedPiece(int y) {
        switch (y) {
            case 0 -> placeNewBlackQueen(PieceCodes.BLACK_QUEEN);
            case 1 -> placeNewBlackQueen(PieceCodes.BLACK_ROOK);
            case 2 -> placeNewBlackQueen(PieceCodes.BLACK_BISHOP);
            case 3 -> placeNewBlackQueen(PieceCodes.BLACK_KNIGHT);
            default -> isChoosingMorphedPiece = true;
        }
    }

    private void placeNewBlackQueen(int pieceCode) {
        board.getSquare(morphingPawnX, 0).setOccupyingPiece(new Queen(pieceCode, morphingPawnX, 0));
        isChoosingMorphedPiece = false;
    }

    private void placeNewWhiteQueen(int pieceCode) {
        board.getSquare(morphingPawnX, 7).setOccupyingPiece(new Queen(pieceCode, morphingPawnX, 7));
        isChoosingMorphedPiece = false;
    }

    private void prepareNextTurn() {
        changeTurn();
        computeMovesForCurrentPlayer();
        markIsEnd();
    }

    private void markIsEnd() {
        if (players[TURN].amILoose()) {
            isEnd = true;
        }
    }

    private void computeMovesForCurrentPlayer() {
        players[TURN].computePossibleCaptures(board);
        players[TURN].computePossibleMoves(board);
    }

    private void changeTurn() {
        players[TURN].clearMovesAndCaptures();
        selectedPiece = null;

        if (TURN == WHITE_TURN) {
            TURN = BLACK_TURN;
        }
        else {
            TURN = WHITE_TURN;
        }
    }

    private void updateKingPositionIfNeeded(int targetX, int targetY) {
        if (selectedPiece.getType() == PieceCodes.WHITE_KING || selectedPiece.getType() == PieceCodes.BLACK_KING) {
            players[TURN].setKingPosition(targetX, targetY);
        }
    }

    private void uncheckPieceSelection() {
        selectedPiece.setXY(currentX * Constants.SQUARE_SIZE, currentY * Constants.SQUARE_SIZE);
        selectedPiece = null;
    }

}
