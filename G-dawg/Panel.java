import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel{
	private int width;
	private int height;
	private BufferedImage buffer;
	private ArrayList<Circle> shapes;
	
	public Panel(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		shapes = new ArrayList<Circle>();
		
		/**/
		shapes.add(new Circle(100, 100, 50, Color.RED));
		shapes.add(new Circle(500, 300, 50, Color.GREEN));
		shapes.add(new Circle(100, 300, 50, Color.BLUE));
		shapes.add(new Circle(100, 400, 50, Color.GREEN));
		shapes.add(new Circle(900, 600, 50, Color.RED));
		shapes.add(new Circle(100, 900, 50, Color.GREEN));
		shapes.add(new Circle(1200, 800, 50, Color.RED));
		/**/
		System.out.println("HELLO2");
	}
	
	void draw() {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D b = buffer.createGraphics();
		Graphics2D g = (Graphics2D) this.getGraphics();
		
		/**/
		b.setColor(Color.BLACK);
		b.drawRect(0, 0, width, height);
		for(int i=0; i<shapes.size(); i++) shapes.get(i).draw(b);		
		/**/
		
		g.drawImage(buffer, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
		b.dispose();
		g.dispose();
	}

	public void update() {
		draw();
		for(int i=0; i<shapes.size(); i++){
			shapes.get(i).move();
			if(shapes.get(i).isOutOfBounds(width, height)) shapes.get(i).bounce(); 
		}
	}
	
}
