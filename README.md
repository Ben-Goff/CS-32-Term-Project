# Weekli: cs0320 Term Project 2021

**Team Members:** Benjamin Goff, Cameron Fiore, Dustin Wu, Miru Yang

Weekli is a productivity-boosting application that creates a more involved
connection between the user and the app. Planners and reminders are productivity tools that work for
some but what they lack is a way react to when things don't go perfectly according to plan
(examples include getting stuck on a certain task or procrastination). Users will enter in their
tasks/assignments and their due date, and the app will tailor a schedule that whittles away at each
task while preventing the user from getting overwhelmed. Furthermore, the user will update the app
with the progress that they have made, and the app will react accordingly, updating the schedule and
providing suggestions on how the user can improve their productivity.

Requirements: Weekli attempts to address the problem of unproductive habits such as
procrastination, spending too much time on a certain task, and leaving distractions open on a
physical/digital workspace.

The first crucial feature is a method of collecting user input on goals, their deadlines, and
schedule of non goal-related commitments; this is how the app collects the data that it will
process. The challenge of this feature will be making the input process as non-tedious and
non-intrusive as possible while still collecting sufficient data.

The second crucial feature is a method of assigning blocks of time towards getting a list of tasks
done, each of which by a certain due date. This is the core scheduling feature of the app, and the
challenge here will be determining the core algorithm to fulfill this feature.

The third is a way for the user to let the app know on the progress that they are making, and a way
for the app to update the schedule according to this progress. This is how the app becomes flexible
to unplanned outcomes. Perhaps the app can update the tasks and due dates and simply remake the
schedule, but it may be necessary to implement multiple "lifestyles" of productivity that work best
for different users.

The final requirement is for Weekli to detect potential issues such as frequent rescheduling, or
the user reporting that little progress has been made on a task, and provide recommendations
accordingly. This is so that the app can help the user improve, rather than simply reinforce,
productivity. Deciding on how the app should detect and categorize such issues is speculated to be
the most difficult task of the project.

**Team Strengths and Weaknesses:**

Benjamin:

- Coding strengths: I think that I have good attention to detail, which makes me good at debugging
  my code. I also think that I am patient when working in groups.

- Coding weaknesses: I came from the CS19 track, so I have limited experience with object-oriented
  programming. In addition, I sometimes struggle with code readability and procrastination.

Cameron:

* Strengths: I like to think I am good at setting up a project's structure, as in creating
  appropriate class heirarchies, interface implentation, inheretance, etc. I am good at choosing
  appropriate data structures for time efficiency. Like Dustin, I also like to think I am good at
  making pseudo code for a given algorithm.
* Weaknesses: Clearly, my biggest weakness is comitting changes. I am not great at finding edge
  cases for testing, and I tend to avoid testing till the end of a project. I am also not very great
  at front end coding
  (e.g. writing html, css, js).

Dustin:

* Strengths: I'd like to think that I'm good at planning out code and getting ideas onto pseudo-code
  and first draft code. I can also keep code organized and readable.
* Weaknesses: Frankly I have an unhealthy aversion to testing, and I basically always write out all
  the code before writing any tests, which isn't a great habit to have when writing complex code. I
  also tend to get picky with how code is organized out and tend to prioritize this over getting
  everything implemented first.

Miru:

* Strengths: I'm pretty familiar with Java and object oriented programming in Java since I've been
  using it on and off for several years now. I also think I generally do an okay to good job with
  commenting my code as I write it and keeping it easily readable.
* Weaknesses: I can have an unfortunate tendency to procrastinate and am not very confident in my
  understanding of data structures and making my code more efficient. I also tend to not test my
  code as thoroughly as I probably should.

## Mentor TA

Sarah Rockhill

sarah_rockhill@brown.edu

## How to Build and Run

In the main project directory, run ```mvn package``` if it hasn't been run already to compile the
project. Then, run ```./run --gui``` to run the backend server, which will run on port 4567.

Then, in a separate terminal window, ```cd``` into the ```frontend/``` directory. Before running the
frontend, run ```npm install``` to install all the dependencies of the project. Now,
run ```npm start``` to start the frontend gui hosted on port 3000.

A browser window should automatically open (and if it doesn't you can go
to ```http://localhost:3000/``` manually), where you will be greeted with a login/signup page. The
database already contains a user with a sample schedule with username "testuser" and password
"testpassword". To make a new user, go to the sign up page and choose a username and password. 
From there, the help instructions and intuitive interface should be sufficient to understand 
where to go from here.
