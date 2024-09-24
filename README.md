# PADMEH_Prototype
Author: David REI @Divadriel

Current version: v1.0

Last date of modification: May, 15, 2020

No further development is planned.

As my Phd thesis ended, I decided to make this piece of research software public. Apart from my thesis subject, it doesn't have any purpose other than showing what I did during the first year of my PhD. 

## Current features
* Configuration tab
  * User Profile pane: static and dynamic profile, load from and save to json files, display to trace
  * Configuration trace: display of any event happening in the configuration tab
  * Session configuration pane: exercise configuration
* Simulation pane
  * Session configuration

## ~~Coming features~~ Obsolete
* Adaptation rules pane
  * Specific rules and modifiers will be editable
  * Compute adaptation rules to exercises and user profile
* Results pane // updated display area and dynamic profile display area
* Import profile, sessions and stats
* Export profile, sessions and stats
* META APP: Main screen with access to different sub applications:
  * User configuration
  * Exercise configuration
  * Session configuration
  * Adaptation rules configuration
  * Automatic simulation of exercises, sessions
  * Manual simulation of exercises, sessions
  * Results
  * Results graphs

## Dependencies
* Java 8
* JavaFX 8 (included in Java 8 JDK)

## Cloning or importing project to IDE
_Instructions for Intellij IDEA_
1. Import or clone the project
2. Open "Run" menu, "Edit configurations"
3. Button "+" top left of the screen, "Add New Configuration"
4. Select "Application", then give it a name (className is OK)
5. Next to the field name, check "Allow parallel run" in order to be able to run several times at once the application
6. On the filed "Main class", select "Main"
7. Apply, then OK to close the window

Steps should be more or less the same for any other IDE. The main idea here is to configure the IDE to recognize the project as an actual application, to trigger JavaFX while running it.

 
  
