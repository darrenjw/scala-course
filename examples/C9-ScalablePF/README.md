# A scalable particle filter in Scala

Code examples for the blog post: 

https://darrenjw.wordpress.com/2016/07/22/a-scalable-particle-filter-in-scala/


Note that this repo contains everything that is needed to build and run the Scala code examples on any system that has Java installed. Any recent version of Java is fine. You do not need to "install" Scala or any Scala "packages" in order to run the code. If you have Java and a decent internet connection, you are good to go. This is one of the benefits of Scala - you can run it anywhere, on any system with a Java installation.

To check if you have Java installed, just run:

```bash
java -version
```

at your system command prompt. If you get an error, Java is absent or incorrectly installed. Installing Java is very easy on any platform, but the best way to install it depends on exactly what OS you are running, so search the internet for advice on the best way to install Java on your OS.

The code uses `sbt` (the simple build tool) as the build tool. The sbt launcher has been included in the repo for the benefit of those new to Scala. It should be possible to run sbt from this directory by typing:

```bash
..\sbt
```

on Windows (which should run `..\sbt.bat`), or

```bash
../sbt
```

on Linux and similar systems (including Macs). If you want to be able to experiment with Scala yourself, you should copy the script and the file `sbt-launch.jar` to the same directory somewhere in your path, but this isn't necessary to run these examples.

The sbt launcher script will download and run sbt, which will then download scala, the scala compiler, scala standard libraries and all dependencies needed to compile and run the code. All the downloaded files will be cached on your system for future use. Therefore, make sure you have a good internet connection and a bit of free disk space before running sbt for the first time.

Assuming you can run sbt, just typing `run` at the sbt prompt will compile and run the example code. Typing `test` will run some tests. Typing `console` will give a Scala REPL with a properly configured classpath including all dependencies. You can type scala expressions directly into the REPL just as you would in your favourite dynamic math/stat language. Type `help` at the sbt prompt for help on sbt. Type `:help` at the Scala REPL for help on the REPL.








