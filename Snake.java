package snakeGame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener
{

	public static Snake snake;

	public JFrame jframe;

	public RenderPanel renderPanel;

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10, TIMER_VAL = 40, WIDTH = 190, HEIGHT = 103;
	
	public Timer timer = new Timer(TIMER_VAL, this);

	public ArrayList<Point> snakeBody = new ArrayList<Point>();

	public int ticks = 0, direction = DOWN, score, tailLength = 5, time, difficulty = 2;

	public Point head, apple, poison;

	public Random random;

	public boolean gameOver = false, paused;
	public int lives  = 3,oldScore = 0;

	public Dimension dim;

	public Snake()
	{
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(dim.width, dim.height);
		jframe.setResizable(false);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}

	public void startGame()
	{
		gameOver = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 5;
		ticks = 0;
		difficulty = 2;
		direction = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeBody.clear();
		apple = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
		poison = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
		timer.start();
	}

	public void actionPerformed(ActionEvent arg0)
	{
		renderPanel.repaint();
		ticks++;

		if (ticks % difficulty == 0 && head != null && !gameOver && !paused)
		{
			time++;

			snakeBody.add(new Point(head.x, head.y));

			if (direction == UP)
			{
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
				{
					head = new Point(head.x, head.y - 1);
				}
				else
				{
					lives--;
					gameOver = true;
				}
			}

			if (direction == DOWN)
			{
				if (head.y + 1 <= HEIGHT && noTailAt(head.x, head.y + 1))
				{
					head = new Point(head.x, head.y + 1);
				}
				else
				{
					lives--;
					gameOver = true;
				}
			}

			if (direction == LEFT)
			{
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
				{
					head = new Point(head.x - 1, head.y);
				}
				else
				{
					lives--;
					gameOver = true;
				}
			}

			if (direction == RIGHT)
			{
				if (head.x + 1 <= WIDTH && noTailAt(head.x + 1, head.y))
				{
					head = new Point(head.x + 1, head.y);
				}
				else
				{
					lives--;
					gameOver = true;
				}
			}

			if (snakeBody.size() > tailLength)
			{
				snakeBody.remove(0);

			}

			if (apple != null)
			{
				if (head.equals(apple))
				{
					score += 10;
					oldScore = score;
					tailLength++;
					apple.setLocation(random.nextInt(WIDTH), random.nextInt(HEIGHT));
				}
			}
			if (poison != null)
			{
				if (head.equals(poison))
				{
					score -= 10;
					oldScore = score;
					tailLength--;
					snakeBody.remove(0);
					if(tailLength<=0)
					{
						lives--;
						gameOver = true;
					}
					poison.setLocation(random.nextInt(WIDTH), random.nextInt(HEIGHT));
				}
			}
		}
	}

	public boolean noTailAt(int x, int y)
	{
		for (Point point : snakeBody)
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		snake = new Snake();
	}

	public void keyPressed(KeyEvent e)
	{
		int k = e.getKeyCode();

		if ((k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) && direction != RIGHT)
		{
			direction = LEFT;
		}

		if ((k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) && direction != LEFT)
		{
			direction = RIGHT;
		}

		if ((k == KeyEvent.VK_W || k == KeyEvent.VK_UP) && direction != DOWN)
		{
			direction = UP;
		}

		if ((k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) && direction != UP)
		{
			direction = DOWN;
		}

		if (k == KeyEvent.VK_SPACE)
		{
			if (gameOver)
			{
				startGame();
				if(lives >= 0)
					score = oldScore;
				else
				{
					oldScore = 0;
					score = 0;
					lives = 3;
				}
			}
			else
			{
				paused = !paused;
			}
		}
		
		if (k == KeyEvent.VK_I)
		{
			if(difficulty < (TIMER_VAL/4))
				difficulty++;
		}
		
		if (k == KeyEvent.VK_I)
		{
			if(difficulty > 1)
				difficulty--;
		}
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{
	}

}
