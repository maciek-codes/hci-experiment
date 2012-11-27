import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main implements Runnable{

	JFrame frame;
	Panel panel;
	
    public static void main(String[] args){
        Main m = new Main();
        SwingUtilities.invokeLater(m);
        m.loop();
    }

	@Override
	public void run() {
		//PROBLEM WITH THIS IN THAT IT DOESNT TAKE TOOLBARS INTO ACCOUNT
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = d.width;
		int height = d.height;
		
		frame = new JFrame("test");
		
		//frame.addKeyListener(gamePanel);
        //frame.addMouseListener(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        
        panel = new Panel(width, height);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        System.out.println("HELLO");
	}
	
	void loop(){
        while(true){
            try{
            	Thread.sleep(15);
                panel.update();
            } catch (Exception ex){
            }
        }
    }
	
}
