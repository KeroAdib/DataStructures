package myPackage;

public class Burger
{
	private MyStack<String> burger;
	private int pattyCount;
	private String pattyType;

	public Burger(boolean theWorks)
	{
		burger = new MyStack<String>();
		pattyType = "Beef";
		if (theWorks)
		{
			burger.push("Bun");
			burger.push("Ketchup");
			burger.push("Mustard");
			burger.push("Mushrooms");
			burger.push("Beef");
			burger.push("Cheddar");
			burger.push("Mozzarella");
			burger.push("Pepperjack");
			burger.push("Onions");
			burger.push("Tomato");
			burger.push("Lettuce");
			burger.push("Baron-Sauce");
			burger.push("Mayonnaise");
			burger.push("Bun");
			burger.push("Pickle");
		}
		else
		{
			burger.push("Bun");
			burger.push("Beef");
			burger.push("Bun");
		}
		pattyCount = 1;
	}

	public void changePatties(String pattyType)
	{
		MyStack<String> temp = new MyStack<String>();
		MyStack<String> x = burger;
		while (x.size() != 0)
		{
			if (!compareToPatties(x.peek()))
			{
				temp.push(x.pop());
			}
			else
			{
				x.pop();
				temp.push(pattyType);
			}
		}

		while (temp.size() != 0)
		{
			burger.push(temp.pop());
		}
	}

	public void addPatty()
	{
		if (pattyCount == 3)
		{
			return;
		}
		boolean firstBun = false;
		MyStack<String> temp = new MyStack<String>();
		MyStack<String> x = burger;
		boolean added = false;
		if (pattyCount == 0)
		{
			while (x.size() != 0 && !added)
			{
				if (firstBun == false)
				{
					if (x.peek().equals("Bun"))
					{
						firstBun = true;
					}
				}
				else
				{
					if (x.peek().equals("Bun") || compareToBottomSauce(x.peek()))
					{
						temp.push(pattyType);
						added = true;
					}
				}
				temp.push(x.pop());
			}
		}
		else
		{
			while (x.size() != 0 && !added)
			{
				if (x.peek().equals(pattyType) || compareToCheese(x.peek()))
				{
					temp.push(pattyType);
					added = true;
				}
				temp.push(x.pop());
			}
		}
		while (temp.size() != 0)
		{
			x.push(temp.pop());
		}
	}

	public void addCategory(String type)
	{
		if (type.equals("Cheese"))
		{
			addIngredient("Cheddar");
			addIngredient("Mozzarella");
			addIngredient("Pepperjack");
		}
		else if (type.equals("Veggies"))
		{
			addIngredient("Lettuce");
			addIngredient("Tomato");
			addIngredient("Onions");
			addIngredient("Pickle");
			addIngredient("Mushrooms");
		}
		else if (type.equals("Sauce"))
		{
			addIngredient("Ketchup");
			addIngredient("Mustard");
			addIngredient("Mayonnaise");
			addIngredient("Baron-Sauce");
		}
	}

	public void removeCategory(String type)
	{
		if (type.equals("Cheese"))
		{
			removeIngredient("Cheddar");
			removeIngredient("Mozzarella");
			removeIngredient("Pepperjack");
		}
		else if (type.equals("Veggies"))
		{
			removeIngredient("Lettuce");
			removeIngredient("Tomato");
			removeIngredient("Onions");
			removeIngredient("Pickle");
			removeIngredient("Mushrooms");
		}
		else if (type.equals("Sauce"))
		{
			removeIngredient("Ketchup");
			removeIngredient("Mustard");
			removeIngredient("Mayonnaise");
			removeIngredient("Baron-Sauce");
		}
	}

	public void addIngredient(String type)
	{
		MyStack<String> x = burger;
		MyStack<String> temp = new MyStack<String>();
		boolean added = false;
		int y = pattyCount;
		if (compareToCheese(type))
		{
			while (x.size() != 0 && !added)
			{
				if ((compareToPatties(x.peek()) && y == 1) || (type.equals("Mozzarella") && x.peek().equals("Cheddar"))
						|| (type.equals("Pepperjack") && compareToCheese(x.peek())))
				{
					temp.push(type);
					added = true;
				}
				else if (compareToPatties(x.peek()) && y != 1)
				{
					y--;
				}
				temp.push(x.pop());
			}
		}
		else if (compareToVeggies(type))
		{
			while (x.size() != 0 && !added)
			{
				if ((type.equals("Lettuce") && compareToVeggies(x.peek()))
						|| (type.equals("Tomato") && x.peek().equals("Onions")) || compareToCheese(x.peek())
						|| (compareToPatties(x.peek()) && y == 1))
				{
					temp.push(type);
					added = true;
				}
				else if (compareToPatties(x.peek()) && y != 1)
				{
					y--;
				}
				temp.push(x.pop());
			}
		}
		else if (type.equals("Pickle"))
		{
			while (x.size() != 0 && !added)
			{
				if (x.peek().equals("Bun"))
				{
					temp.push(type);
					added = true;
				}
				temp.push(x.pop());
			}
		}
		else if (type.equals("Mushrooms"))
		{
			while (x.size() != 0 && !added)
			{
				if ((compareToPatties(x.peek()) && y == 1))
				{
					temp.push(x.pop());
					temp.push(type);
					added = true;
				}
				else if (compareToPatties(x.peek()) && y != 1)
				{
					y--;
					temp.push(x.pop());
				}
				else
				{
					temp.push(x.pop());
				}
			}
		}
		else if (compareToTopSauce(type))
		{
			while (x.size() != 0 && !added)
			{
				if (x.peek().equals("Bun"))
				{
					
					temp.push(x.pop());
					if (type.equals("Baron-Sauce") && x.peek().equals("Mayonnaise"))
					{
						temp.push(x.pop());
					}
					temp.push(type);
					added = true;
				}
				else
				{
					temp.push(x.pop());
				}
			}
		}
		else if (compareToBottomSauce(type))
		{
			boolean firstBun = false;
			while (x.size() != 0 && !added)
			{
				if (x.peek().equals("Bun") && firstBun == true || (type.equals("Mustard") && compareToBottomSauce(x.peek())))
				{
					temp.push(type);
					added = true;
				}
				else if (x.peek().equals("Bun") && firstBun == false)
				{
					firstBun = true;
				}
				temp.push(x.pop());
			}
		}
		while (temp.size() != 0)
		{
			x.push(temp.pop());
		}
	}

	public void removeIngredient(String type)
	{
		MyStack<String> x = burger;
		MyStack<String> temp = new MyStack<String>();
		while (x.size() != 0)
		{
			if (!x.peek().equals(type))
			{
				temp.push(x.pop());
			}
			else
			{
				x.pop();
			}
		}
		while (temp.size() != 0)
		{
			x.push(temp.pop());
		}
	}

	public String toString()
	{
		String returnValue = "[";
		MyStack<String> x = burger;
		MyStack<String> temp = new MyStack<String>();
		while (x.size() != 1)
		{
			returnValue += x.peek() + ", ";
			temp.push(x.pop());
		}
		returnValue += x.peek() + "]";
		while (temp.size() != 0)
		{
			x.push(temp.pop());
		}
		return returnValue;
	}

	public boolean compareToPatties(String type)
	{
		if (type.equals("Beef") || type.equals("Chicken") || type.equals("Veggie"))
		{
			return true;
		}
		return false;
	}

	public boolean compareToCheese(String type)
	{
		if (type.equals("Cheddar") || type.equals("Mozzarella") || type.equals("Pepperjack"))
		{
			return true;
		}
		return false;
	}

	public boolean compareToVeggies(String type)
	{
		if (type.equals("Lettuce") || type.equals("Tomato") || type.equals("Onions"))
		{
			return true;
		}
		return false;
	}

	public boolean compareToTopSauce(String type)
	{
		if (type.equals("Mayonnaise") || type.equals("Baron-Sauce"))
		{
			return true;
		}
		return false;
	}

	public boolean compareToBottomSauce(String type)
	{
		if (type.equals("Ketchup") || type.equals("Mustard"))
		{
			return true;
		}
		return false;
	}
}
