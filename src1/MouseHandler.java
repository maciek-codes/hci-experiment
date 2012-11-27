import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.*;

public class MouseHandler implements MouseMotionListener {

	ShapeComponent sc;
		
	public MouseHandler(ShapeComponent sc){
		this.sc=sc;
	}
	
    public void mouseClicked(MouseEvent e) {
    	//System.out.println(e.getX()); 
    	//int R=50;
    	//int dx=(int) (Math.sqrt(e.getX()-550));
    	//int dy=(int) Math.sqrt(e.getY()-150);
		//if (e.getButton()==1 && (dx + dy < R))  	
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(sc.s[8].contains(e.getPoint())){
			//System.out.println("onShape");
			//stop timer
			//put output data
			System.exit(0);
			
		}
		
	}
}
