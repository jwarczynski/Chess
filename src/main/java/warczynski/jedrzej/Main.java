package warczynski.jedrzej;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Objects;

import javax.swing.*;

class Main{
    public static void main(String[] args) throws IOException {
        
        Chess game = new Chess();
        JFrame frame = new JFrame("Chess");
        frame.add(game);
        ImageIcon image = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("img/wknight.png")));
        frame.setIconImage(image.getImage());
        frame.setBounds(300, 100, 100, 100);
        frame.setBounds(300, 50, 528, 551);
        frame.setResizable(false);
        game.setPreferredSize(new Dimension(512,512));        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}