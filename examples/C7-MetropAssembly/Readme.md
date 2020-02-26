# Creating an assembly JAR (for calling from R)

This directory shows how to create an assembly JAR for a Scala project, bundling all dependencies. This can be convenient for deployment reasons, generally, but also for calling from R.

Just typing
```bash
sbt assembly
```
should build the fat JAR. This will be placed in `target/scala-2.12`, along with the regular artefact. Comparing the file sizes is enlightening! The `assembly` SBT task is provided by the relevant line in `project/plugins.sbt`. 

To run the code directly in the JVM, use something like
```bash
java -jar target/scala-2.12/metropolis-assembly-assembly-0.1.jar
```
or
```bash
java -jar target/scala-2.12/metropolis-assembly-assembly-0.1.jar 20
```
The file `rscala.R` shows how to call this from R using the `rscala` library. You can run this with
```bash
R CMD BATCH rscala.R
```
if you have R installed.

