package myPackage;

import java.util.Random;

public class Genome implements Comparable<Genome>
{
	public static final String target = "CHRISTOPHER PAUL MARRIOTT";
	public static final char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\'' };
	public StringBuilder name;
	public double mutationRate;
	public static Random random;

	public Genome(double mutationRate)
	{
		this.mutationRate = mutationRate;
		name = new StringBuilder("A");
		random = new Random();
	}

	public Genome(Genome gene)
	{
		this.mutationRate = gene.mutationRate;
		name = new StringBuilder(gene.name.toString());
		random = new Random();
	}

	public void mutate()
	{
		int x = (int) (1 / mutationRate);
		int y = (random.nextInt(x));
		
		if (random.nextInt(x) == y)
		{
			char z = letters[random.nextInt(letters.length)];
			int add = random.nextInt(name.length() + 1);
			if (add == name.length())
			{
				name.append(z);
			}
			else
			{
				String s = name.substring(add);
				name.delete(add, name.length());
				name.append(z);
				name.append(s);
			}
		}
		else if (random.nextInt(x) == y && name.length() > 2)
		{
			int remove = random.nextInt(name.length());
			name.deleteCharAt(remove);
		}
		else if (random.nextInt(x) == y)
		{
			int change = random.nextInt(name.length());
			name.setCharAt(change, letters[random.nextInt(28)]);
		}
	}

	public void crossover(Genome other)
	{
		StringBuilder newName = new StringBuilder();
		int i = 0;
		while (true)
		{
			if (random.nextBoolean())
			{
				if (i < name.length())
				{
					newName.append(name.charAt(i));
				}
				else
				{
					break;
				}
			}
			else
			{
				if (i < other.name.length())
				{
					newName.append(other.name.charAt(0));
				}
				else
				{
					break;
				}
			}
			i++;
		}
		name = newName;
	}

	public Integer fitness()
	{
		int n = name.length();
		int m = target.length();
		int i = Integer.max(n, m);
		int f = Math.abs(m - n);
		for (int x = 0; x < i; x++)
		{
			if (x < n && x < m)
			{
				if (name.charAt(x) != target.charAt(x))
				{
					f++;
				}
			}
		}
		return f;
	}

	public String toString()
	{
		return name.toString() + " Fitness: " + this.fitness();
	}
	
	public int compareTo(Genome other)
	{
		if (this.fitness() > other.fitness())
		{
			return 1;
		}
		else if (this.fitness() < other.fitness())
		{
			return -1;
		}
		return 0;
	}
}
