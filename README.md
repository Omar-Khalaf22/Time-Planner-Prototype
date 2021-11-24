# Time-Planner-Prototype
This is the (**unfinished**) code of an app that creates to the user his/her own personalized plan for either a day or a week (user's choice). After extensive research, I have created an algorithm (almost done, just needs some final edits) that taps into the user's Circadian Rhythm and incorporate it to the user's plan to optimize his/her day. Circadian rhythms are physical, mental, and behavioral changes that follow a 24-hour cycle. Identifying a user's circadian rhythm will provide us with information about the user's variables (like productivity, creativity, etc.) throughout the day, which will help create a personalized plan that fits the user.

-Programming Languages used in the production of this app: Java (main language), XML(a language extremley cimilar to CSS for design), and some SQL to save permanent information into the phone's local memory

***Description of the files so you can know what you're looking at***

There are **2 main directories** in this repository that you need to be aware of:

1- Location: **"src/main/java/com/ued/timeplanner/"**  ===>To access the java files

2- Location: **"src/main/res/layout/"**   ====>To access the xml files for the app's layout

***Important notes: this is not the final code for the project, but it does include the majority of the main algorithm of the app's function. 
                    The following summary is just a short summary of how the algorithm works, and it doesn't go into the specific details behind how they work.***

**"src/main/java/com/ued/timeplanner/CreateAPlan.java" file is the file that contains the code for the "personalized plan" tailored to the user's Circadian Rhythym"**
A short summary of how the algorithm works:

    1-The algortithm firstly need to figure out the user's Circadian cycle (since these differ from one person to another). According to the user's input, the app determines whether the user is an "early bird" (someone who tends to have more energy during the morning and wakes up relatively early) or a "night owl" (someone who tends to have more energy during the late night and relatively sleeps late). 
    
    2-Based on the previous information, the app creates a set of Ultradian cycles (90 minute intervals derived from the Circadian Rhythm). These 90-minute intervals determine the user's productivity, energy, creativety, efficency, and other similar variables throughout that cycle.
    
    3-The code then looks for the most convienient time intervals for each activity/task the user has to finish that day based on the effort, importance, and the estimated time the task requires. The final result that a user will get will be on a user-friendly looking calendar/schedule that enables him/her to finish these tasks faster and more efficiently than it would take to normally finish the task without the app.
