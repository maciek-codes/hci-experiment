package org.hci;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.color.*;

public class MainWindow extends JPanel implements Runnable, KeyListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage buffer;
	static int width;
	static int height;
	
	int testNo = 1, numTests=2;
	
	Circle start_circle;
	
	Square stationary;
	
	Shapes[] shapeArray;

	static Logging logger;
	
	int states;
	/* 0 = launch screen
	 * 1 = start screen
	 * 2 = in-test screen
	 */
	
	public static void main(String[] args) {
		
		logger = Logging.GetLogger();
		JFrame frame = new JFrame("HCI - Group Floor 2pi");
		MainWindow w;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setUndecorated(true); //removes top bar with close buttons
		frame.setLocationByPlatform(true);
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize);
		width = screenSize.width;
		height = screenSize.height;
		
		//create shapes
		
		w = new MainWindow(frame);
		
		frame.setFocusable(true);
		
		frame.add(w);
		
		logger.SetEnvironment('M', 21, new Resolution(screenSize.width, screenSize.height));
		
			
		//frame.pack();
		frame.setVisible(true);
		SwingUtilities.invokeLater(w);
		w.mainLoop();
	}
	
	public MainWindow(JFrame f) {
		this.setBackground(Color.black);
		f.addKeyListener(this);
		f.addMouseMotionListener(this);

		f.addMouseMotionListener(stationary);
	}
	
	public void run(){}
	
	void mainLoop() {
		while (true)
			draw();
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
			        new Circle(550, 150, 50, Color.red, width, height, true)
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
		int x=0, y=0;
		try {
			x=e.getX();
			y=e.getY();
			//check if mouse is in the centre start circle
			if (states==1 && start_circle.contains(new Point(x, y-23))) {
				System.out.println("Mouse in circle... ready to start.");
				startTest();
				states = 2;
			}
			if (states==2 && stationary.contains(new Point(x, y-23))) {
				System.out.println("On target...");
				states = 1;
				testNo++;
				if (testNo > numTests)
					states = 3;
				
				logger.Log(testNo-1, new Date(), new Date(), 10, 2, 1.5, 1.5);
			}
		} catch (NullPointerException n) {}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
}
