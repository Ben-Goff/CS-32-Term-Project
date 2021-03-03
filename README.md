# cs0320 Term Project 2021

**Team Members:** Benjamin Goff, Cameron Fiore, Dustin Wu, Miru Yang

**Team Strengths and Weaknesses:**

Benjamin:

<<<<<<< HEAD
- Coding strengths: I think that I have good attention to detail, which makes me good at debugging my code.
I also think that I am patient when working in groups.

- Coding weaknesses: I came from the CS19 track, so I have limited experience with object-oriented programming.
  In addition, I sometimes struggle with code readability and procrastination.

Cameron:

* Strengths: I like to think I am good at setting up a project's structure, as in creating appropriate
  class heirarchies, interface implentation, inheretance, etc. I am good at choosing appropriate data
  structures for time efficiency. Like Dustin, I also like to think I am good at making pseudo code for
  a given algorithm.
* Weaknesses: Clearly, my biggest weakness is comitting changes. I am not great at finding edge cases for 
  testing, and I tend to avoid testing till the end of a project. I am also not very great at front end coding
  (e.g. writing html, css, js).
  
Dustin:

* Strengths: I'd like to think that I'm good at planning out code and getting ideas onto pseudo-code
  and first draft code. I can also keep code organized and readable.
* Weaknesses: Frankly I have an unhealthy aversion to testing, and I basically always write out all
  the code before writing any tests, which isn't a great habit to have when writing complex code. I
  also tend to get picky with how code is organized out and tend to prioritize this over getting
  everything implemented first.

Miru:

* Strengths: I'm pretty familiar with Java and object oriented programming in Java since I've 
  been using it on and off for several years now. I also think I generally do an okay to good job with
  commenting my code as I write it and keeping it easily readable.
* Weaknesses: I can have an unfortunate tendency to procrastinate and am not very confident
  in my understanding of data structures and making my code more efficient. I also tend to not
  test my code as thoroughly as I probably should. 

**Project Idea(s):** _Fill this in with three unique ideas! (Due by March 1)_

### Idea 1

Our first idea is to create a productivity-boosting application that creates a more involved
connection between the user and the app. Planners and reminders are productivity tools that work for
some but what they lack is a way react to when things don't go perfectly according to plan
(examples include getting stuck on a certain task or procrastination). Users will enter in their
tasks/assignments and their due date, and the app will tailor a schedule that whittles away at each
task while preventing the user from getting overwhelmed. Furthermore, the user will update the app
with the progress that they have made, and the app will react accordingly, updating the schedule and
providing suggestions on how the user can improve their productivity.

Requirements: This app attempts to address the problem of unproductive habits such as
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

The final requirement is for the app to detect potential issues such as frequent rescheduling, or
the user reporting that little progress has been made on a task, and provide recommendations
accordingly. This is so that the app can help the user improve, rather than simply reinforce,
productivity. Deciding on how the app should detect and categorize such issues is speculated to be
the most difficult task of the project.

**HTA Approval (dpark20):** Idea approved

### Idea 2

Study scheduler web app: addresses a similar problem to the previous idea but with different 
features:

Problem: With all that students have to balance - classes, homework, extracurriculars, sports,
social commitments - it can be hard to fit everything into a sustainable schedule. Although many
calendar apps exist, they rely on the student finding their optimal schedule on their own, and
holding themselves to the schedule.

Solution: A web app that intelligently creates schedules for a student given their obligations and
predicted time commitments.

Feature #1: Throughout the day, users will be able to log how long scheduled tasks took in
actuality. Using this data, schedules can be improved on in future days with more fine-tuned times.
This feature is important in helping users realize what is realistic for them to complete given time
constraints. In addition, it will help them have a better grasp on their daily schedule. The most
challenging part of this feature is knowing which tasks are similar in nature to each other, and
should have altered time allocation in the future.

Feature #2: Chooses an order of tasks for each day that minimizes burnout. It could do this by
grouping tasks into categories and mixing them together throughout the day. This way, users wont
have 3 hours of straight reading followed by 3 hours of straight problem sets. In my experience,
variety throughout the day helps me stay focused while studying. The most challenging part of this
feature is figuring out how users can separate tasks by type. Options include having a set of
options they can choose from, which could allow us to include more functionality for each, or
allowing users to define their own categories, which would give users the most variety of options.

Feature #3: Promote motivation and efficiency throughout the day. This could be done in a few
different ways. The first would be to tie the app to a chrome extension that would blacklist a set
of websites while the user should be focusing (or whitelist just the ones needed to focus on a
specific task). The second approach is to be able to connect with friends in you same classes /
activities. With these fellow users, you could start a "study streak," where every day you
successfully do your work for your shared class / activity without being distracted (visiting a
blacklisted website in chrome), the streak goes up by one. When one of you fail to do so, the streak
breaks. This social motivation would work similarly to snapchat streaks, which have proven extremely
effective. We would need to reach out to users to see if this second approach would be effective, as
I believe it's viability depends heavily on users study methodology. Would this add more pressure on
their studies, which could decrease productivity, or would it give them the push they need to stay
on track? The most challenging part of this feature would be providing the infrastructure for users
to connect to each other through their profiles.

**HTA Approval (dpark20):** Idea not approved - it needs more algorithmic complexity beyond just the features you have!

Good ideas! Looking forward to seeing what you make.

### Idea 3

**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings

_On your first meeting with your mentor TA, you should plan dates for at least the following
meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 15)_

**4-Way Checkpoint:** _(Schedule for on or before April 5)_

**Adversary Checkpoint:** _(Schedule for on or before April 12 once you are assigned an adversary
TA)_

## How to Build and Run

_A necessary part of any README!_
