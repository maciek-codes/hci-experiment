package org.hci;

import java.awt.*;
import java.awt.event.MouseMotionListener;

public abstract class Shapes implements MouseMotionListener {
    int x;
	int y;
	int width, height;
	boolean moving;
    Color c;
    
    public Shapes(int x,int y,Color c, int w, int h, boolean moving){
    	this.moving = moving;
    	width = w;
    	height = h;
        this.x=x;
        this.y=y;
        this.c=c;
    }
    
    public abstract void draw(Graphics2D g);
    
	public abstract void move();
	
	public abstract void bounce();
	
	public abstract boolean isOutOfBounds(int width, int height);
    
    public void setLocation(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    public abstract String getShapeType();

	public abstract boolean contains(Point point);
		// TODO Auto-generated method stub
	
	

	
}
