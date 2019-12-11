import java.io.*;
class CreateSLL
{
	public static void main(String args[]) throws Exception
	{
		SLL list1= new SLL();
		SLL list2= new SLL();
		SLL list3= new SLL(list1, list2);
	}
}
class SLL
{
	Node head;
	class Node
	{
		int value;
		Node next;
		Node(int d)
		{
			value=d;
			next=null;
		}
	}
	SLL() throws Exception
	{
		head=null;
		int choice, v;
		Node newnode, curr;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Creating singly linked list");
		do
		{
			System.out.println("Enter 1 to add new node, 0 to stop: ");
			choice=Integer.parseInt(br.readLine());
			if(choice==1)
			{
				System.out.println("Enter value for new node: ");
				v= Integer.parseInt(br.readLine());
				newnode= new Node(v);
				if(head==null)
				{
					head=newnode;
				}
				else
				{
					curr=head;
					while(curr.next!=null)
					{
						curr=curr.next;
					}
					curr.next=newnode;
				}
			}
		}while(choice==1);
		listOperations();
	}

    SLL(SLL list1,SLL list2) throws Exception
	{
		merge(list1,list2);
		listOperations();
	}

	void listOperations() throws Exception
	{
		int choice,value;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		do
		{
			System.out.println("Enter 1 for insertion, 2 for deletion, 3 for display, 0 to quit: ");
			choice= Integer.parseInt(br.readLine());
			switch(choice)
			{
				case 1:
				{
					System.out.println("Enter 1 for insert at front, 2 for insert at end, 3 for insert after desired node: ");
					int choice1=Integer.parseInt(br.readLine());
					insertNode(choice1);
					break;
				}
				case 2:
				{
					System.out.println("Enter 1 for delete front node, 2 for delete last node, 3 for delete desired node: ");
					int choice2=Integer.parseInt(br.readLine());
					deleteNode(choice2);
					break;
				}
				case 3:
				{
					display();
					break;
				}
				case 0:
				{
					break;
				}
			}
		}while(choice>0);
	}
	void insertNode(int choice) throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter value to insert: ");
		int val=Integer.parseInt(br.readLine());
		Node newnode= new Node(val);
		Node curr;
		if(choice==1) //insert at front
		{
			if(head==null)
			{
				head=newnode;
			}
			else
			{
				newnode.next=head;
				head=newnode;
			}
		}
		else if(choice==2) //insert at end
		{
			if(head==null)
			{
				head=newnode;
			}
			else
			{
				curr=head;
				while(curr.next!=null)
				{
					curr=curr.next;
				}
				curr.next=newnode;
			}
		}
		else //insert after desired node
		{
			System.out.println("Enter value of node after which insertion is to be made: ");
			int key=Integer.parseInt(br.readLine());
			curr=head;
			while(curr.next!=null && curr.value!=key)
			{
				curr=curr.next;
			}
			if(curr.next==null && curr.value!=key)
			{
				System.out.println("Key node not found");
			}
			else
			{
				newnode.next=curr.next;
				curr.next=newnode;
			}
		}
	}
	void deleteNode(int choice) throws Exception
	{
		Node temp;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		if(head==null)
		{
			System.out.println("List is empty. Deletion not possible.");
			return;
		}
		if(choice==1)
		{
			temp=head;
			head=head.next;
			temp=null;
		}
		else if(choice==2)
		{
			temp=head;
			Node prev=temp;
			while(temp.next!=null)
			{
				prev=temp;
				temp=temp.next;
			}
			prev.next=null;
			temp=null;
		}
		else
		{
			System.out.println("Insert value of node to be deleted: ");
			int key=Integer.parseInt(br.readLine());
			temp=head;
			Node prev=temp;
			while(temp.next!=null && temp.value!=key)
			{
				prev=temp;
				temp=temp.next;
			}
			if(temp.next==null && temp.value!=key)
			{
				System.out.println("Node not found");
			}
			else
			{
				prev.next=temp.next;
				temp=null;
			}
		}
	}
	void merge(SLL list1, SLL list2) throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 to concatenatenate one list after the other, 2 to merge them in sorted order: ");
		int choice=Integer.parseInt(br.readLine());
		if(choice==2)
		{
			Node prev=null;
			head= new Node(list1.head.value);
			Node curr=head;
			System.out.println("curr.value: "+curr.value);
			Node newnode;
			Node curr1=list1.head;
			do //while(curr1!=null)
			{
				curr1=curr1.next;
			    while(curr1!=null && curr!=null && curr1.value>=curr.value)
			    {
			    	prev=curr;
			    	curr=curr.next;
			    }
			    if(curr==head) //curr1.value<head.value
			    {
			    	newnode= new Node(curr1.value);
			    	newnode.next=head;
			    	head=newnode;
			    }				
			    else
			    {
			    	newnode= new Node(curr1.value);
			    	newnode.next=curr;
			    	prev.next=newnode;
			    }
			    curr=head;
			}while(curr1.next!=null);
			Node curr2=list2.head;
			while(curr2!=null)
			{
				while(curr2!=null && curr!=null && curr2.value>=curr.value)
				{
			    	prev=curr;
			    	curr=curr.next;
			    }
			    if(curr==head) //curr1.value<head.value
			    {
			    	newnode= new Node(curr2.value);
			    	newnode.next=head;
			    	head=newnode;
			    }				
			    else
			    {
			    	newnode= new Node(curr2.value);
			    	newnode.next=curr;
			    	prev.next=newnode;
			    }
			    curr2=curr2.next;
			    curr=head;
			}
		}
		else if(choice==1)
		{
			Node newnode= new Node(list1.head.value);
			Node curr1=list1.head.next, curr2=list2.head;
			head=newnode;
			Node curr=head;
			while(curr1!=null)
			{
				newnode= new Node(curr1.value);
				curr.next=newnode;
				curr=curr.next;
				curr1=curr1.next;
			}
			while(curr2!=null)
			{
				newnode= new Node(curr2.value);
				curr.next=newnode;
				curr=curr.next;
				curr2=curr2.next;
			}
		}
	}
	void display()
	{
		if(head==null)
		{
			System.out.println("List is empty");
		}
		else
		{
			Node curr=head;
			while(curr!=null)
			{
				System.out.print(" "+curr.value+" -->");
				curr=curr.next;
			}
			System.out.println("null");
		}
	}
}