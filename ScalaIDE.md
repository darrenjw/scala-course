# Installing the Scala IDE

## Useful links

* [ScalaIDE](http://scala-ide.org/) - based on Eclipse
  * [Download](http://scala-ide.org/download/sdk.html)
  * [Documentation](http://scala-ide.org/documentation.html)
* [sbteclipse](https://github.com/typesafehub/sbteclipse) - sbt plugin for eclipse
  * [Documentation](https://github.com/typesafehub/sbteclipse/wiki)
    * [Installation](https://github.com/typesafehub/sbteclipse/wiki/Installing-sbteclipse)
    * [User guide](https://github.com/typesafehub/sbteclipse/wiki/Using-sbteclipse)

## Installation

The ScalaIDE is based on Eclipse, which is a JVM application, and is therefore easy to install as a user without admin/root privilages.

From the [download site](http://scala-ide.org/download/sdk.html), select the version of the IDE for your OS. Unpack this in a convenient place on your system and follow any installation instructions. Running it should be a simple matter of running the eclipse jar. See the [Documentation](http://scala-ide.org/documentation.html) for further details.

To use the ScalaIDE with sbt projects, you must also install the eclipse plugin for sbt, `sbteclipse`.  This should be as simple as adding the line:
```scala
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.1.0")
```
to your `~/.sbt/0.13/plugins/plugins.sbt` file. Create this file if you don't already have it. See the [sbteclipse](https://github.com/typesafehub/sbteclipse) page for further details.

## Using the ScalaIDE with sbt projects

The main thing to understand is that the ScalaIDE needs to know about the structure of your sbt project. This information is encoded in Eclipse project files in the top-level directory of your sbt project (where the file `build.sbt` will often be present). An initial set of project files for an sbt project can be generated using the `eclipse` sbt task provided by the `sbteclipse` plugin.

So, before using the ScalaIDE with a particular sbt project for the first time, first run
```bash
sbt eclipse
```
to analyse the project and create eclipse project files for it. Then start the ScalaIDE. If it asks about a workspace, make sure you select something *different to* the sbt project directory. Then import the project using the *Import Wizard* (under the File menu) to import *Existing Projects into Workspace*. You may need to repeat this process if you make significant changes to the `build.sbt` file.

Once you are up-and-running, Eclipse provides fairly sophisticated IDE functionality. Some commonly used commands include:

* Shift-Ctrl-F - Reformat source file
* Shift-Ctrl-W - Close all windows (from package explorer)
* Shift-Ctrl-P - Go to matching bracket
* Ctrl-Space - Content assist

### Scala worksheet

* Shift-Ctrl-B - Re-run all code

See the [ScalaIDE Documentation](http://scala-ide.org/documentation.html) for further information.




#### eof



