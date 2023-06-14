package warczynski.jedrzej;
public class Bishop extends Piece {
    
    public Bishop(int type_code, int start_x, int start_y)
    {
        super(type_code, start_x, start_y);
    }
    
    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y)
    {
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
        }       
    }

    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        int opp_color = getOpponentVariable();
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
                        if((b.getOccupyingPieceType(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i) & 1) == opp_color)
                        {
                            if(isKingSafe(b, king, actual_position.x, actual_position.y,
                               actual_position.x + hor_direction*i, actual_position.y + ver_direction*i, king_x, king_y))
                                legal_captures.add(new Point(actual_position.x + hor_direction*i, actual_position.y + ver_direction*i));
                        }
                        break;
                    } 
                }
            }
        }       
    }

}
