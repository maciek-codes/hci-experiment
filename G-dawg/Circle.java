import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Circle {
	
	private int x, y, r;
	private int vx, vy;
	private Color c;
	private Random rand;
	
	public Circle(int x, int y, int r, Color c){
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = c;
		
		rand = new Random();
		vx = (int) (rand.nextDouble()*100)%10;
		vy = (int) ((rand.nextDouble()*100)%10);
	}
	
	void draw(Graphics2D b){
		b.setColor(c);
		b.fillOval(x-r, y-r, r+r, r+r);
	}
	
	void move(){
		x += vx;
		y += vy;
		if(rand.nextDouble() < 0.01){
			vx = (int) ((rand.nextDouble()*100)*(rand.nextDouble()<0.5 ? -1:1)%10);
			vy = (int) ((rand.nextDouble()*100)*(rand.nextDouble()<0.5 ? -1:1)%10);
		}
	}
	
	void bounce(){
		vx = vx * -1;
		vy = vy * -1;
	}
	
	boolean isOutOfBounds(int width, int height){
		if(x-r < 0 || x+r > width) return true;
		else if(y < 0 || y+r+r > height) return true;
		else return false;
	}
}
