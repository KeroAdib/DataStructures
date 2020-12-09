package myPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Maze
{
	int width;
	int height;
	int startX = 0;
	int startY = 0;
	int currentX = 0;
	int currentY = 0;
	ArrayList<Integer> x = new ArrayList<Integer>();
	ArrayList<Integer> y = new ArrayList<Integer>();
	HashMap<Integer, ArrayList<Integer>> z = new HashMap<Integer, ArrayList<Integer>> ();
	int finishX;
	int finishY;
	boolean debug;
	boolean solved = false;
	private Node[][] nodes;
	Random r = new Random();

	static class Node
	{
		private Node north;
		private Node south;
		private Node east;
		private Node west;
		private boolean northWall;
		private boolean southWall;
		private boolean eastWall;
		private boolean westWall;
		private boolean visited;

		Node()
		{
			north = null;
			south = null;
			east = null;
			west = null;
			northWall = true;
			southWall = true;
			eastWall = true;
			westWall = true;
			visited = false;

		}

		public boolean isNull()
		{
			if (this.equals(null))
			{
				return true;
			}
			return false;
		}
	}

	public Maze(int width, int height, boolean debug)
	{
		this.width = width;
		this.height = height;
		nodes = new Node[width][height];
		this.debug = debug;
		finishX = width - 1;
		finishY = height - 1;
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				nodes[i][j] = new Node();
			}
		}
		connectWalls(width, height);
		connectCorners(width, height);
		connectMiddle(width, height);
		nodes[0][0].northWall = false;
		nodes[nodes.length - 1][nodes[0].length - 1].southWall = false;
	}

	private void connectWalls(int width, int height)
	{
		for (int i = 1; i < width - 1; i++)
		{
			nodes[i][0].west = nodes[i - 1][0];
			nodes[i][0].east = nodes[i + 1][0];
			nodes[i][0].south = nodes[i][1];
			nodes[i][0].north = new Node();
			nodes[i][0].north.visited = true;
			nodes[i][height - 1].west = nodes[i - 1][height - 1];
			nodes[i][height - 1].east = nodes[i + 1][height - 1];
			nodes[i][height - 1].north = nodes[i][height - 2];
			nodes[i][height - 1].south = new Node();
			nodes[i][height - 1].south.visited = true;
		}

		for (int j = 1; j < nodes[0].length - 1; j++)
		{
			nodes[0][j].north = nodes[0][j - 1];
			nodes[0][j].east = nodes[1][j];
			nodes[0][j].south = nodes[0][j + 1];
			nodes[0][j].west = new Node();
			nodes[0][j].west.visited = true;
			nodes[width - 1][j].north = nodes[width - 1][j - 1];
			nodes[width - 1][j].west = nodes[width - 2][j];
			nodes[width - 1][j].south = nodes[width - 1][j + 1];
			nodes[width - 1][j].east = new Node();
			nodes[width - 1][j].east.visited = true;
		}
	}

	private void connectCorners(int width, int height)
	{
		nodes[0][0].east = nodes[1][0];
		nodes[0][0].south = nodes[0][1];
		nodes[0][0].west = new Node();
		nodes[0][0].west.visited = true;
		nodes[0][0].north = new Node();
		nodes[0][0].north.visited = true;
		nodes[0][height - 1].east = nodes[1][height - 1];
		nodes[0][height - 1].north = nodes[0][height - 2];
		nodes[0][height - 1].west = new Node();
		nodes[0][height - 1].west.visited = true;
		nodes[0][height - 1].south = new Node();
		nodes[0][height - 1].south.visited = true;
		nodes[width - 1][0].west = nodes[width - 2][0];
		nodes[width - 1][0].south = nodes[width - 1][1];
		nodes[width - 1][0].north = new Node();
		nodes[width - 1][0].north.visited = true;
		nodes[width - 1][0].east = new Node();
		nodes[width - 1][0].east.visited = true;
		nodes[width - 1][height - 1].west = nodes[width - 2][height - 1];
		nodes[width - 1][height - 1].north = nodes[width - 1][height - 2];
		nodes[width - 1][height - 1].east = new Node();
		nodes[width - 1][height - 1].east.visited = true;
		nodes[width - 1][height - 1].south = new Node();
		nodes[width - 1][height - 1].south.visited = true;
	}

	private void connectMiddle(int width, int height)
	{
		for (int i = 1; i < width - 1; i++)
		{
			for (int j = 1; j < height - 1; j++)
			{
				nodes[i][j].north = nodes[i][j - 1];
				nodes[i][j].east = nodes[i + 1][j];
				nodes[i][j].west = nodes[i - 1][j];
				nodes[i][j].south = nodes[i][j + 1];
			}
		}
	}

	public void makePaths(int currentX, int currentY)
	{
		nodes[currentX][currentY].visited = true;
		this.currentX = currentX;
		this.currentY = currentY;
		if (debug)
		{
			display();
		}
		boolean random = true;
		while (random)
		{
			int x = r.nextInt(4);
			if (x == 0 && !nodes[currentX][currentY].north.visited)
			{

				nodes[currentX][currentY].northWall = false;
				nodes[currentX][currentY - 1].southWall = false;
				makePaths(currentX, currentY - 1);

			}
			else if (x == 1 && !nodes[currentX][currentY].east.visited)
			{

				nodes[currentX][currentY].eastWall = false;
				nodes[currentX + 1][currentY].westWall = false;
				makePaths(currentX + 1, currentY);
			}
			else if (x == 2 && !nodes[currentX][currentY].south.visited)
			{

				nodes[currentX][currentY].southWall = false;
				nodes[currentX][currentY + 1].northWall = false;
				makePaths(currentX, currentY + 1);
			}
			else if (x == 3 && !nodes[currentX][currentY].west.visited)
			{

				nodes[currentX][currentY].westWall = false;
				nodes[currentX - 1][currentY].eastWall = false;
				makePaths(currentX - 1, currentY);
			}
			else if ((nodes[currentX][currentY].north.visited) && (nodes[currentX][currentY].south.visited)
					&& (nodes[currentX][currentY].west.visited) && (nodes[currentX][currentY].east.visited))
			{
				random = false;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void findPath(int currentX, int currentY)
	{
		if (!z.containsKey(currentY))
		{
			z.put(currentY, new ArrayList<Integer>());
		}
		z.get(currentY).add(currentX);
		nodes[currentX][currentY].visited = true;
		if (currentX == finishX && currentY == finishY)
		{
			solved = true;
			return;
		}
		if (!nodes[currentX][currentY].northWall && !solved && !nodes[currentX][currentY].north.visited)
		{

			findPath(currentX, currentY - 1);
		}
		if (!nodes[currentX][currentY].eastWall && !solved && !nodes[currentX][currentY].east.visited)
		{

			findPath(currentX + 1, currentY);
		}
		if (!nodes[currentX][currentY].southWall && !solved && !nodes[currentX][currentY].south.visited)
		{
			findPath(currentX, currentY + 1);
		}
		if (!nodes[currentX][currentY].westWall && !solved && !nodes[currentX][currentY].west.visited)
		{
			findPath(currentX - 1, currentY);
		}
		if (!z.get(currentY).isEmpty() && !solved)
		{
			z.get(currentY).remove(z.get(currentY).size() - 1);
		}

	}

	public void resetVisits()
	{
		for (int i = 0; i < nodes.length; i++)
		{
			for (int j = 0; j < nodes[i].length; j++)
			{
				nodes[i][j].visited = false;
			}
		}
	}

	public void display()
	{
		for (int i = 0; i < nodes[0].length; i++)
		{
			for (int j = 0; j < nodes.length; j++)
			{
				if (nodes[j][i].northWall == true)
				{
					System.out.print("X X ");
				}
				else
				{
					System.out.print("X   ");
				}
			}
			System.out.println("X");
			for (int j = 0; j < nodes.length; j++)
			{
				if (nodes[j][i].westWall == true)
				{
					System.out.print("X ");
				}
				else
				{
					System.out.print("  ");
				}
				if (j == currentX && i == currentY)
				{
					System.out.print("V ");
				}
				else
				{
					System.out.print("  ");
				}
			}
			System.out.println("X");
		}
		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i][nodes[i].length - 1].southWall == true)
			{
				System.out.print("X X ");
			}
			else
			{
				System.out.print("X   ");
			}
		}
		System.out.println("X");
		System.out.println();
	}

	@SuppressWarnings("unchecked")
	public void testMaze()
	{
		
		
		for (int i = 0 ; i < z.size(); i++)
		{
			Collections.sort((ArrayList<Integer>) z.values().toArray()[i]);
		}
		for (int y = 0; y < nodes[0].length; y++)
		{
			for (int x = 0; x < nodes.length; x++)
			{
				if (nodes[x][y].northWall == true)
				{
					System.out.print("X X ");
				}
				else
				{
					System.out.print("X   ");
				}
			}
			System.out.println("X");
			for (int x = 0; x < nodes.length; x++)
			{
				if (nodes[x][y].westWall == true)
				{
					System.out.print("X ");
				}
				else
				{
					System.out.print("  ");
				}
				if (z.get(y).contains(x))
				{
					System.out.print("+ ");
				}
				else
				{
					System.out.print("  ");
				}
			}
			System.out.println("X");
		}
		for (int y = 0; y < nodes.length; y++)
		{
			if (nodes[y][nodes[y].length - 1].southWall == true)
			{
				System.out.print("X X ");
			}
			else
			{
				System.out.print("X   ");
			}
		}
		System.out.println("X");
		System.out.println();
	}
}