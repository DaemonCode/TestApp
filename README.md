# SimpleApp

Name: maybe BeatKing?

My first experience with Android programming.
Creating a game which is essentially a port of the Indie game Osu! to Android.
Match on screen movement cues to music to score points and beat your high score.

Known bugs: Multiple touch events at once will throw off the renderer. Maybe try tracking pointer indexes?

Update 1: App now gets accurate X,Y coordiantes from touchevent, and OpenGL object should now move across screen proportionally to gestures

Update 2: Score system added to game loop! Gain points by  moving the shape across the screen. Next update will implement score saving, and a table of high scores to "Scores" button on main activity. I also plan to add animations and toasts for when you beat your high score.

Update 3: Added persistent score tracker. App now using internal storage to save your latest score, accessible from the Scores activity on the main menu.

Update 4: Big Update!

Added several features and fixed a lot of bugs.
Objects in OpenGL are now rendered at the point of touch, rather than being dragged around from the center.
Fixed a bug where the field of view affected the render point of opengl objects
Added a circle class so we can render a more intuitive touch point, rather than a triangle (its made of triangles though)
If you haven't saved a score yet, the Scores page should show "No Scores Available" rather than throw an exception

Added developer mode to create game levels
Created a MotionGroup class and linked list data structure to store input from dev mode
Added functionality to write this data structure to a level file

Future plans:
Add functionality to read level file into a MotionGroup data structure to be used for level creation
Add SQLlite Database for high scores
Use MotionGroup structure to render lines and create a level
Add level selection page to load levels created in developer mode
Add sign in functional
