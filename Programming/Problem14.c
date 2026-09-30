/*Problem 14
Given an integer k and a queue of integers, reverse the order of the first k elements of the queue, leaving the other elements in the same relative order.

Only following standard operations are allowed on the queue. 

enqueue(x): Add an item x to rear of queue
dequeue(): Remove an item from the front of the queue
size(): Returns the number of elements in the queue.
front(): Finds front item.*/
#include<stdio.h>
#define MAX 15
int queue[MAX];
int front=-1,rear=-1,data;
void enqueue(int data){
    int rear;
    printf("Enter the values to be inserted in queue:");
    scanf("%d", data);
    if(rear==MAX-1){
        printf("Queue Overflow!");
    }
    else{
        rear=rear+1;
        queue[rear]=data;
    }
}
int reversek(int queue){
    int k;
    printf("Enter the number of elements you want to be reversed:");
    scanf("%d", k);
    if(k>15){
        printf("SIZE EXCEEDED! CANNOT INPUT K GREATER THAN 15!");
    }
    else{
            int temp, i, z=MAX;
            int queue[MAX];/*ERROR:- tried to initialize a variable length array, not possible. have to intialize queue first, then use z as for loop variable*/
            for(i=0;i<=z;i++){/*ALSO, DECLARE INT QUEUE[MAX] OUTSIDE*/
                queue[i]=temp;
                temp=queue[i];/*MOST RANDOM ERRORS RECORDED, NEED TO DO QUEUE PROGRAMS MORE!!!*/
            }
        }
    }
int main(){
    void enqueue(int data);
    int reversek(int queue);
    return 0;
}