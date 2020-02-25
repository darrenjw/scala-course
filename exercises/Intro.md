# Introduction

## Practical exercises

These exercises are to be undertaken following presentation of the material from Chapter 1 of the course notes.


### 1. SBT and editor setup

Make sure that you have this repo cloned/downloaded on your system, that SBT is installed and working, and that you have a usable editor/IDE. Check through the [laptop set-up instructions](../Setup.md) and make sure you have done everything required. In particular, make sure that typing `sbt run` from the `sbt-test` directory correctly runs the test script.

### 2. Explore the repo

Explore some of the directories in this repo. In particular, find the code fragments automatically extracted from each chapter, and the complete runnable examples. Examine the complete runnable `HelloWorld` example from Chapter 1. Type `sbt run` from the relevant directory to compile and run it. Note that no `build.sbt` file or fancy directory structure is required for a simple single-file project with no dependencies.

### 3. Create your own Scala SBT project

Try *not* to run SBT from the `app-template` directory in order to keep it clean. Copy that directory (and its contents) somewhere on your system and create your own SBT project. Just copy the source code file for the `HelloWorld` example into the correct source code sub-directory and check that it works by first running `sbt` and then `run` from the SBT prompt. Open up the source code in your editor/IDE and edit the message that is printed, save, then `run` again from SBT. Then type `~run` in SBT, go back to your editor and change the message again. As you save the buffer, note that SBT detects that the source file has changed and automatically re-compiles and re-runs the project.

### 4. Use the REPL

From the SBT prompt, type `console` to get a REPL. Enter `1+2` to check it works. 

### 5. Scala basics tour

Start working throu the [basic tour](https://docs.scala-lang.org/tour/tour-of-scala.html) from the official [Scala documentation](http://docs.scala-lang.org/). When you get to the appropriate point in the tour, open Scala Fiddle in another browser tab and interactively explore Scala in the browser. Try to understand as much as possible as you go along. You should only attempt the first two or three sections for now. If you get these finished, browse some of the other official Scala documentation. You will want to bookmark this material to return to and work through some additional sections later.


#### eof


