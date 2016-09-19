# SimpleApp

My first experience with Android programming. So far we have a simple app which lets you drag a large triangle around. Next up is to add a scoring system so that you get points the longer you move the shape.

Known bugs: Triangle motion maps to finger motion, rather than actual position

Update 1: App now gets accurate X,Y coordiantes from touchevent, and OpenGL object should now move across screen proportionally to gestures

Update 2: Score system added to game loop! Gain points by  moving the shape across the screen. Next update will implement score saving, and a table of high scores to "Scores" button on main activity. I also plan to add animations and toasts for when you beat your high score.

Update 3: Added persistent score tracker. App now using internal storage to save your latest score, accessible from the Scores activity on the main menu.

Next Update: Track top 10 highest scores, sorted high -> low
