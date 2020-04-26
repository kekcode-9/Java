/*this will create an array of String elements where each element of the array will be a string of format: 
"size/parent:node1 node2 node3" where size is the size of the tree which this element is a root of i.e.,
if this element is in fact a root of some tree. For a root node the parent part in the string will be same
as that node's index. For a child node in any tree, size will be 0 and parent will be the index of its
parent node. For any node, node1, node2, ...  are indexes of its child nodes. size indicates total number
of nodes in a tree.
The path compression function will be called only when a tree with root and child nodes are to be inserted
in another tree. The pathCompression() method holds the only for loop in this code, the number of times
the for loop runs is same as the number of child nodes in the tree to be inserted. For an array of N elements
the maximum number of children that a tree, which is being inserted into another, can have is N/2. So for
a single call to Union, the maximum number of times the for loop can run is N/2. So worst case time complexity
is O(lg N). Space complexity is O(N).*/
import java.io.*;
class WeightedUnionFindWithPathCompression
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter number of nodes: ");
		int size= Integer.parseInt(br.readLine());
		String arr[]= new String[size];
		WUFArray obj= new WUFArray(arr);
		int option;
		do
		{
			System.out.println("Enter 1 for creating union, 2 to display, 3 to find connection, 4 to find parent, 0 to end program: ");
			option= Integer.parseInt(br.readLine());
			switch(option)
			{
				case 1:
				{
					int p, q;
					System.out.println("Enter first node: ");
					p= Integer.parseInt(br.readLine());
					System.out.println("Enter second node: ");
					q= Integer.parseInt(br.readLine());
					obj.union(p,q);
					break;
				}
				case 2:
				{
					obj.display();
					break;
				}
				case 3:
				{
					System.out.println("Enter first node: ");
					int p= Integer.parseInt(br.readLine());
					System.out.println("Enter second node: ");
					int q= Integer.parseInt(br.readLine());
					if(obj.isConnected(p,q))
					{
						System.out.println(p+" and "+q+" are connected");
					}
					else
					{
						System.out.println(p+" and "+q+" are not connected");
					}
					break;
				}
				case 4:
				{
					System.out.println("Enter node: ");
					int p=Integer.parseInt(br.readLine());
					System.out.println("parent of "+p+" is "+obj.find(p));
					break;
				}
				case 0:
				{
					break;
				}
				default:
				{
					break;
				}
			}
		}while(option!=0);
	}
}
class WUFArray
{
	String arr[];
	int size;
	WUFArray(String str[])
	{
		arr=str;
		size=arr.length;
		for(int i=0;i<size;i++)
		{
			arr[i]="1/"+i+":"; //initialize each node with size of 1
		}
	}
	void union(int p, int q)
	{
		int flag=0; //flag indicates whether or not both p and q are roots of their own trees
		int pchildren[], qchildren[];
		int psize=Integer.parseInt(arr[p].split("/")[0]);
		int qsize=Integer.parseInt(arr[q].split("/")[0]);
		if(psize>0 && qsize>0) //both nodes are roots of their own trees
		{
			flag=1; 
		}
		else if(psize>0 && qsize==0) //p is a root node but q is a child node in their respective trees
		{
			int qparent=Integer.parseInt(arr[q].split("/")[1].split(":")[0]);
			if(qparent!=p) //if p and q are indeed part of different trees
			{
				flag=1;
				q=qparent;
				qsize=Integer.parseInt(arr[q].split("/")[0]);
			}
			else
			{
				System.out.println(q+"is already a child node in "+p+"'s tree");
			}
		}
		else if(psize==0 && qsize>0)
		{
			int pparent=Integer.parseInt(arr[p].split("/")[1].split(":")[0]);
			if(pparent!=q)
			{
				flag=1;
				p=pparent;
				psize=Integer.parseInt(arr[p].split("/")[0]);
			}
			else
			{
				System.out.println(p+"is already a child node in "+q+"'s tree");
			}
		}
		else //both p and q are children in their respective tress
		{
			int pparent=Integer.parseInt(arr[p].split("/")[1].split(":")[0]);
			int qparent=Integer.parseInt(arr[q].split("/")[1].split(":")[0]);
			if(pparent!=qparent)
			{
				flag=1;
				p=pparent;
				q=qparent;
				psize=Integer.parseInt(arr[p].split("/")[0]);
				qsize=Integer.parseInt(arr[q].split("/")[0]);
			}
			else
			{
				System.out.println(p+" and "+q+" already belong to the same tree");
			}
		}
		if(flag==1)
		{
			if(psize==qsize && psize==1) //both trees are of same size and have only one node
			{
				arr[q]="0/"+p+":"; //make p the parent of q
				arr[p]=arr[p]+q; //add q as a node of p
				arr[p]=2+"/"+arr[p].split("/")[1];
			}
			else if((psize==qsize && psize!=1)||(psize>qsize)) //both nodes have same number of children or p have more children than q
			{
				if(qsize>1)
				{
				    pathCompression(p,q,qsize-1); //call path compression on q's children
				}
				else
				{
					arr[p]=(Integer.parseInt(arr[p].split("/")[0])+1)+"/"+arr[p].split("/")[1];
				}
				arr[q]="0/"+p+":"; //make p the parent of q
				arr[p]=arr[p]+" "+q; //add q as child of p
			}
			else if(qsize>psize) //q has more children than p
			{
				if(psize>1)
				{
				    pathCompression(q,p,psize-1); //call path compression on p's children
				}
				else
				{
					arr[q]=(Integer.parseInt(arr[q].split("/")[0])+1)+"/"+arr[q].split("/")[1];
				}
				arr[p]="0/"+q+":"; //make q the parent of p
				arr[q]=arr[q]+" "+p; //add p as child of q
			}
		}
	}
	void pathCompression(int newroot, int oldroot, int treesize)
	{
		int index;
		String orootkidsstr=arr[oldroot].split(":")[1];
		for(int i=0;i<treesize;i++)
		{
			index=Integer.parseInt(orootkidsstr.split(" ")[i]); //index of ith child of oldroot
			arr[index]="0/"+newroot+":"; //change root of all child nodes
			arr[newroot]=arr[newroot]+" "+index; //add node to root's list of child nodes
		}
		int prevsize=Integer.parseInt(arr[newroot].split("/")[0]); //previous size of newroot
		int newsize=prevsize+treesize+1; //all children plus the oldroot is added to newroot
		arr[newroot]=newsize+"/"+arr[newroot].split("/")[1];
	}
	Boolean isConnected(int p, int q) //checks if two nodes are connected
	{
		if(arr[p].split("/")[1].split(":")[0].equals(arr[q].split("/")[1].split(":")[0]))
		{
			return true;
		}
		return false;
	}
	int find(int p) //finds parent of p
	{
		return Integer.parseInt(arr[p].split("/")[1].split(":")[0]);
	}
	void display()
	{
		for(int i=0;i<size;i++)
		{
			System.out.println(i+" | "+arr[i]);
		}
	}
}