import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private int WIDTH, HEIGHT;
    private int X_MARGIN, Y_MARGIN;

    JPanel mainPanel;

    MainFrame(){
        WIDTH = 800;
        HEIGHT = 450;
        X_MARGIN = 20;
        Y_MARGIN = 20;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Game of Life Simulator");
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setLayout(null);
        add(mainPanel);
        pack();
        setResizable(false);
    }

    void contentPanel(){
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setBounds(X_MARGIN,Y_MARGIN, WIDTH-2*X_MARGIN, HEIGHT-2*Y_MARGIN);
        //content.setBackground(Color.BLACK);
        mainPanel.add(content);
        GamePanel gp = new GamePanel((WIDTH-2*X_MARGIN)*2/3, HEIGHT-2*Y_MARGIN);
        content.add(gp);
    }



}
