package myPackage;

public class Main
{
	public static final Integer GENOMES = 100;
	public static final Double MUTATIONRATE = .05;

	public static void main(String[] args)
	{
		Population population = new Population(GENOMES, MUTATIONRATE);
		int generations = 0;
		long startTime = System.nanoTime();
		while (true)
		{
			population.day();
			System.out.println("Generation: " + generations + " " + population.mostFit.toString());
			generations++;

			if (population.mostFit.fitness() == 0)
				break;
		}
		long endtime = System.nanoTime();
		double runtime = (double) (endtime - startTime) / 1000000000; // in milliseconds
		System.out.println("\nDone\n" + population.mostFit.toString());
		System.out.println("Iterated for " + generations + " generations");
		System.out.println("Runtime: " + runtime + " seconds");

		//testGenome();
		//testPopulation();

	}

	private static void testGenome()
	{
		Genome test = new Genome(.05);
		Genome test2 = new Genome(.05);
		int i = 0;
		while (i < 1000)
		{
			test.mutate();
			test2.mutate();
			test.crossover(test2);
			System.out.println(test.toString() + test2.toString() + test.compareTo(test2));
			i++;
		}
	}

	private static void testPopulation()
	{
		Population test = new Population(GENOMES, MUTATIONRATE);
		System.out.println(test.toString());
		test.day();
		System.out.println(test.toString());
	}

}
