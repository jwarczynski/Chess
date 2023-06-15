package warczynski.jedrzej.pieces;
import warczynski.jedrzej.game.Board;
import warczynski.jedrzej.game.Point;
import warczynski.jedrzej.constants.Constants;

import java.util.Vector;

public abstract class Piece {

    protected final int type;
    protected final Point starting_coordinates;
    protected Vector <Point> legal_moves;
    protected Vector <Point> legal_captures;
    protected int x;
    protected int y;

    public abstract void move(Board b, King king, Point actual_position, int x_coor_of_king, int y_coor_of_king);
    public abstract void capture(Board b, King king, Point actual_position, int x_coor_of_king, int y_coor_of_king);

    public Piece(int type, int start_x, int start_y)
    {
        legal_moves = new Vector<>();
        legal_captures = new Vector<>();
        this.type = type;
        starting_coordinates = new Point(start_x, start_y);
        starting_coordinates.x = start_x;
        starting_coordinates.y= start_y;
        x = start_x* Constants.SQUARE_SIZE;
        y = start_y*Constants.SQUARE_SIZE;
    }

    public int getType() {
        return type;
    }

    protected boolean isOnBoard(int x, int y)
    {
        if(x<0) return false;
        if(y<0) return false;
        if(x>7) return false;
        return y <= 7;
    }
    
    protected int getOpponentVariable()
    {
        return ((type & 1)==1? 0:1);
        // if((type & 1) == 1) return 0;
        // return 1;
    }

    public Vector<Point> getLegalMoves()
    {
        return legal_moves;
    }

    public Vector<Point> getLegalCaptures()
    {
        return legal_captures;
    }

    public void clearPreviousMoves()
    {
        legal_moves.clear();
    }
    public void clearPreviousCaptures()
    {
        legal_captures.clear();
    }

    protected boolean isKingSafe(Board b, King king, int src_x, int src_y, int dest_x, int dest_y, int king_x, int king_y)
    {
        boolean safety = true;
        Piece tmp = b.getOccupyingPiece(dest_x, dest_y);
        b.executeMove(src_x, src_y, dest_x, dest_y, 0, 0);
        if(king.amIAttacked(b, new Point(king_x, king_y))) safety = false;
        b.executeMove(dest_x, dest_y, src_x, src_y, 0, 0);
        b.getSquare(dest_x, dest_y).setOccupyingPiece(tmp);
        return safety;
    }
    
    public int getX(){return x;}
    public int getY(){return y;}
    public void setXY(int x, int y){this.x = x; this.y =y;}
}
