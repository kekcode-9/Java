import java.io.*;
import java.lang.*;
import java.util.ArrayList;

class MergeKSorted
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter no. of arrays: ");
		int k = Integer.parseInt(br.readLine());
		System.out.println("Enter size of each array: ");
		int n = Integer.parseInt(br.readLine());

		int mat[][] = new int[k][n]; // each row will store one array of size n

		for(int i=0; i<k; i++)
		{
			System.out.println("Enter elements of array "+ (i+1)+ ": ");
			String str = br.readLine();
			String arr[] = str.split(" ");
			for(int j=0; j<n; j++)
			{
				mat[i][j] = Integer.parseInt(arr[j]);
			}
		}

		//System.out.println(mat.length); // no. of rows, k
		//System.out.println(mat[0].length); // no. of cols, n
		Merge merob = new Merge(mat);
		merob.display();
	}
}

class Merge
{
	int n, k;
	int sorted[];
	class Node
	{
		int val;
		int arr;

		Node(int x, int y)
		{
			val = x;
			arr = y;
		}

		int getVal()
		{
			return val;
		}
		int getArr()
		{
			return arr;
		}
		void setNode(int x, int y)
		{
			val = x;
			arr = y;
		}
	}
	Merge(int mat[][])
	{
		k = mat.length;
		n = mat[0].length;
		sorted = new int[n*k];
		mergeSort(mat);
	}
	void heapify(ArrayList<Node> heap, int pos)
	{
		if(pos==0)
		{
			int leftChild = 2*pos + 1;
			int rightChild = 2*pos + 2;
			int swapWith = 0;
			int inserted = 0;

			while(inserted!=1)
			{
				if(leftChild<heap.size() && rightChild<heap.size()) // if both children exist
				{
					if(heap.get(leftChild).getVal()<heap.get(rightChild).getVal())
					{
						swapWith = leftChild;
					}
					else
					{
						swapWith = rightChild;
					}
				}
				else if(leftChild<heap.size() && rightChild>=heap.size()) // if only left child exists
				{
					swapWith = leftChild;
				}
				else if(leftChild>=heap.size() && rightChild<heap.size()) // if only right child exists
				{
					swapWith = rightChild;
				}
				else // the node is at leaf position
				{
					inserted = 1;
				}

				if(heap.get(swapWith).getVal()<heap.get(pos).getVal())
				{
					//shiftdown operation
					Node temp = heap.get(pos);
					heap.set(pos, heap.get(swapWith));
					heap.set(swapWith, temp);
					pos = swapWith;
					leftChild = 2*pos + 1;
					rightChild = 2*pos + 2;
				}
				else
				{
					inserted = 1;
				}
			}
		}
		else 
		{
			int parent = (int) Math.floor((pos-1) / 2);
			while(parent>=0 && heap.get(parent).getVal()>heap.get(pos).getVal())
			{
				// shiftup operation
				Node temp = heap.get(pos);
				heap.set(pos, heap.get(parent));
				heap.set(parent, temp);
				pos = parent;
				parent = (int) Math.floor((pos-1)/2);
			}
		}
	}

	void mergeSort(int mat[][])
	{
		ArrayList<Node> minHeap = new ArrayList<Node>();

		// value of current minimum
		int minel = 0;
		// row index in mat of the array with current min
		int arrayWithMin = 0;
		// for every array, index of the element of that array which is in heap
		int inHeapIndices[] = new int[k];

		// build the initial minHeap with 0th element of every array
		for(int i=0; i<k; i++)
		{
			Node x = new Node(mat[i][0], i);
			minHeap.add(x);
			heapify(minHeap, i);
			inHeapIndices[i] = 0;

			if(i==0)
			{
				minel = mat[i][0];
				arrayWithMin = i;
			}
			else if(minel>mat[i][0])
			{
				minel = mat[i][0];
				arrayWithMin = i;
			}
		}

		for(int i=0; i<n*k; i++)
		{
			// store minimum element in sorted array
			sorted[i] = minel;
			// increment index value of arrayWithMin in the indices array
			inHeapIndices[arrayWithMin] = inHeapIndices[arrayWithMin] + 1;
			int x = inHeapIndices[arrayWithMin];
			// if the array is not yet exhausted
			if(x<n)
			{
				// replace root with next element fro arrayWithMin
				Node root = minHeap.get(0);
				// replace root with next element from arrayWithMin
				root.setNode(mat[arrayWithMin][x], arrayWithMin);
				minHeap.set(0, root);
				heapify(minHeap, 0);
			}
			else // if the array has been exhausted
			{
				int size = minHeap.size();
				if(size>1)
				{
					// replace root with last node
					minHeap.set(0, minHeap.get(size-1));
					minHeap.remove(size-1);
					heapify(minHeap, 0);
				}
			}
			minel = minHeap.get(0).getVal();
			arrayWithMin = minHeap.get(0).getArr();
		}
	}
	void display()
	{
		System.out.println("Sorted array: ");
		for(int i=0; i<n*k; i++)
		{
			System.out.print(sorted[i]+"  ");
		}
	}
}