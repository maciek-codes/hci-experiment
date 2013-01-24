package org.hci;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EQTriangle extends Shapes {
    int l;

    public EQTriangle(int x, int y, int l, Color c, int w, int h, boolean moving) {
        super(x, y, c, w, h, moving);
        this.l = l;

    }

    public double area() {
        return Math.sqrt(3) * l * l / 4;
    }

    public double perim() {
        return l * 3;
    }

    public void draw(Graphics2D g) {
        g.setColor(c);
        int[] xp = new int[]{x, x - l / 2, x + l / 2};
        int[] yp = new int[]{y - (int) (Math.sqrt(3) / 3 * l), y + (int) (Math.sqrt(3) / 6 * l), y + (int) (Math.sqrt(3) / 6 * l)};
        g.fillPolygon(xp, yp, xp.length);
    }

	@Override
	public boolean contains(Point point) {return false;}

	/*@Override
	public void move() {
		if (!super.moving) return;
	}

	@Override
	public void bounce() {

		
	}*/

	@Override
	public boolean isOutOfBounds(int width, int height){
		if(x-(l/2) < 0 || x+(l/2) > width) return true;
		else if(y < 0 || y+l > height) return true;
		else return false;
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
		return "EQ Triangle";
	}
}