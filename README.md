# pacman
Programmation du jeu Pac-Man dans le cadre de l'APP GEI 799 - Architecture et construction d’un
logiciel à l'université de Sherbrooke - Faculté de Génie

## Setup for a lab computer
- Open Git Bash
- `git clone https://github.com/soukitanass/pacman.git`
- Set environment variable JAVA_HOME to most recent JDK. E.g.: `C:\Program Files\Java\jdk1.8.0_181`
- Restart Git Bash

### Run with command line
- Open Git Bash in the Git repository
- Run the app with: `./gradlew.bat run`
- (Optional) Run unit tests with: `./gradlew.bat test`

### Run with Eclipse
- Open Eclipse
- File > Import... > Gradle > Existing Gradle Project
- Next
- Select project root directory. E.g.: `C:\Users\CIP1234\Desktop\pacman`
- Next, Next, Finish
- Run > Run configurations...
- Double click Gradle Project to create a new configuration
- Set the name as you wish. E.g. `Game`
- Set Gradle tasks to: `run`
- Set working directory: E.g.: `C:\Users\CIP1234\Desktop\pacman`
- (Optional) Create another run configuration for unit tests with Gradle task `test`
- Run button down arrow > `Game`

### Run with IntelliJ
- Import project
- Import project from external model > Gradle
- Next
- (Optional) Check the option `Use auto-import` to automatically update the project when it changes
- Check option `Use gradle 'wrapper' task configuration`
- Run > Edit configurations...
- Plus button > Gradle
- Set the name as you wish. E.g. `Game`
- Set Gradle project: `pacman`
- Set tasks: run
- (Optional) Create another run configuration for unit tests with Gradle task `test`
- Run > Run Game
