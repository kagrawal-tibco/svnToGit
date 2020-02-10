# Car Evaluation Data Set
# http://archive.ics.uci.edu/ml/datasets/Car+Evaluation

library("randomForest")

load(paste(getwd(),"/classes/test/analytics/com/tibco/cep/analytics/terr/io/resources/carAcceptabilityModel.RDA",sep=""));

predictAcceptability <- function() {
  	input <- getDataFrame()

    # Since the transfered data frame just works for the primitive data type, as a result,
    # users have to convert it to factor type manually.

    input$buying   = factor(input$buying, levels=c("high","low", "med", "vhigh"))
    input$maint   = factor(input$maint, levels=c("high","low", "med", "vhigh"))
    input$doors   = factor(input$doors, levels=c("2", "3", "4", "5more"))
    input$persons = factor(input$persons,levels=c("2", "4", "more"))
    input$lug_boot = factor(input$lug_boot, levels=c("big","med","small"))
    input$safety   = factor(input$safety, levels=c("high", "low", "med"))

    outStrings <- as.character(predict(car.rf, input))
    setStrings(outStrings);
}
