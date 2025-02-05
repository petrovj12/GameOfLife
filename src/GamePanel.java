import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

    int WIDTH, HEIGHT;
    int NUM_ROWS, NUM_COLS;
    int CELL_WIDTH, CELL_HEIGHT;
    int timePerFrame;
    int GRID_WIDTH, GRID_HEIGHT;
    boolean[][] cellStatus;
    public boolean clearFlag;
    JLabel popText;
    JLabel genText;
    Timer timer;
    Graphics2D g2;
    int generation;
    //boolean[][] oldStatus;
    ArrayList<Vec2D> toChange;

    GamePanel(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setBounds(0,0, WIDTH, HEIGHT);

        //this.setBackground(Color.BLACK);
        NUM_ROWS = 30;
        NUM_COLS = 30;
        CELL_WIDTH = WIDTH/NUM_COLS;
        CELL_HEIGHT = HEIGHT/NUM_ROWS;

        cellStatus = new boolean[NUM_ROWS][NUM_COLS];
        clearStatus();

        //oldStatus = new boolean[NUM_ROWS][NUM_COLS];
        toChange = new ArrayList<Vec2D>();
        toChange.clear();

        //setGlider(3,3);
        //setBlinker(12,3);
        setCrazy(14,14);

        clearFlag = true;
        repaint();

        timePerFrame = 100;
        timer = new Timer(timePerFrame, this);

        addMouseListener(this);

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

    public void setCrazy(int i, int j){ // don't know the real name
        setStatus(i,j,true);
        setStatus(i,j+1,true);
        setStatus(i,j+2,true);
        setStatus(i+1,j+2,true);
        setStatus(i-1,j+1,true);
    }

    public void fillArray(boolean[][] array, boolean v){
        int nr = array.length;
        int nc = array[0].length;
        for(int i = 0; i < nr; ++i){
            for(int j = 0; j < nc; ++j){
                array[i][j] = v;
            }
        }
    }

    public void copyBool2D(boolean[][] source, boolean[][] dest){
        int nr = source.length;
        int nc = source[0].length;
        for(int i = 0; i < nr; ++i){
            for(int j = 0; j < nc; ++j){
                dest[i][j] = source[i][j];
            }
        }
    }

    public void clearStatus(){
        for(int i = 0; i < NUM_ROWS; ++i){
            for(int j = 0; j < NUM_COLS; ++j){
                cellStatus[i][j] = false;
            }
        }
    }

    public void drawGrid(){
        g2.setPaint(Color.WHITE);
        GRID_WIDTH = NUM_COLS * CELL_WIDTH;
        GRID_HEIGHT = NUM_ROWS * CELL_HEIGHT;
        for(int i = 0; i <= NUM_ROWS; ++i){
            g2.drawLine(0, i*CELL_HEIGHT, GRID_WIDTH, i*CELL_HEIGHT);
        }
        for(int j = 0; j <= NUM_COLS; ++j){
            g2.drawLine(j*CELL_WIDTH, 0, j*CELL_WIDTH, GRID_HEIGHT);
        }
    }

    public void drawStatus(){
        g2.setPaint(Color.WHITE);
        for(Vec2D c: toChange){
            boolean v = cellStatus[c.x][c.y];
            g2.setPaint((v) ? Color.WHITE : Color.BLACK);
            g2.fillRect(CELL_WIDTH*c.x, CELL_HEIGHT*c.y, CELL_WIDTH, CELL_HEIGHT);
        }

        toChange.clear();
    }

    public void drawInit(){
        for(int i = 0; i < NUM_COLS; ++i){
            for(int j = 0; j < NUM_ROWS; ++j){
                boolean v = cellStatus[i][j];
                g2.setPaint((v) ? Color.WHITE : Color.BLACK);
                g2.fillRect(CELL_WIDTH*i, CELL_HEIGHT*j, CELL_WIDTH, CELL_HEIGHT);
            }
        }

    }

    public void drawAll(){
        drawStatus();
        drawGrid();
    }

    public void setStatus(int i, int j, boolean value){
        cellStatus[i][j] = value;
    }

    public void updateStatus(){

        for(int i = 0; i < NUM_COLS; ++i){
            for(int j = 0; j < NUM_ROWS; ++j){
                int neigh = getAliveNeighbours(cellStatus, i, j);
                if(cellStatus[i][j]){
                    if(!(2 <= neigh && neigh <= 3)){
                        toChange.add(new Vec2D(i,j));
                    }
                }else{
                    if((neigh == 3)){
                        toChange.add(new Vec2D(i,j));
                    }
                }
            }
        }

        for(Vec2D c: toChange){
            cellStatus[c.x][c.y] = !cellStatus[c.x][c.y];
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
        //super.paint(g);

        g2 = (Graphics2D) g;

        if(clearFlag) {
            drawInit();
            clearFlag = false;
        }

        drawAll();
   }

   public int getPopulation(){
        int pop = 0;
        for (int i = 0; i < NUM_COLS;++i){
            for (int j = 0; j < NUM_ROWS; ++j){
                pop += cellStatus[i][j] ? 1 : 0;
            }
        }

        return  pop;
   }
    public void setPopulationText(){
        popText.setText("Population: " + getPopulation());
    }

    public void setGenerationText(){
        genText.setText("Generation: " + generation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateStatus();
        setPopulationText();
        ++generation;
        setGenerationText();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        int row = (x*this.NUM_COLS)/this.GRID_WIDTH;
        int col = (y*this.NUM_ROWS)/this.GRID_HEIGHT;

        cellStatus[row][col] = !cellStatus[row][col];
        toChange.add(new Vec2D(row,col));
        generation = 0;
        setGenerationText();
        setPopulationText();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
