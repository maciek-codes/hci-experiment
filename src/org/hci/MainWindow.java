package org.hci;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.color.*;

public class MainWindow extends JPanel implements Runnable, KeyListener, MouseListener {
	
	BufferedImage buffer;
	static int width;
	static int height;
	int states;
	
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
		
		
		frame.add(w);
		
		//frame.pack();
		frame.setVisible(true);
		SwingUtilities.invokeLater(w);
		w.mainLoop();
	}
	
	public MainWindow() {
		this.setBackground(Color.black);
	}
	
	public void run(){

	}
	
	void mainLoop() {
		states = 0;
		while (true)
			draw();
	}
	
	void draw() {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D b = buffer.createGraphics();
		Graphics2D g = (Graphics2D) this.getGraphics();
		switch (states) {
			case 0:
			b = drawStartScreen(b);
			break;
			case 1:
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//if key is SPACE key, then go to experiment
		System.out.println(e.getKeyCode());
		/*if (states==0)
			e.getKeyCode()*/
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
