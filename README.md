# Advent of Code 2024

Solutions in Java for the Advent of Code in 2024

## Goals

1. Do not spend too much development time on a solution
2. Write tests with the example input to ensure that everything works and keeps working (also to test the second part)
3. Improve readability when writing an algorithm
4. Lower down the complexity and improve the performances when not impacting too much the readability
5. Try some new features of the languages or libraries when possible
6. Use the [Darkyen's Time Tracker](https://plugins.jetbrains.com/plugin/9286-darkyen-s-time-tracker) to inject the time spend of each puzzle

## Automatic tool

This year I created an automation in Java to quickly setup the daily environment.

The code is in [DaySetup.java](src/main/java/aminetti/adventofcode2024/DaySetup.java), it uses [DayXX.java](src/main/java/aminetti/adventofcode2024/dayXX/DayXX.java) and [DayXXTest.java](src/test/java/aminetti/adventofcode2024/dayXX/DayXXTest.java) as templates for the main code and the tests.

When the `DaySetup::main` runs it:

1. downloads the daily input to the `test/resources` folder
2. prepares the initial Java code for the main algorithm 
3. prepares the tests to run the algorithm with the proper input

This repo does follow the automation guidelines on the `/r/adventofcode` [community wiki](https://www.reddit.com/r/adventofcode/wiki/faqs/automation). Specifically:

- Inputs files are downloaded once and locally cached
- It is always possible delete the local copy and ensure to download a new uncorrupted copy
- The `User-Agent` header contains the info about this repository


## Mistakes and wrong assumptions I did while solving puzzles

- **Day 6**: 
  - (part 1) missed that in a single step the guard could find his direction blocked and the direction at 90°, so he must turn by 180°
  - (part 2) checking any possible positions for placing obstacles instead of using the initial path of the guard; it just requires more iterations and time 
- **Day 5**: (part 1) considering that multiple items could be at the same topological order, the problem was simpler; it was a nice game to adapt a standard algorithm for topological order (Kahn's) to generate a data structure (`List<List<String>> partialOrder`) that holds the information about missing relation between items
- **Day 4**: (part 1) forgot to check in diagonal
- **Day 3**: (part 2) checking if `s.startsWith("do")` or `s.startsWith("don't")` and the first always triggers
- **Day 2**: 
  - (part 1) missed to check when two levels are equals
  - (part 2) tried to fix the levels without the bruteforce approach
- **Day 1**: (part 1) forgot to sort