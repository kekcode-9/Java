
package Lib;
import java.util.ArrayList;
import java.io.*;
import BookInfo.Book;

class LibraryWork 
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		Library l= new Library();
		int choice, type;
		String name;
		do
		{
			System.out.println("Enter 1 to issue a copy, 2 to receive returned copy, 3 to display book details, 0 to stop: ");
			choice=Integer.parseInt(br.readLine());
			switch(choice)
			{
				case 1:
				{
					l.issueCopy();
					break;
				}
				case 2:
				{
					l.receiveReturned();
					break;
				}
				case 3:
				{
					System.out.println("Enter 1 for history, 2 for maths, 3 for programming: ");
					type=Integer.parseInt(br.readLine());
					System.out.println("Enter name of book: ");
					name=br.readLine();
					l.displayDetail(name, type);
					break;
				}
				case 0:
				{
					break;
				}
			}
		}while(choice!=0);
	}
}

class Library extends Book
{
	ArrayList<HistoryBook> hblist= new ArrayList<HistoryBook>(); //list of history books
	ArrayList<MathsBook> mblist= new ArrayList<MathsBook>(); //list of math books
	ArrayList<ProgrammingBook> pblist= new ArrayList<ProgrammingBook>(); //list of programming books
	ArrayList<Info> infolist= new ArrayList<Info>();

	class Info //stores lending information of each book
	{
		String book;
		int copy;
		ArrayList<String> dates= new ArrayList<String>();
		int loanedcount;
		Info(String name, int n)
		{
			book=name;
			copy=n;
			loanedcount=0;
		}
		void lend(String d) //method for lending book
		{
			if(loanedcount==copy)
			{
				System.out.println("No copies currently available");
			}
			else
			{
				dates.add(d); //add issue date
				loanedcount++;
				for(String str:dates)
				{
					System.out.println(str);
				}
			}
		}
		void receive(String issuedate, String returndate) //method for receiving returned copy
		{
			int pos= dates.indexOf(issuedate);
			String d= dates.get(pos);
			dates.remove(d);
			d=d+"      "+returndate;
			dates.add(pos,d);
			loanedcount--;
		}
	}

	Library() throws Exception
	{
		int choice;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Library has History, Maths and Programming Books ");
		do
		{
			System.out.println("Enter 1 to add new book: ");
			choice=Integer.parseInt(br.readLine());
			if(choice==1)
			{
				addBook();
			}
		}while(choice==1);
	}
	void issueCopy() throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter today's date(dd/mm/yy): ");
		String date=br.readLine();
		System.out.println("Enter 1 for History book, 2 for Maths book, 3 for Programming Book: ");
		int choice=Integer.parseInt(br.readLine());
		System.out.println("Enter name of book: ");
		String name=br.readLine();
		int found=0;
		if(choice==1)
		{
			for(HistoryBook h:hblist) //for each object in hblist
			{
				if(h.confirmBook(name)==1) //call confirmBook() to check if it's the required book
				{
					found=1;
				}
			}
		}
		else if(choice==2)
		{
			for(MathsBook m:mblist)
			{
				if(m.confirmBook(name)==1)
				{
					found=1;
				}
			}
		}
		else if(choice==3)
		{
			for(ProgrammingBook p:pblist)
			{
				if(p.confirmBook(name)==1)
				{
					found=1;
				}
			}
		}
		if(found==0)
		{
			System.out.println("Book not found");
			return;
		}
		for(Info i:infolist)
		{
			String b= i.book;
			if(b.equalsIgnoreCase(name)) //find the entry in infolist for the required book
			{
				i.lend(date); //call method update book information accordingly
			}
		}
	}
	void receiveReturned() throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter issue date: ");
		String idate= br.readLine();
		System.out.println("Enter return date: ");
		String rdate= br.readLine();
		System.out.println("Enter book name: ");
		String name= br.readLine();
		for(Info i:infolist)
		{
			String b=i.book;
			if(b.equalsIgnoreCase(name)) //find entry in the infolist
			{
				i.receive(idate,rdate);
			}
		}
	}
	void addBook() throws Exception
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 for history book, 2 for maths book, 3 for Programming book: ");
		int choice=Integer.parseInt(br.readLine());
		System.out.println("Enter Book name: ");
		String bname=br.readLine();
		System.out.println("Enter author's name: ");
		String aname=br.readLine();
		System.out.println("Enter total no. of copies: ");
		int c=Integer.parseInt(br.readLine());
		System.out.println("Enter edition: ");
		int e=Integer.parseInt(br.readLine());
		if(choice==1)
		{
			HistoryBook newbook= new HistoryBook(bname,aname,e); //create object of HistoryBook
			hblist.add(newbook); //add object to list
		}
		else if(choice==2)
		{
			MathsBook newbook= new MathsBook(bname,aname,e);
			mblist.add(newbook);
		}
		else if(choice==3)
		{
			ProgrammingBook newbook= new ProgrammingBook(bname,aname,e);
			pblist.add(newbook);
		}
		Info iobj= new Info(bname,c);
		infolist.add(iobj);
	}
	void displayDetail(String bname,int type)
	{
		String s;
		if(type==1)
		{
			for(HistoryBook h:hblist)
			{
				if(h.confirmBook(bname)==1)
				{
					h.display();
				}
			}
		}
		else if(type==2)
		{
			for(MathsBook m:mblist)
			{
				if(m.confirmBook(bname)==1)
				{
					m.display();
				}
			}
		}
		else if(type==3)
		{
			for(ProgrammingBook p:pblist)
			{
				if(p.confirmBook(bname)==1)
				{
					p.display();
				}
			}
		}
		for(Info i:infolist)
		{
			s=i.book;
			if(s.equalsIgnoreCase(bname))
			{
				System.out.println("No. of copies: "+i.copy);
				System.out.println("No. of copies loaned: "+i.loanedcount);
				System.out.println("Issue date   Return date");
				for(String str:i.dates)
				{
					System.out.println(str);
				}
			}
		}
	}
}
