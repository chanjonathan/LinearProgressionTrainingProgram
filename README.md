# Linear Progression Training Program

A simple X by Y powerlifting program generator that determines the prescribed weight for each successive workout

## Introduction

This application is developed for the average powerlifting novice who is still focused on learning basics, and who are
not knowledgeable enough to create their own training programs and who need a convenient way to log their progress.
This program aims to both automatically create a program and be a convenient way to log progress so that these users
get the challenging tasks of the workout done without the extra effort of programming homework and manual logging.

This project interests me as a novice lifter interested in powerlifting because it is indeed a lot of work to find a
program off of a shady forum, print it out, and write your progress into it at the gym, all the while sweating and
tired. Once a training plan is completed, you're at a loss for what to do again. I was interested in applying software 
design to this to automate the whole process; Using a set of defined rules to continuously generate training program
items that also adjusts to completion or failure of the last set of items.

## User Stories
- As a user, I want to be able to load previously save progress or start a new training program
- As a user, I want to be able to *input* my training maxes to create a **workout**
- As a user, I want the application to add *multiple* **exercises** to a workout
- As a user, I want the application to add *multiple* **sets** to an exercise
- As a user, I want the application to add *multiple* **reps** to a set
- As a user, I want to be able to view the **description** of each exercise in my workout
- As a user, I want to be able to view the prescribed **weight** of each exercise in my workout
- As a user, I want to be able to view the **list** exercises, the sets they contain, the reps they contain
- As a user, I want to be able to the **status** of all the reps in each set of each exercise in my workout
- As a user, I want to be able to mark a repetition as *completed* or *missed*
- As a user, I want to be able to generate new workout with appropriately prescribed weights once my workout has been
completed
- As a user I want to be able to save my current progress and exit the program

## Phase 4: Task 3
Classes for the three major lifts were added with the expectation that they would need exhibit different implementations
while all the same methods were called on them. These never quite got used to their
potentials. The only difference between exercise classes is that they each increment weight by a different
amount. I think that the classes can be simplified down to an upper body exercise class
and a lower body exercise class that extend an abstract exercise class.

Reps may or may not be a very useful class when it can be represented more simply as an integer field inside the set
class. It has certainly complicated methods by increasing the depth of the list of lists that methods needed to 
traverse. However, I think that in its current form, the advantage of having a dedicated class for reps is that they can
represent three different states, and keep track of their order in a list. 

Additionally, the status field of rep can be represented better with a string rather than an integer, which would be
more clear to work with.

The gui class LinearProgressionTrainingProgram can be broken down into different classes representing different panels
and screens to be more easily navigated.
