/*Problem 15 Given 2 sorted linked lists of different sizes, we need to merge them into a single list while maintaining their sorted order.*/
#include<stdio.h>
#include<stdlib.h>
struct node{
    struct node *h1;
    struct node *h2;
    int data;
    struct node *next;
};
struct node *createll(struct node){
    struct node *ll1, *ll2;
    ll1=(struct node*)malloc(sizeof(struct node));
    ll2=(struct node*)malloc(sizeof(struct node));
}
struct node *sort(struct node* h1, struct node* h2){
    if(h1==NULL||h2==NULL){
        return h1==NULL?h2:h1;
    }
    if(h1->data<=h2->data){
        h1->next=sort(h1->next,h2);
        return h1;
    }
    else{
        h2->next=sort(h1,h2->next);
        return h2;  
    }
}
int main(){
    int i,data;
    printf("Enter %d sorted linked lists:");
    struct node *sort(struct node* h1,struct node *h2);
    return 0;
}