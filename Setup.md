# Setup

## Setting up your laptop for the course

It will save time during the course if everyone sets up their laptop with some essential required software in advance. Since Scala runs on the JVM, and the JVM is platform independent, it doesn't really matter what OS is used - in particular, Linux, Windows and Mac should all be fine. The basic requirements are, *in order*:

* Download and install Java 8 (OpenJDK is fine) - *requires root/administrator access*
* Download (or clone) this course code repository
* Download, install and test `sbt`
* Install a Scala-aware editor or IDE
* Download (but don't install) Apache Spark

Further information is given below. For avoidance of doubt, I am *not* assuming that you will have done a system-wide installation of Scala or the Scala compiler, and I don't particularly recommend doing so. It is not necessary if you are using `sbt`.

### Download and install Java

*This step requires root/administrator access to your laptop, so if you don't have this, you will need help from your system administrator*

Versions of Scala prior to 2.12.x worked with Java 6 and Java 7, but the 2.12.x Scala releases require Java 8, as Java 8 introduced a number of features which make it a better target for Scala compilation. I will be using Scala 2.12.1 in the course, so Java 8 is required.

If you are running a Linux (or similar) system, you may prefer to use the [OpenJDK](http://openjdk.java.net/) rather than Oracle's Java. This is fine. On Debian-based Linux systems (including Ubuntu), this should be as simple as:
```bash
sudo apt-get update
sudo apt-get -y install openjdk-8-jdk
```
It should be something similar on other Linux systems. For other OS's, you may want to search the Internet for the best way to install Java on your system. If in doubt, try the Java download page: https://java.com/en/download/

To check whether you have Java installed correctly, type `java -version` into a terminal window. If you get a version number of the form 1.8.x you should be fine.

### Download or clone this course code repository

The course code repository is at: https://github.com/darrenjw/scala-course - on the front page there should be a "Clone or download" button. If you are familiar with git, you should clone the repo on your system (and pull the latest changes the day before the course starts). If you are not familiar with git, you should download and unpack a ZIP of the course repo. If you go for the ZIP option, you should do this roughly two weeks before the course starts, before installing and testing `sbt` (below), but then also download and unpack a fresh ZIP no more than 2 days before the course starts (in case of last-minute changes).

Git users should be able to clone the repo with a command like:
```bash
git clone git@github.com:darrenjw/scala-course.git
```
Others should be able to download [this ZIP file](https://github.com/darrenjw/scala-course/archive/master.zip). Linux users can download from a terminal with a command like:
```bash
wget https://github.com/darrenjw/scala-course/archive/master.zip
```

### Download and install sbt

`sbt` is the Scala build tool. You should download, install and test this before the course starts. The *testing* part is particularly important, as it will download and cache a lot of Scala libraries on your system ready for use during the course. See my [sbt instalation page](sbt/Readme.md) for further details.

### Install a Scala IDE

People starting out with programming in Scala are likely to benefit from writing code using an editor which can provide instant feedback and assistance. There are many possible options here, but it is not possible to provide support for every Scala-aware editor in existence. The course presenter uses [Emacs](https://www.gnu.org/software/emacs/) together with [Ensime](http://ensime.org/editors/emacs/install/), and considers this to be a good option for people already comfortable with the Emacs text editor. However, this is probably not a good option for people unfamiliar with Emacs. For everyone else, the [ScalaIDE](http://scala-ide.org/) (which is based on Eclipse) is probably a safer bet, and the course presenter has some familiarity with it, so should be able to provide basic support. The course presenters will not be able to provide support for any other editor or IDE. It is therefore strongly recommended that participants comfortable with Emacs set up Emacs with Ensime, and that everyone else installs the ScalaIDE. Switching to another editor/IDE in the future will be quite straightforward, but it will save a lot of time during the course if everyone uses one of the two recommended IDEs. See one of the following pages for further details:

* [Installing the ScalaIDE](ScalaIDE.md)
* [Installing Ensime](Ensime.md)

### Download Apache Spark

In case of a poor Internet connection during the course, it will be helpful if everyone could download this [Apache Spark 2.1.0](http://www.eu.apache.org/dist/spark/spark-2.1.0/spark-2.1.0-bin-hadoop2.7.tgz) package to their system in advance. Linux users can download from a terminal with a command like:
```bash
wget http://www.eu.apache.org/dist/spark/spark-2.1.0/spark-2.1.0-bin-hadoop2.7.tgz
```
You should make sure that you have a tool on your system which can unpack a "tgz" file (no issue for Linux users), but there is no need to "install" Spark - we will walk through installation/setup as part of the course.

## Further information

Daniel Spiewak has a nice guide to [getting started in Scala](https://gist.github.com/djspiewak/cb72c41ac335a3a9b28b3307be04aa43) that could be a useful source of additional information. However, I will not be assuming that you have followed all of the advice in his guide. In particular, although the Ammonite REPL is very nice, I understand that there are issues with it on Windows. We will therefore not be using it in this course.


