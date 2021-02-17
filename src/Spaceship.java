import java.awt.*;
public class Spaceship {
	//change this to decide where the spaceship is orginally
	private int positionX = 20;
	private int positionY = 20;
	public void draw(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(positionX, positionY, 20, 20);
		if(positionX > 400) positionX = 400;
		if(positionY > 400) positionY = 400;
		if(positionX < 0 ) positionX = 0;
		if(positionY < 0 ) positionY = 0;
	}
	public void left()
	{
		positionX -= 20;
	}
	public void right()
	{
		positionX += 20;
	}
}
