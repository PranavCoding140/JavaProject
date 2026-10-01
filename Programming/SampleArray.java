/*Write a Java program that accepts n integers into an array from the user.*/
//RESOLVE ERRORS
import java.util.Scanner;
public class SampleArray{
    public static void main(String[] args){
    int n=10;
    int arr[]= new int[n];
    System.out.print("Array of"+n+"size is initialized");
    SampleArray s1= new SampleArray();
    s1.EnterVaL(arr);     
}
    void EnterVaL(int[] arr){
            int i;
            Scanner sc=new Scanner(System.in);
            System.out.print("Enter the integers");
            for(i=0;i<=10;i++){
            arr[i]=sc.nextInt();
        }
        System.out.print("Number entered successfully.The number is:");
        System.out.print(arr[i]);
        }}  