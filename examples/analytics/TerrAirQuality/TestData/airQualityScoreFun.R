library("randomForest")

#Loading Dataset
load("C:\\Users\\Ameya\\workspace\\TerrAirQuality\\TestData\\airQualityModel.RDA");

airQuality <- function() {
    rawData <- getDoubles()

	input <- data.frame(SolarR=rawData[,3], Wind=rawData[,5], Temp=rawData[,4], Month=rawData[,2], Day=rawData[,1])
    outDoubles <- predict(ozone.rf, input)

    setDoubles(outDoubles);
}