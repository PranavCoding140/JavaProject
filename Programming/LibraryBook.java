//java program to create class LibraryBook with members: bookId,title,author,and isIssued. Include method to issue, return, and display details of book.Demonstrate object creation
//and method calls in main()
import java.util.Scanner;
public class LibraryBook{
    Scanner sc= new Scanner(System.in);
    int bookId=90400;
    String tit,aut;
    String isIssued(int bookId){
        tit="A Thousand Splendid Suns";
        aut="Khaled Hosseini";
        System.out.print("Enter the Book number/ID:");
        sc.nextInt();
        if(bookId==90400){
        System.out.print("Book with ID"+bookId+"is issued.Title is"+tit+".Author is"+aut);}
        return tit;
    }
    int BookReturn(int bookId){
        System.out.print("Enter the BookID:");
        sc.nextInt();
        System.out.print("Book with ID"+bookId+"is returned.");
        return bookId;
    }

    public void main(String[] args){
        this.bookId=90400;
        LibraryBook lb1= new LibraryBook(); 
        lb1.isIssued(90400);
        lb1.BookReturn(90400);
    }
}