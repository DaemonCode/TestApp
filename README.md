# SimpleApp

Name: maybe BeatKing?

My first experience with Android programming.
Creating a game which is essentially a port of the Indie game Osu! to Android.
Match on screen movement cues to music to score points and beat your high score.

Known bugs: Multiple touch events at once will throw off the renderer. Maybe try tracking pointer indexes?

Update 1: App now gets accurate X,Y coordiantes from touchevent, and OpenGL object should now move across screen proportionally to gestures

Update 2: Score system added to game loop! Gain points by  moving the shape across the screen. Next update will implement score saving, and a table of high scores to "Scores" button on main activity. I also plan to add animations and toasts for when you beat your high score.

Update 3: Added persistent score tracker. App now using internal storage to save your latest score, accessible from the Scores activity on the main menu.

Update 4: 
