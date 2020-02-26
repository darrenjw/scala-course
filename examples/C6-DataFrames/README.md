# Scala data tables and frames

Code samples associated with my blog post "Scala data frames and tables" which can be found at:

https://darrenjw.wordpress.com/2015/08/21/data-frames-and-tables-in-scala/

See the post for explanation of the examples.

Note, however, the addition here of the [Smile](http://haifengl.github.io/) `DataFrame` example, which is a welcome development.

Note that you must run the script r/gen-csv.R in an R session FIRST, in order to generate the CSV file required for the Scala examples.

If you have R installed, then:
```bash
cd r
R CMD BATCH gen-csv.R
```
should generate the file `cars93.csv` required by all of the scripts.

The other directories contain Scala examples. Each can be run by going in to the relevant directory and doing `sbt run`, except for the Spark example, which needs to be run in a Spark shell (to be covered later).



