package warczynski.jedrzej.pieces;

import warczynski.jedrzej.game.Board;
import warczynski.jedrzej.game.Point;

public class Queen extends Piece{
    
    public Queen(int type_code, int start_x, int start_y)
    {
        super(type_code, start_x, start_y);
    }

    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        for(int ver_direction = -1;ver_direction<2;ver_direction+=2)
        {
            for(int i = 1;i<8;i++)
            {
                if(!isOnBoard(actual_position.x, actual_position.y + ver_direction*i))
                    break;
                if(b.getOccupyingPieceType(actual_position.x, actual_position.y + ver_direction*i) != 0)
                    break;
                if(isKingSafe(b, king, actual_position.x, actual_position.y,
                actual_position.x, actual_position.y + ver_direction*i, king_x, king_y))
                    legal_moves.add(new Point(actual_position.x, actual_position.y + ver_direction*i));
            }
        }
        for(int hor_direction = -1;hor_direction<2;hor_direction+=2)
        {
            for(int i = 1;i<8;i++)
            {
                if(!isOnBoard(actual_position.x + hor_direction*i, actual_position.y))
                    break;
                if(b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y) != 0)
                    break;
                if(isKingSafe(b, king, actual_position.x, actual_position.y,
                actual_position.x + hor_direction*i, actual_position.y, king_x, king_y))
                    legal_moves.add(new Point(actual_position.x + hor_direction*i, actual_position.y));
            }            
        }//copied from rook
        for(int ver_direction = -1;ver_direction<2;ver_direction+=2)
        {
            for(int hor_direction = -1;hor_direction<2;hor_direction+=2)
            {
                for(int i = 1;i<8;i++)
                {
                    if(!isOnBoard(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i))
                        break;
                    if(b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i) != 0)
                        break;
                    if(isKingSafe(b, king, actual_position.x, actual_position.y,
                            actual_position.x + hor_direction*i, actual_position.y + ver_direction*i, king_x, king_y))
                        legal_moves.add(new Point(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i));
                }
            }
        }//copied from buishop
    }

    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        int opp_var = getOpponentVariable();
        for(int ver_direction = -1;ver_direction<2;ver_direction+=2)
        {
            for(int hor_direction = -1;hor_direction<2;hor_direction+=2)
            {
                for(int i = 1;i<8;i++)
                {
                    if(!isOnBoard(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i))
                        break;
                    if(b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i) != 0)
                    {
                        if((b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i) & 1) == opp_var)
                        {
                            if(isKingSafe(b, king, actual_position.x, actual_position.y,actual_position.x + hor_direction*i, actual_position.y + ver_direction*i, king_x, king_y))
                                legal_captures.add(new Point(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i));
                        }
                        break;
                    }
                }
            }
        }       
        for(int ver_direction = -1;ver_direction<2;ver_direction+=2)
        {
            for(int i = 1;i<8;i++)
            {
                if(!isOnBoard(actual_position.x, actual_position.y + ver_direction*i))
                    break;
                if(b.getOccupyingPieceType(actual_position.x, actual_position.y + ver_direction*i) != 0 )
                {
                    if((b.getOccupyingPieceType(actual_position.x, actual_position.y + ver_direction*i) & 1) == opp_var)
                    {
                        if(isKingSafe(b, king, actual_position.x, actual_position.y, actual_position.x, actual_position.y + ver_direction*i, king_x, king_y))    
                            legal_captures.add(new Point(actual_position.x, actual_position.y + ver_direction*i));
                    }
                    break;
                }
            }
        }
        for(int hor_direction = -1;hor_direction<2;hor_direction+=2)
        {
            for(int i = 1;i<8;i++)
            {
                if(!isOnBoard(actual_position.x + hor_direction*i, actual_position.y))
                    break;
                if(b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y) != 0)
                {
                    if((b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y) & 1) == opp_var)
                    {
                        if(isKingSafe(b, king, actual_position.x, actual_position.y, actual_position.x + hor_direction*i, actual_position.y, king_x, king_y))    
                            legal_captures.add(new Point(actual_position.x + hor_direction*i, actual_position.y));
                    }
                    break;
                }
            }            
        }
    }

}
