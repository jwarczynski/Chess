package warczynski.jedrzej.pieces;

import warczynski.jedrzej.game.Board;
import warczynski.jedrzej.game.Point;

public class King extends Piece {
    
    public King(int type_code, int start_x, int start_y)
    {
        super(type_code, start_x, start_y);
    }

    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        for(int ver_direction = -1;ver_direction<2;ver_direction++)
        {
            for(int hor_direction = -1;hor_direction<2;hor_direction++)
            {
                if(ver_direction == 0 && hor_direction ==0) continue;
                if(!isOnBoard(actual_position.x + hor_direction, actual_position.y + ver_direction))
                    continue;
                if(b.getOccupyingPieceType(actual_position.x + hor_direction, actual_position.y + ver_direction) != 0)
                    continue;
                if(isKingSafe(b, king, actual_position.x, actual_position.y, actual_position.x + hor_direction, actual_position.y + ver_direction,
                    actual_position.x + hor_direction, actual_position.y+ver_direction))
                        legal_moves.add(new Point(actual_position.x + hor_direction, actual_position.y + ver_direction));
            }
        }       
    }

    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        int opp_var = getOpponentVariable();
        for(int ver_direction = -1;ver_direction<2;ver_direction++)
        {
            for(int hor_direction = -1;hor_direction<2;hor_direction++)
            {
                if(ver_direction == 0 && hor_direction ==0) continue;
                if(!isOnBoard(actual_position.x + hor_direction, actual_position.y + ver_direction))
                    continue;
                if(b.getOccupyingPieceType(actual_position.x + hor_direction, actual_position.y + ver_direction) != 0 &&
                (b.getOccupyingPieceType(actual_position.x + hor_direction, actual_position.y + ver_direction) & 1) == opp_var)
                {
                    if(!amIAttacked(b, new Point(actual_position.x + hor_direction, actual_position.y + ver_direction)))
                        legal_captures.add(new Point(actual_position.x + hor_direction, actual_position.y + ver_direction));
                }
            }
        }       
    }

    public boolean isAttackingKnight(Board b, Point actual_position)
    {
        int opp_var = getOpponentVariable(); //checking opponent color(white or black)
        for(int x = -1;x<2;x+=2)
        {
            for(int y = -2; y<4;y+=4)
            {
                if(isOnBoard(actual_position.x + x, actual_position.y + y))
                {
                    if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) == 8+opp_var)
                        return true;
                }
            }
        }
        for(int x = -2;x<3;x+=4)
        {
            for(int y = -1; y<2;y+=2)
            {
                if(isOnBoard(actual_position.x + x, actual_position.y + y))
                {
                    if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) == 8+opp_var)
                        return true;
                }
            }
        }
        return false;
    }

    public boolean isAttackingPawn(Board b, Point actual_position)
    {
        int opp_var = getOpponentVariable();
        int opp_direction;
        // opp_direction = opp_var == 0 ? 1:-1; nie mam pewnosci czy to jest ok
        if(opp_var == 0) opp_direction = 1;
        else opp_direction = -1;
        for(int x = -1;x<2;x+=2)
        {
            if(isOnBoard(actual_position.x + x, actual_position.y - opp_direction))
            {
                if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y - opp_direction) == 2+opp_var)
                    return true;
            }
        }
        return false;
    }

    public boolean isAttackingBishopOrQueen(Board b, Point actual_position)
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
                        if(b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i) == 4+opp_var ||
                            b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i) == 64+opp_var)
                            return true;
                        break;
                    }
                }
            }
        }       
        return false;
    }

    public boolean isAttackingRookOrQueen(Board b, Point actual_position)
    {
        int opp_var = getOpponentVariable();
        for(int ver_direction = -1;ver_direction<2;ver_direction+=2)
        {
            for(int i = 1;i<8;i++)
            {
                if(!isOnBoard(actual_position.x, actual_position.y + ver_direction*i))
                    break;
                if(b.getOccupyingPieceType(actual_position.x, actual_position.y + ver_direction*i) != 0)
                {
                    if(b.getOccupyingPieceType(actual_position.x, actual_position.y + ver_direction*i) == 16+opp_var ||
                        b.getOccupyingPieceType(actual_position.x, actual_position.y + ver_direction*i) == 64+opp_var)
                        return true;
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
                    if(b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y) == 16+opp_var||
                        b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y) == 64+opp_var)
                        return true;
                    break;
                }
            }            
        }
        return false;
    }

    public boolean isAttackingKing(Board b, Point actual_position)
    {
        int opp_var = getOpponentVariable();
        for(int ver_direction = -1;ver_direction<2;ver_direction++)
        {
            for(int hor_direction = -1;hor_direction<2;hor_direction++)
            {
                if(ver_direction == 0 && hor_direction ==0) continue;
                if(!isOnBoard(actual_position.x + hor_direction, actual_position.y + ver_direction))
                    continue;
                if(b.getOccupyingPieceType(actual_position.x + hor_direction, actual_position.y + ver_direction) == 32+opp_var)
                    return true;
            }
        }       
        return false;
    }

    public boolean amIAttacked(Board b, Point actual_position)
    {
        if(isAttackingBishopOrQueen(b, actual_position)) return true;
        if(isAttackingKing(b, actual_position)) return true;
        if(isAttackingRookOrQueen(b, actual_position)) return true;
        if(isAttackingPawn(b, actual_position)) return true;
        return isAttackingKnight(b, actual_position);
    }

    public void setPosition(int x, int y)
    {
        starting_coordinates.x = x;
        starting_coordinates.y = y;
    }
}
