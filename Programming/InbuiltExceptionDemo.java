import java.util.Scanner;
/*
public class InbuiltExceptionDemo {
    public static void main(String[] args) {
	
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Enter First number:");
	int a = sc.nextInt();
	sc.nextLine();
	System.out.println("Enter Second number:");
        int b = sc.nextInt();
	sc.nextLine();

            int result = a / b; // This may throw ArithmeticException in some cases
            System.out.println("Result: " + result);
                
           System.out.println("Program continues inspite of the exception taking place...");
	   System.out.println("There may be multiple actions listed in rest of the program,");

    }
}
*/

// Alternative program to handle such runtime behaviour



public class InbuiltExceptionDemo {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;

        try 
	{
            int result = a / b; // This will throw ArithmeticException
            System.out.println("Result: " + result);
        } 
	catch (ArithmeticException e) 
	{
            System.out.println("You are trying to divide a number by zero, Exception caught: " + e);
        }

        System.out.println("Program continues inspite of the exception taking place......");
	System.out.println("There may be multiple actions listed in rest of the program,");
    }
}




