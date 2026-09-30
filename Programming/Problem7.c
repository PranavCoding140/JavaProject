/*Problem 7:- Given an array, return the count of odd elements and then even elements*/
/*Refer to Let us C Problem 3*/
#include<stdio.h>
#define SIZE 10
int arr[SIZE];
int odd(int arr[SIZE]);
int even(int arr[SIZE]);
int main(){
    int arr1[SIZE];
    printf("Enter the elements into the array:");
    scanf("%d", arr1[SIZE]);
    int even(int arr1[SIZE]);/*ERROR IN DECLARATION:- expected declaration specifiers:-PATCHED*/
    int odd(int arr1[SIZE]);/*ERROR IN DECLARATION:- expected declaration specifiers:-PATCHED*/
    return 0;
}
int odd(int arr[SIZE]){/*ERROR IN DECLARATION:- expected declaration specifiers:-PATCHED*/
    int i;
    printf("The odd elements in the array are:");
    for(i=0;i<=SIZE;i+=2){
        printf("%d", arr[i]);
        return 0;
    }
}
int even(int arr[SIZE]){/*ERROR IN  DECLARATION:- expected declaration specifiers:-PATCHED*/
    int j;
    printf("The even elements in the array are:");
    for(j=1;j=SIZE;j+=2){
        printf("%d", arr[j]);
        return 0;
    }
}