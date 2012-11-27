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

	int testNo = 1, numTests=6;

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
			        new Circle((int)((429/1000.0)*width), (int)((229/1000.0)*height), 100, Color.blue, width, height, false),
			        new Circle((int)((602/1000.0)*width), (int)((892/1000.0)*height), 50, Color.white, width, height, false),
			        new Circle((int)((299/1000.0)*width), (int)((513/1000.0)*height), 50, Color.yellow, width, height, false),
			        new Circle((int)((532/1000.0)*width), (int)((184/1000.0)*height), 100, Color.white, width, height, false),
			        new Circle((int)((901/1000.0)*width), (int)((870/1000.0)*height), 80, Color.red, width, height, false),
			        new Circle((int)((182/1000.0)*width), (int)((839/1000.0)*height), 100, Color.green, width, height, false),
			        new Circle((int)((725/1000.0)*width), (int)((822/1000.0)*height), 69, Color.green, width, height, false),
			        new Circle((int)((848/1000.0)*width), (int)((65/1000.0)*height), 100, Color.yellow, width, height, false),
			        new Circle((int)((462/1000.0)*width), (int)((731/1000.0)*height), 50, Color.red, width, height, false),
			        new Square((int)((210/1000.0)*width), (int)((272/1000.0)*height), 50, Color.blue, width, height, false),
			        new EQTriangle((int)((805/1000.0)*width), (int)((343/1000.0)*height), 50, Color.green, width, height, false)
			};
			break;
		case 2:
			shapeArray = new Shapes[]{
			        new Square((int)((654/1000.0)*width), (int)((135/1000.0)*height), 100, Color.blue, width, height, true),
			        new Circle((int)((495/1000.0)*width), (int)((651/1000.0)*height), 50, Color.white, width, height, true),
			        new Circle((int)((542/1000.0)*width), (int)((294/1000.0)*height), 50, Color.yellow, width, height, true),
			        new Square((int)((907/1000.0)*width), (int)((368/1000.0)*height), 100, Color.white, width, height, true),
			        new Circle((int)((457/1000.0)*width), (int)((548/1000.0)*height), 80, Color.red, width, height, true),
			        new Square((int)((489/1000.0)*width), (int)((643/1000.0)*height), 100, Color.green, width, height, true),
			        new Circle((int)((250/1000.0)*width), (int)((373/1000.0)*height), 69, Color.green, width, height, true),
			        new Square((int)((819/1000.0)*width), (int)((461/1000.0)*height), 100, Color.yellow, width, height, true),
			        new Circle((int)((396/1000.0)*width), (int)((746/1000.0)*height), 50, Color.red, width, height, true)
			};
			stationary = new Square(422, 835, 50, Color.red, width, height, false);
			break;
		case 3:
			shapeArray = new Shapes[]{
			        new Square((int)((543/1000.0)*width), (int)((786/1000.0)*height), 100, Color.pink, width, height, false),
			        new EQTriangle((int)((453/1000.0)*width), (int)((378/1000.0)*height), 50, Color.red, width, height, false),
			        new Circle((int)((123/1000.0)*width), (int)((887/1000.0)*height), 50, Color.green, width, height, false),
			        new Circle((int)((543/1000.0)*width), (int)((620/1000.0)*height), 100, Color.white, width, height, false),
			        new Square((int)((400/1000.0)*width), (int)((400/1000.0)*height), 80, Color.gray, width, height, false),
			        new Circle((int)((933/1000.0)*width), (int)((320/1000.0)*height), 100, Color.blue, width, height, false),
			        new Circle((int)((150/1000.0)*width), (int)((350/1000.0)*height), 69, Color.green, width, height, false),
			        new Square((int)((854/1000.0)*width), (int)((560/1000.0)*height), 100, Color.white, width, height, false),
			        new Circle((int)((755/1000.0)*width), (int)((150/1000.0)*height), 50, Color.yellow, width, height, false),
			        new Square((int)((234/1000.0)*width), (int)((180/1000.0)*height), 50, Color.gray, width, height, false),
			        new EQTriangle((int)((1000/1000.0)*width), (int)((456/1000.0)*height), 50, Color.yellow, width, height, false)
			};
			stationary = new Square(600, 389, 50, Color.red, width, height, false);
			break;
		case 4:
			shapeArray = new Shapes[]{
			        new Square((int)((916/1000.0)*width), (int)((812/1000.0)*height), 61, Color.white, width, height, true),
			        new EQTriangle((int)((600/1000.0)*width), (int)((327/1000.0)*height), 84, Color.green, width, height, true),
			        new Circle((int)((500/1000.0)*width),(int)((500/1000.0)*height), 98, Color.red, width, height, true),
			        new Square((int)((374/1000.0)*width), (int)((781/1000.0)*height), 67, Color.white, width, height, true),
			        new EQTriangle((int)((688/1000.0)*width), (int)((400/1000.0)*height), 96, Color.blue, width, height, true),
			        new Square((int)((941/1000.0)*width), (int)((320/1000.0)*height), 82, Color.pink, width, height, true),
			        new Circle((int)((295/1000.0)*width), (int)((546/1000.0)*height), 76, Color.pink, width, height, true),
			        new Square((int)((332/1000.0)*width), (int)((719/1000.0)*height), 97, Color.blue, width, height, true),
			        new EQTriangle((int)((382/1000.0)*width), (int)((134/1000.0)*height), 87, Color.blue, width, height, true),
			        new Square((int)((394/1000.0)*width), (int)((620/1000.0)*height), 55, Color.white, width, height, true),
			        new EQTriangle((int)((482/1000.0)*width), (int)((13/1000.0)*height), 94, Color.blue, width, height, true),
			        new Square((int)((102/1000.0)*width),(int)((61/1000.0)*height), 74, Color.pink, width, height, true),
			        new Circle((int)((849/1000.0)*width), (int)((588/1000.0)*height), 78, Color.pink, width, height, true),
			        new Square((int)((297/1000.0)*width), (int)((982/1000.0)*height), 73, Color.white, width, height, true),
			        new EQTriangle((int)((672/1000.0)*width), (int)((340/1000.0)*height), 79, Color.blue, width, height, true)
			};
			stationary = new Square(573, 546, 50, Color.red, width, height, false);
			break;
		case 5:
			shapeArray = new Shapes[]{
			        new Square((int)((418/1000.0)*width), (int)((268/1000.0)*height), 100, Color.pink, width, height, false),
			        new Circle((int)((545/1000.0)*width), (int)((860/1000.0)*height), 50, Color.red, width, height, false),
			        new Square((int)((124/1000.0)*width), (int)((853/1000.0)*height), 50, Color.green, width, height, false),
			        new EQTriangle((int)((351/1000.0)*width), (int)((56/1000.0)*height), 100, Color.white, width, height, false),
			        new Circle((int)((49/1000.0)*width), (int)((354/1000.0)*height), 80, Color.gray, width, height, false),
			        new EQTriangle((int)((64/1000.0)*width), (int)((50/1000.0)*height), 100, Color.blue, width, height, false),
			        new Square((int)((196/1000.0)*width), (int)((673/1000.0)*height), 69, Color.green, width, height, false),
			        new Square((int)((298/1000.0)*width), (int)((856/1000.0)*height), 100, Color.white, width, height, false),
			        new EQTriangle((int)((412/1000.0)*width), (int)((21/1000.0)*height), 50, Color.yellow, width, height, false),
			        new Square((int)((971/1000.0)*width), (int)((533/1000.0)*height), 50, Color.gray, width, height, false),
			        new Circle((int)((767/1000.0)*width), (int)((450/1000.0)*height), 50, Color.yellow, width, height, false),
			        new Square((int)((946/1000.0)*width), (int)((794/1000.0)*height), 100, Color.pink, width, height, false),
			        new Circle((int)((800/1000.0)*width), (int)((355/1000.0)*height), 50, Color.red, width, height, false),
			        new Square((int)((300/1000.0)*width), (int)((486/1000.0)*height), 50, Color.green, width, height, false),
			        new EQTriangle((int)((380/1000.0)*width), (int)((408/1000.0)*height), 100, Color.white, width, height, false),
			        new Circle((int)((497/1000.0)*width), (int)((366/1000.0)*height), 80, Color.gray, width, height, false),
			        new EQTriangle((int)((824/1000.0)*width), (int)((359/1000.0)*height), 100, Color.blue, width, height, false),
			        new Square((int)((738/1000.0)*width), (int)((339/1000.0)*height), 69, Color.green, width, height, false),
			        new Square((int)((540/1000.0)*width), (int)((851/1000.0)*height), 100, Color.white, width, height, false),
			        new EQTriangle((int)((100/1000.0)*width), (int)((223/1000.0)*height), 50, Color.yellow, width, height, false),
			        new Square((int)((596/1000.0)*width), (int)((857/1000.0)*height), 50, Color.gray, width, height, false),
			        new Circle((int)((444/1000.0)*width), (int)((655/1000.0)*height), 50, Color.yellow, width, height, false)
			};
			stationary = new Square(377, 654, 50, Color.red, width, height, false);
			break;
		case 6:
			shapeArray = new Shapes[]{
			        new Circle((int)((287/1000.0)*width), (int)((377/1000.0)*height), 100, Color.blue, width, height, true),
			        new Square((int)((96/1000.0)*width), (int)((765/1000.0)*height), 50, Color.gray, width, height, true),
			        new Square((int)((107/1000.0)*width), (int)((219/1000.0)*height), 50, Color.green, width, height, true),
			        new EQTriangle((int)((526/1000.0)*width), (int)((751/1000.0)*height), 100, Color.white, width, height, true),
			        new Circle((int)((928/1000.0)*width), (int)((37/1000.0)*height), 80, Color.yellow, width, height, true),
			        new EQTriangle((int)((861/1000.0)*width), (int)((127/1000.0)*height), 100, Color.blue, width, height, true),
			        new Square((int)((204/1000.0)*width), (int)((399/1000.0)*height), 69, Color.green, width, height, true),
			        new Circle((int)((278/1000.0)*width), (int)((574/1000.0)*height), 100, Color.green, width, height, true),
			        new Square((int)((551/1000.0)*width), (int)((232/1000.0)*height), 50, Color.white, width, height, true),
			        new Circle((int)((111/1000.0)*width), (int)((206/1000.0)*height), 100, Color.blue, width, height, true),
			        new Square((int)((202/1000.0)*width), (int)((577/1000.0)*height), 50, Color.yellow, width, height, true),
			        new Square((int)((923/1000.0)*width), (int)((381/1000.0)*height), 50, Color.green, width, height, true),
			        new EQTriangle((int)((676/1000.0)*width), (int)((221/1000.0)*height), 100, Color.white, width, height, true),
			        new Circle((int)((603/1000.0)*width), (int)((263/1000.0)*height), 68, Color.yellow, width, height, true),
			        new EQTriangle((int)((718/1000.0)*width), (int)((567/1000.0)*height), 100, Color.blue, width, height, true),
			        new Square((int)((877/1000.0)*width), (int)((778/1000.0)*height), 69, Color.blue, width, height, true),
			        new Circle((int)((109/1000.0)*width), (int)((18/1000.0)*height), 100, Color.green, width, height, true),
			        new Square((int)((714/1000.0)*width), (int)((162/1000.0)*height), 50, Color.white, width, height, true)
			};
			stationary = new Square(219, 765, 50, Color.red, width, height, false);
			break;

		}
	}

	Graphics2D drawLaunchScreen(Graphics2D b) {
		b.setColor(Color.WHITE);
		b.setFont(new Font(Font.SERIF, Font.PLAIN, 72));
		b.drawString("Instructions", centreAlignString("Instructions", b), 100);
		b.drawString("During this, move the mouse to the", centreAlignString("During this, move the mouse to the", b), 300);
		b.drawString("center circle and click to start.", centreAlignString("center circle and click to start.", b), 400);
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
		txt = "Go to the "+"red"+" "+stationary.getShapeType();
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

