
import java.awt.*;

public class Circle extends Shape {

    public int r;

    public Circle(int x,int y, int r,Color c) {
        super(x, y, c);
        this.r = r;
    }

    public void draw(Graphics g){
        g.setColor(c);
        g.fillOval(x - r, y - r, r * 2, r * 2);
        
    }

	@Override
	public boolean contains(Point point) {
		/*
		if (point.x <= x+r && point.x >= x-r && point.y <= y+r && point.y >= y-r)
			if (((point.x-x)^2) +((point.y-y)^2) < (r^2))
				return true;
		*/
		int dx = Math.abs(point.x-x);
		int dy = Math.abs(point.y-y);
		int R = r;
		if ((dx^2) + (dy^2) <= (R^2))
			    return true;
	
	return false;
	}
}