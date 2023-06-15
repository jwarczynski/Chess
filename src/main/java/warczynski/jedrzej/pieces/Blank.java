package warczynski.jedrzej.pieces;

import warczynski.jedrzej.game.Board;
import warczynski.jedrzej.game.Point;

public class Blank extends Piece {

    public Blank(int pieceCode, int startX, int startY) {
        super(pieceCode, startX, startY);
    }

    @Override
    public void move(Board b, King king, Point actual_position, int king_x, int king_y) {
        throw new IllegalStateException("Blank piece cannot move");
    }

    @Override
    public void capture(Board b, King king, Point actual_position, int king_x, int king_y) {
        throw new IllegalStateException("Blank piece cannot capture");
    }

}
