package myPackage;

import java.util.ArrayList;
import java.util.Collections;

public class Population
{
	Genome mostFit;
	ArrayList<Genome> names;
	double mutationRate;

	public Population(Integer numGenomes, double mutationRate)
	{
		names = new ArrayList<Genome>();
		this.mutationRate = mutationRate;
		for (int i = 0; i < numGenomes; i++)
		{
			names.add(new Genome(mutationRate));
		}
		Collections.sort(names);
		mostFit = names.get(0);
	}

	public void day()
	{
		mostFit = names.get(0);
		final int size = names.size();
		while (names.size() > size / 2)
		{
			names.remove(size / 2);
		}

		while (names.size() < size)
		{
			if (Genome.random.nextBoolean())
			{
				Genome copy = new Genome(names.get(Genome.random.nextInt(size / 2)));
				copy.mutate();
				names.add(copy);
			}
			else
			{
				Genome cross1 = new Genome(names.get(Genome.random.nextInt(size / 2)));
				Genome cross2 = new Genome(names.get(Genome.random.nextInt(size / 2)));
				cross1.crossover(cross2);
				cross1.mutate();
				names.add(cross1);
			}
		}
		Collections.sort(names);
		mostFit = names.get(0);
	}
	
	public String toString()
	{
		StringBuilder namesString = new StringBuilder();
		for (int i = 0; i < names.size(); i++) {
			namesString.append(i +": " +this.names.get(i).toString() + "\n");
		}
		return namesString.toString();
	}
}
