package org.hci;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.color.*;

public class MainWindow extends JPanel implements KeyListener, MouseListener, Runnable  {
	
	private static final long serialVersionUID = 1L;

	public enum State {
		START, EXPERIMENT
	}
	
	BufferedImage buffer;
	static int width;
	static int height;
	State s;
	
	public static void main(String[] args) {
		MainWindow w = new MainWindow();
		JFrame frame = new JFrame("HCI - Group Floor 2pi");
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
		
		//frame.pack();
		frame.setVisible(true);
		SwingUtilities.invokeLater(w);
		w.mainLoop();
	}
	
	public MainWindow() {
		this.setBackground(Color.black);
		addKeyListener(this);
		addMouseListener(this);
	}
	
	public void run(){}
	
	void mainLoop() {
		s = State.START;
		while (true)
			draw();
	}
	
	void draw() {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D b = buffer.createGraphics();
		Graphics2D g = (Graphics2D) this.getGraphics();
		switch (s) {
			case START:
			b = drawStartScreen(b);
			break;
			case EXPERIMENT:
			b = drawExperiment(b);
			break;
		}
		g.drawImage(buffer, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
		b.dispose();
		g.dispose();
	}
	
	Graphics2D drawStartScreen(Graphics2D b) {
		b.setColor(Color.WHITE);
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 72));
		b.drawString("Instructions", centreAlignString("Instructions", b), 100);
		b.drawString("During this, move the mouse", centreAlignString("During this, move the mouse", b), 300);
		b.drawString("to the center circle to start.", centreAlignString("to the center circle to start.", b), 400);
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 60));
		b.drawString("Press space key to begin.", centreAlignString("Press space key to begin.", b), 600);
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
		System.out.println("here2");
		if (s==State.START && e.getKeyCode()==KeyEvent.VK_SPACE)
			s=State.EXPERIMENT;
	}

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
