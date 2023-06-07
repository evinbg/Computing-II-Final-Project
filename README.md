# CSCI24000 Final Project
My final project for CSCI 24000 (Computing II)

## What It Is
This project’s main objective is to show the majority of what I have learned in this course
this semester, with the most important concept being object oriented programming. This
project is a course management system that is like a very simplified Canvas. It has a
user login with two different users, an instructor and a student. The instructor can have
multiple courses where they can create / post quizzes and discussions. The student can join
multiple courses using the courses’ names; and take the posted quizzes, reply to the
posted discussions, and rate their courses. This project is a command-line program and
uses Java serialization to save and load data from files.

## Use of Object-Oriented Programming Concepts
* **Abstraction:** There are abstract classes of Account and Question
* **Encapsulation:** The data in all of the instantiated classes are accessed through getter and setter methods
* **Inheritance:** The Instructor and Student classes inherit from the Account class, and the different types of questions inherit from the Question class
* **Polymorphism:** Methods like printMenu() are implemented differently in classes that inherit from an abstract class
* **Composition:** An Instructor “owns” instances of Course classes
* **Aggregation:** A Student “uses” instances of Course classes
