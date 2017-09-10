## rscala.R

library(rscala)

sc = scala(
 "target/scala-2.12/metropolis-assembly-assembly-0.1.jar",
 scala.home="~/.rscala/scala-2.12.3"
)

met = sc %~% 'Metropolis.chain.take(10000).toArray'

library(smfsb)
mcmcSummary(matrix(met,ncol=1))

## eof

