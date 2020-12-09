package myPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodingTree
{
	public final Map<Character, Integer> characterMap = new HashMap<Character, Integer>();
	public final Map<Character, String> codes = new HashMap<Character, String>();
	public final ArrayList<Node> characterList = new ArrayList<Node>();
	public byte[] bytes;
	public Node root;
	public StringBuilder encoded;

	static class Node implements Comparable<Node>
	{
		char character;
		int frequency;
		Node left, right;

		Node(char character, int frequency, Node left, Node right)
		{
			this.character = character;
			this.frequency = frequency;
			this.left = left;
			this.right = right;
		}

		Node(int frequency, Node left, Node right)
		{
			this.frequency = frequency;
			this.left = left;
			this.right = right;
		}

		private boolean isLeaf()
		{
			return (left == null) && (right == null);
		}

		public int compareTo(Node other)
		{
			return this.frequency - other.frequency;
		}

		public String toString()
		{
			return "Node " + character + "  Frequency:" + frequency;
		}
	}

	public CodingTree(String string)
	{
		for (int i = 0; i < string.length() - 1; i++)
		{
			if (characterMap.containsKey(string.charAt(i)))
			{
				int x = characterMap.get(string.charAt(i));
				x++;
				characterMap.put(string.charAt(i), x);
			}
			else
			{
				characterMap.put(string.charAt(i), 1);
				characterList.add(new Node(string.charAt(i), 1, null, null));
			}
		}

		for (int i = 0; i < characterList.size(); i++)
		{
			characterList.get(i).frequency = characterMap.get(characterList.get(i).character);
		}

		Collections.sort(characterList);
		makeTree(characterList);
		makeCode(root, "");
		writeBits(string);
	}

	private void makeTree(ArrayList<Node> list)
	{
		ArrayList<Node> x = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
		{
			x.add(list.get(i));
		}
		while (x.size() > 1)
		{
			Collections.sort(x);
			Node tree = new Node(x.get(0).frequency + x.get(1).frequency, x.get(0), x.get(1));
			x.remove(0);
			x.remove(0);
			x.add(0, tree);
		}
		root = x.get(0);
	}

	private void makeCode(Node root, String code)
	{
		if (!root.isLeaf())
		{
			makeCode(root.left, code + '0');
			makeCode(root.right, code + '1');
		}
		else
		{
			codes.put(root.character, code);
		}
	}

	private void writeBits(String string)
	{
		encoded = new StringBuilder();
		for (int i = 0; i < string.length() - 1; i++)
		{
			encoded.append(codes.get(string.charAt(i)));
		}

		bytes = new byte[encoded.length() / 8];
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) Integer.parseUnsignedInt(encoded.substring(i * 8, (i * 8) + 8), 2);
		}
		System.out.println("done");
	}

}
