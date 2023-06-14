package warczynski.jedrzej;
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
    // white pieces have even codes - exatcly the same as above
    // black pieces have codes one greater than white
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    // size of displaying squrae on the chessboard
    public static final int SQUARE_SIZE = 64;
}
