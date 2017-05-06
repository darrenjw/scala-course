# gen-csv.R
# Generate a CSV file for subsequent analysis

package=function(somepackage)
{
  cpackage <- as.character(substitute(somepackage))
  if(!require(cpackage,character.only=TRUE)){
    install.packages(cpackage)
    library(cpackage,character.only=TRUE)
  }
}

package(MASS)

write.csv(Cars93,"cars93.csv",row.names=FALSE)



# eof

