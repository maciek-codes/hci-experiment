package org.hci;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainWindow extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage buffer;
	static int width;
	static int height;
	
	int testNo = 1, numTests=2;
	
	Circle start_circle;
	
	Square stationary;
	
	Shapes[] shapeArray;

	static Logging logger;
	
	JFrame frame;

	int states;
	/* 0 = launch screen
	 * 1 = start screen
	 * 2 = in-test screen
	 * 3 = end of test screen
	 */
	
	long timeToSee, timeToMove;
	boolean firstMouseMove = false;
	
	public static void main(String[] args) {
		
		logger = Logging.GetLogger();
		JFrame f = new JFrame("HCI - Group Floor 2pi");
		MainWindow w;
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setUndecorated(true); //removes top bar with close buttons
		f.setLocationByPlatform(true);
		f.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(screenSize);
		width = screenSize.width;
		height = screenSize.height;
		
		//create shapes
		
		w = new MainWindow(f);
		
		f.setFocusable(true);
		
		f.add(w);
		
		logger.SetEnvironment('M', 21, new Resolution(screenSize.width, screenSize.height));
		
			
		//frame.pack();
		f.setVisible(true);
		SwingUtilities.invokeLater(w);
		w.mainLoop();
	}
	
	public MainWindow(JFrame f) {
		this.frame = f;
		this.setBackground(Color.black);
		this.frame.addKeyListener(this);
		this.frame.addMouseListener(this);
		this.frame.addMouseMotionListener(this);
		this.frame.addMouseMotionListener(stationary);
	}
	
	public void run(){}
	
	void mainLoop() {
		
		if(setUp()) {
			while (true)
				draw();
		}
	}
	
	boolean setUp() {
		
		Object[] possibilities = {"Female", "Male"};
		
		String gender = (String)JOptionPane.showInputDialog(
                frame,
                "Choose your gender:\n",
                "Gender",
                JOptionPane.OK_OPTION,
                null,
                possibilities,
                "Female");
		
		String age = (String)JOptionPane.showInputDialog(frame, "Enter your age:", "Age",  JOptionPane.OK_OPTION); 
		
		if(gender == null || age == null) 
			return false;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int ageNumber = Integer.parseInt(age);
		
		if(ageNumber < 15 || ageNumber > 125) {
			JOptionPane.showMessageDialog(frame, "Sorry, your age disqualifies you as a participant in this test.");
			return false;
		}
		
		logger.SetEnvironment(gender.charAt(0), ageNumber, new Resolution(screenSize.width, screenSize.height));
				
		return true;
	}
	
	void draw() {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D b = buffer.createGraphics();
		Graphics2D g = (Graphics2D) this.getGraphics();

		switch (states) {
			case 0:
				b = drawLaunchScreen(b);
				break;
			case 1:
				b = drawStartScreen(b);
				break;
			case 2:
				b = drawExperiment(b);
				break;
			case 3:
				b = drawEndScreen(b);
				logger.Close();
				break;
		}
		g.drawImage(buffer, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();		
		b.dispose();
		g.dispose();
	}
	
	void startTest() {
		switch (testNo) {
		case 1:
			shapeArray = new Shapes[]{
			        new Circle(350, 300, 100, Color.blue, width, height, false),
			        new Circle(550, 500, 50, Color.white, width, height, false),
			        new Circle(850, 700, 50, Color.yellow, width, height, false),
			        new Circle(250, 620, 100, Color.white, width, height, false),
			        new Circle(400, 400, 80, Color.red, width, height, false),
			        new Circle(933, 320, 100, Color.green, width, height, false),
			        new Circle(150, 350, 69, Color.green, width, height, false),
			        new Circle(1033, 560, 100, Color.yellow, width, height, false),
			        new Circle(755, 150, 50, Color.red, width, height, false),
			        new Square(234, 180, 50, Color.blue, width, height, false),
			        new EQTriangle(1000, 456, 50, Color.green, width, height, false)
			};
			break;
		case 2:
			shapeArray = new Shapes[]{
			        new Square(350, 300, 100, Color.blue, width, height, true),
			        new Circle(550, 500, 50, Color.white, width, height, true),
			        new Circle(850, 700, 50, Color.yellow, width, height, true),
			        new Square(250, 620, 100, Color.white, width, height, true),
			        new Circle(333, 400, 80, Color.red, width, height, true),
			        new Square(933, 320, 100, Color.green, width, height, true),
			        new Circle(150, 350, 69, Color.green, width, height, true),
			        new Square(1033, 560, 100, Color.yellow, width, height, true),
			        new Circle(550, 150, 50, Color.red, width, height, true),
			        new EQTriangle(1000, 456, 500, Color.blue, width, height, true),
			        new EQTriangle(1000, 456, 500, Color.red, width, height, true)
			};
			
			stationary = new Square(700, 300, 50, Color.red, width, height, false);
			break;
		
		}
	}
	
	Graphics2D drawLaunchScreen(Graphics2D b) {
		b.setColor(Color.WHITE);
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 72));
		b.drawString("Instructions", centreAlignString("Instructions", b), 100);
		b.drawString("During this, move the mouse", centreAlignString("During this, move the mouse", b), 300);
		b.drawString("to the center circle to start.", centreAlignString("to the center circle to start.", b), 400);
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 60));
		b.drawString("Press space key to begin.", centreAlignString("Press space key to begin.", b), 600);
		return b;
	}
	
	Graphics2D drawStartScreen(Graphics2D b) {
		String txt;
		b.setColor(Color.WHITE);
		b.fillRect(0, 150, width, 15);
		//TODO get shape colour and type function calls
		//shape for first Test
		stationary = new Square(550, 150, 50, Color.red, width, height, false);
		txt = "Go to "+"red"+" "+stationary.getShapeType();
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 72));
		b.drawString(txt, centreAlignString(txt, b), 100);
		//draw circle in centre of screen
		start_circle = new Circle((width/2), (height/2), 20, Color.WHITE, width, height, false);
		start_circle.draw(b);
		return b;
	}
	
	Graphics2D drawExperiment(Graphics2D b) {
		for (Shapes s : shapeArray) {
			s.draw(b);
			s.move();
		}
		stationary.draw(b);
		stationary.move();
		return b;
	}
	
	Graphics2D drawEndScreen(Graphics2D b) {
		b.setColor(Color.WHITE);
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 72));
		b.drawString("End of experiment", centreAlignString("End of experiment", b), 100);
		b.drawString("Thanks for completing", centreAlignString("Thanks for completing", b), 400);
		return b;
	}
	
	int centreAlignString(String s, Graphics2D g) {
		FontMetrics f = g.getFontMetrics();
		Rectangle2D rect = f.getStringBounds(s, g);
		return (int) ((width-rect.getWidth())/2);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//if key is SPACE key, then go to experiment
		if (states==0 && e.getKeyCode()==KeyEvent.VK_SPACE) {
			System.out.println("Spacebar pressed");
			states = 1;
		}
			
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!firstMouseMove){
			long t = System.nanoTime();
			//timeToSee = (long) (t*(long)Math.pow(10, -9)) - (timeToSee*(long)Math.pow(10, -9));
			timeToSee = t - timeToSee;
			timeToMove = t;
			firstMouseMove = true;
		}
		
		int x=0, y=0;
		try {
			x=e.getX();
			y=e.getY();
			//check if mouse is in the centre start circle
			
			if (states==2 && stationary.contains(new Point(x, y-23))) {
				long t = System.nanoTime();
				timeToMove = t - timeToMove;
				firstMouseMove = false;
				System.out.println("On target...");
				states = 1;
				testNo++;
				if (testNo > numTests)
					states = 3;
				System.out.println("time to see: "+timeToSee+ " ttm: " + timeToMove);
				logger.Log(testNo-1, new Date(), new Date(), shapeArray.length, shapeArray[1].moving, timeToSee, timeToMove);
			}
		} catch (NullPointerException n) {}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (states==1 && start_circle.contains(new Point(arg0.getX(), arg0.getY()-23))) {
			System.out.println("Mouse in circle... ready to start.");
			startTest();
			states = 2;
			timeToSee = System.nanoTime();
			firstMouseMove = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
