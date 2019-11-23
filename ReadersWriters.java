import java.io.*;
import java.util.Random;
class ReadersWriters
{
	public static void main(String args[]) throws Exception
	{
		Queue q= new Queue(8);
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter total number of writers: ");
		int writercount=Integer.parseInt(br.readLine());
		System.out.println("Enter total number of readers: ");
		int readercount=Integer.parseInt(br.readLine());
		Writers w[]= new Writers[writercount];
		Readers r[]= new Readers[readercount];
		for(int i=0;i<writercount;i++)
		{
			w[i]= new Writers(q,String.valueOf(i));
		}
		for(int i=0;i<readercount;i++)
		{
			r[i]= new Readers(q, String.valueOf(i));
		}
	}
}
class Writers implements Runnable
{
	Thread w;
	Queue q;
	Random randval;
	String name;
	Writers(Queue q1, String wname)
	{
		q=q1;
		name=wname;
		w= new Thread(this,name);
		randval= new Random(); //creating instance of Random class
		w.start();
	}
	public void run() 
	{
		while(true)
		{
			int val= randval.nextInt(5000); //generate random integer value in range 0 to 4999
		    q.write(val,name);
		}
	}
}
class Readers implements Runnable
{
	Thread r;
	Queue q;
	String name;
	Readers(Queue q1, String rname)
	{
		q=q1;
		name=rname;
		r= new Thread(this,name);
		r.start();
	}
	public void run()
	{
		while(true)
		{
			q.read(name);
		}
	}
}
class Queue
{
	int Q[];
	int front, rear, size, count;
	Random randval;
	Queue(int size)
	{
		this.size=size;
		Q= new int[size];
		front=-1;
		rear=-1;
		count=0;
	}
	synchronized void write(int value, String name)
	{
		if(count==size)
		{
			try
			{
				System.out.println("Queue is full. Writer "+name+" has to wait.");
				wait(); //writer thread waits
			}
			catch(InterruptedException e)
			{
				System.out.println("Writer "+name+" interrupted");
			}
		}
		else
		{
			if(front==-1)
			{
				front++;
				notifyAll(); //notify readers
			}
			rear=(rear+1)%size;
			Q[rear]=value;
			System.out.println("Writer "+name+" wrote "+value);
			count++;
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				System.out.println("Writer "+name+" interrupted");
			}
		}
	}
	synchronized void read(String name)
	{
		if(count==0)
		{
			try
			{
				System.out.println("Queue is empty. Reader "+name+" has to wait.");
    			wait(); //reader thread waits
			}
			catch(InterruptedException e)
			{
				System.out.println("\nReader "+name+" interrupted");
			}
		}
		else
		{
			System.out.println(" Reader "+name+" read "+Q[front]);
			front=(front+1)%size;
			count--;
			if(size-count<=1)
			{
				notifyAll(); //notify writers
			}
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				System.out.println("Reader "+name+" interrupted");
			}
		}
	}
}