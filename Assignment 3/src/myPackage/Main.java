package myPackage;

import java.io.*;

public class Main
{

	public static void main(String[] args) throws IOException
	{

		StringBuilder characters = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader("WarAndPeace.txt"));
		File x = new File("WarAndPeace.txt");
		long start	 = System.nanoTime();
		while (reader.ready())
		{
			String y = reader.readLine();
			characters.append(y + "\n");
		}

		CodingTree codingTree = new CodingTree(characters.toString());

		File codes = new File("codes.txt");
		if (!codes.exists())
		{
			codes.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(codes.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		for (int i = 0; i < codingTree.codes.size(); i++)
		{
			bufferedWriter.write(codingTree.characterList.get(i).character);
			bufferedWriter.write("=" + codingTree.codes.get(codingTree.characterList.get(i).character) + ", ");
		}
		testCodingTree(characters);

		File file = new File("encoded.txt");
		if (!file.exists())
		{
			file.createNewFile();
		}

		FileOutputStream fileOutput = new FileOutputStream(file.getAbsoluteFile());
		fileOutput.write(codingTree.bytes);
		fileOutput.close();
		reader.close();
		bufferedWriter.close();

		long end = System.nanoTime();
		double runtime = (double) (end - start) / 1000000000;
		System.out.println("Runtime: " + runtime + " ms");
		System.out.println("File Compression: " + ((float) file.length() / (float) x.length()) * 100 + "%");

	}

	public static void testCodingTree(StringBuilder string)
	{
		CodingTree codingTree = new CodingTree(string.toString());
		for (int i = 0; i < codingTree.characterList.size() - 1; i++) {
			System.out.println(codingTree.characterList.get(i).toString());
		}

		System.out.println(codingTree.root.toString());
		System.out.println(codingTree.root.frequency + " root");
	}
}