package warczynski.jedrzej;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.Objects;

public class Chess extends JPanel {

    JLabel label;
    Board board;
    Player[] players;
    BufferedImage buf_img;
    Image[] img;
    Piece selectedPiece = null;
    private int TURN = 0;
    private int prev_x = 0;
    private int prev_y = 0;
    private int x_piece = -1;
    private int y_piece = -1;
    private boolean choosing = false;
    boolean end = false;

    public Chess() throws IOException {
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
        board = new Board();
        players = new Player[2];
        players[0] = new Player(Constants.WHITE, 3, 0);
        players[1] = new Player(Constants.BLACK, 3, 7);
        img = new Image[12];
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/wpawn.png")));
        img[0] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/bpawn.png")));
        img[1] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/wknight.png")));
        img[2] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/bknight.png")));
        img[3] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/wbishop.png")));
        img[4] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/bbishop.png")));
        img[5] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/wrook.png")));
        img[6] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/brook.png")));
        img[7] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/wqueen.png")));
        img[8] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/bqueen.png")));
        img[9] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/wking.png")));
        img[10] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        buf_img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/bking.png")));
        img[11] = buf_img.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);

        Border border = BorderFactory.createLineBorder(Color.white, 5);
        this.setBorder(border);
        repaint();
        players[0].computePossibleCaptures(board);
        players[0].computePossibleMoves(board);
        label = new JLabel();
        label.setText("KONIEC GRY");
        label.setForeground(new Color(0x3f3e3e));
        label.setFont(new Font("MV Boli", Font.PLAIN, 50));
        this.setLayout(null);
        label.setBounds(100, 60, 400, 400);
        this.add(label);
        label.setVisible(false);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (end) {
            label.setVisible(true);
        }

        boolean white = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (white) g.setColor(new Color(235, 235, 208));
                else g.setColor(new Color(119, 148, 85));

                g.fillRect(x * Constants.SQUARE_SIZE, y * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
                white = !white;
            }
            white = !white;
        }
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = board.getSquare(x, y).getOccupyingPiece();

                switch (board.getSquare(x, y).getOccupyingPiece().getType()) {
                    case 2 -> g.drawImage(img[0], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 3 -> g.drawImage(img[1], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 4 -> g.drawImage(img[4], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 5 -> g.drawImage(img[5], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 8 -> g.drawImage(img[2], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 9 -> g.drawImage(img[3], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 16 -> g.drawImage(img[6], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 17 -> g.drawImage(img[7], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 32 -> g.drawImage(img[10], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 33 -> g.drawImage(img[11], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 64 -> g.drawImage(img[8], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                    case 65 -> g.drawImage(img[9], p.getX(), /* 7*Constants.SQUARE_SIZE - */ p.getY(), this);
                }
            }
        }
        if (x_piece > -1) {
            if (y_piece == 0) {
                g.setColor(Color.gray);
                g.fillRect(x_piece * Constants.SQUARE_SIZE, 0, Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE);
                g.drawImage(img[9], x_piece * Constants.SQUARE_SIZE, 0, this);
                g.drawImage(img[7], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, this);
                g.drawImage(img[5], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 2, this);
                g.drawImage(img[3], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 3, this);
            } else {
                g.setColor(Color.lightGray);
                g.fillRect(x_piece * Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE);
                g.drawImage(img[2], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 4, this);
                g.drawImage(img[4], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 5, this);
                g.drawImage(img[6], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 6, this);
                g.drawImage(img[8], x_piece * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 7, this);
            }
        }
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (choosing) {
                int x = e.getX() / Constants.SQUARE_SIZE;
                int y = e.getY() / Constants.SQUARE_SIZE;
                if (x == x_piece) {
                    choosing = false;
                    if (y_piece == 0) {
                        switch (y) {
                            case 0 -> board.getSquare(x_piece, 0).setOccupyingPiece(new Queen(65, x_piece, 0));
                            case 1 -> board.getSquare(x_piece, 0).setOccupyingPiece(new Queen(17, x_piece, 0));
                            case 2 -> board.getSquare(x_piece, 0).setOccupyingPiece(new Queen(5, x_piece, 0));
                            case 3 -> board.getSquare(x_piece, 0).setOccupyingPiece(new Queen(9, x_piece, 0));
                            default -> choosing = true;
                        }
                    } else {
                        switch (y) {
                            case 7 -> board.getSquare(x_piece, 7).setOccupyingPiece(new Queen(64, x_piece, 7));
                            case 6 -> board.getSquare(x_piece, 7).setOccupyingPiece(new Queen(16, x_piece, 7));
                            case 5 -> board.getSquare(x_piece, 7).setOccupyingPiece(new Queen(4, x_piece, 7));
                            case 4 -> board.getSquare(x_piece, 7).setOccupyingPiece(new Queen(8, x_piece, 7));
                            default -> choosing = true;
                        }

                    }
                }
                if (!choosing) {
                    repaint();
                    x_piece = -1;
                    y_piece = -1;
                    players[TURN].clearMovesAndCaptures();
                    if (TURN == 0) TURN++;
                    else TURN--;
                    players[TURN].computePossibleCaptures(board);
                    players[TURN].computePossibleMoves(board);
                    if (players[TURN].amILoose()) {
                        end = true;
                    }
                }
            } else {
                int x = e.getX() / Constants.SQUARE_SIZE;
                int y = e.getY() / Constants.SQUARE_SIZE;
                prev_x = x;
                prev_y = y;
                if (players[TURN].findSourceSquare(x, y)) {
                    selectedPiece = board.getOccupyingPiece(x, y);
                }
            }
        }

        private boolean morph(int y) {
            if (selectedPiece.getType() == 2 && y == 7) return true;
            return selectedPiece.getType() == 3 && y == 0;
        }

        public void mouseReleased(MouseEvent e) {
            if (selectedPiece != null) {
                int x = (selectedPiece.getX() + 32) / Constants.SQUARE_SIZE;
                int y = (selectedPiece.getY() + 32) / Constants.SQUARE_SIZE;
                if (players[TURN].findDestSquare(prev_x, prev_y, x, y)) {
                    selectedPiece.setXY(x * Constants.SQUARE_SIZE, y * Constants.SQUARE_SIZE);
                    board.executeMove(prev_x, prev_y, x, y, 0, 0);

                    if (morph(y)) {
                        x_piece = x;
                        y_piece = y;
                        choosing = true;
                        selectedPiece = null;
                        repaint();
                    } else {
                        if (selectedPiece.getType() == 32 || selectedPiece.getType() == 33) {
                            players[TURN].setKingPosition(x, y);
                        }

                        selectedPiece = null;
                        players[TURN].clearMovesAndCaptures();
                        if (TURN == 0) TURN++;
                        else TURN--;
                        players[TURN].computePossibleCaptures(board);
                        players[TURN].computePossibleMoves(board);
                        if (players[TURN].amILoose()) {
                            end = true;
                        }
                    }
                } else {
                    selectedPiece.setXY(prev_x * Constants.SQUARE_SIZE, prev_y * Constants.SQUARE_SIZE);
                    selectedPiece = null;
                }
                repaint();
            }
        }
    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (selectedPiece != null) {
                selectedPiece.setXY(e.getX() - 32, e.getY() - 32);
                repaint();
            }
        }

    }
}

