/*Given the head of a SLL find mid of SLL
for ODD nodes, return mid
for EVEN nodes, return mid+1*/
#include<stdio.h>
#include<stdlib.h>

struct node{
    int data;
    struct node *next;
};
struct node *createll(struct node *next){/*ERROR:- conflicting types for createll PATCHED*/
    struct node *new_node=(struct node*)malloc(sizeof(struct node));
    struct node *ptr;
    int num, *slow, *fast;
}
struct node *findmid(struct node *head){/*ERROR:- conflicting types for findmid, PATCHED*/
    struct node *fast, *slow;
    while(fast!=NULL||fast->next!=NULL){
        slow=slow->next;
        fast=fast->next->next;
    }
    return slow;
}
int main(){ 
    int data;
    printf("Enter values of linked list:");
    scanf("%d", data);
    struct node *createll(struct node *next);
    struct node *findmid(struct node *head);
    return data;
}
/*CANNOT CALL A FUNCTION BEFORE DECLARING IT. THIS IS NOT OOPS :)*/