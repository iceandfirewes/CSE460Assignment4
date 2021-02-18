import java.awt.Color;
import java.awt.Graphics;

public class Alien implements Drawable {
	private int positionX;
	private int positionY;
	
	public Alien(int X, int Y) {
		positionX = X;
		positionY = Y;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawRect(positionX, positionY, 20, 20);
	}
	
	public void moveLeft() {
		positionX -= 10;
	}
	
	public void moveRight() {
		positionX += 10;
	}
	
	public void moveDown() {
		positionY += 10;
	}
}
