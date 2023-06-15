package warczynski.jedrzej.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static warczynski.jedrzej.constants.PiecesImagesPaths.WHITE_KNIGHT;

public class UI implements Runnable {

    private static final String TITLE = "Chess";

    @Override
    public void run() {
        try {
            startGameUI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startGameUI() throws IOException {
        JPanel board = createBoardPanel();
        JFrame frame = initGameFrame();
        addBoardPanelAndShowFrame(frame, board);
    }

    private JPanel createBoardPanel() throws IOException {
        BoardPanel board = new BoardPanel();
        board.setPreferredSize(new Dimension(512,512));
        return board;
    }

    private JFrame initGameFrame() {
        JFrame frame = new JFrame(TITLE);
        setIconImage(frame);
        setSize(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return frame;
    }

    private void setIconImage(JFrame frame) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(UI.class.getClassLoader().getResource(WHITE_KNIGHT)));
        frame.setIconImage(image.getImage());
    }

    private void setSize(JFrame frame) {
        frame.setBounds(300, 50, 528, 551);
        frame.setResizable(false);
    }

    private void addBoardPanelAndShowFrame(JFrame frame, JPanel board) {
        frame.add(board);
        frame.pack();
        frame.setVisible(true);
    }

}
