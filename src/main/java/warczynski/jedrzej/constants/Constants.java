package warczynski.jedrzej.constants;
public class Constants {
    
    // codes of pieces 
    public static final int BLANK = 0;
    public static final int PAWN = 2;
    public static final int BISHOP = 4;
    public static final int KNIGHT = 8;
    public static final int ROOK = 16;
    public static final int KING = 32;
    public static final int QUEEN = 64;

    // use to determine color of a piece by & operation
    // white pieces have even codes - exactly the same as above
    // black pieces have codes one greater than white
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    // size of displaying square on the chessboard
    public static final int SQUARE_SIZE = 64;

    public static final int KING_START_X = 3;
    public static final int WHITE_KING_START_Y = 0;
    public static final int BLACK_KING_START_Y = 7;

    public static final int WHITE_TURN = 0;
    public static final int BLACK_TURN = 1;
}
