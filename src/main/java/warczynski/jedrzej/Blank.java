package warczynski.jedrzej;
public class Blank extends Piece {
    
    public Blank(int type_code, int start_x, int start_y)
    {
        super(type_code, start_x, start_y);
    }

    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y)
    {
    }

    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y)
    {
    }

}
