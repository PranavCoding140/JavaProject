/*Twenty-five numbers are entered from the keyboard into an
array. Write a program to find out how many of them are
positive, how many are negative, how many are even and how
many odd. */
#include<stdio.h>
#define SIZE 25
int pos(int arr[],int size);
int neg(int arr[],int size);/*Expected ; in declarations:- SILLY*/
int main()/*ERROR expected attribute before int:-PATCHED*/
{
    int arr[SIZE],i,count=0;
    printf("Enter 25 numbers:");
    scanf("%d", arr);
    printf("number of positive integers:");
    /* Function to check positive numbers*//*ERROR IN ARRAY DEC:- PATCHED*/
    printf("number of negative integers:");
    /*fn for neg*//*ERROR IN ARRAY DEC: These definitions cannot be written in main:-PATCHED*/
    printf("Number of positive integers: %d\n", pos(arr,SIZE));
    return 0;
}
int pos(int arr[], int size)
{/*ERROR IN ARRAY DEC*//*Same error as line 9:-PATCHED*/
    int i, count;
    for(i=0;i<=25;i++){
        if(arr[i]>0){
            count++;
            printf("number of Positive integer:", count);
        }
   else if(arr[i]<0){
    printf("Negative integer.");
   } 
else{
    printf("Neither positive nor negative.");
}}
}
/*ERROR IS: arr[] is not declared. PATCH:- ANY DECLARATIONS CANNOT BE MADE IN MAIN FUNCTION*/