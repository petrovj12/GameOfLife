import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener {

    JButton startBtn;
    JButton stopBtn;
    JButton stepBtn;
    JButton clearBtn;
    JSlider speedSlider;
    JLabel speedText;
    GamePanel gamePanel;

    public ControlPanel(int x, int y, int width, int height, GamePanel gamePanel){
        setBounds(x,y,width,height);
        setLayout(new FlowLayout());
        this.gamePanel = gamePanel;

        startBtn = new JButton("Start");
        startBtn.addActionListener(this);
        add(startBtn);

        stopBtn = new JButton("Stop");
        stopBtn.addActionListener(this);
        add(stopBtn);

        stepBtn = new JButton("Step");
        stepBtn.addActionListener(this);
        add(stepBtn);

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        add(clearBtn);

        speedText = new JLabel();
        speedText.setPreferredSize(new Dimension(200,40));
        speedText.setVerticalAlignment(JLabel.BOTTOM);
        speedText.setHorizontalAlignment(JLabel.CENTER);
        speedText.setText("Animation speed:");
        add(speedText);

        speedSlider = new JSlider(1,10);
        speedSlider.setPaintTicks(true);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(this);
        //speedSlider.addActionListener(this);
        add(speedSlider);

        gamePanel.popText = new JLabel();
        gamePanel.popText.setPreferredSize(new Dimension(200,30));
        gamePanel.popText.setVerticalAlignment(JLabel.BOTTOM);
        gamePanel.popText.setHorizontalAlignment(JLabel.LEFT);
        gamePanel.popText.setText("Population: 0");
        add(gamePanel.popText);

        gamePanel.genText= new JLabel();
        gamePanel.genText.setPreferredSize(new Dimension(200,20));
        gamePanel.genText.setVerticalAlignment(JLabel.BOTTOM);
        gamePanel.genText.setHorizontalAlignment(JLabel.LEFT);
        gamePanel.genText.setText("Generation: 0");
        add(gamePanel.genText);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == startBtn){
            gamePanel.timer.start();
        }
        if(actionEvent.getSource() == stopBtn){
            gamePanel.timer.stop();
        }
        if(actionEvent.getSource() == stepBtn){
            gamePanel.actionPerformed(new ActionEvent(stepBtn, 0,""));
        }
        if(actionEvent.getSource() == clearBtn){
            gamePanel.clearStatus();
            gamePanel.clearFlag = true;
            gamePanel.generation = 0;
            gamePanel.setGenerationText();
            gamePanel.setPopulationText();
            gamePanel.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        System.out.println(speedSlider.getValue());
        gamePanel.timer.setDelay(1000/speedSlider.getValue());
    }
}
