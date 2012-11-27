package org.hci;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Square extends Shapes {
    int s;
    
    public Square(int x,int y,int s,Color c, int w, int h, boolean moving) {
        super(x, y, c, w, h, moving);
        this.s = s;
    }

    public double area() {
        return s*s;
    }

    public double perim(){
        return s*4;
    }
    public void draw(Graphics2D g){
        g.setColor(c);
        g.fillRect(x, y, s, s);

    }

	@Override
	public boolean contains(Point point) {
		if (point.x <= x+s && point.x >= x && point.y <= y+s && point.y >= y) 
			return true;
		else
			return false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (!super.moving) return;
	}

	@Override
	public void bounce() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOutOfBounds(int width, int height) {
		// TODO Auto-generated method stub
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
		return "square";
	}

}