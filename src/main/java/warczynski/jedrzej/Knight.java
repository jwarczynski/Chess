package warczynski.jedrzej;
public class Knight extends Piece {

    public Knight(int type_code, int start_x, int start_y)
    {
        super(type_code, start_x, start_y);
    }

    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        for(int x = -1;x<2;x+=2)
        {
            for(int y = -2; y<4;y+=4)
            {
                if(isOnBoard(actual_position.x + x, actual_position.y + y))
                {
                    if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) == 0)
                    {
                        if(isKingSafe(b, king, actual_position.x, actual_position.y,
                        actual_position.x + x, actual_position.y + y, king_x, king_y))
                            legal_moves.add(new Point(actual_position.x + x, actual_position.y + y));
                    }
                }
            }
        }
        for(int x = -2;x<3;x+=4)
        {
            for(int y = -1; y<2;y+=2)
            {
                if(isOnBoard(actual_position.x + x, actual_position.y + y))
                {
                    if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) == 0)
                    {
                        if(isKingSafe(b, king, actual_position.x, actual_position.y,
                        actual_position.x + x, actual_position.y + y, king_x, king_y))
                            legal_moves.add(new Point(actual_position.x + x, actual_position.y + y));
                    }
                }
            }
        }
    }

    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y)
    {
        int opp_color = getOpponentVariable();
        for(int x = -1;x<2;x+=2)
        {
            for(int y = -2; y<4;y+=4)
            {
                if(isOnBoard(actual_position.x + x, actual_position.y + y))
                {
                    if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) != 0 &&
                    (b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) & 1) == opp_color)
                    {
                        if(isKingSafe(b, king, actual_position.x, actual_position.y,
                        actual_position.x + x, actual_position.y + y, king_x, king_y))
                            legal_captures.add(new Point(actual_position.x + x, actual_position.y + y));
                    }
                }
            }
        }
        for(int x = -2;x<3;x+=4)
        {
            for(int y = -1; y<2;y+=2)
            {
                if(isOnBoard(actual_position.x + x, actual_position.y + y))
                {
                    if(b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) != 0 &&
                    (b.getOccupyingPieceType(actual_position.x + x, actual_position.y + y) & 1) == opp_color)
                    {
                        if(isKingSafe(b, king, actual_position.x, actual_position.y,
                        actual_position.x + x, actual_position.y + y, king_x, king_y))
                            legal_captures.add(new Point(actual_position.x + x, actual_position.y + y));
                    }
                }
            }
        }
    }

}
