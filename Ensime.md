# Installing Ensime

## Useful links

Some useful links for using Emacs and Ensime with sbt:

* [Ensime](http://ensime.org/)
  * [Learning Emacs](http://ensime.org/editors/emacs/learning)
  * [Installing with Emacs](http://ensime.org/editors/emacs/install/)
  * [Sbt plugin for Ensime](http://ensime.org/build_tools/sbt/)
  * [Emacs Ensime User Guide](http://ensime.org/editors/emacs/userguide/)

## Installation

I am assuming that you are already familar with Emacs and have it installed on your system. If this is not the case, I recommend using the [Scala IDE](ScalaIDE.md) for the short course, as Emacs has a fairly steep learning curve. You can always investigate Emacs and Ensime later once you are more familiar with Scala.

Ensime is installed using [MELPA](http://melpa.org/) - the Emacs package archive. If you don't currently use MELPA, you must first enable it by copying a snippet of code like:
```lisp
;; MELPA package manager
(require 'package)
(setq
 package-archives '(("gnu" . "http://elpa.gnu.org/packages/")
                    ("org" . "http://orgmode.org/elpa/")
                    ("melpa" . "http://melpa.org/packages/")
                    ("melpa-stable" . "http://stable.melpa.org/packages/"))
 package-archive-priorities '(("melpa-stable" . 1)))

(package-initialize)
(when (not package-archive-contents)
  (package-refresh-contents)
  (package-install 'use-package))
(require 'use-package)
```
into your `.emacs` or `.emacs.d/init.el` file. See the [Learning Emacs](http://ensime.org/editors/emacs/learning) page for details.

Once you have MELPA set up, installing Ensime should be as simple as copying the snippet:
```lisp
(use-package ensime
  :ensure t
  :pin melpa-stable)
```
to the end of your init file and restarting Emacs, but see the [Installing with Emacs](http://ensime.org/editors/emacs/install/) page for further details.

To use Ensime with sbt, you also need to install the Ensime plugin for sbt. This should be as simple as adding the line:
```scala
addSbtPlugin("org.ensime" % "sbt-ensime" % "1.12.5")
```
to your `~/.sbt/0.13/plugins/plugins.sbt` file. Create this file if you don't already have it. See the [Sbt plugin for Ensime](http://ensime.org/build_tools/sbt/) page for further details.

## Using Ensime

The main thing to understand is that Ensime needs to know about the structure of your sbt project. This information is encoded in a file `.ensime` in the top-level directory of your sbt project (where the file `build.sbt` will often be present). An initial `.ensime` file for an sbt project can be generated using the `ensimeConfig` sbt task provided by the `sbt-ensime` plugin.

So, before using Emacs/Ensime with a particular sbt project for the first time, first run
```bash
sbt ensimeConfig
```
to analyse the project and create a `.ensime` file for it. You should probably re-run this after editing `build.sbt` or other build configuration files. Then start emacs with a command like `emacs src/main/scala/blah/*.scala &`. This will start up emacs and some basic syntax highlighting will be provided by `scala-mode`. However, you still need to start up Ensime with `M-x ensime`. Once you are up-and-running, Ensime provides fairly sophisticated IDE functionality. Some commonly used commands include:

* M-x ensime - Start up Ensime
* C-c C-v f - Reformat source code in this buffer
* C-c C-b c - sbt compile
* C-c C-b r - sbt run

See the [Emacs Ensime User Guide](http://ensime.org/editors/emacs/userguide/) for further details.



#### eof

