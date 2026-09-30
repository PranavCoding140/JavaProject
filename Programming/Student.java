/*SPECIALITY HERE IS CONSTRUCTOR OVERLOADING
create a class Student. which has fields for id number, credit hours and points.
also find gpa=points/credit. also display values in student field */
public class Student{
    int id;
    double credit, points, gpa; 
Student(){
    id=0;
    credit=0;
    points=0;
    gpa=points/credit;
}
Student(int i, double credit){
    id=i;
    credit=4;
    points=7;
}
Student(int i,double credit, double gpa, double points){
    id=i;
    credit=4;
    points=16;
    gpa=points/credit;
}
void display(){
    System.out.println("Student ID:"+id+"Credits:"+credit+"GPA:"+gpa+"Points"+points);
}
public static void main(String[] args){
    Student s1=new Student(25108767,52);
    Student s2=new Student(25106785,38);
    Student s3=new Student(25106743,40);
    Student s4=s2;
    s1.display();
    s2.display();
    s3.display();
    s4.display();
}
}
