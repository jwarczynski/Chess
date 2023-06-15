package warczynski.jedrzej;

import warczynski.jedrzej.constants.Constants;
import warczynski.jedrzej.constants.PieceCodes;

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

public class Chess extends JPanel {

    private static final String FONT = "MV Boli";
    private static final int FONT_SIZE = 50;
    private static final int FOREGROUND_COLOR = 0x3f3e3e;
    private static final String END_OF_GAME_TEXT = "END OF GAME";
    private static final Color WHITE_COLOR = new Color(235, 235, 208);
    private static final Color BLACK_COLOR = new Color(119, 148, 85);

    JLabel endOfGameLabel;
    Board board;
    Player[] players;
    BufferedImage pieceIcons;
    Image[] img;
    Piece selectedPiece = null;
    private int TURN = 0;
    private int currentX = 0;
    private int currentY = 0;
    private int morphingPawnX = -1;
    private int morphingPawnY = -1;
    private boolean isChoosingMorphedPiece = false;
    boolean isEnd = false;

    public Chess() throws IOException {
        initGame();
        repaint();
        setEndOfGameLabel();
    }

    private void initGame() throws IOException {
        initUI();
        initGameObjects();
        computeInitialMoves();
    }

    private void computeInitialMoves() {
        players[0].computePossibleMoves(board);
    }

    private void initUI() {
        initUIListeners();

        Border border = BorderFactory.createLineBorder(Color.white, 5);
        this.setBorder(border);
    }

    private void initUIListeners() {
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    private void initGameObjects() throws IOException {
        board = new Board();
        initPlayers();
        readPieceIcons();
    }

    private void setEndOfGameLabel() {
        endOfGameLabel = new JLabel();
        endOfGameLabel.setText(END_OF_GAME_TEXT);
        endOfGameLabel.setForeground(new Color(FOREGROUND_COLOR));
        endOfGameLabel.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
        endOfGameLabel.setBounds(100, 60, 400, 400);
        endOfGameLabel.setVisible(false);
        this.setLayout(null);
        this.add(endOfGameLabel);
    }

    private void initPlayers() {
        players = new Player[2];
        players[0] = new Player(Constants.WHITE, KING_START_X, WHITE_KING_START_Y);
        players[1] = new Player(Constants.BLACK, KING_START_X, BLACK_KING_START_Y);
    }

    private void readPieceIcons() throws IOException {
        img = new Image[12];
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_PAWN)));
        img[0] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_PAWN)));
        img[1] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_KNIGHT)));
        img[2] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_KNIGHT)));
        img[3] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_BISHOP)));
        img[4] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_BISHOP)));
        img[5] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_ROOK)));
        img[6] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_ROOK)));
        img[7] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_QUEEN)));
        img[8] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_QUEEN)));
        img[9] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(WHITE_KING)));
        img[10] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
        pieceIcons = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(BLACK_KING)));
        img[11] = pieceIcons.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        showEndGameLabelIfEnd();
        paintBoard(g);
        drawPieces(g);
        drawPieceChooseMenuIfPawnReachBoardEnd(g);
    }

    private void drawPieceChooseMenuIfPawnReachBoardEnd(Graphics g) {
        if (blackPawnReachBoardEnd()) {
            drawBlackPieceToChoose(g);
        } else {
            drawWhitePieceToChoose(g);
        }
    }

    private void showEndGameLabelIfEnd() {
        if (isEnd) {
            endOfGameLabel.setVisible(true);
        }
    }

    private void drawWhitePieceToChoose(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(morphingPawnX * Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE);
        g.drawImage(img[2], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 4, this);
        g.drawImage(img[4], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 5, this);
        g.drawImage(img[6], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 6, this);
        g.drawImage(img[8], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 7, this);
    }

    private void drawBlackPieceToChoose(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(morphingPawnX * Constants.SQUARE_SIZE, 0, Constants.SQUARE_SIZE, 4 * Constants.SQUARE_SIZE);
        g.drawImage(img[9], morphingPawnX * Constants.SQUARE_SIZE, 0, this);
        g.drawImage(img[7], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, this);
        g.drawImage(img[5], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 2, this);
        g.drawImage(img[3], morphingPawnX * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE * 3, this);
    }

    private boolean blackPawnReachBoardEnd() {
        return morphingPawnY == 0;
    }

    private void drawPieces(Graphics g) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getSquare(x, y).getOccupyingPiece();
                drawPiece(g, piece);
            }
        }
    }

    private void drawPiece(Graphics g, Piece piece) {
        switch (piece.getType()) {
            case 2 -> g.drawImage(img[0], piece.getX(),  piece.getY(), this);
            case 3 -> g.drawImage(img[1], piece.getX(),  piece.getY(), this);
            case 4 -> g.drawImage(img[4], piece.getX(),  piece.getY(), this);
            case 5 -> g.drawImage(img[5], piece.getX(),  piece.getY(), this);
            case 8 -> g.drawImage(img[2], piece.getX(),  piece.getY(), this);
            case 9 -> g.drawImage(img[3], piece.getX(),  piece.getY(), this);
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
            g.setColor(BLACK_COLOR);
        }
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int mouseX = e.getX() / Constants.SQUARE_SIZE;
            int mouseY = e.getY() / Constants.SQUARE_SIZE;
            if (isChoosingMorphedPiece) {
                selectNewQueenIfValidMouseClick(mouseX, mouseY);
                nextTurnIfMorphPieceSelected();
            } else {
                selectPieceIfCanMove(mouseX, mouseY);
            }
        }

        private boolean pawnReachedMorphSquare(int y) {
            if (selectedPiece.getType() == 2 && y == 7) return true;
            return selectedPiece.getType() == 3 && y == 0;
        }

        public void mouseReleased(MouseEvent e) {
            if (selectedPiece != null) {
                int targetX = (selectedPiece.getX() + SQUARE_SIZE / 2) / SQUARE_SIZE;
                int targetY = (selectedPiece.getY() + SQUARE_SIZE / 2) / SQUARE_SIZE;
                if (players[TURN].canMoveFromCurrentToTarget(currentX, currentY, targetX, targetY)) {
                    executeMove(targetX, targetY);
                } else {
                    uncheckPieceSelection();
                }
                repaint();
            }
        }

        private void executeMove(int targetX, int targetY) {
            selectedPiece.setXY(targetX * SQUARE_SIZE, targetY * SQUARE_SIZE);
            board.executeMove(currentX, currentY, targetX, targetY, 0, 0);

            if (pawnReachedMorphSquare(targetY)) {
                morphingPawnX = targetX;
                morphingPawnY = targetY;
                isChoosingMorphedPiece = true;
                selectedPiece = null;
                repaint();
            } else {
                updateKingPositionIfNeeded(targetX, targetY);
                prepareNextTurn();
            }
        }
    }

    private void selectPieceIfCanMove(int mouseX, int mouseY) {
        currentX = mouseX;
        currentY = mouseY;
        if (players[TURN].anyMovesFromSquareExist(mouseX, mouseY)) {
            selectedPiece = board.getOccupyingPiece(mouseX, mouseY);
        }
    }

    private void nextTurnIfMorphPieceSelected() {
        if (!isChoosingMorphedPiece) {
            repaint();
            morphingPawnX = -1;
            morphingPawnY = -1;
            prepareNextTurn();
        }
    }

    private void selectNewQueenIfValidMouseClick(int mouseX, int mouseY) {
        if (mouseX == morphingPawnX) { // clicked on the square with new piece menu
            if (morphingPawnY == 0) {
                chooseBlackMorphedPiece(mouseY);
            } else {
                chooseWhiteMorphedPiece(mouseY);
            }
        }
    }

    private void chooseWhiteMorphedPiece(int y) {
        switch (y) {
            case 7 -> placeNewWhiteQueen(PieceCodes.WHITE_QUEEN);
            case 6 -> placeNewWhiteQueen(PieceCodes.WHITE_ROOK);
            case 5 -> placeNewWhiteQueen(PieceCodes.WHITE_BISHOP);
            case 4 -> placeNewWhiteQueen(PieceCodes.WHITE_KNIGHT);
            default -> isChoosingMorphedPiece = true;
        }
    }

    private void chooseBlackMorphedPiece(int y) {
        switch (y) {
            case 0 -> placeNewBlackQueen(PieceCodes.BLACK_QUEEN);
            case 1 -> placeNewBlackQueen(PieceCodes.BLACK_ROOK);
            case 2 -> placeNewBlackQueen(PieceCodes.BLACK_BISHOP);
            case 3 -> placeNewBlackQueen(PieceCodes.BLACK_KNIGHT);
            default -> isChoosingMorphedPiece = true;
        }
    }

    private void placeNewBlackQueen(int pieceCode) {
        board.getSquare(morphingPawnX, 0).setOccupyingPiece(new Queen(pieceCode, morphingPawnX, 0));
        isChoosingMorphedPiece = false;
    }

    private void placeNewWhiteQueen(int pieceCode) {
        board.getSquare(morphingPawnX, 7).setOccupyingPiece(new Queen(pieceCode, morphingPawnX, 7));
        isChoosingMorphedPiece = false;
    }

    private void prepareNextTurn() {
        changeTurn();
        computeMovesForCurrentPlayer();
        markIsEnd();
    }

    private void markIsEnd() {
        if (players[TURN].amILoose()) {
            isEnd = true;
        }
    }

    private void computeMovesForCurrentPlayer() {
        players[TURN].computePossibleCaptures(board);
        players[TURN].computePossibleMoves(board);
    }

    private void changeTurn() {
        players[TURN].clearMovesAndCaptures();
        selectedPiece = null;

        if (TURN == WHITE_TURN) {
            TURN = BLACK_TURN;
        }
        else {
            TURN = WHITE_TURN;
        }
    }

    private void updateKingPositionIfNeeded(int targetX, int targetY) {
        if (selectedPiece.getType() == PieceCodes.WHITE_KING || selectedPiece.getType() == PieceCodes.BLACK_KING) {
            players[TURN].setKingPosition(targetX, targetY);
        }
    }

    private void uncheckPieceSelection() {
        selectedPiece.setXY(currentX * Constants.SQUARE_SIZE, currentY * Constants.SQUARE_SIZE);
        selectedPiece = null;
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

