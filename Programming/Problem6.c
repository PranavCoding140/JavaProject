/*Problem 6:- Given an ll, reverse the list and return new head*/
#include<stdio.h>
#include<stdlib.h>
struct node{
    int data;
    struct node *next;
    /*Declared LL using struct node*/};
struct node takell(struct node *next){/*ERROR:- expected identifier before struct, PATCHED:- missed semicolon in previous line*/
    struct node *ll;
    ll=(struct node*)malloc(sizeof(struct node));/*WARNING:- ignoring return value of malloc, PATCHED:-wrong declaration*/
    int data;
    printf("Enter the data in linked list:");
    scanf("%d", data);
}
struct node revll(struct node *start){
    struct node *ptr;/*PATCHED:- Declared *start pointer outside*/
    ptr=start;
    while(ptr->next!=NULL){
        ptr=ptr->next;
    }
    struct node ll2(struct node *ptr){
        struct node *j;
        for(j=ptr;j->next!=NULL;j--){
            printf("\t%d",j->data);  /*ERROR:- incompatible type argument for printf, PATCHED:- wrong printf statement*/
        }
        return *start;/*ERROR:- expected expression before struct, PATCHED:- Write *start not start*/
 }
}
struct node* main(){
    int data;
    struct node *head;
    printf("Enter the elements of the LL:");
    scanf("%d", data);/*ERROR:-data undeclared:-PATCHED*/
    struct node takell(struct node *next);
    struct node revll(struct node *start); 
    return head;/*ERROR:- head undeclared:-PATCHED*/
}