//Create a Billing class with methods 1.computeBill(cost)+8%tax, 2.computeBill(cost,qty)+8%tax,3.computeBill(cost,qty,cpnval)+8%tax.
//4 errors remaining
import java.util.Scanner;
public class Billing{
    double amount;
    int billno,qty,cpnval;}
    double computeBill(double amount,int billno){
        double total;
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the cost of the item:");
        sc.nextDouble();
        System.out.println("Enter Billno:");
        sc.nextInt();
        total=amount+(8/100)*amount;
        System.out.print("Total bill is:", total);//no suitable method found for println
        System.out.print("Billno is:", billno);//no suitable method found for println
    }
    public void main(String[]args){
        double CpBill1=new computeBill(amount,billno);//error cannot find symbol
        double CpBill2=new computeBill(amount,qty,billno);//error cannot find symbol
        double CpBill3=new computeBill(amount,qty,cpnval,billno);//error cannot find symbol
    }
    double computeBill(double amount,int qty,int billno){
        double price,total;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the cost of the item:");
        sc.nextDouble();
        System.out.print("Enter the quantity:");
        sc.nextInt();
        System.out.println("Enter the billno");
        sc.nextInt();
        price=amount*qty;
        total=price+(8/100)*price;//cannot find symbol
        System.out.println("Total bill is:");
        System.out.println(total); //no suitable method found for println
        System.out.println("Billno is:");
        System.out.println(billno);
    }
    double computeBill(double amount,int qty,int cpnval,int billno){
        double price,cost,total;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the cost of the item:");
        System.out.println("Enter the quantity:");
        System.out.println("Enter the billno:");
        System.out.println("Enter the coupon no:");
        if(cpnval==696721){
            System.out.println("Coupon validated. Applied discount of 10%.");
            cost=amount*qty;
            total=price+((8/100)*price)-((10/100)*price);
            System.out.println("Grand total is:", total);
            System.out.println("Billno is:", billno);
        }
    }
    
