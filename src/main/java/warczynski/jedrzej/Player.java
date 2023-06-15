package warczynski.jedrzej;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Player {

    private final int color;
    private final HashMap<Point, Vector<Point>> possible_moves = new HashMap<>();
    private final HashMap<Point, Vector<Point>> possible_captures = new HashMap<>();
    private int x_coor_of_king;
    private int y_coor_of_king;
    private final King king;

    public Player(int color, int x_coor_of_king, int y_coor_of_king) {
        this.x_coor_of_king = x_coor_of_king;
        this.y_coor_of_king = y_coor_of_king;
        this.color = color;
        king = new King(32 + color, x_coor_of_king, y_coor_of_king);
    }

    public void setKingPosition(int x, int y) {
        king.setPosition(x, y);
        x_coor_of_king = x;
        y_coor_of_king = y;
    }

    public void computePossibleMoves(Board b) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (b.getOccupyingPieceType(x, y) != 0 && (b.getOccupyingPieceType(x, y) & 1) == color) {
                    b.getOccupyingPiece(x, y).clearPreviousMoves();
                    b.getOccupyingPiece(x, y).move(b, king, new Point(x, y), x_coor_of_king, y_coor_of_king);
                    Vector<Point> legal_moves_from_here = b.getOccupyingPiece(x, y).getLegalMoves();
                    if (!legal_moves_from_here.isEmpty())
                        possible_moves.put(new Point(x, y), legal_moves_from_here);
                }
            }
        }
    }

    public void computePossibleCaptures(Board b) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (b.getOccupyingPieceType(x, y) != 0 && (b.getOccupyingPieceType(x, y) & 1) == color) {
                    b.getOccupyingPiece(x, y).clearPreviousCaptures();
                    b.getOccupyingPiece(x, y).capture(b, king, new Point(x, y), x_coor_of_king, y_coor_of_king);
                    Vector<Point> legal_captures_from_here = b.getOccupyingPiece(x, y).getLegalCaptures();
                    if (!legal_captures_from_here.isEmpty())
                        possible_captures.put(new Point(x, y), b.getOccupyingPiece(x, y).getLegalCaptures());
                }
            }
        }
    }

    public boolean anyMovesFromSquareExist(int x, int y) {
        return possible_moves.keySet().stream().anyMatch(p -> p.x == x && p.y == y) || possible_captures.keySet().stream().anyMatch(p -> p.x == x && p.y == y);
    }

    private boolean findDestination(HashMap<Point, Vector<Point>> mapa, int src_x, int src_y, int x, int y) {
        return findDestinationSquare(mapa, src_x, src_y, x, y);
    }

    private boolean findDestinationSquare(HashMap<Point, Vector<Point>> mapa, int src_x, int src_y, int x, int y) {
        for (Map.Entry<Point, Vector<Point>> entry : mapa.entrySet()) {
            Point src_p = entry.getKey();
            if (src_p.x == src_x && src_p.y == src_y) {
                for (Point p : entry.getValue()) {
                    if (p.x == x && p.y == y) return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveFromCurrentToTarget(int src_x, int src_y, int x, int y) {
        if (findDestinationSquare(possible_captures, src_x, src_y, x, y)) return true;
        return findDestination(possible_moves, src_x, src_y, x, y);
    }

    public boolean amILoose() {
        if (!possible_moves.isEmpty()) return false;
        return possible_captures.isEmpty();
    }

    public void clearMovesAndCaptures() {
        possible_captures.clear();
        possible_moves.clear();
    }

}