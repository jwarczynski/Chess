package warczynski.jedrzej.pieces;

import warczynski.jedrzej.game.Board;
import warczynski.jedrzej.game.Point;

public class Pawn extends Piece {
    
    public Pawn(int type, int start_x, int start_y)
    {
        super(type, start_x, start_y);
    }

    private int getDirection()
    {
        if( (type & 1) == 1 ) return -1;
        return 1;
    }
    
    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        int direction = getDirection();
        
        if(b.getOccupyingPieceType(actual_position.x, actual_position.y + direction) == 0)
        {
            if(isKingSafe(b, king, actual_position.x, actual_position.y, actual_position.x, actual_position.y + direction, king_x, king_y))
                legal_moves.add(new Point(actual_position.x, actual_position.y + direction));
            if(b.getOccupyingPieceType(actual_position.x, actual_position.y + 2*direction) == 0 &&
                starting_coordinates.equals(actual_position))
            {
                if(isKingSafe(b, king, actual_position.x, actual_position.y,actual_position.x, actual_position.y + 2*direction, king_x, king_y))
                    legal_moves.add(new Point(actual_position.x, actual_position.y + 2*direction));
            }
        }
    }
    
    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        int direction = getDirection();

        if(b.getOccupyingPieceType(actual_position.x + 1, actual_position.y + direction) != 0 &&
            b.getOccupyingPieceType(actual_position.x + 1, actual_position.y + direction) != -1)
        {
            if(isKingSafe(b, king, actual_position.x, actual_position.y,
            actual_position.x + 1, actual_position.y + direction, king_x, king_y))
                legal_captures.add(new Point(actual_position.x+1, actual_position.y + direction));
        }
        if(b.getOccupyingPieceType(actual_position.x - 1, actual_position.y + direction) != 0 &&
            b.getOccupyingPieceType(actual_position.x - 1, actual_position.y + direction) != -1)
        {
            if(isKingSafe(b, king, actual_position.x, actual_position.y,
            actual_position.x - 1, actual_position.y + direction, king_x, king_y))
                legal_captures.add(new Point(actual_position.x-1, actual_position.y + direction));
        }
    }

}
