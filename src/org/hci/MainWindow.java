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
	
	Shape start_circle;
	
	int states;
	/* 0 = launch screen
	 * 1 = start screen
	 * 2 = in-test screen
	 */
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("HCI - Group Floor 2pi");
		MainWindow w = new MainWindow(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setUndecorated(true); //removes top bar with close buttons
		frame.setLocationByPlatform(true);
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize);
		width = screenSize.width;
		height = screenSize.height;
		
		frame.setFocusable(true);
		
		frame.add(w);
		
		Logging logger = Logging.GetLogger();
		
		logger.Log(1, 22, new Date(), new Date(), 'M', 21, new Resolution("1000x2000"), 10, 2, 1.5, 1.5);
		
		logger.Close();
		
		//frame.pack();
		frame.setVisible(true);
		SwingUtilities.invokeLater(w);
		w.mainLoop();
	}
	
	public MainWindow(JFrame f) {
		this.setBackground(Color.black);
		f.addKeyListener(this);
		f.addMouseMotionListener(this);
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
<<<<<<< HEAD
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
		}
		g.drawImage(buffer, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
		b.dispose();
		g.dispose();
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
		//txt = "Click "+shapeColour+" "+shapeType;
		txt = "Click "+"red"+" "+"square";
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 72));
		b.drawString(txt, centreAlignString(txt, b), 100);
		//draw circle in centre of screen
		int radius=50;
		start_circle = new Ellipse2D.Double((width/2)-(radius/2), (height/2)-(radius/2), radius, radius);
		b.fill(start_circle);
		return b;
	}
	
	Graphics2D drawExperiment(Graphics2D b) {
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
		} catch (NullPointerException n) {}
			
		//check if mouse is in the centre start circle
		if (states==1 && start_circle.contains(x, y-20)) {
			System.out.println("Mouse in circle... ready to start.");
			states = 2;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
}
