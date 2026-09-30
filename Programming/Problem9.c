/* Problem 9:-
Given the root of binary tree, Convert it into its mirror. 
A mirror of a binary tree is another tree in which the left and right children of every non-leaf node are interchanged.*/
#include<stdio.h>
#include<stdlib.h>
#include<malloc.h>
struct node{
    int data;
    struct node *left, *right;
};
struct node InterLeaf(struct node* left, struct node* right){
    int data;
    if(left==NULL||right==NULL){
        printf("ERROR! Leaf node encountered");
    }
    else{
        struct node *ptr;
        int temp;
        void swap(int *left, int *right){/*ERROR:- expected a ;*/
        int temp=*left;
        *left=*right;
        *right=temp;
        }
   return left[data];/*ERROR:-part of interleaf not else, PATCHED*/ } 
}
int main(){
    struct node *tree;
    struct node *left;
    struct node *right;
    int data;
    tree=(struct node*)malloc(sizeof(struct node));
    printf("Enter the data for the tree:");
    scanf("%d", data);
    struct node InterLeaf(struct  node* left, struct node* right);/*ERROR, PATCH:-didnt declare with prototype during call*/
    return 0;
}
