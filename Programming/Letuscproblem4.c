/*Let Us C Problem 4
Write a Program to copy the contents of one array to another in reverse order*/
#include<stdio.h>
#define SIZE 21
int arr2[SIZE]; 
int arr1[SIZE];
int main(){
    printf("Add elements to array:");
    scanf("%d", arr1[SIZE]);
    int arrev(int arr1[SIZE]);/*WARNING:- Old style definition*/
}
int arrev(int arr1[]){
    int temp, i, j;
    for(i=0;i<=SIZE;i++){
        for(j=SIZE;j>=0;j--){
            arr1[i]=temp;/*ERROR:- Subscripted-[] value is neither array nor pointer FIX:- Repaired declaration in arrev fn from arr[] to int arr1[]*/
            temp=arr2[j];
        }
    }
   printf("Reversed array:", arr2); 
}/*Ask is this is correct logic or not*/