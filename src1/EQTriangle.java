import java.awt.*;

public class EQTriangle extends Shape {
    int l;

    public EQTriangle(int x, int y, int l, Color c) {
        super(x, y, c);
        this.l = l;

    }

    public double area() {
        return Math.sqrt(3) * l * l / 4;
    }

    public double perim() {
        return l * 3;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        int[] xp = new int[]{x, x - l / 2, x + l / 2};
        int[] yp = new int[]{y - (int) (Math.sqrt(3) / 3 * l), y + (int) (Math.sqrt(3) / 6 * l), y + (int) (Math.sqrt(3) / 6 * l)};
        g.fillPolygon(xp, yp, xp.length);
    }

	@Override
	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}
}