/*Problem 12 
Given a linked list. The task is to remove the Nth node from the end of the linked list.*/
#include<stdio.h>
#include<stdlib.h>
struct node{
    int data;
    struct node *next, *prev;
};
struct node *createll(struct node *next, struct node *prev)
{
    struct node *newnode=(struct node*)malloc(sizeof(struct node));
    struct node *ptr;
}
struct node *remnth(struct node *ptr, struct node *next, struct node *prev){
    int n;
    printf("Enter n:");
    scanf("%d", n);
    ptr=0;
    while(ptr->next!=NULL){
        ptr=ptr->next;
        printf("Last node reached");
    }
    while(ptr->prev!=n){
        
        ptr=ptr->prev;
        free(ptr);
        printf("Required node deleted");
    }
}
int main(){
    struct node *createll(struct node *next, struct node *prev);
    struct node *remnth(struct node *ptr, struct node *next, struct node *prev);
    return 0;
}
