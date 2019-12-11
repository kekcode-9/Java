/*The class Book only has nested classes but doesn't have object of any of the inner classes as a data member
Consider it as a template of the library's content. The inner classes are like sub-template(or something)
- Rupamita*/
package BookInfo;
public class Book
{
	public class HistoryBook
	{
		protected String name, author;
		protected int edition;
		public HistoryBook(String s1, String s2, int e)
		{
			name=s1;
			author=s2;
			edition=e;
		}
		public int confirmBook(String bname)
		{
			if(name.equalsIgnoreCase(bname))
			{
				return 1;
			}
			return 0;
		}
		public void display()
		{
			System.out.println("Name: "+name);
			System.out.println("Author: "+author);
			System.out.println("Edition: "+edition);
		}
	}
	public class MathsBook
	{
		protected String name, author;
		protected int edition;
		public MathsBook(String s1, String s2, int e)
		{
			name=s1;
			author=s2;
			edition=e;
		}
		public int confirmBook(String bname)
		{
			if(name.equalsIgnoreCase(bname))
			{
				return 1;
			}
			return 0;
		}
		public void display()
		{
			System.out.println("Name: "+name);
			System.out.println("Author: "+author);
			System.out.println("Edition: "+edition);
		}
	}
	public class ProgrammingBook
	{
		protected String name, author;
		protected int edition;
		public ProgrammingBook(String s1, String s2, int e)
		{
			name=s1;
			author=s2;
			edition=e;
		}
		public int confirmBook(String bname)
		{
			if(name.equalsIgnoreCase(bname))
			{
				return 1;
			}
			return 0;
		}
		public void display()
		{
			System.out.println("Name: "+name);
			System.out.println("Author: "+author);
			System.out.println("Edition: "+edition);
		}
	}
}