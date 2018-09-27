# Pac-Man
Programmation du jeu Pac-Man dans le cadre de l'APP GEI 799 - Architecture et construction d’un
logiciel à l'université de Sherbrooke - Faculté de Génie

## Setup for a lab computer
- Open Git Bash
- `git clone https://github.com/soukitanass/pacman.git`
- Set environment variable JAVA_HOME to most recent JDK. E.g.: `C:\Program Files\Java\jdk1.8.0_181`
- Restart Git Bash

### Run with Eclipse
- Open Eclipse
- File > New > Java Project
- Enter a project name > Finish
- Right click on project > Import... > General > Existing project into workspace
- Next
- Select project root directory. E.g.: `C:\Users\CIP1234\Desktop\pacman`
- Next, Next, Finish
- Right click on project > Run as > Java Application
- Run button down arrow > `Game`

## Setup for Travis CI
- Go to https://travis-ci.com/
- Sign in with your GitHub account.
- Add the project's repository.
- Activate the owner permissions.
- Create a file .travis.yml on your repository and commit it.
- Add the language to .travis.yml
- Build your project.

## Setup for SonarQube
- Go to https://docs.sonarqube.org/Analyzing+with+SonarQube+Scanner.
- Download SonarQubeScanner
- Add the path of SonarQubeScanner's folder to your variable PATH.
- Check out if the command sonar-scanner work.
- Modify the file sonar-scanner.properties in the directory
 "~/sonar-scanner/conf/"
    - Change the sonar.host.url to http://zeus.gel.usherbrooke.ca:9000
    - Change the sonar.sourceEncoding to UTF-8
- Save changes
- Launch the command `sonar-scanner` 
- Check out the result on `http://zeus.gel.usherbrooke.ca:9000/dashboard?id=agilea18bKey`

## Generate UML class diagrams with robinbird

### Setup
- Make sure your JDK is jdk1.8.0_181. If not:
    - Install https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    - Set JAVA_HOME to this JDK. E.g.: `C:\Program Files\Java\jdk1.8.0_181`
    - To be able to run java, add to path: `%JAVA_HOME%\bin`
- Install GraphViz: https://graphviz.gitlab.io/download/
- Download plantuml.jar: http://plantuml.com/download
    - Move the plantuml.jar to where you want to keep it: E.g.: `~/plantuml/plantuml.jar`
- Test PlantUML setup with: `java -jar ~/plantuml/plantuml.jar -testdot`
- Install robinbird: https://github.com/SeokhyunKim/robinbird#how-to-compile-and-install
    - Precisions for after building robinbird:
        - extract "robinbird\build\distributions\robinbird.zip" somewhere, e.g.: To "C:\Users\Username\robinbird\"
        - Add robinbird to your environment variables: ROBINBIRD_HOME = "C:\Users\Username\robinbird\bin"
        - Add to path: `%ROBINBIRD_HOME%`

### Generate UML class diagram
- Generate the UML text file: `robinbird -r ~/Desktop/pacman/src/main/ > ~/Desktop/pacman_uml.txt`
- Generate the UML picture: `java -jar ~/plantuml/plantuml.jar -verbose ~/Desktop/pacman_uml.txt`


