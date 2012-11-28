package org.hci;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class Circle extends Shapes {
	
	private int r;
	
	public Circle(int x, int y, int r, Color c, int w, int h, boolean moving){
		super(x, y, c, w, h, moving);
		this.r = r;		
	}
	
	@Override
	public void draw(Graphics2D g){
		g.setColor(c);
		g.fillOval(x, y, r+r, r+r);
	}
	
	/*@Override
	public void move(){
		if (!super.moving) return;
		x += vx;
		y += vy;
		if(rand.nextDouble() < 0.01){
			vx = (int) ((rand.nextDouble()*100)*(rand.nextDouble()<0.5 ? -1:1)%10);
			vy = (int) ((rand.nextDouble()*100)*(rand.nextDouble()<0.5 ? -1:1)%10);
		}
		if (isOutOfBounds(width, height))
			bounce();
	}*/
	
	/*@Override
	public void bounce(){
		vx = vx * -1;
		vy = vy * -1;
	}*/
	
	@Override
	public boolean isOutOfBounds(int width, int height){
		if(x < 0 || x+r+r > width) return true;
		else if(y < 0 || y+r+r > height) return true;
		else return false;
	}

	@Override
	public boolean contains(Point point) {
		if (point.x<=x+r+r && point.x>=x && point.y<=y+r+r && point.y>=y)
			return true;
		else 
			return false;
		/*int centre_x=x+r, centre_y=y+r;
		int dx = Math.abs(point.x-centre_x);
		int dy = Math.abs(point.y-centre_y);
		double d = Math.sqrt((dx^2) + (dy^2));
		 if (d <= r) {
			System.out.println("point x: "+point.x+" centre x: "+centre_x);
			System.out.println("point y: "+point.y+" centre y: "+centre_y);
			System.out.println(d);
			return true;
		}
		return false;*/
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getShapeType() {
		return "circle";
	}
}
