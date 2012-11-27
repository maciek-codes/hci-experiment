package org.hci;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Circle extends Shapes {
	
	private int r;
	private int vx, vy;
	private Random rand;
	
	public Circle(int x, int y, int r, Color c, int w, int h, boolean moving){
		super(x, y, c, w, h, moving);
		this.r = r;
		rand = new Random();
		vx = (int) (rand.nextDouble()*100)%10;
		vy = (int) ((rand.nextDouble()*100)%10);
	}
	
	@Override
	public void draw(Graphics2D g){
		g.setColor(c);
		g.fillOval(x-r, y-r, r+r, r+r);
	}
	
	@Override
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
	}
	
	@Override
	public void bounce(){
		vx = vx * -1;
		vy = vy * -1;
	}
	
	@Override
	public boolean isOutOfBounds(int width, int height){
		if(x-r < 0 || x+r > width) return true;
		else if(y < 0 || y+r+r > height) return true;
		else return false;
	}

	@Override
	public boolean contains(Point point) {
		int dx = Math.abs(point.x-x);
		int dy = Math.abs(point.y-y);
		int R = r;
		if ((dx^2) + (dy^2) <= (R^2))
			return true;
		return false;
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
