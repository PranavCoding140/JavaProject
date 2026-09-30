/*Problem 10
Given a string str, the task is to reverse it using stack. 
Follow the steps given below to reverse a string using stack. 

Create an empty stack.
One by one push all characters of string to stack.
One by one pop all characters from stack and put them back to string.*/
#include<stdio.h>
#define SIZE 20
int stack[SIZE],top=-1;
void push(char data){
    printf("Enter the word to be pushed into the stack:");
    scanf("%s", data);
    top++;
    stack[top]=data;
    printf("Word pushed into stack");
}
char result(int top){
    int i; char data;
    for(i=top;i>=-1;i--){
        printf("%d\n", stack[i]);/*ERROR:- how to write the printf statement?, PATCHED*/
    }
    return data; 
}
int main(){
    int top;
    char data;
    void push(char data);
    char result(int top);/*ERROR*/
    return 0;
}