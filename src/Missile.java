import java.awt.Color;
import java.awt.Graphics;

public class Missile implements Drawable, Sprite {
	private int positionX;
	private int positionY;
	
	public Missile(int X, int Y) {
		positionX = X;
		positionY = Y;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(positionX, positionY, 5, 20);
	}
	
	public void moveUp() {
		positionY -= 20;
	}
	
	public int getX() {
		return positionX;
	}
	
	public int getY() {
		return positionY;
	}

	public int getWidth() {
		return 10;
	}
}
