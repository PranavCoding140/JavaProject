//Design BankAccount class. include Name,Accno, AccType, Balance as data members. Include initial values,deposits,withdrawals and checking balance and displaying it with name as
// methods
import java.util.*;
class BankAcc{
    String name;
    int accno;
    String acctype;
    double balance=1000; 
    void getData(int a, String b, String c){
        accno=a;
        name=b;
        acctype=c;
    }
        void display(){
            System.out.println("Account Number:" +accno);
            System.out.println("Name of Holder:" +name);
            System.out.println("Account Type:" +acctype);
            System.out.println("Balance:"+balance);
        }   
}
public class BankInfo{
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        BankAcc b1=new BankAcc();
        System.out.print("Enter the Account Number:");
        int a=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the Name of AccHolder:");
        String b=sc.nextLine();
        System.out.println("Enter the type of Account:");
        String c=sc.nextLine();
        b1.getData(a, b, c);
        b1.display();
        System.out.print("""
        ---TASK---
        1.Deposit
        2. Withdrawal
        3.Exit
         """);
         System.out.print("Enter Your choice:");
         int t= sc.nextInt();
         if(t==1){
            System.out.print("Enter the amount to be deposited:");
            double dep= sc.nextInt();
            b1.balance +=dep;
            System.out.println("Final Balance:"+b1.balance);
         }
         else if(t==2){
            System.out.print("Enter the amount to be withdrawn:");
            double wth= sc.nextInt();
            b1.balance -=wth;
            System.out.println("Final Balance:"+b1.balance);
        }
        else{
            System.out.print("Exiting the Program!");
        }
    }}
