import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class ShapeComponent extends JPanel  {
    //int w = 500, h=400;  // set size of component
    Shape[] s;         // array of shapes to paint
    //Dimension screenSize; 
    int w;
    int h;
    
    //int w = getWidth();
    //int h = getHeight();
    
    
    int velX=4; 
    int velY=4;

    
    public ShapeComponent(Shape[] s, int width, int height) { //Constructor
        this.s = s;
        w = width;
        h = height;
       /*screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       w=screenSize.width;
       h=screenSize.height;*/
        System.out.println("w,h: " + w + "," + h);
    }
    public void paintComponent(Graphics g) {
        //g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        for (int i=0; i<s.length;i++) {
        	s[i].draw(g);
            
        	if (i!=8)
            {
        		if (i==0 ){
            		s[i].y += velY;
            		s[i].x += velX;
            		
            	}
        		
        		if (i==1 ){
            		s[i].y -= velY;
            		s[i].x -= velX;
            	}
        		
        		if (i==2){
        			s[i].x += velX;
        		}
        		
        		if (i==3){
        			s[i].x += 2*velX;
        			
        		}
        		if (i==4 ){
        			s[i].x += 3*velX;
        		}
        		if (i==5 ){
        			s[i].x -= velX;
        			s[i].y += velY;
        		}
        		if (i==6 ){
        			s[i].y += velY;
        			//s[i].x += velY;
        			
        		}
        		if (i==7 ){
        			s[i].y += velY;
        			
        			
        		}
        	
        		
            }   
        	
    		if (i==5)
    			System.out.println("x: "+s[i].x+" y: "+s[i].y);
        	
        	if (s[i].x > w || s[i].x < 0){
        		//System.out.println(w);
        		if (i==5)
        			System.out.println("bounce x: "+s[i].x+" y: "+s[i].y);
        		velX = -velX;
             	
        	}
        	if (s[i].y > h-100 || s[i].y < 0){
        		if (i==5)
        			System.out.println("bounce x: "+s[i].x+" y: "+s[i].y);
        		velY = -velY;
             	//System.out.println("h " + h);
        	}
        	repaint();
      
        }
   }

        
   // public Dimension getPreferredSize() {
    //    return new Dimension(w, h);
    //}


    


}
