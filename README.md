# Table of Contents
Note
- I committed all my files at the submission date to prevent plagarism and cloned by the others.

---
- [GitHub](#github)
- [Compilation Instructions](#compilation-instructions)
- [Implemented and Working Properly](#implemented-and-working-properly)
- [Implemented but Not Working Properly](#implemented-but-not-working-properly)
- [Features Not Implemented](#features-not-implemented)
- [New Java Classes](#new-java-classes)
- [Modified Java Classes](#modified-java-classes)
- [Unexpected Problems](#unexpected-problems)

## GitHub

---
**Name:** Wong Xuan Kai <br>
**Student ID:** 20495251 <br>
**Link:** https://github.com/xuankaiwong1/CW2024.git

## Compilation Instructions
[Back to Table of Contents](#table-of-contents)

---

### 1. Check if you have installed JDK 21 or later
- Currently, Oracle offers JDK 21 or later for installation.
- JDK 23 is preferred
- You can find the download link here:
  https://www.oracle.com/cis/java/technologies/downloads/#jdk23-windows
  
- Verify your JDK version:
    ```bash
    java -version
    ```

### 2. Make sure you have installed JavaFX ver 21 and above
- Ensure that you are at least using JDK 17 or above as only those versions can support JavaFX 21++.
- You can find the download link here:
  https://gluonhq.com/products/javafx/

### 3. Install IntelliJ IDEA Community Edition 2023.3.4
- IntelliJ IDEA Community Edition was used as the IDE to refactor this project. Install 2023.3.4 or above to avoid any unexpected problems.
- You can download it here (make sure to download the community edition):
  https://www.jetbrains.com/idea/download/?section=windows

### 4. Install Git
  - Windows: Download and install from [Git for Windows](https://gitforwindows.org/)
  - macOS: Install via Homebrew: `brew install git`
  - Linux: `sudo apt-get install git` (Ubuntu/Debian) or `sudo dnf install git` (Fedora)
  - Verify installation:
    ```bash
    git --version
    ```

### 5. Clone the Repository
  - Navigate a green button <> Code in github
  - Copy the GitHub repository URL or Download ZIP
  - Select "Clone Repository" in Intellij
  - Paste the URL from earlier into the URL field in Repository URL
  - Click "Clone", and IntelliJ will clone and open the repository.

![img_1.png](img_1.png)

### 6. Make sure that the compiler is running on JDK 22
  - Go to `File` > `Project Structure` > `Project`.
  - Set the `Project SDK` to JDK 22.
  -  Go to `File` > `Settings` > `Build, Execution, Deployment` > `Compiler` > `Java Compiler`.
  - Set the `Target bytecode version` to 22.


![img_2.png](img_2.png)

![img_3.png](img_3.png)

### 7. Make sure that all dependencies in pom.xml are added and up to 22
  - Open your `pom.xml` file.
  - Ensure the `maven.compiler.source` and `maven.compiler.target` properties are set to 22:
      ```xml
      <properties>
          <maven.compiler.source>22</maven.compiler.source>
          <maven.compiler.target>22</maven.compiler.target>
      </properties>
      ```
  - Add and update dependencies to be compatible with JDK 22. 
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>20.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>20.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-media</artifactId>
            <version>20.0.1</version>
        </dependency>
        <!-- Add other dependencies here -->
    </dependencies>
    ```

### 8. Install dependencies
  - Ensure you have Maven installed. Then, run:
    ```bash
    mvn clean install
    ```
    
### 9. Run the application
  - to compile and run your JavaFX application
    ```bash
    mvn javafx:run
    ```
### 10. Run the Main.java file to start the game
  - It is located in /src/main/java/com/example/demo/controller/Main.java
  - You can click on the green play button on the top right of the screen to run the game.


## Implemented and Working Properly
[Back to Table of Contents](#table-of-contents)

---

### <ins>1. Main Menu</ins>
  - Displaying main menu in game with **start game, settings and exit** button to allow players to navigate key options easily.
![img_4.png](img_4.png)

### **Start Game**
  - Upon selection, it will proceed to the level selection screen.

### **Level Selection**
  - Allow the player to choose levels.
  - Include three levels, with levels two and three initially locked.
  - Unlock levels two and three sequentially upon completing the previous level.
  - Provide a "Back to Main Menu" button to navigate back to the main menu page.
![img_5.png](img_5.png)

### **Settings**
 - Enable players to adjust the background music volume using a slider.
 - Display the key bindings for the control keys.
 - Include a "Back" button to return to the previous page.
![img_6.png](img_6.png)

### <ins>2. Background Music</ins>
  - Include background music in the game to enhance engagement and reduce monotony.
  - Here’s the link to the selected music : Moon Halo (Instrumental) https://www.youtube.com/watch?v=lqPpZ-enm2s

### <ins>3. i-Frames</ins>
  - The user plane will blink when it takes damage
  - Colliding with an enemy will destroy the enemy.
  - During the blinking phase, it becomes invulnerable for a few seconds to prevent losing hearts too quickly.

### <ins>4. New Level</ins>
  - Introduced a new level called LevelTwo.java.
  - This level is slightly more challenging than Level One but less difficult than Level Three (boss level).
  - Features include spawning a new type of stronger enemy and introducing a longer projectile.
![img_7.png](img_7.png)

### <ins>5. New Enemy Types</ins>
  - Features higher health and an increased fire rate.
  - Includes longer projectiles, requiring the user to dodge bullets more carefully.
![img_8.png](img_8.png)

### <ins>6. Pause Menu</ins>
  - Pressing the 'Esc' key pauses the game and displays a pause menu with the following options: **Resume**, **Restart**, **Settings**, **Main Menu**, and **Exit**.
  - Players can pause the game to take a break, continue when ready, or adjust settings as needed.
  - When the game is paused, a dark overlay will appear on the screen to help focus on the pause menu without distractions from the game.
![img_9.png](img_9.png)

### <ins>7. Projectile Delay</ins>
  - Added a projectile delay, enabling an adjustable fire rate for the player.
  - When holding the spacebar, the userplane will not shoot an excessive number of bullets simultaneously, ensuring the game remains challenging and rewarding for players with varying levels of experience.
![img_10.png](img_10.png)

### <ins>8. Added Game Over and Win Screens/ins>
  - Each level now includes a Game Over screen with options to return to the main menu or restart the level.
![img_11.png](img_11.png)
  - A Win screen has been added to each level, featuring options to return to the main menu or proceed to the next level.
![img_12.png](img_12.png)



## Implemented but Not Working Properly
[Back to Table of Contents](#table-of-contents)

---

### Not Applicable (N/A)
  - All implemented features are working properly to prevent errors and warnings.



## Features Not Implemented
[Back to Table of Contents](#table-of-contents)

---
### <ins>1. Sound Effect and Music Background</ins>
  - The sound effect for the player's plane shooting projectiles has not been implemented.
  - The sound effect for clicking buttons has not been implemented.
  - The sound effect for defeating enemies or bosses and for the player's plane has not been implemented.
  - The background music is static, only playing one track instead of changing with the levels.

### <ins>2. Power Ups</ins>
  - Power-ups for upgrading bullets (e.g., increasing shooting speed or firing multiple lines) have not been implemented.
  - The ability to collect hearts for reviving lost hearts has not been implemented.

### <ins>3. Self-Changeable Key Bindings</ins>
  - Unable to change the control keys.
  - The control key changes are not saved during gameplay.
  - When exiting the game, the control keys retain the previous changes instead of resetting to the default.

### <ins>4. Enemy and Boss Attack Patterns</ins>
  - Currently, the enemies and bosses in the game are boring, as they only shoot one type of projectile.
  - Their movement is somewhat predictable, making the game less challenging.
  - The boss is too easy, as it only shoots one type of projectile. It would be more engaging to add varied attack patterns, such as lasers, to increase the difficulty.

### <ins>5. Game Objectives</ins>
  - The game objectives are not displayed during the levels, making it unclear to the player when or how they win.
  - The player is unable to plan tactics while playing the game.
  - For example, the player should know how many enemies need to be defeated to proceed to the next level, or how many waves they must survive to progress

### <ins>5. Health Bars</ins>
  - A health bar should be displayed for enemies and bosses, as players are unaware of their initial health and the number of projectiles needed to defeat them.
  - This would allow players to easily track the health of enemies and bosses, enhancing the gameplay experience.


## New Java Classes
[Back to Table of Contents](#table-of-contents)

---
### 1. ElitePlane.java
#### (com/example/demo/Actor/ElitePlane.java)
  - A new enemy that spawns in Level 2.
  - Has more health and a higher fire rate compared to regular enemies.

### 2. EliteProjectile.java
#### (com/example/demo/Actor/EliteProjectile.java)
  - A new projectile used by the Elite Plane in Level 2.
  - Features a longer range, requiring players to be more cautious while dodging.
  - Has a faster velocity.

### 3. GameState.java
#### (com/example/demo/Level/GameState.java)
  - Singleton pattern implementation designed to track the completion status of game levels
  - Store the completion status of the first and second levels to ensure that the next level is only unlocked after the previous one has been completed in level selection.

### 4. LevelThree.java
#### (com/example/demo/Level/LevelThree.java)
  - The boss level was initially level 2, but with the addition of a new level in between, it has now become level 3.
  - Adjusted the boss's movements accordingly.

### 5. LevelSelection.java
#### (com/example/demo/Screen/LevelSelection.java)
  - Players can select from Level 1, Level 2, and Level 3.
  - When losing a game, players don’t have to start from Level 1; they can simply restart the current level.
  - A new level will only unlock after the previous level is completed.
  - Once all levels are unlocked, players can choose any level to play.

### 6. MainMenu.java
#### (com/example/demo/Screen/MainMenu.java)
  - Display options to start the game, access settings, or quit.
  - Introduce the game to give players an overview before starting.
  - Players can choose to either play the game or quit.


### 7. SettingsScreen.java
#### (com/example/demo/Screen/SettingsScreen.java)
  - Users can modify audio settings and view the key bindings.
  - Provides a smooth transition back to the previous screen (either the game or the main menu) once the user is done adjusting the settings.
  - show(): This method sets up the scene for the settings screen, arranges the UI components in a VBox layout, and sets the scene on the stage.
  - createBackground(): This method loads and sets the background image for the screen, scaling it to fit the screen dimensions.


## Modified Java Classes
[Back to Table of Contents](#table-of-contents)

---
### 1. Boss.java
#### (com/example/demo/Actor/Enemy/Boss.java)
  - Made the shield visible and ensure it follows the boss as the boss moves.
  - Adjusted the hitbox to match the boss's new shape and size.
  - Changed the boss image to update its appearance.

### 2. BossProjectile.java
#### (com/example/demo/Actor/Enemy/BossProjectile.java)
  - Adjusted the image height, projectile velocity, and position to ensure smooth consistency and eliminate any sense of dissonance.

### 3. EnemyPlane.java
#### (com/example/demo/Actor/Enemy/EnemyPlane.java)
  - Adjusted the image height and position, as well as the enemy's health and fire rate in Level 1.
  - Changed the image of the enemy to update its appearance.

### 4. EnemyProjectile.java
#### (com/example/demo/Actor/Enemy/EnemyProjectile.java)
  - Adjusted the image height and projectile velocity.

### 5. UserPlane.java
#### (com/example/demo/Actor/User/UserPlane.java)
  - The user plane can now move not only up and down but also left and right.
  - The fireProjectile() method creates and fires a UserProjectile at specified offsets from the plane's current position, with projectiles fired at regular intervals controlled by a Timeline.
  - When the player takes damage, the plane becomes invincible for a short duration. During this time, the plane blinks and its collision is disabled to prevent further damage.
  - Increased the velocity of the user plane to enhance the gameplay experience and make it more fun.

### 6. UserProjectile.java
#### (com/example/demo/Actor/User/UserProjectile.java)
  - Adjusted the speed of the projectile to make gameplay more exciting.
  - Changed the asset image to update the appearance.

### 7. Controller.java
#### (com/example/demo/controller/Controller.java)
  - The controller is initialized with a Stage, which is used to manage the display of scenes, and a MediaPlayer for managing media such as background music.
  - Depending on the method called (launchLevelOne(), launchLevelTwo(), launchLevelThree()), the controller initializes the corresponding level's scene and starts the game for that level.

### 8. Main.java
#### (com/example/demo/controller/Main.java)
  - The main method calls launch(args), a static method in the Application class, which initializes the JavaFX application and eventually invokes the start method.
  - In the start method, an instance of MainMenu is created, and its start method is called to set up the main menu scene, which serves as the game's entry point where the player can interact with options like "Start Game," "Settings," or "Exit."
  - primaryStage.setResizable(false) is used to make the window size fixed, meaning the user won't be able to resize the application window.

### 9. GameOverImage.java, HeartDisplay.java, ShieldImage.java, WinImage.java
#### (com/example/demo/Image/*)
  - Designed new assets and adjusted their size.
  - In ShieldImage.java, moved the show/hide shield logic from LevelViewLevelThree.java to ShieldImage.java.

### 10. LevelOne.java
#### (com/example/demo/Level/LevelOne.java)
  - Adjusted the kill count required to advance and modified the probability rates.
  - It provides smooth transitions between the game state (win/loss), offering the player options to proceed to the next level, restart, or return to the main menu.

### 11. LevelTwo.java
#### (com/example/demo/Level/LevelTwo.java)
  - Initially a boss level, it is now positioned as a level between Level One and the boss level.
  - Adjusted the kill count required to advance and modified the probability rates.
  - It provides smooth transitions between the game state (win/loss), offering the player options to proceed to the next level, restart, or return to the main menu.

### 12. LevelParent.java
#### (com/example/demo/Level/LevelParent.java)
  - Added a `HashSet` to track input keys, preventing issues such as "lagging" visuals when moving and shooting simultaneously.
  - By tracking pressed keys accurately, the `HashSet` ensures seamless movement and shooting actions without delays or conflicts.
  - Implemented `pauseGame()` and game state checks to allow the player to pause the game, providing the flexibility to temporarily stop and resume gameplay as needed.
  - Game state checks ensure that the game is properly paused or resumed, preventing conflicts or unexpected behavior when the game is inactive.
  - Introduced a pause menu with options to resume, restart, access settings, return to the main menu, or quit, improving the user experience.
  - Added functionality to dynamically restart the game or navigate to the main menu, enhancing gameplay flexibility.
  - Optimized game loop performance by handling input actions and game updates in a timely manner, keeping gameplay smooth.
  - Enhanced collision handling for both user and enemy projectiles, improving the game's responsiveness and accuracy during interactions.
  - Ensured that the game remains responsive during pauses or state changes, offering a consistent and stable experience for the player.

### 13. LevelView.java
#### (com/example/demo/Level/LevelView.java)
  - Adjusted the images position and changed the image of heart

### 14. LevelViewLevelThree.java
#### (com/example/demo/Level/LevelViewLevelThree.java)
  - Renamed from LevelViewLevelTwo -> LevelViewLevelThree.java
  - Adjusted the shield image creation to ensure proper display.
  - Added debug statements to confirm that the shield is correctly initialized and added to the root.

## Unexpected Problems
[Back to Table of Contents](#table-of-contents)

---
### 1. Multiple Pop-Ups
  - At the start, gotoNextLevel() did not properly handle the clearing of assets, which caused the game to crash and led to an excessive number of pop-ups, eventually crashing the PC.
    To resolve this, timeline.stop() was added to halt active timelines before proceeding to the next level.

### 2. Shield not appearing for Boss
  - The shield was initially not visible during the boss stage.
  - It was later identified that this issue stemmed from the order in which JavaFX elements were created, causing the shield to be layered behind the background.
  - To resolve this, the shield was initialized as part of the Boss object, allowing it to correctly follow the boss’s movements while ensuring proper visibility.

### 3. Hitboxes Problems
  - The hitboxes did not align accurately with the images, often being larger than expected, which caused unfair hits to the player.
  - In the boss level, the boss's hitbox was disproportionately large, resulting in projectiles missing the boss visually but still causing damage.
  - To address this, the images were resized individually after being replaced, and the hitbox sizes were adjusted to ensure fair and enjoyable gameplay.

### 4. UserProjectiles Not Aligning with Player's X Position
  - After enabling the player to move left and right, it was observed that the projectiles did not align with the player's updated X position during movement.
  - To resolve this, the player's current X position was incorporated into the projectile's creation logic, ensuring it spawns correctly relative to the player's position.

### 5. Userplane Immediately Takes Damage When Colliding with Enemies
  - Without any "invincibility" frames, the player continuously took damage upon collision, unlike typical arcade games that provide a brief recovery period between hits.
  - To address this, temporary invincibility (i-Frames) was implemented, accompanied by a blinking effect and disabled collision during this period to prevent consecutive damage.

### 6. Music overlapping with each other.
  - Clicking on different buttons or navigating to other pages in the game caused the background music to loop repeatedly, creating overlapping audio playback.
  - To resolve this, checks were added to ensure the music does not restart if it is already playing, and any duplicate playback instances were stopped before initiating new ones.

### 7. Pause Menu
  - Initially, the pause menu was implemented in a separate PauseMenu class. However, despite multiple attempts and solutions, the menu did not display in the game.
  - This was due to issues with node attachment, focus handling, and scene hierarchy.
  - To resolve this, the pause menu was integrated directly into the LevelParent class. The pause menu became part of the same node hierarchy (root) as the game elements. Proper focus handling and visibility control using pausePane.toFront() and event handling. Seamless synchronization with the game state (timeline, isGamePaused).
