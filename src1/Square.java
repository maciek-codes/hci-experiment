
import java.awt.*;

public class Square extends Shape {
    int s;
    public Square(int x,int y,int s,Color c) {

        super(x, y, c);
        this.s = s;
    }

    public double area() {
        return s*s;
    }

    public double perim(){
        return s*4;
    }
    public void draw(Graphics g){
        g.setColor(c);
        g.fillRect(x-s/2, y-s/2, s, s);

    }

	@Override
	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

}