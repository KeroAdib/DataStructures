package myPackage;

public class MyStack<Type>
{
	
	private Node front;
	
	private class Node
	{
		private Type item;
		private Node next;
	}
	
	public boolean isEmpty()
	{
		if (front == null)
		{
			return true;
		}
		return false;
	}
	
	public void push(Type item)
	{
		Node temp = front;
		front = new Node();
		front.item = item;
		front.next = temp;
	}
	
	public Type pop()
	{
		Type returnValue = front.item;
		Node temp = front.next;
		front = new Node();
		front = temp;
		return returnValue;
	}
	
	public Type peek()
	{
		return front.item;
	}
	
	public int size()
	{
		Node temp = front;
		int size = 0;
		while (temp != null)
		{
			size++;
			temp = temp.next;
		}
		return size;
	}
	
	public String toString()
	{
		String returnValue = "[";
		Node x = front;
		while (x.next != null)
		{
			returnValue += x.item + ", ";
			x = x.next;
		}
		returnValue += x.item + "]";
		return returnValue;
	}
}
