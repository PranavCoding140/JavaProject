/*Problem Statement 8:-
Given a singly linked list, the task is to remove every kth node of the linked list. 
Assume that k is always less than or equal to the length of the Linked List.
Eg:- Input: LinkedList: 1 -> 2 -> 3 -> 4 -> 5 -> 6, k = 2
Output: 1 -> 3 -> 5 
Explanation: After removing every 2nd node of the linked list, the resultant linked list will be: 1 -> 3 -> 5 .*/
#include<stdio.h>
#include<stdlib.h>
#include<malloc.h>
struct node{
    int data;
    struct node *next;
};
struct node inputll(struct node *next){/*ERROR:- expected identifier, Same mistake of semicolon as Problem 6*/
    struct node *ll1;
    printf("Enter the values of LL:");
    ll1=(struct node*)malloc(sizeof(struct node));
    int data;/*REDECLARATION OF DATA IS IMPORTANT*/
    scanf("%d", data);
}
struct node altll(struct node *ptr, struct node *preptr){
    struct node *ll2, *ll1, *i;
    ptr=0, preptr=ptr-1;
    for(i=ptr;i->next!=NULL;i->next=ptr->next){/*ERROR:- invalid argument -> PATCH:- write i->next=ptr->next instead of i=ptr->next*/
        preptr=ptr;
        ptr=ptr->next;
        preptr->next=ptr->next;
        free(ptr);
    }
    return *ptr;
}
struct node* main(){
    struct node* ll2;
    struct node inputll(struct node *next);
    struct node altll(struct node *ptr, struct node *preptr);
    return ll2;
}