package org.hci;

import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public abstract class Shapes implements MouseMotionListener {
    int x, y, vx, vy;
	int width, height;
	boolean moving;
    Color c;
	Random rand;
    
    public Shapes(int x,int y,Color c, int w, int h, boolean moving){
    	this.moving = moving;
    	width = w;
    	height = h;
        this.x=x;
        this.y=y;
        this.c=c;
        
        rand = new Random();
		vx = (int) (rand.nextDouble()*100)%10;
		vy = (int) ((rand.nextDouble()*100)%10);
    }
    
    public abstract void draw(Graphics2D g);
    
	public void move(){
		if (!moving) return;
		x += vx;
		y += vy;
		if(rand.nextDouble() < 0.01){
			vx = (int) ((rand.nextDouble()*100)*(rand.nextDouble()<0.5 ? -1:1)%10);
			vy = (int) ((rand.nextDouble()*100)*(rand.nextDouble()<0.5 ? -1:1)%10);
		}
		if (isOutOfBounds(width, height)) bounce();
	}
	
	public void bounce(){
		vx = vx * -1;
		vy = vy * -1;
	}
	
	public abstract boolean isOutOfBounds(int width, int height);
    
    public void setLocation(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    public abstract String getShapeType();

	public abstract boolean contains(Point point);
		// TODO Auto-generated method stub
	
	

	
}
