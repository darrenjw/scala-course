# df.R
# Example of processing a CSV-derived data frame using R

df=read.csv("cars93.csv")
print(dim(df))

df=df[df$EngineSize<=4.0,]
print(dim(df))

df$WeightKG=df$Weight*0.453592
print(dim(df))

write.csv(df,"cars93m.csv",row.names=FALSE)

# eof

