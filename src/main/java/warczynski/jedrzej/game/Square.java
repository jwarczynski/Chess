package warczynski.jedrzej.game;

import warczynski.jedrzej.pieces.*;

import static warczynski.jedrzej.constants.Constants.BLACK;
import static warczynski.jedrzej.constants.PieceCodes.*;

public class Square {

    private Piece occupyingPiece;

    public Square(int piece_code, int x_coordinate, int y_coordinate) {
        int whitePieceCodeEquivalent;
        int pieceColor = piece_code & 1;
        if (pieceColor == BLACK) {
            whitePieceCodeEquivalent = piece_code - 1;
        } else {
            whitePieceCodeEquivalent = piece_code;
        }
        switch (whitePieceCodeEquivalent) {
            case BLANK -> occupyingPiece = new Blank(piece_code, x_coordinate, y_coordinate);
            case PAWN -> occupyingPiece = new Pawn(piece_code, x_coordinate, y_coordinate);
            case WHITE_BISHOP -> occupyingPiece = new Bishop(piece_code, x_coordinate, y_coordinate);
            case WHITE_KNIGHT -> occupyingPiece = new Knight(piece_code, x_coordinate, y_coordinate);
            case WHITE_ROOK -> occupyingPiece = new Rook(piece_code, x_coordinate, y_coordinate);
            case WHITE_KING -> occupyingPiece = new King(piece_code, x_coordinate, y_coordinate);
            case WHITE_QUEEN -> occupyingPiece = new Queen(piece_code, x_coordinate, y_coordinate);
        }
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public void setOccupyingPiece(Piece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }
}
