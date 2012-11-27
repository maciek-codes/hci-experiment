import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class HCI extends JComponent {

	
	 static Shape[] shapeArray = new Shape[]{
                      
             new Circle(350, 300, 100, Color.blue),
             new Circle(550, 500, 50, Color.white),
             new Circle(850, 700, 50, Color.yellow),
             new Circle(250, 620, 100, Color.white),
             new Circle(333, 400, 80, Color.red),
             new Circle(933, 320, 100, Color.green),
             new Circle(150, 350, 69, Color.green),
             new Circle(1033, 560, 100, Color.yellow),
             
             new Circle(550, 150, 50, Color.red) //STATIONARY ONE  SITS ON TOP HAHA
             
       
     };
	
	public static void main(String[] args) {
		

		
		JFrame frame=new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize);
		frame.setVisible(true);
		
		//Container content=frame.getContentPane();
		ShapeComponent sc = new ShapeComponent(shapeArray, screenSize.width, screenSize.height);
		sc.addMouseMotionListener(new MouseHandler(sc)); //add mouse listener
		
		//content.add(sc);
		frame.add(sc);
		frame.setBackground(Color.black);
		
	}
	
		

}
