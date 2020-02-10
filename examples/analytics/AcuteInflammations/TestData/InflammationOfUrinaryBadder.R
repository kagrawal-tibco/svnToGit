library("randomForest")

load("C:\\Users\\Ameya\\workspace\\AcuteInflammations\\TestData\\InflammationOfUrinaryBadder.RDA");

PredictInflammationOfUrinaryBadder <- function() {
    rawBooleansData <- getBooleans();
    rawDoublesData <- getDoubles();

    input <- data.frame(
	   TemperatureOfPatient=rawDoublesData,
        OccurrenceOfNausea=factor(rawBooleansData[,1],c('FALSE','TRUE')),
        LumbarPain=factor(rawBooleansData[,2],c('FALSE','TRUE')),
        UrinePushing=factor(rawBooleansData[,3],c('FALSE','TRUE')),
        MicturitionPains=factor(rawBooleansData[,4],c('FALSE','TRUE')),
        BurningOfUrethra=factor(rawBooleansData[,5],c('FALSE','TRUE')))

    # Convert factors into strings
    outStrings <- as.character(predict(InflammationOfUrinaryBadder.rf, input))
    setStrings(outStrings);
}
