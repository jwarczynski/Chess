package warczynski.jedrzej;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Board{
    
    private final Square[][] board;
    public Board()
    {
        board = new Square[8][8];
        for(int i =2;i<7;i++)
        {
            for(int j = 0;j<8;j++)
            {
                board[j][i] = new Square(0, j, i);
            }
        }
        for(int i = 0;i<8;i++)
        {
            board[i][1] = new Square(2, i, 1);
            board[i][6] = new Square(3, i, 6);
        }
        board[0][0] = new Square(16, 0, 0);
        board[7][0] = new Square(16, 7, 0);
        board[0][7] = new Square(17, 0, 7);
        board[7][7] = new Square(17, 7, 7);

        board[1][0] = new Square(8, 1, 0);
        board[6][0] = new Square(8, 6, 0);
        board[1][7] = new Square(9, 1, 7);
        board[6][7] = new Square(9, 6, 7);

        board[2][0] = new Square(4, 2, 0);
        board[5][0] = new Square(4, 5, 0);
        board[2][7] = new Square(5, 2, 7);
        board[5][7] = new Square(5, 5, 7);

        board[3][0] = new Square(32, 3, 0);
        board[4][0] = new Square(64, 4, 0);
        board[3][7] = new Square(33, 3, 7);
        board[4][7] = new Square(65, 4, 7);
    }

    public Board(String file_path) throws FileNotFoundException
    {
        board = new Square[8][8];
        try (Scanner scanner = new Scanner(new FileReader(file_path))) {
            while (scanner.hasNext()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        String token = scanner.next();
                        int type = Integer.parseInt(token);
                        board[j][i] = new Square(type, j, i);
                    }
                }
            }
        }
    }

    public int getOccupyingPieceType(int x, int y)
    {
        if(x<0) return -1;
        if(y<0) return -1;
        if(x>7) return -1;
        if(y>7) return -1;

        return board[x][y].getOccupyingPiece().getType();
    }

    public Square[][] getBoard()
    {
        return board;
    }

    public Piece getOccupyingPiece(int x,int y)
    {
        return board[x][y].getOccupyingPiece();
    }

    public void executeMove(int x1, int y1, int x2, int y2, int new_figure, int color)
    {
        if(new_figure != 0)
        {
            switch (new_figure) {
                case 4 -> board[x2][y2].setOccupyingPiece(new Bishop(new_figure + color, x2, y2));
                case 8 -> board[x2][y2].setOccupyingPiece(new Knight(new_figure + color, x2, y2));
                case 16 -> board[x2][y2].setOccupyingPiece(new Rook(new_figure + color, x2, y2));
                case 64 -> board[x2][y2].setOccupyingPiece(new Queen(new_figure + color, x2, y2));
            }
        }
        board[x2][y2].setOccupyingPiece(getOccupyingPiece(x1, y1));
        board[x1][y1].setOccupyingPiece(new Blank(0, x1, y1));
    }

    public Square getSquare(int x, int y)
    {
        return board[x][y];
    }
}
