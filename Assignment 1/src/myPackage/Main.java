package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
	static int ordernumber = 0;

	public static void main(String[] args) throws FileNotFoundException
	{

		File file = new File("test.txt");
		if (file != null)
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			try
			{
				while (br.ready())
				{
					String orderline = br.readLine();
					parseLine(orderline);
				}
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		testMyStack();
		testBurger();
	}

	private static void parseLine(String line)
	{

		boolean baron = false;
		boolean changePatty = false;
		int numberOfPatties = 1;
		String pattyType = "Beef";
		Burger burger;
		String[] orderLine = line.split(" ");
		for (int i = 0; i < orderLine.length - 1; i++)
		{
			if (orderLine[i].equalsIgnoreCase("Baron"))
			{
				baron = true;
			}
			else if (orderLine[i].equalsIgnoreCase("Chicken"))
			{
				changePatty = true;
				pattyType = "Chicken";
			}
			else if (orderLine[i].equalsIgnoreCase("Veggie"))
			{
				changePatty = true;
				pattyType = "Veggie";
			}
			else if (orderLine[i].equalsIgnoreCase("Double"))
			{
				numberOfPatties = 2;
			}
			else if (orderLine[i].equalsIgnoreCase("Triple"))
			{
				numberOfPatties = 3;
			}
		}

		burger = new Burger(baron);
		for (int i = 0; i < orderLine.length; i++)
		{
			if (baron)
			{
				if (orderLine[i].equalsIgnoreCase("no"))
				{
					i++;
					while (i < orderLine.length)
					{
						if (orderLine[i].equalsIgnoreCase("but"))
						{
							i++;
							while (i < orderLine.length)
							{
								burger.addIngredient(orderLine[i]);
								i++;
							}
							break;
						}
						if (orderLine[i].equalsIgnoreCase("Cheese") || orderLine[i].equalsIgnoreCase("Sauce")
								|| orderLine[i].equalsIgnoreCase("Veggies"))
						{
							burger.removeCategory(orderLine[i]);
						}
						else
						{
							burger.removeIngredient(orderLine[i]);
						}
						i++;
					}
				}
				else if (orderLine[i].equalsIgnoreCase("but"))
				{
					i++;
					while (i < orderLine.length)
					{
						burger.addIngredient(orderLine[i]);
						i++;
					}
				}
			}
			else
			{
				if (orderLine[i].equalsIgnoreCase("with"))
				{
					while (i < orderLine.length)
					{
						if (orderLine[i].equalsIgnoreCase("but"))
						{
							i++;
							if (orderLine[i].equalsIgnoreCase("no"))
								i++;
							while (i < orderLine.length)
							{
								burger.removeIngredient(orderLine[i]);
								i++;
							}
							break;
						}
						if (orderLine[i].equalsIgnoreCase("Cheese") || orderLine[i].equalsIgnoreCase("Sauce")
								|| orderLine[i].equalsIgnoreCase("Veggies"))
						{
							burger.addCategory(orderLine[i]);
						}
						else
						{
							burger.addIngredient(orderLine[i]);
						}
						i++;
					}
				}
				else if (orderLine[i].equalsIgnoreCase("but"))
				{
					i++;
					if (orderLine[i].equalsIgnoreCase("no"))
						i++;
					while (i < orderLine.length)
					{
						burger.removeIngredient(orderLine[i]);
						i++;
					}
				}
			}
		}

		if (numberOfPatties == 2)
		{
			burger.addPatty();
		}
		else if (numberOfPatties == 3)
		{
			burger.addPatty();
			burger.addPatty();
		}

		if (changePatty)
		{
			burger.changePatties(pattyType);
		}

		System.out.println("Order #" + ordernumber + "  " + line);
		System.out.println(burger.toString());
		ordernumber++;
	}

	private static void testMyStack()
	{
		MyStack<String> x = new MyStack<String>();
		x.push("Test");
		x.push("a");
		x.push("is");
		x.push("This");
		System.out.println(x.toString());
		System.out.println("Size:  " + x.size());
		System.out.println("Peek:  " + x.peek());
		if (!x.isEmpty())
			System.out.println("NOT EMPTY");
		System.out.println(x.pop());
		System.out.println(x.pop());
		System.out.println(x.pop());
		System.out.println(x.pop());
		if (x.isEmpty())
			System.out.println("EMPTY");
	}

	private static void testBurger()
	{
		Burger x = new Burger(true);
		x.toString();
		x.removeCategory("Sauce");
		x.removeIngredient("Lettuce");
		x.addPatty();
		x.changePatties("Chicken");
		x.toString();
		System.out.println(x.toString());
		x = new Burger(false);
		x.addCategory("Sauce");
		x.addIngredient("Tomato");
		x.removeIngredient("Ketchup");
		x.addPatty();
		x.addPatty();
		x.changePatties("Veggie");
		System.out.println(x.toString());
	}

}
