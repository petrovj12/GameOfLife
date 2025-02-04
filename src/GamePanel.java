import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {

    int WIDTH, HEIGHT;
    int NUM_ROWS, NUM_COLS;
    int CELL_WIDTH, CELL_HEIGHT;
    int timePerFrame;
    boolean[][] cellStatus;

    GamePanel(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setBounds(0,0, WIDTH, HEIGHT);
        //this.setBackground(Color.BLACK);
        NUM_ROWS = 32;
        NUM_COLS = 32;
        cellStatus = new boolean[NUM_ROWS][NUM_COLS];
        clearStatus();
        setGlider(3,3);
        setBlinker(12,3);
        CELL_WIDTH = WIDTH/NUM_COLS;
        CELL_HEIGHT = HEIGHT/NUM_ROWS;
        timePerFrame = 300;


        Timer timer = new Timer(timePerFrame, this);
        timer.start();
    }

    public void setBlinker(int i, int j){
        setStatus(i,j,true);
        setStatus(i,j+1,true);
        setStatus(i,j+2,true);
    }

    public void setGlider(int i, int j){
        setStatus(i,j,true);
        setStatus(i,j+1,true);
        setStatus(i,j+2,true);
        setStatus(i-1,j+2,true);
        setStatus(i-2,j+1,true);
    }

    public void clearStatus(){
        for(int i = 0; i < NUM_ROWS; ++i){
            for(int j = 0; j < NUM_COLS; ++j){
                cellStatus[i][j] = false;
            }
        }
    }

    public void drawGrid(Graphics2D g2){
        g2.setPaint(Color.WHITE);
        int GRID_WIDTH = NUM_COLS * CELL_WIDTH;
        int GRID_HEIGHT = NUM_ROWS * CELL_HEIGHT;
        for(int i = 0; i <= NUM_ROWS; ++i){
            g2.drawLine(0, i*CELL_HEIGHT, GRID_WIDTH, i*CELL_HEIGHT);
        }
        for(int j = 0; j <= NUM_COLS; ++j){
            g2.drawLine(j*CELL_WIDTH, 0, j*CELL_WIDTH, GRID_HEIGHT);
        }
    }

    public void drawStatus(Graphics2D g2){
        g2.setPaint(Color.WHITE);
        for(int i = 0; i < NUM_ROWS; ++i){
            for(int j = 0; j < NUM_COLS; ++j){
                g2.setPaint((cellStatus[i][j]) ? Color.WHITE : Color.BLACK);
                g2.fillRect(CELL_WIDTH*i, CELL_HEIGHT*j, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public void setStatus(int i, int j, boolean value){
        cellStatus[i][j] = value;
    }

    public void updateStatus(){
        boolean[][] oldStatus = new boolean[NUM_COLS][NUM_ROWS];

        for(int i = 0; i < NUM_COLS; ++i){
            for(int j = 0; j < NUM_ROWS; ++j){
                oldStatus[i][j] = cellStatus[i][j];
            }
        }

        for(int i = 0; i < NUM_COLS; ++i){
            for(int j = 0; j < NUM_ROWS; ++j){
                int neigh = getAliveNeighbours(oldStatus, i, j);
                if(oldStatus[i][j]){
                    if(!(2 <= neigh && neigh <= 3)){
                        cellStatus[i][j] = false;
                    }
                }else{
                    if((neigh == 3)){
                        cellStatus[i][j] = true;
                    }
                }
            }
        }

    }

    public boolean inBounds(int i, int j){
        return i >= 0 && i < NUM_COLS && j >= 0 && j < NUM_ROWS;
    }

    public int getAliveNeighbours(boolean[][] array, int i, int j){

        int out = 0;
        for(int x = -1; x <= 1; ++x){
            for(int y= -1; y <= 1; ++y){
                if(x == 0 && y == 0)
                    continue;
                if(inBounds(i+x,j+y)){
                    out += (array[i+x][j+y])? 1: 0;
                }
            }
        }

        return out;
    }

   @Override
   public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;




       drawStatus(g2);
       drawGrid(g2);
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateStatus();
        repaint();
    }
}
