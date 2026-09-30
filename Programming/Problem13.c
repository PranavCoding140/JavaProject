/*Problem 13 
Given the root of Binary Search Tree(BST) and k>0, find kth smallest element in BST*/
#include<stdio.h>
#include<stdlib.h>
struct node{
    int data;
    struct node *left, *right;
};
struct node *maketree(struct node *tree){
    int data;
    tree=(struct node*)malloc(sizeof(struct node));
    printf("Enter the values of tree:");
    scanf("%d", &data);
    tree->left=NULL;
    tree->right=NULL;
}
struct node *findkth(struct node *tree, int k){/*which traversal technique to be used? answer: inorder*/
    int count=0;
    struct node *ptr;
    printf("Enter the kth element to be found:");
    scanf("%d", k);
    void inorder(struct node *tree, int k){
    if(tree!=NULL){
        while(tree->left!=NULL||tree->right!=NULL){/*WARNING:- COMPARISON BETWEEN POINTER AND INTEGER, PATCHED*/
        inorder(tree->left, k);
        printf("%d", tree->data);
        inorder(tree->right, k);
        }
    }}
}
int main(){
    struct node *tree;
    struct node *maketree(struct node *tree);
    struct node *findkth(struct node *tree, int k);
    return 0;
}