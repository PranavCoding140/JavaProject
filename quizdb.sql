CREATE DATABASE quizdb;
USE quizdb;
CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic VARCHAR(20) NOT NULL,
    question VARCHAR(255) NOT NULL,
    option1 VARCHAR(150) NOT NULL,
    option2 VARCHAR(150) NOT NULL,
    option3 VARCHAR(150) NOT NULL,
    option4 VARCHAR(150) NOT NULL,
    answer INT NOT NULL,            
    explanation VARCHAR(255)
);
INSERT INTO questions (topic, question, option1, option2, option3, option4, answer, explanation) VALUES
('Java','Which keyword is used to create an object in Java?','new','create','make','object',0,'The new keyword allocates memory and calls the constructor.'),
('Java','What is the default value of an int instance variable?','null','0','1','undefined',1,'Numeric instance variables are automatically set to 0.'),
('Java','Which of these is NOT a primitive data type?','int', 'char', 'String', 'boolean',2,'String is a class (reference type), not a primitive.'),
('Java','Which keyword stops a class from being inherited?', 'static', 'final', 'private', 'abstract',1,'A final class cannot be extended.'),
('Java','What is method overloading?', 'Same method name with different parameters', 'Same name and parameters in a subclass', 'Two classes with the same name', 'A method calling itself',0,'Overloading is compile-time polymorphism: same name, different parameter lists.')
('Java','Which collection does NOT allow duplicate elements?', 'ArrayList', 'LinkedList', 'HashSet', 'Vector',2,'A Set stores only unique elements.'),
('Java','Which is the correct signature of the main method?', 'public void main()', 'public static void main(String[] args)', 'static main(String args)', 'void main(String[] args)',1,'The JVM looks for exactly this signature to start a program.'),
('Java','Which keyword is used to inherit a class?', 'implements', 'inherits', 'extends', 'super' ,2,'extends is for class inheritance; implements is for interfaces.')
('Java','Which exception occurs when an array index is out of range?', 'ArithmeticException', 'NullPointerException', 'ArrayIndexOutOfBoundsException', 'ClassCastException' ,2,'Accessing an invalid index throws ArrayIndexOutOfBoundsException.'),
('Java','What does the JVM do?', 'Compiles source code to machine code', 'Executes Java bytecode', 'Edits Java programs', 'Stores Java files',1,'javac makes bytecode; the JVM runs that bytecode on any platform.'),
('OS','Which part is the core of an operating system?','Shell','Kernel','Compiler','BIOS',1,'The kernel manages CPU, memory and devices.'),
('OS','Which scheduling algorithm picks the process with the smallest CPU burst?','FCFS','Round Robin','SJF','LRU',2,'Shortest Job First gives the minimum average waiting time.'),
('OS','Which is a necessary condition for deadlock?','Mutual exclusion','Multitasking','Paging','Spooling',0,'The four conditions are mutual exclusion, hold and wait, no preemption and circular wait.'),
('OS','Round Robin scheduling uses a fixed...','Time quantum','Page size','Burst priority','Semaphore',0,'Each process runs for one time quantum, then goes to the back of the queue.'),
('OS','What is virtual memory?','Makes the CPU faster','Uses disk space to extend usable memory','Encrypts memory','Deletes unused processes',1,'Parts of programs are kept on disk and loaded into RAM when needed.'),
('OS','Which of these is a page replacement algorithm?','LRU','SJF','SCAN','FCFS',0,'Least Recently Used replaces the page not used for the longest time.'),
('OS','What is thrashing?','Very high CPU usage','Excessive paging that lowers CPU utilisation','A process finishing early','Disk failure',1,'The system spends more time swapping pages than executing.'),
('OS','What is a semaphore used for?','Process synchronisation','Compiling code','Printing files','Memory allocation',0,'A semaphore is an integer variable that controls access to shared resources.'),
('OS','A process in the ready state is...','Waiting for I/O','Waiting to be given the CPU','Running on the CPU','Finished execution',1,'Ready means it has everything except the CPU.'),
('OS','Which scheduling algorithm is non-preemptive?','Round Robin', 'SRTF', 'FCFS', 'Preemptive priority',2,'In FCFS a process keeps the CPU until it finishes or blocks.')
('DBMS','What does SQL stand for?','Structured Query Language','Simple Query Logic','Sequential Query Language','Standard Question List',0,'SQL is the standard language for relational databases.'),
('DBMS','What does a primary key do?','Stores large files','Uniquely identifies each row','Sorts the table','Links two databases',1,'Primary key values are unique and cannot be NULL.')
('DBMS','Which command removes a table along with its structure?','TRUNCATE','DELETE','DROP','UPDATE',2,'DROP deletes the whole table; DELETE removes only rows.')
('DBMS','What is the purpose of normalization?','Increase redundancy','Reduce redundancy and anomalies','Make queries slower','Encrypt data',1,'Normalization splits tables to avoid duplicate data and update problems.')
('DBMS','What does the A in ACID stand for?','Atomicity','Accuracy','Availability','Authorisation',0,'Atomicity means a transaction happens completely or not at all.'),
('DBMS','What is a foreign key?','Uniquely identifies its own table','Refers to a primary key in another table','Sorts rows','Deletes duplicates',1,'Foreign keys create relationships between tables.'),
('DBMS','Which clause filters groups created by GROUP BY?','WHERE','ORDER BY','HAVING','LIMIT',2,'WHERE filters rows before grouping; HAVING filters groups after.'),
('DBMS','Which join returns only rows that match in both tables?','INNER JOIN','FULL OUTER JOIN','LEFT JOIN','CROSS JOIN',0,'INNER JOIN keeps only matching rows.'),
('DBMS','First Normal Form (1NF) requires...','Atomic (indivisible) column values','No primary key','All columns numeric','Only one column',0,'Each cell must hold a single value, with no repeating groups.'),
('DBMS','Which of these is a DDL command?','SELECT','CREATE','INSERT','UPDATE',1,'DDL (Data Definition Language) defines structure: CREATE, ALTER, DROP.');
