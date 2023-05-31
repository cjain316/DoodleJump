import java.awt.Color;
import java.awt.Font;
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
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
	private Player player = new Player();
	private int prevX;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	private Image Sprite;
	private AffineTransform tx;
	private Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
	
	private boolean mouseDown;
	
	private int frames;
	private int prevScore = calculateScore();
	
	private String menu = "MAIN";
	
	
	int timer = 1000;
	
	private Point point;
	
	public int playerPos;
	public int ScreenPos;
	
	String[] skins = {"Resources\\jumperFacingRight.png", "Resources\\bobbletteRight.png",
			"Resources\\genshinBobblerRight.png",
			"Resources\\ninjaRight.png","Resources\\puritanRight.png",
			"Resources\\fortniteRight.png","Resources\\gokuRight.png","Resources\\rossRight.png",
			"Resources\\jonseyRight.png", "Resources\\danielRight.png"};
	
	String[] skinsCounter = {"Resources\\jumperFacingLeft.png", "Resources\\bobbletteLeft.png",
			"Resources\\genshinBobblerLeft.png",
			"Resources\\ninjaLeft.png","Resources\\puritanLeft.png",
			"Resources\\fortniteLeft.png","Resources\\gokuLeft.png","Resources\\rossLeft.png",
			"Resources\\jonseyLeft.png", "Resources\\danielLeft.png"};
	
	boolean[] skinsUnlocked = {true, false, false, false, false, false, false, false, false, false};
	int[] skinPrices = {0, 10, 30, 50, 100, 150, 200, 300, 400, 500};
	int freeCash = 0;
	
	Rectangle[] buttons;
	
	private String skinRight = "Resources\\jumperFacingRight.png";
	private String skinLeft = "Resources\\jumperFacingLeft.png";
			
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread musico = new Thread(new AudioPlayer(".\\src\\Sounds\\Musico.wav", true));
		musico.start();
		new Main();
		
	}
	
	public void parseButtons() {
		int x = 50;
		int y = 80;
		buttons = new Rectangle[skins.length];
		for (int i = 0;i < skins.length; i++) {
			buttons[i] = new Rectangle(x,y,50,50);
			x += 110;
			if (i+1 % 5 == 0 || i == 4) {
				y += 110;
				x = 50;
			}
		}
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        updatePlatforms();
        
        if(timer == 0) {
        	writeToFile();
        	timer = 1000;
        }
        timer--;
        
 
        frames++;
        drawBackground(g);
        mouseLoop(g);
        if (menu.equals("MAIN")) {menuLoop(g);}
        if (menu.equals("GAME")) {gameLoop(g);}
        if (menu.equals("DEAD")) {deadLoop(g);}
        if (menu.equals("SKIN")) {skinLoop(g);}
        
		
		drawOverlay(g);
		
		if (menu.equals("GAME") || menu.equals("DEAD")) scoreLoop(g);
	}
	
	
	
	//************************
	//*      Menu Loops      *
	//************************
	public void mouseLoop(Graphics g) { //Always active
		PointerInfo p = MouseInfo.getPointerInfo();
        point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        
        point.setLocation(point.getX()-35,point.getY()-30);
        
	}
	
	public void menuLoop(Graphics g) { // Main menu code 
		Graphics2D g2 = (Graphics2D) g;
		
		Rectangle mouse = new Rectangle((int)point.getX(),(int)point.getY(),2,2);
		Rectangle button = new Rectangle(100,450,390,140);
		Rectangle skinButton = new Rectangle(100,700,390,140);
		
		
		tx = AffineTransform.getTranslateInstance(-10, 100);
        Sprite = getImage("Resources\\menuText.png");
		g2.drawImage(Sprite, tx, null);
		
		
		tx = AffineTransform.getTranslateInstance(100, 450);
        Sprite = getImage("Resources\\menuButton.png");
		g2.drawImage(Sprite, tx, null);
		
		tx = AffineTransform.getTranslateInstance(100, 700);
        Sprite = getImage("Resources\\skinsButton.png");
		g2.drawImage(Sprite, tx, null);
		
		if (mouse.intersects(button) && mouseDown) {
			mouseDown = false;
			menu = "GAME";
			platforms.add(new Platform(-10,300,false,false,false,false));
			reset();
			for (int i = 0; i < 10; i++) {
				generatePlatforms();
			}
		}
		
		if (mouse.intersects(skinButton) && mouseDown) {
			mouseDown = false;
			menu = "SKIN";
		}
		
	}
	
	public void deadLoop(Graphics g) { // Main menu code 
		Graphics2D g2 = (Graphics2D) g;
		
		Rectangle mouse = new Rectangle((int)point.getX(),(int)point.getY(),2,2);
		Rectangle button = new Rectangle(100,600,390,140);
		
		
		tx = AffineTransform.getTranslateInstance(100, 100);
        Sprite = getImage("Resources\\diedMenuText.png");
		g2.drawImage(Sprite, tx, null);
		
		tx = AffineTransform.getTranslateInstance(100, 350);
        Sprite = getImage("Resources\\diedMenuScore.png");
		g2.drawImage(Sprite, tx, null);
		
		font = new Font(Font.DIALOG_INPUT, Font.BOLD, 72);
		g.setFont(font);
        
		g.setColor(new Color(255,255,255));
		int a = calculateScore();
		g.drawString(a+"", 100,500);
		prevScore = a;
		
		tx = AffineTransform.getTranslateInstance(100, 600);
        Sprite = getImage("Resources\\diedMenuButton.png");
		g2.drawImage(Sprite, tx, null);
		
		if (mouse.intersects(button) && mouseDown) {
			mouseDown = false;
			menu = "MAIN";
		}
		
	}
	
	public void gameLoop(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		prevX = player.getX();
        player.setVy(player.getVy()-1);
        player.update(point);
        
        if (player.getX() > prevX) player.setFacing("Right");
        if (player.getX() < prevX) player.setFacing("Left");
        
        tx = AffineTransform.getTranslateInstance(player.getX(), 600 + player.maxHeight - player.height);
        if (player.getFacing().equals("Left")) {Sprite = getImage(skinLeft);}
        else {Sprite = getImage(skinRight);}
    	g2.drawImage(Sprite, tx, null);
    	
    	if (player.getJetpack()) {
    
            if (player.getFacing().equals("Left")) {
            	Sprite = getImage("Resources\\jetpack_Using_Left.gif");
            	tx = AffineTransform.getTranslateInstance(player.getX()+20, 600 + 10 + player.maxHeight - player.height);
            }
            else {
            	Sprite = getImage("Resources\\jetpack_Using_Right.gif");
            	tx = AffineTransform.getTranslateInstance(player.getX()-8, 600 + 10 + player.maxHeight - player.height);
            }
        	g2.drawImage(Sprite, tx, null);
    	}
    	
    	if (player.getProphat()) {
    	    
            if (player.getFacing().equals("Left")) {
            	Sprite = getImage("Resources\\prophatFlying.gif");
            	tx = AffineTransform.getTranslateInstance(player.getX()+23, 600 - 10 + player.maxHeight - player.height);
            }
            else {
            	Sprite = getImage("Resources\\prophatFlying.gif");
            	tx = AffineTransform.getTranslateInstance(player.getX() + 8, 600-10 + player.maxHeight - player.height);
            }
        	g2.drawImage(Sprite, tx, null);
    	}
        
        for (int i = 0; i < platforms.size();i++) { //platforms
			platforms.get(i).paint(g,false,player);
			
			if (colliding(platforms.get(i),player) && player.getVy() < 0) {
				if (platforms.get(i).getSpring()) {
					springFunction();
				} if (platforms.get(i).getTrampoline()){
					trampolineFunction();
				} if (platforms.get(i).getJetpack()) {
					jetpackFunction(platforms.get(i));
				} if (platforms.get(i).getProphat()) {
					prophatFunction(platforms.get(i));
				} if (!platforms.get(i).hasAttribute()) {
					player.setVy(25);
				}
			}
		}
        
        checkDead();
        
	}
	
	public void scoreLoop(Graphics g) {
		font = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
		g.setFont(font);
        
        if (frames > 1000) frames = 0;
		g.setColor(new Color(255,255,255));
		int a = calculateScore();
		g.drawString(a+"", 10,30);
		prevScore = a;
	}
	
	public void skinLoop(Graphics g) { // Main menu code 
		boolean showClickBoxes = false;
		
		Graphics2D g2 = (Graphics2D) g;
		
		Rectangle mouse = new Rectangle((int)point.getX()+28,(int)point.getY(),2,2);
		Rectangle menuButton = new Rectangle(475,900,100,50);
		
		tx = AffineTransform.getTranslateInstance(475, 900);
        Sprite = getImage("Resources\\menuButtonSkin.png");
		g2.drawImage(Sprite, tx, null);
		
		if (mouse.intersects(menuButton) && mouseDown) {
			mouseDown = false;
			menu = "MAIN";
		}
		
		for (int i = 0; i < skins.length;i++) {
			tx = AffineTransform.getTranslateInstance(buttons[i].getX(), buttons[i].getY());
	        Sprite = getImage(skins[i]);
			g2.drawImage(Sprite, tx, null);
			if(!skinsUnlocked[i]) {
				tx = AffineTransform.getTranslateInstance(buttons[i].getX()-5, buttons[i].getY());
				Sprite = getImage("Resources\\lock.png");
				g2.drawImage(Sprite, tx, null);
				g2.setColor(Color.black);
				g2.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));
				g2.drawString("" + skinPrices[i], (int)buttons[i].getX()+15, (int)buttons[i].getY()+80);
			}
		}
		
		for (int i = 0; i < buttons.length;i++) {
			if (mouse.intersects(buttons[i]) && mouseDown) {
				mouseDown = false;
				menu = "MAIN";
				skinLeft = skinsCounter[i];
				skinRight = skins[i];
			}
		}
		
		if (showClickBoxes) {
			for (int i = 0; i < buttons.length; i++) {
				g.setColor(Color.BLACK);
				g.drawRect((int)buttons[i].getX(),(int)buttons[i].getY(),(int)buttons[i].getWidth(),(int)buttons[i].getHeight());
				g.drawRect((int)mouse.getX(),(int)mouse.getY(),(int)mouse.getWidth(),(int)mouse.getHeight());
			}
		}
	}
	
	
	//Drawing outlines
	
	public void drawBackground(Graphics g) {
		g.setColor(new Color(209, 197, 33));
		g.fillRect(0,0,2000,2000);
		g.setColor(new Color(13, 104, 189));
		for (int i = 0; i < 200; i++) {
			g.drawLine(0,i*20,2000,i*20);
		}
		g.setColor(new Color(194, 53, 35));
		g.drawLine(75,0,75,2000);
	}
	
	public void drawOverlay(Graphics g) {
		g.setColor(new Color(145, 42, 29));
		g.fillRect(0,0,2000,61);
	}
	
	//End of section
	
	
	//Helper methods
	public boolean colliding(Platform b, Player p) {
    	return b.getHitbox().intersects(p.getHitbox());
    }
	
	public int calculateScore() {
		int score = player.getY()/10; 
		
		
		if (prevScore < score) return score;
		else return prevScore;
	}
	
	public void reset() {
		player.setY(0);
		player.setVy(25);
		prevScore = 0;
		player.maxHeight = 0;
		player.height = 0;
		while (platforms.size() > 0) {
			platforms.remove(0);
		}
	}
	
	//end of section
	
	
	//**************************
	//*        Controls        *
	//**************************
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Mouse down");
		mouseDown = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Mouse up");
		mouseDown = false;
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
		writeToFile();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//*************************************
	//*************************************
	//*************************************
	
	Timer t;
	String[] JFrameNames = {"Bobble Bounce","Doodle Jumpero"};
	
	
	//*************************************
	//*        Platform Generation        *
	//*************************************
	
	
	public void unloadUnusedPlatformsL() {
		int i = 0;
		while (i < platforms.size()) {
			if (600+platforms.get(i).getY()+player.getY() > 1500) {
				platforms.remove(i);
			} else {
				i++;
			}
		}
	}
	
	public void unloadUnusedPlatformsU() {
		int i = 0;
		while (i < platforms.size()) {
			if (platforms.get(i).getY()*-1-(player.getY()+1000) > 1500) {
				platforms.remove(i);
			} else {
				i++;
			}
		}
	}
	
	public int getHighestPlatform() { // returns the INDEX of the highest platform
		if (platforms.size() == 0) return 0;
		Platform highest = platforms.get(0);
		int index = 0;
		for (int i = 0; i < platforms.size();i++) {
			if (platforms.get(i).getY() < highest.getY()) {
				highest = platforms.get(i);
				index = i;
			}
		}
		return index;
	}
	
	public int getLowestPlatform() {
		Platform lowest = platforms.get(0);
		int index = 0;
		for (int i = 0; i > platforms.size();i++) {
			if (platforms.get(i).getY() < lowest.getY()) {
				lowest = platforms.get(i);
				index = i;
			}
		}
		return index;
	}
	
	public void generatePlatforms() {
		if (platforms.size() == 0) platforms.add(new Platform(-10,300,false,false,false,false));
		int y = player.getY()+600;
		int h = getHighestPlatform();
		//System.out.println("Highest: " + platforms.get(h).getY() + "\nPlayer: " + y);
		if (platforms.get(h).getY() < (y+1000)) {
			platforms.add(new Platform((int)(Math.random()*530),platforms.get(h).getY()-100,
					springGenerate(),trampGenerate(),jetpackGenerate(),prophatGenerate()));
		}
	}
	
	
	//*************************************
	//*************************************
	//*************************************
	public void springFunction() {
		player.setVy(50);
		Thread spring = new Thread(new AudioPlayer(".\\\\src\\\\Sounds\\\\spring.wav", false));
		spring.start();
	}
	
	public void trampolineFunction() {
		player.setVy(75);
		Thread tramp = new Thread(new AudioPlayer(".\\\\src\\\\Sounds\\\\trampoline.wav", false));
		tramp.start();
	}
	
	public void jetpackFunction(Platform p) {
		player.setJetpack(true);
		p.setJetpack(false);
		Thread jetpack = new Thread(new AudioPlayer(".\\\\src\\\\Sounds\\\\jetpack.wav", false));
		jetpack.start();
	}
	
	public void prophatFunction(Platform p) {
		player.setProphat(true);
		p.setProphat(false);
		Thread prophat = new Thread(new AudioPlayer(".\\\\src\\\\Sounds\\\\jetpack.wav", false));
		prophat.start();
	}
	
	
	public void updatePlatforms() {
		if (frames%2==0) {
			generatePlatforms();
			unloadUnusedPlatformsL();
			unloadUnusedPlatformsU();
		}
	}
	
	public void checkDead() {
		if (600 + player.maxHeight - player.height >=1000 && menu.equals("GAME")) {
			menu = "DEAD";
			Thread death = new Thread(new AudioPlayer(".\\\\src\\\\Sounds\\\\Half-Life Death Sound.wav", false));
			death.start();
		}
	}
	
	
	
	
	public Main() {

		readFromFile();
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
        
        player.setY(0);
        parseButtons();
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
    
    public boolean springGenerate() {
    	return Math.random() >= 0.95;
    }
    public boolean trampGenerate() {
    	return Math.random() >= 0.98;
    }
    public boolean jetpackGenerate() {
    	return Math.random() >= 0.995;
    }
    public boolean prophatGenerate() {
    	return Math.random() >= 0.98;
    }
    
    public boolean readFromFile() {
    	try {
			Scanner scanner = new Scanner(new File("save.sav"));
			freeCash = scanner.nextInt();
			for(int i = 0; i < skins.length; i++) {
				if(scanner.nextInt() == 1) skinsUnlocked[i] = true;
			}
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Can't read from file!");
			return false;
		}
    }
    
    public boolean writeToFile() {
    	try {
			FileWriter fw = new FileWriter("save.sav");
			
			//write the money amount
			fw.write(freeCash + "\n");
			//write the skins unlocked
			for(int i = 0; i < skins.length; i++) {
				if(skinsUnlocked[i]) fw.write("1 ");
				else fw.write("0 ");
			}
			fw.close();
			return true;
		} catch (IOException e) {
			System.out.println("Can't write to file!");
			return false;
		}
    }
    
}

class Background {
	
}
