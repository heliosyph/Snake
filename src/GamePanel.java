import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
  
      //declare everything we will need in this program
      static final int SCREEN_WIDTH = 500;
      static final int SCREEN_HEIGHT = 500;
  
      static final int INDIVIDUAL_UNIT_SIZE = 20;
      static final int TOTAL_GAME_UNITS = ((SCREEN_WIDTH) * (SCREEN_HEIGHT)) / (INDIVIDUAL_UNIT_SIZE);
  
      //the higher the delay for timer, the slower the game is
      static final int DELAY = 120;
  
  
      //create two arrays, which hold all the coordinates for all the body parts
      final int[] x_coordinates = new int[TOTAL_GAME_UNITS];
      final int[] y_coordinates = new int[TOTAL_GAME_UNITS];
  
      int initialBodyParts = 1;
      int applesEaten;
  
      //the x coordinate that apple appears
      int appleX;
  
      //the y coordinate that apple appears
      int appleY;
  
      //poison
      int poisonX;
      int poisonY;
  
      char direction = 'R';
      boolean running = false;
      Timer timer;
      Random random;
  
  
  
      GamePanel(){
      random = new Random();
  
      Dimension dimension = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
      MyKeyAdapter myKey = new MyKeyAdapter();
  
      this.setPreferredSize(dimension);
      this.setBackground(Color.white);
      this.setFocusable(true);
      this.addKeyListener(myKey);
  
      startGame();
      }
  
      public void startGame(){
      newApple(); //generate a random apple
      running = true;
      timer = new Timer(DELAY, this);
      timer.start();
      }
  
      public void paintComponent(Graphics g){
      super.paintComponent(g);
      draw(g);
  
      }
  
  
      public void draw(Graphics g) {
  
          if (running) {
  
              if(applesEaten > 0){
                  //focusing on the poison
                  g.setColor(Color.red);
                  g.fillRect(poisonX, poisonY, INDIVIDUAL_UNIT_SIZE, INDIVIDUAL_UNIT_SIZE);
              }
  
              //focusing on the apple
              g.setColor(Color.cyan);
              g.fillOval(appleX, appleY, INDIVIDUAL_UNIT_SIZE, INDIVIDUAL_UNIT_SIZE);
  
              for (int i = 0; i < initialBodyParts; i++) {
                  if (i == 0) {
                      //dealing with head of snake
                      g.setColor(Color.pink);
                  }
                  else {
                      //randomly choose the rgb values
                      g.setColor(new Color(random.nextInt(255),
                              random.nextInt(255),
                              random.nextInt(255)));
  
                  }
  
                  g.fillRect(x_coordinates[i], y_coordinates[i], INDIVIDUAL_UNIT_SIZE, INDIVIDUAL_UNIT_SIZE);
              }
  
              //needs to go here
              g.setColor(Color.blue);
              g.setFont(new Font("Times New Roman", Font.BOLD, 25));
              FontMetrics metrics = getFontMetrics(g.getFont());
              g.drawString("Score: " + applesEaten,
                      (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) /2 ,
                      g.getFont().getSize());
  
          }
  
          else {
              //kinda wanna restart it
              gameOver(g);
          }
      }
  
      public void newApple(){
      //generating coordinates of a new Apple whenever this method is called
          //appear someplace along the x axis
          appleX = random.nextInt(SCREEN_WIDTH/INDIVIDUAL_UNIT_SIZE) * INDIVIDUAL_UNIT_SIZE;
          appleY = random.nextInt(SCREEN_HEIGHT/INDIVIDUAL_UNIT_SIZE)* INDIVIDUAL_UNIT_SIZE;
      }
  
      public void newPoison(){
          //random spawn of poison food
          poisonX = random.nextInt(SCREEN_WIDTH/INDIVIDUAL_UNIT_SIZE) * INDIVIDUAL_UNIT_SIZE;
          poisonY = random.nextInt(SCREEN_HEIGHT/INDIVIDUAL_UNIT_SIZE)* INDIVIDUAL_UNIT_SIZE;
      }
  
  
  
      public void move(){
  
          //for loop to iterate through all the body parts
          for(int i = initialBodyParts; i > 0; i--){
              x_coordinates[i] = x_coordinates[i-1]; //shifting coordinates
              y_coordinates[i] = y_coordinates[i-1];
          }
          switch(direction){
              case 'U' :
                  y_coordinates[0] = y_coordinates[0] - INDIVIDUAL_UNIT_SIZE;
                  break;
              case 'D' :
                  y_coordinates[0] = y_coordinates[0] + INDIVIDUAL_UNIT_SIZE;
                  break;
              case 'L':
                  x_coordinates[0] = x_coordinates[0] - INDIVIDUAL_UNIT_SIZE;
                  break;
              case 'R':
                  x_coordinates[0] = x_coordinates[0] + INDIVIDUAL_UNIT_SIZE;
                  break;
          }
      }
  
      public void checkApple(){
          //examine the coordinates of snake and apple
          if((x_coordinates[0] == appleX) && (y_coordinates[0] == appleY)){
              initialBodyParts++;
              applesEaten++; //functions as score
              this.setBackground(Color.white);
              newApple();
          }
      }
  
      public void checkPoison(){
          //Snake doesn't like to eat this so tail will shrink
          if(applesEaten < 0){
              running = false;
          }
  
          if((x_coordinates[0] == poisonX) && (y_coordinates[0] == poisonY) && applesEaten > 0){
              initialBodyParts--;
              applesEaten--;
              this.setBackground(Color.black);
              newPoison();
          }
  
      }
  
  
      public void checkCollisions(){
          //this checks if head collides with body
      for(int i = initialBodyParts; i > 0; i--){
          if ((x_coordinates[0] == x_coordinates[i]) && (y_coordinates[0] == y_coordinates[i])) {
              running = false; //trigger a game over method
              break;
          }
      }
  
      //if head touches left border
          if(x_coordinates[0] < 0){
              running = false;
          }
          if(x_coordinates[0] > SCREEN_WIDTH){
              running = false;
          }
          if(y_coordinates[0] < 0){
              running = false;
          }
          if(y_coordinates[0] > SCREEN_HEIGHT){
              running = false;
          }
          if(!running){
              timer.stop();
          }
  
      }
  
  
      public void gameOver(Graphics g){
          //Gameover text
  
          this.setBackground(Color.black);
  
          g.setColor(Color.green);
          g.setFont(new Font("Times New Roman", Font.BOLD, 30));
          FontMetrics metrics1 = getFontMetrics(g.getFont());
          g.drawString("Score: " + applesEaten,
                  (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) /2,
                  g.getFont().getSize());
  
  
          g.setColor(Color.green);
          g.setFont(new Font("Times New Roman", Font.BOLD, 35));
          FontMetrics metrics2 = getFontMetrics(g.getFont());
          g.drawString("<3Game Over<3",
                  (SCREEN_WIDTH - metrics2.stringWidth("<3Game Over<3")) /2,
                  SCREEN_HEIGHT/2);
  
      }
  
  
  
  
      @Override
      public void actionPerformed(ActionEvent e){
          //TODO Auto-generated method stub
          if(running){
              move();
              checkApple();
              checkPoison();
              checkCollisions();
          }
          repaint();
      }
  
      public class MyKeyAdapter extends KeyAdapter{
          //only has one method
          @Override
          public void keyPressed(KeyEvent e) {
              //super.keyPressed(e);
  
                  switch(e.getKeyCode()){
                      case KeyEvent.VK_LEFT:
                          //limit user to only 90degree turns
                          if (direction != 'R') {
                              direction = 'L';
                              break;
                          }
                      case KeyEvent.VK_RIGHT:
                          //limit user to only 90degree turns
                          if (direction != 'L') {
                              direction = 'R';
                              break;
                          }
                      case KeyEvent.VK_UP:
                          //limit user to only 90degree turns
                          if (direction != 'D') {
                              direction = 'U';
                              break;
                          }
                      case KeyEvent.VK_DOWN:
                          //limit user to only 90degree turns
                          if (direction != 'U') {
                              direction = 'D';
                              break;
                          }
                  }
  
          }
      }
}
