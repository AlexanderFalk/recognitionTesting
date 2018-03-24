# Recognition Testing

#### Author: Alexander Falk
#### Date: 22-03-2018
#### Course: Test

Project 1 has been completed. Changes have been made with JUnit 5, Parameterized testing,
and Java Hamcrest library.

* **Explain the purpose of the Test (what the original test exposed, and what your test exposes)**  
    
  The purpose of the test is to see how many plates will be recognized from the functionality created within the program. It starts with a basic single test, just to see whether the program works as intended. When that has been done it creates another test-case, where it is data-driven. Here we are testing all the plates that we got in our resource folder to see whether we can find a match or not. 
  
* **Explain about Parameterized Tests in JUnit and how you have used it in this exercise.**  
Parameterized tests helps the developers to avoid replicated code. It allows them to run the same test over and over again, just with different values inserted into the test. To make Parameterized tests you need to have a method returning a collection of objects - this will be our data-set. Then you need a constructor, which will function as a "row" of test data. Lastly we need an instance variable that we will use as an overriden value for each step the test performs. 
* **Explain the topic Data Driven Testing, and why it often makes a lot of sense to read test data from a file.**  
Data Driven Testing allow the developers to create dynamic testing instead of static. When you create a common test case you usually end up with hardcoding the values. This can be a good idea, if the test case only checks for a certain value in the exact same case. Sometimes you want to know whether your code works for more than just one case. An example could be that a laboratory that keeps pushing data into a CSV (comma-separated values) file and they want to know whether this data is valid or invalid. Here we could have a data-driven test that uses this CSV file and parses all the values, running them through the program, and tests whether they are valid or invalid. This could be automated and we could have the test case running every hour to keep checking whether we are going in the right direction. Data-Driven Testing is something that most developers will end up doing, since it can help save a lot of time and you can cover more areas with tests.   
* **Your answers to the question; whether what you implemented was a Unit Test or a JUnit Test, the problems you might have discovered with the test and, your suggestions for ways this could have been fixed.**  
Unit Testing is Unit Testing. JUnit is a library for Java, which follows the principles of Unit Testing. Unit Testing can be seen as theory, where JUnit is a practical implementation of the theory. I discovered 
* **The steps you took to include Hamcrest matchers in the project, and the difference they made for the test**  
I would not state that it made a huge difference for the testing. I would state that the Hamcrest library gave a better explanation of the error, when one was occurring. Example when the match of a picture was incorrect. Here the explanation of the error was more in depth. 
