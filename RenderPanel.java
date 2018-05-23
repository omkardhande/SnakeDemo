package snakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel
{

	public static final Color LIGHT_BLUE = new Color(39423);
	public static final Color PURPLE = new Color(13255156);

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Snake snake = Snake.snake;

		g.setColor(LIGHT_BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLUE);
		for (Point point : snake.snakeBody)
		{
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		}
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		g.setColor(Color.RED);
		g.fillRect(snake.apple.x * Snake.SCALE, snake.apple.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		g.setColor(PURPLE);
		g.fillRect(snake.poison.x * Snake.SCALE, snake.poison.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		String string = "Score: " + snake.score + ", Length: " + snake.tailLength + ", Lives: " + snake.lives;
		
		g.setColor(Color.yellow);
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);
		
		if(snake.lives>=0)
			string = "Lives:"+snake.lives;
		else
			string = "Game Over!";

		if (snake.gameOver)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}

		string = "Pause!";

		if (snake.paused && !snake.gameOver)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
	}
}
