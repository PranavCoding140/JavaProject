/*Problem 11
Given a number x, determine whether the given number is Armstrong's number or not.
 A positive integer of n digits is called an Armstrong number of order n (order is the number of digits) if
abcd... = pow(a,n) + pow(b,n) + pow(c,n) + pow(d,n) + ....
Here a, b, c and d are digits of input number abcd.
eg:- 153=(1^3)+(5^3)+(3^3)*/
#include<stdio.h>
#include<math.h>

int arm(int a,int n){
    int org,rem,sum;
    org=a;
    while(org!=0){
        rem=org%10;
        sum+=pow(rem,n);
        org/=10;
    }
    if((int)sum==a){
        printf("Number is Armstrong number.");
    }
    else{
        printf("Number is not Armstrong number.");
    }
}
int main(){
    int a,n;
    printf("Enter number of digits:");
    scanf("%d", &n);
    printf("Enter a %d digit number:");
    scanf("%d", a);
    arm(a,n);
    return 0;
}/*SEEN AND SOLVED. NEXT LEVEL LOGIC.*/