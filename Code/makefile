Main.class: Main.java Course.class Student.class Instructor.class
	javac Main.java

Course.class: Course.java Discussion.class Quiz.class
	javac Course.java

Discussion.class: Discussion.java
	javac Discussion.java

Quiz.class: Quiz.java ShortAnswer.class TrueFalse.class MultipleChoice.class
	javac Quiz.java

Question.class: Question.java
	javac Question.java

ShortAnswer.class: ShortAnswer.java Question.class
	javac ShortAnswer.java

TrueFalse.class: TrueFalse.java Question.class
	javac TrueFalse.java

MultipleChoice.class: MultipleChoice.java Question.class
	javac MultipleChoice.java

Account.class: Account.java
	javac Account.java

Instructor.class: Instructor.java Account.class
	javac Instructor.java

Student.class: Student.java Account.class
	javac Student.java

run: Main.class
	java Main

clean:
	rm *.class
