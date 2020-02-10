library("randomForest")

load(paste(getwd(),"/classes/test/analytics/com/tibco/cep/analytics/terr/io/resources/NephritisOfRenalPelvisOrigin.RDA",sep=""));

PredictNephritisOfRenalPelvisOrigin <- function() {
    input <- getDataFrame()

    input$OccurrenceOfNausea <- factor(input$OccurrenceOfNausea, c('FALSE','TRUE'))
    input$LumbarPain         <- factor(input$LumbarPain, c('FALSE','TRUE'))
    input$UrinePushing       <- factor(input$UrinePushing, c('FALSE','TRUE'))
    input$MicturitionPains   <- factor(input$MicturitionPains, c('FALSE','TRUE'))
    input$BurningOfUrethra   <- factor(input$BurningOfUrethra, c('FALSE','TRUE'))

    outStrings <- as.character(predict(NephritisOfRenalPelvisOrigin.rf, input))
    setStrings(outStrings);
}
