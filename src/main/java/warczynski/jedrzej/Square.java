package warczynski.jedrzej;

public class Square {

    private Piece occupyingPiece;

    public Square(int piece_code, int x_coordinate, int y_coordinate) {
        int tmp = piece_code & 1;
        if (tmp == 1) tmp = piece_code - 1;
        else tmp = piece_code;
        // System.out.println(tmp);
        switch (tmp) {
            case Constants.BLANK -> occupyingPiece = new Blank(piece_code, x_coordinate, y_coordinate);
            case Constants.PAWN -> occupyingPiece = new Pawn(piece_code, x_coordinate, y_coordinate);
            case Constants.BISHOP -> occupyingPiece = new Bishop(piece_code, x_coordinate, y_coordinate);
            case Constants.KNIGHT -> occupyingPiece = new Knight(piece_code, x_coordinate, y_coordinate);
            case Constants.ROOK -> occupyingPiece = new Rook(piece_code, x_coordinate, y_coordinate);
            case Constants.KING -> occupyingPiece = new King(piece_code, x_coordinate, y_coordinate);
            case Constants.QUEEN -> occupyingPiece = new Queen(piece_code, x_coordinate, y_coordinate);
        }
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public void setOccupyingPiece(Piece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }
}
