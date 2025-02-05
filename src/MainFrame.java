import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private int WIDTH, HEIGHT;
    private int X_MARGIN, Y_MARGIN;

    JPanel mainPanel;

    JMenuBar menuBar;


    MainFrame(){
        WIDTH = 800;
        HEIGHT = 460;
        X_MARGIN = 20;
        Y_MARGIN = 20;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Game of Life Simulator");
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setLayout(null);
        add(mainPanel); // add main panel
        pack();
    }

    void addMenu(){ // no clue wheter I should add one
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
    }

    void contentPanel(){
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setBounds(X_MARGIN,Y_MARGIN, WIDTH-2*X_MARGIN, HEIGHT-2*Y_MARGIN);
        //content.setBackground(Color.BLACK);
        mainPanel.add(content); // add content panel to main panel
        int gWidth = (WIDTH-2*X_MARGIN)*2/3;
        int gHeight = HEIGHT-2*Y_MARGIN;
        GamePanel gamePanel = new GamePanel(gWidth, gHeight);
        content.add(gamePanel); // add game panel to content panel

        ControlPanel controlPanel = new ControlPanel(gWidth + X_MARGIN, 0, WIDTH-3*X_MARGIN-gWidth,gHeight, gamePanel);
        //control.setBackground(Color.BLUE);

        content.add(controlPanel);

    }



}
