library("randomForest")

load(paste(getwd(),"/classes/test/analytics/com/tibco/cep/analytics/terr/io/resources/airQualityModel.RDA",sep=""));

predictOzone <- function() {
	input <- getDataFrame()

    outDoubles <- predict(ozone.rf, input)

    setDoubles(outDoubles);
}
