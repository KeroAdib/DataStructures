package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
	public static void main(String[] args)
	{
		Maze x = new Maze(10, 10, true);
		x.makePaths(0, 0);
		x.resetVisits();
		x.findPath(0, 0);
		x.testMaze();
	}
}
