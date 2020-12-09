package myPackage;

import java.util.ArrayList;
import java.util.Collections;

public class CodingTree
{
	public static final int CAP = 32768;
	public MyHashTable<String, String> codes;
	public MyHashTable<String, Integer> frequencyMap;
	public final ArrayList<Node> keyList = new ArrayList<Node>();
	public byte[] bytes;
	public Node root;
	String bits;

	public static final String nonSeparators = "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz" + "'-";

	static class Node implements Comparable<Node>
	{
		String key;
		String code;
		int frequency;
		Node left, right;

		Node(String key, String code)
		{
			this.key = key;
			this.code = code;
		}

		public String getKey()
		{
			return key;
		}

		public String getCode()
		{
			return code;
		}

		public void setCode(String newCode)
		{
			code = newCode;
		}

		public int getFrequency()
		{
			return frequency;
		}

		public void setFrequency(int newFrequency)
		{
			frequency = newFrequency;
		}

		private boolean isLeaf()
		{
			return (left == null) && (right == null);
		}

		public int compareTo(Node other)
		{
			return this.frequency - other.frequency;
		}

		public boolean equalsFrequency(String other)
		{
			return this.key.contentEquals(other);
		}
	}

	CodingTree(String text)
	{
		codes = new MyHashTable<>(CAP);
		frequencyMap = new MyHashTable<>(CAP);

		StringBuilder temp = new StringBuilder();
		String word;
		String separator;
		for (int i = 0; i < text.length(); i++)
		{
			if (nonSeparators.contains(text.substring(i, i + 1)))
			{
				temp.append(text.substring(i, i + 1));
			}
			else
			{
				if (temp.length() != 0)
				{
					word = temp.toString();
					temp.delete(0, temp.length());
					if (frequencyMap.containsKey(word))
					{
						frequencyMap.put(word, frequencyMap.get(word) + 1);
					}
					else
					{
						frequencyMap.put(word, 1);
						keyList.add(new Node(word, ""));
					}
				}
				separator = text.substring(i, i + 1);
				if (frequencyMap.containsKey(separator))
				{
					frequencyMap.put(separator, frequencyMap.get(separator) + 1);
				}
				else
				{
					frequencyMap.put(separator, 1);
					keyList.add(new Node(separator, ""));
				}
			}
		}

		for (int i = 0; i < keyList.size() - 1; i++)
		{
			keyList.get(i).setFrequency(frequencyMap.get(keyList.get(i).getKey()));
		}
		formTree(keyList);
		formCodes(root, "");
		writeBits(text);
	}

	private void formTree(ArrayList<Node> list)
	{
		ArrayList<Node> temp = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
		{
			temp.add(list.get(i));
		}
		while (temp.size() > 1)
		{
			Collections.sort(temp);
			Node newTree = new Node(null, null);
			newTree.setFrequency(temp.get(0).getFrequency() + temp.get(1).getFrequency());
			newTree.left = temp.get(0);
			newTree.right = temp.get(1);
			temp.remove(0);
			temp.remove(0);
			temp.add(0, newTree);
		}
		root = temp.get(0);
	}

	private void formCodes(Node root, String code)
	{
		if (!root.isLeaf())
		{
			formCodes(root.left, code + '0');
			formCodes(root.right, code + '1');
		}
		else
		{
			codes.put(root.getKey(), code);
		}
	}

	private void writeBits(String text)
	{
		StringBuilder encoded = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < text.length(); i++)
		{
			if (nonSeparators.contains(text.substring(i, i + 1)))
			{
				temp.append(text.substring(i, i + 1));
			}
			else
			{
				if (temp.length() != 0)
				{
					encoded.append(codes.get(temp.toString()));
					temp.delete(0, temp.length());
				}
				encoded.append(codes.get(text.substring(i, i + 1)));
			}
		}

		bytes = new byte[encoded.length() / 8];
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) Integer.parseUnsignedInt(encoded.substring(i * 8, (i * 8) + 8), 2);
		}
		System.out.println("Done");

	}
}