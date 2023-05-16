import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
	private Player player = new Player();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main f = new Main();
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        
        point.setLocation(point.getX()-35,point.getY()-30);
        
        if (point.getX() < 525 && point.getX() > 0) player.setX((int)point.getX());
        else if (point.getX() < 0) player.setX(0);
        else player.setX(525);
        
        g.fillRect(player.getX(),player.getY(),60,60);
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	Timer t;
	String[] JFrameNames = {"Doodle Jump","Doodle Jumpero"};
	
	
	public Main() {
    	
        JFrame f = new JFrame(JFrameNames[(int) (Math.random()*JFrameNames.length)]);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,1000);

        f.add(this);
        f.addMouseListener(this);
        f.addKeyListener(this);
        f.setResizable(false);
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        t = new Timer(1, this);
        t.start();
        f.setVisible(true);
        
       
        
    }
	
	private void update() {

    }
    
    protected Image getImage(String path) {

        Image tempImage = null;
        try {
            URL imageURL = Background.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }
}

class Background {
	
}
