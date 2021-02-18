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
	private Spaceship spaceship = new Spaceship();
	
	private List<Bunker> bunkers = IntStream.range(0, 3)
		.map(i -> (800 / 2) + (200 * (i - 1)))
		.mapToObj(x -> new Bunker(x, 800 - 200))
		.collect(Collectors.toList());
	
	private List<List<Optional<Alien>>> aliens = IntStream.range(0, 3)
		.mapToObj(row -> IntStream.range(0, 7)
			.mapToObj(col -> Optional.of(
				new Alien(800 / 2 + (100 * (col - 3)), 200 + row * 50))
			)
			.collect(Collectors.toList()))
		.collect(Collectors.toList());
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		spaceship.draw(g);
		
		for (Bunker bunker : bunkers) {
			bunker.draw(g);
		}
		
		this.applyToAliens((alien) -> alien.draw(g));
	}
	
	private void applyToAliens(Consumer<Alien> consumer) {
		for (List<Optional<Alien>> alienRow : aliens) {
			for (Optional<Alien> alien : alienRow) {
				if (alien.isPresent()) {
					consumer.accept(alien.get());
				}
			}
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
		
		
		GameWindow window = this;
		ActionListener gameRunner = new ActionListener() {
			private int ticks = 0;
			public void actionPerformed(ActionEvent evt) {
				if (keys.contains(KeyEvent.VK_D)) {					
					spaceship.right();
				} else if (keys.contains(KeyEvent.VK_A)) {
					spaceship.left();
				}

				if (ticks % 5 == 0) {
					applyToAliens(actionMap.get(ticks % 25));
				}
				
				window.repaint();
				ticks += 1;
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