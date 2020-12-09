package myPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		StringBuilder message = new StringBuilder();

		BufferedReader br = new BufferedReader(new FileReader("WarAndPeace.txt"));
		File original = new File("WarAndPeace.txt");
		long starttime = System.nanoTime();
		int r;
		while ((r = br.read()) != -1)
		{
			message.append((char) r);
		}
		CodingTree ct = new CodingTree(message.toString());
		ct.codes.stats();
		File codes = new File("codes.txt");
		if (!codes.exists())
		{
			codes.createNewFile();
		}
		FileWriter fw = new FileWriter(codes.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < ct.codes.entries; i++)
		{
			bw.write(ct.keyList.get(i).getKey());
			bw.write("=" + ct.codes.get(ct.keyList.get(i).getKey()) + ", ");

		}

		File file = new File("encoded.txt");
		if (!file.exists())
		{
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
		fos.write(ct.bytes);
		fos.close();
		br.close();
		bw.close();

		long endtime = System.nanoTime();
		double runtime = (double) (endtime - starttime) / 1000000; // in milliseconds
		System.out.println("Runtime: " + runtime + " ms");
		System.out.println("File Compression: " + ((float) file.length() / (float) original.length()) * 100 + "%");

	}

	public static void testCodingTree()
	{
		CodingTree codingTree = new CodingTree("Hi Hi Hello Hello TryndMyBoi");
		for (int i = 0; i < codingTree.keyList.size() - 1; i++)
		{
			System.out.println(codingTree.keyList.get(i).toString());
		}
		System.out.println(codingTree.root.toString());
		System.out.println(codingTree.root.frequency + " root");
	}

	public static void testHashTable()
	{
		MyHashTable<Integer, Integer> x = new MyHashTable<>(10);
		x.put(1, 1);
		x.put(11, 11);
		x.stats();
	}

}