/*Let us C Problem 3
Write a Program that interchanges the odd and even components of array*/ 
#include<stdio.h>
#include<stdlib.h>
#define SIZE 20
int arr[SIZE];
/*ERROR:- Expected Declaration Specifiers, PATCH:- declare int arr[] not int* */
int exc(int arr[], int n){
    printf("Enter the elements:");
    scanf("%d", arr[SIZE]);
    int i, j, temp;
printf("Exchanging elements");
/*Program Logic didnt understand ask teachers for explanation*/
for(i=0;i<n;i++){
    for(j=i+1;j<n;j++){
        if(arr[i]>=0 && arr[j]>=0 && arr[i]%2==0 && arr[j]%2!=0){/*SEEN SOLUTION:- NEED TO STUDY THIS QUICKLY.*/
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            arr[i]=-arr[j];
        }
        else if(arr[i]>=0 && arr[j]>=0 && arr[i]%2!=0 && arr[j]%2==0){
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            arr[j]=-arr[i];
            break;
        }
    }
}
for(i=0;i<n;i++){
    arr[i]=abs(arr[i]);
}
for(i=0;i<n;i++){
    printf("%d", arr[i]);
}
}
int main(){
    int arr[]={1,5,4,3};
    int n=sizeof(arr)/sizeof(arr[0]);
    int exc(int arr[], int n);
    return 0;
}
