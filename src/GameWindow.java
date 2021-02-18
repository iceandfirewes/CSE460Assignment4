import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameWindow extends JPanel implements KeyListener {
	private static final long serialVersionUID = 2327147863654551969L;
	private HashSet<Integer> keys = new HashSet<Integer>();
	private Spaceship spaceship;
	
	private String gamestate = "";
	
	private List<Bunker> bunkers;
	
	private List<List<Optional<Alien>>> aliens;
	
	private Optional<Missile> missile = Optional.empty();
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gamestate.equals("game")) {
			spaceship.draw(g);
			
			for (Bunker bunker : bunkers) {
				bunker.draw(g);
			}
			
			if (missile.isPresent()) {
				missile.get().draw(g);
			}
			
			this.applyToAliens((alien) -> alien.draw(g));
		} else if (gamestate.equals("gameover")) {
			g.setColor(Color.WHITE);
			g.drawString("Game Over", 400, 400);
			g.drawString("Press r to restart", 400, 500);
		}
	}
	
	private void applyToAliens(Consumer<Alien> consumer) {
		for (List<Optional<Alien>> alienRow : aliens) {
			for (Optional<Alien> maybeAlien : alienRow) {
				if (maybeAlien.isPresent()) {
					consumer.accept(maybeAlien.get());
				}
			}
		}
	}
	
	private boolean checkBunkerCollisions() {
		for (List<Optional<Alien>> alienRow : aliens) {
			for (Optional<Alien> maybeAlien : alienRow) {
				if (maybeAlien.isPresent()) {
					Alien alien = maybeAlien.get();
					for (Bunker bunker : bunkers) {
						if (alien.collidesWithSprite(bunker)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private void handleMissleCollision() {
		for (List<Optional<Alien>> alienRow : aliens) {
			int colIndex = 0;
			for (Optional<Alien> maybeAlien : alienRow) {
				if (maybeAlien.isPresent()) {
					Alien alien = maybeAlien.get();
					if (alien.collidesWithSprite(missile.get())) {
						alienRow.set(colIndex, Optional.empty());
						missile = Optional.empty();
						return;
					}
				}
				colIndex += 1;
			}
		}
		
		if (missile.isPresent()) {
			if (missile.get().getY() < 0) {
				missile = Optional.empty();
			}
		}
	}
	
	private void setGameState(String gameState) {
		
		if (gameState.equals("game")) {
			this.bunkers = IntStream.range(0, 3)
				.map(i -> (800 / 2) + (200 * (i - 1)))
				.mapToObj(x -> new Bunker(x, 800 - 200))
				.collect(Collectors.toList());
				
				this.aliens = IntStream.range(0, 3)
					.mapToObj(row -> IntStream.range(0, 7)
						.mapToObj(col -> Optional.of(
							new Alien(800 / 2 + (100 * (col - 3)), 200 + row * 50))
						)
						.collect(Collectors.toList()))
					.collect(Collectors.toList());
				
				this.spaceship = new Spaceship();
		}
		
		
		this.gamestate = gameState;
	}
	
	public void fireMissle() {
		if (missile.isEmpty()) {
			missile = Optional.of(new Missile(spaceship.getX(), spaceship.getY()));
		}
	}
	
	public GameWindow() {
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setBackground(Color.black);
		
		Consumer<Alien> moveLeft = (alien) -> alien.moveLeft();
		Consumer<Alien> moveRight = (alien) -> alien.moveRight();
		Consumer<Alien> moveDown = (alien) -> alien.moveDown();
		
		HashMap<Integer, Consumer<Alien>> actionMap = new HashMap<Integer, Consumer<Alien>>();
		actionMap.put(0, moveLeft);
		actionMap.put(5, moveLeft);
		actionMap.put(10, moveDown);
		actionMap.put(15, moveRight);
		actionMap.put(20, moveRight);
		
		
		this.setGameState("game");
		
		GameWindow window = this;
		ActionListener gameRunner = new ActionListener() {
			private int ticks = 0;
			public void actionPerformed(ActionEvent evt) {
				if (window.gamestate.equals("game")) {
					if (keys.contains(KeyEvent.VK_D)) {					
						spaceship.right();
					} else if (keys.contains(KeyEvent.VK_A)) {
						spaceship.left();
					}
					if (keys.contains(KeyEvent.VK_SPACE)) {
						window.fireMissle();
					}

					if (ticks % 5 == 0) {
						applyToAliens(actionMap.get(ticks % 25));
					}
					
					if (checkBunkerCollisions()) {
						setGameState("gameover");
					}
					
					if (missile.isPresent()) {
						missile.get().moveUp();
						window.handleMissleCollision();
					}
					
					window.repaint();
					ticks += 1;
				} else if (window.gamestate.equals("gameover")) {
					if (keys.contains(KeyEvent.VK_R)) {
						setGameState("game");
					}
				}
			}
		};
		new Timer(30, gameRunner).start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}