class Person {
    String name;
    int id;

    //Superclass constructor
    Person(String name, int id) {
        this.name = name;
        this.id = id;
        System.out.println("Person constructor called.");
    }
/*Person(){
this.name="unknown";
id=-1;
System.out.println("Superclass Constructor called.");}*/

    // Superclass method
    void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }
}

class Employee extends Person {
    double salary;
// Default constructor always works even though the programmer has not initialized it
    // Subclass constructor using super to call parent constructor
    Employee(String name, int id, double salary) {
        
	super(name, id); // Call to superclass constructor
	this.salary = salary;
	//super(name, id); 
        System.out.println("Employee constructor called.");
    }

    // Overriding method and using super to access parent method
    void displayDetails() {
        super.displayDetails(); // Call to superclass method
        System.out.println("Salary: " + salary);
    }
}

public class SuperDemo {
    public static void main(String[] args) {
        Employee emp = new Employee("Ravi", 101, 50000.0);
        emp.displayDetails();
    }
}//'this' keyword can be called as a Dynamic Constructor.  
