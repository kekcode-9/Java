import java.io.*;
class MergesortMultithreaded  //main class
{
	public static void main(String args[]) throws Exception
	{
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        int size;
        System.out.print("Enter size of array: ");
        size=Integer.parseInt(br.readLine());
        int arr[]=new int[size];
        for(int i=0;i<size;i++)
        {
        	System.out.print("\nEnter element "+i+" : ");
        	arr[i]=Integer.parseInt(br.readLine());
        }

        ImplementMergesort obj= new ImplementMergesort(arr);
        obj.show(); //displays sorted array
	}
}
class ImplementMergesort //class that implements mergesort
{
	int arr[];
	int size;
	int temp[];
	ThreadsThatMerge t1,t2;


	class ThreadsThatMerge implements Runnable //inner class that creates a thread
	{
		Thread t;
		int low,high; //lowest and highest index of subarray

		ThreadsThatMerge(int low,int high) 
		{
			t= new Thread(this);
			this.low=low;
			this.high=high;
			t.start();
		}
		public void run()
		{
			merge(low,(low+high)/2,high); //thread calls mergesort on subarray
		}
	}


	ImplementMergesort(int arr[]) //constructor of outer class that calls sort() method
	{
		this.arr=arr;
		size=arr.length;
		temp=new int[size];
		int low=0;
		int high=size-1;

		sort(low,high); //first call to sort() on the undivided array
		merge(low,(low+high)/2,high); //last call to merge() on the undivided array that merges the two main halves
	}


	void sort(int low,int high)
	{
		if((high-low)>=2) //there has to be at least three elements in the subarray or sort() returns
		{
			int mid=(low+high)/2;

			sort(low,mid); //recursive call to sort() on first half of subarray
			sort(mid+1,high); //recursive call to sort() on second half of subarray

			t1= new ThreadsThatMerge(low,mid);    //thread t1 that will call merge() on the first half
			t2= new ThreadsThatMerge(mid+1,high); //thread t2 that will call merge() on the second half
			try //wait for the threads to finish
			{
				t1.t.join();
				t2.t.join();
			}
			catch(InterruptedException e)
			{
				System.out.println("sorting interrupted");
			}
		}
	}
	void merge(int low,int mid,int high)
	{
		int i=low, j=mid+1, k=low;
		while((i<=mid)&&(j<=high))
		{
			if(arr[i]<arr[j])
			{
				temp[k]=arr[i];
				i++;
			}
			else
			{
				temp[k]=arr[j];
				j++;
			}
			k++;
		}
		if((i>mid)&&(j<=high))
		{
			while(j<=high)
			{
				temp[k]=arr[j];
				j++;
				k++;
			}
		}
		else if((i<=mid)&&(j>high))
		{
			while(i<=mid)
			{
				temp[k]=arr[i];
				i++;
				k++;
			}
		}
		for(k=low;k<=high;k++)
		{
			arr[k]=temp[k];
		}
	}
	void show()
	{
		for(int i=0;i<size;i++)
		{
			System.out.print(" "+arr[i]+" ");
		}
	}
}