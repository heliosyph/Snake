import javax.swing.JFrame;

public class GameFrame extends JFrame{
  
      //game frame constructor
      GameFrame(){
        this.add(new GamePanel()); //gamePanel instance
        this.setTitle("Snake"); //title of Jframe
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // hide/close Jframe
        this.setResizable(false); //can't resize
        this.pack(); //fits everything in the frame, w/o it content won't display
        this.setVisible(true); // you want Jframe to NOT be invisible
        this.setLocationRelativeTo(null); //instead of having fixed location
    }
    
}
