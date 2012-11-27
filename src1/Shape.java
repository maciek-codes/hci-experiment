
import java.awt.*;

public abstract class Shape {
    int x;
	int y;
    Color c;
    public Shape(int x,int y,Color c){
        this.x=x;
        this.y=y;
        this.c=c;
    }
    
    public abstract void draw(Graphics g);
    
    public void setLocation(int x, int y){
        this.x=x;
        this.y=y;
    }

	public abstract boolean contains(Point point);
		// TODO Auto-generated method stub
	
	

	
}
