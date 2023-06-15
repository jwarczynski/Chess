package warczynski.jedrzej.ui;

import warczynski.jedrzej.game.GameEngine;
import warczynski.jedrzej.constants.Constants;
import warczynski.jedrzej.pieces.Piece;

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

import static warczynski.jedrzej.constants.Constants.*;
import static warczynski.jedrzej.constants.PiecesImagesPaths.*;

public class BoardPanel extends JPanel {

    private static final Color GREEN_COLOR = new Color(119, 148, 85);
    private static final Color WHITE_COLOR = new Color(235, 235, 208);
    private static final Color FOREGROUND_COLOR = new Color(0x3f3e3e);
    private static final String END_OF_GAME_TEXT = "END OF GAME";
    private static final String FONT = "MV Boli";
    private static final int FONT_SIZE = 50;

    private JLabel endOfGameLabel;
    private Image[] img;

    private final GameEngine gameEngine;

    public BoardPanel() throws IOException {
        initUI();
        repaint();
        gameEngine = new GameEngine();
    }

    private void initUI() throws IOException {
        readPieceIcons();
        initBorder();
        initEndOfGameLabel();
        initUIListeners();
    }

    private void readPieceIcons() throws IOException {
        img = new Image[12];
        BufferedImage pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_PAWN)));

        img[0] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_PAWN)));

        img[1] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_KNIGHT)));

        img[2] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_KNIGHT)));

        img[3] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_BISHOP)));
        img[4] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_BISHOP)));
        img[5] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_ROOK)));
        img[6] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_ROOK)));
        img[7] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_QUEEN)));
        img[8] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_QUEEN)));
        img[9] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_KING)));
        img[10] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcon = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_KING)));
        img[11] = pieceIcon.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    private void initBorder() {
        Border border = BorderFactory.createLineBorder(Color.white, 5);
        this.setBorder(border);
    }

    private void initEndOfGameLabel() {
        endOfGameLabel = new JLabel();
        endOfGameLabel.setText(END_OF_GAME_TEXT);
        endOfGameLabel.setForeground(FOREGROUND_COLOR);
        endOfGameLabel.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
        endOfGameLabel.setBounds(100, 60, 400, 400);
        endOfGameLabel.setVisible(false);
        this.setLayout(null);
        this.add(endOfGameLabel);
    }

    private void initUIListeners() {
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        showEndGameLabelIfEnd();
        paintBoard(g);
        drawPieces(g);
        drawPieceChooseMenuIfPawnReachBoardEnd(g);
    }

    private void drawPieceChooseMenuIfPawnReachBoardEnd(Graphics g) {
        if (gameEngine.blackPawnReachBoardEnd()) {
            drawBlackPieceToChoose(g);
        } else {
            drawWhitePieceToChoose(g);
        }
    }

    private void showEndGameLabelIfEnd() {
        if (gameEngine.isEnd()) {
            endOfGameLabel.setVisible(true);
        }
    }

    private void drawWhitePieceToChoose(Graphics g) {
        int morphingPawnX = gameEngine.getMorphingPawnX();

        g.setColor(Color.lightGray);
        g.fillRect(morphingPawnX * Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE);
        g.drawImage(img[2], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 4, this);
        g.drawImage(img[4], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 5, this);
        g.drawImage(img[6], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 6, this);
        g.drawImage(img[8], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 7, this);
    }

    private void drawBlackPieceToChoose(Graphics g) {
        int morphingPawnX = gameEngine.getMorphingPawnX();

        g.setColor(Color.gray);
        g.fillRect(morphingPawnX * Constants.SQUARE_SIZE, 0, Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE);
        g.drawImage(img[9], morphingPawnX * Constants.SQUARE_SIZE, 0, this);
        g.drawImage(img[7], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, this);
        g.drawImage(img[5], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 2, this);
        g.drawImage(img[3], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 3, this);
    }


    private void drawPieces(Graphics g) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = gameEngine.getPiece(x, y);
                drawPiece(g, piece);
            }
        }
    }

    private void drawPiece(Graphics g, Piece piece) {
        switch (piece.getType()) {
            case 2 -> g.drawImage(img[0], piece.getX(), piece.getY(), this);
            case 3 -> g.drawImage(img[1], piece.getX(), piece.getY(), this);
            case 4 -> g.drawImage(img[4], piece.getX(), piece.getY(), this);
            case 5 -> g.drawImage(img[5], piece.getX(), piece.getY(), this);
            case 8 -> g.drawImage(img[2], piece.getX(), piece.getY(), this);
            case 9 -> g.drawImage(img[3], piece.getX(), piece.getY(), this);
            case 16 -> g.drawImage(img[6], piece.getX(), piece.getY(), this);
            case 17 -> g.drawImage(img[7], piece.getX(), piece.getY(), this);
            case 32 -> g.drawImage(img[10], piece.getX(), piece.getY(), this);
            case 33 -> g.drawImage(img[11], piece.getX(), piece.getY(), this);
            case 64 -> g.drawImage(img[8], piece.getX(), piece.getY(), this);
            case 65 -> g.drawImage(img[9], piece.getX(), piece.getY(), this);
        }
    }

    private void paintBoard(Graphics g) {
        boolean isWhite = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                paintSquare(g, isWhite);
                g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
    }

    private static void paintSquare(Graphics g, boolean isWhite) {
        if (isWhite) {
            g.setColor(WHITE_COLOR);
        } else {
            g.setColor(GREEN_COLOR);
        }
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int mouseX = e.getX() / Constants.SQUARE_SIZE;
            int mouseY = e.getY() / Constants.SQUARE_SIZE;
            gameEngine.handleMouseClick(mouseX, mouseY);
        }


        public void mouseReleased(MouseEvent e) {
            gameEngine.handleMouseRelease();
            repaint();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            gameEngine.handleMouseDrag(e.getX() - SQUARE_SIZE / 2, e.getY() - SQUARE_SIZE / 2);
            repaint();
        }
    }
}

