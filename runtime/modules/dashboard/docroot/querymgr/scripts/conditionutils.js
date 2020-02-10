// - General Functions 

    function transfervalue(conditionasstring){
        window.returnValue = conditionasstring;
        window.close();
        return true;
    }
    
    function prepCondition(datatype){
        if (datatype == "boolean"){
            //do nothing;
        }
        else if (datatype == "int"){
            
        }
        else if (datatype == "long"){
            
        }
        else if (datatype == "float"){
            
        }
        else if (datatype == "double"){
            
        }
        else if (datatype == "short"){
            
        }
        else if (datatype == "datetime"){
            fixTheDaysDropDown();
        }
        else if (datatype == "string"){
            document.actualinputform.conditionvaluefld.focus();
        }
    }
    
    function validateCondition(datatype){
        if (datatype == "boolean"){
            return validateBooleanCondition();
        }
        else if (datatype == "int"){
            return validateIntegerCondition();
        }
        else if (datatype == "long"){
            return validateIntegerCondition();
        }
        else if (datatype == "float"){
            return validateDecimalCondition();
        }
        else if (datatype == "double"){
            return validateDecimalCondition();
        }
        else if (datatype == "short"){
            return validateIntegerCondition();
        }
        else if (datatype == "datetime"){
            return validateDateTimeCondition();
        }
        else if (datatype == "string"){
            return validateStringCondition();
        }    
        return true;
    }
    
    function updateCondition(datatype,actionURL, token, sessionid, params){
        if (datatype == "boolean"){
            updateBooleanCondition(params);
        }
        else if (datatype == "int"){
            updateNumericCondition(params);
        }
        else if (datatype == "long"){
            updateNumericCondition(params);
        }
        else if (datatype == "float"){
            updateNumericCondition(params);
        }
        else if (datatype == "double"){
            updateNumericCondition(params);
        }
        else if (datatype == "short"){
            updateNumericCondition(params);
        }
        else if (datatype == "datetime"){
            updateDateTimeCondition(params);
        }
        else if (datatype == "string"){
            updateStringCondition(params);
        }
        var callbackfn = function(responseText){
            if (responseText != undefined){
                transfervalue(responseText);
            }
        };            
        try {
            sendRequest(actionURL, token, sessionid, "updatefieldcondition", params, callbackfn);
        } catch (err) {
            //do nothing;
        }           
    }
    
    function handleOperatorChange(datatype){
        if (datatype == "boolean"){
            //do nothing;
        }
        else if (datatype == "int"){
            numericOperatorChanged();
        }
        else if (datatype == "long"){
            numericOperatorChanged();
        }
        else if (datatype == "float"){
            numericOperatorChanged();
        }
        else if (datatype == "double"){
            numericOperatorChanged();
        }
        else if (datatype == "short"){
            numericOperatorChanged();
        }
        else if (datatype == "datetime"){
            datetimeOperatorChanged();
        }
        else if (datatype == "string"){
            //do nothing
        }        
    }

//- Boolean Functions

    function validateBooleanCondition(fieldname){
        if (document.actualinputform.radiotrue.checked == false && document.actualinputform.radiofalse.checked == false){
            alert("Please select one of the options...");
            return false;
        }
        return true;
    }
    
    function updateBooleanCondition(params){
        if (document.actualinputform.radiotrue.checked){
            params.push(new Array("value",document.actualinputform.radiotrue.value));
        }
        else if (document.actualinputform.radiofalse.checked){
            params.push(new Array("value",document.actualinputform.radiofalse.value));
        }    
    }
    
// - String Functions 

    function validateStringCondition(){
        if (document.actualinputform.operators.selectedIndex == -1){
            alert("Please select a valid operator");
            document.actualinputform.operators.focus();
            return false;
        }
        var conditionvaluefld = document.actualinputform.conditionvaluefld;
        if (document.actualinputform.conditionvaluefld.value == ""){
            alert("Please enter some value...");
            conditionvaluefld.focus();
            return false;
        }
        return true;
    }    
    
    function updateStringCondition(params){
        params.push(new Array("operator",document.actualinputform.operators[document.actualinputform.operators.selectedIndex].value));
        params.push(new Array("value",document.actualinputform.conditionvaluefld.value));
    }

// - Numeric Functions

    function numericOperatorChanged(){
        var value = document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value;
        var singleconditiondiv = document.getElementById("singleconditiondiv");
        var dualconditiondiv = document.getElementById("dualconditiondiv");
        if (value == "Is In The Range"){
            singleconditiondiv.style.display = "none";
            dualconditiondiv.style.display = "";
        }
        else{
            singleconditiondiv.style.display = "";
            dualconditiondiv.style.display = "none";
        }
    }
    function validateIntegerCondition(){
        if (document.actualinputform.operators.selectedIndex == -1){
            alert("Please select a valid operator");
            document.actualinputform.operators.focus();
            return false;
        }
        if (document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value !=  "Is In The Range"){
            firstvalue = document.actualinputform.conditionvalue0fld.value;
            if (isNumeric(firstvalue) == false){
                alert("Please enter a valid integer input...");
                document.actualinputform.conditionvalue0fld.focus();
                return false;
            }
        }
        else{
            firstvalue = document.actualinputform.conditionvalue1fld.value;
            if (isNumeric(firstvalue) == false){
                alert("Please enter a valid integer input...");
                document.actualinputform.conditionvalue1fld.focus();
                return false;
            }
            secondvalue = document.actualinputform.conditionvalue2fld.value;
            if (isNumeric(secondvalue) == false){
                alert("Please enter a valid integer input...");
                document.actualinputform.conditionvalue2fld.focus();
                return false;
            }
            firstnumber = parseInt(firstvalue);
            secondnumber = parseInt(secondvalue);
            if (firstnumber >= secondnumber){
                alert("Please enter valid range values...");
                return false;
            }
        }
        return true;
    }
    
    function validateDecimalCondition(){
        if (document.actualinputform.operators.selectedIndex == -1){
            alert("Please select a valid operator");
            document.actualinputform.operators.focus();
            return false;
        }
        if (document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value !=  "Is In The Range"){
            firstvalue = document.actualinputform.conditionvalue0fld.value;
            if (isDecimal(firstvalue) == false){
                alert("Please enter a valid decimal input...");
                document.actualinputform.conditionvalue0fld.focus();
                return false;
            }
        }
        else{
            firstvalue = document.actualinputform.conditionvalue1fld.value;
            if (isDecimal(firstvalue) == false){
                alert("Please enter a valid decimal input...");
                document.actualinputform.conditionvalue1fld.focus();
                return false;
            }
            secondvalue = document.actualinputform.conditionvalue2fld.value;
            if (isDecimal(secondvalue) == false){
                alert("Please enter a valid decimal input...");
                document.actualinputform.conditionvalue2fld.focus();
                return false;
            }
            firstnumber = parseFloat(firstvalue);
            secondnumber = parseFloat(secondvalue);
            if (firstnumber >= secondnumber){
                alert("Please enter valid range values...");
                return false;
            }
        }
        return true;
    }
    
    function updateNumericCondition(params){
        params.push(new Array("operator",document.actualinputform.operators[document.actualinputform.operators.selectedIndex].value));
        if (document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value !=  "Is In The Range"){
            params.push(new Array("value",trim(document.actualinputform.conditionvalue0fld.value)));
        }
        else {
            params.push(new Array("value",trim(document.actualinputform.conditionvalue1fld.value)));
            params.push(new Array("value",trim(document.actualinputform.conditionvalue2fld.value)));
        }
    }

//- Date Functions 

    function datetimeOperatorChanged(){
           var value = document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value;
        var firstvaluerow = document.getElementById("firstvaluerow");
        var rangegap = document.getElementById("rangegap");
        var secondvaluerow = document.getElementById("secondvaluerow");
        var thirdvaluerow = document.getElementById("thirdvaluerow");
        var historicalrangegap = document.getElementById("historicalrangegap");
        var fourthvaluerow = document.getElementById("fourthvaluerow");
        var fifthvaluerow = document.getElementById("fifthvaluerow");
        
        if (value == "Is In The Last"){
            firstvaluerow.style.display = "none";
            secondvaluerow.style.display = "none";
            thirdvaluerow.style.display = "none";
            fourthvaluerow.style.display = "none";
            fifthvaluerow.style.display = "";
            rangegap.style.display = "none";        
            historicalrangegap.style.display = "none";
            
            firstvaluerow.style.visibility = "hidden";
            secondvaluerow.style.visibility = "hidden";
            thirdvaluerow.style.visibility = "hidden";
            fourthvaluerow.style.visibility = "hidden";
            rangegap.style.visibility = "hidden";        
            historicalrangegap.style.visibility = "hidden";
            
            fifthvaluerow.style.visibility = "visible";
        } else if (value == "Is In The Historical Range"){
            firstvaluerow.style.display = "none";
            secondvaluerow.style.display = "none";
            thirdvaluerow.style.display = "";
            fourthvaluerow.style.display = "";
            fifthvaluerow.style.display = "none";
            rangegap.style.display = "none";        
            historicalrangegap.style.display = "";
            firstvaluerow.style.visibility = "hidden";
            secondvaluerow.style.visibility = "hidden";
            thirdvaluerow.style.visibility = "visible";
            fourthvaluerow.style.visibility = "visible";
            fifthvaluerow.style.visibility = "hidden";
            rangegap.style.visibility = "hidden";        
            historicalrangegap.style.visibility = "visible";
        }else if (value == "Is In The Range"){
            firstvaluerow.style.display = "";
            secondvaluerow.style.display = "";
            thirdvaluerow.style.display = "none";
            fourthvaluerow.style.display = "none";
            fifthvaluerow.style.display = "none";
            rangegap.style.display = "";        
            historicalrangegap.style.display = "none";
            

            firstvaluerow.style.visibility = "visible";
            secondvaluerow.style.visibility = "visible";
            thirdvaluerow.style.visibility = "hidden";
            fourthvaluerow.style.visibility = "hidden";
            fifthvaluerow.style.visibility = "hidden";
            rangegap.style.visibility = "visible";        
            historicalrangegap.style.visibility = "hidden";

        }else if (value == "Is Today"){
            //It is for Today
            //Hide all rows
            firstvaluerow.style.display = "none";
            secondvaluerow.style.display = "none";
            thirdvaluerow.style.display = "none";
            fourthvaluerow.style.display = "none";
            fifthvaluerow.style.display = "none";

            firstvaluerow.style.visibility = "hidden";
            secondvaluerow.style.visibility = "hidden";
            thirdvaluerow.style.visibility = "hidden";
            fourthvaluerow.style.visibility = "hidden";
            fifthvaluerow.style.visibility = "hidden";
        }else{
            // it is for "Is After" and "Is Before"  and "Is"
            firstvaluerow.style.display = "";
            secondvaluerow.style.display = "";
            thirdvaluerow.style.display = "none";
            fourthvaluerow.style.display = "none";
            fifthvaluerow.style.display = "none";

            firstvaluerow.style.visibility = "visible";
            secondvaluerow.style.visibility = "hidden";
            thirdvaluerow.style.visibility = "hidden";
            fourthvaluerow.style.visibility = "hidden";
            fifthvaluerow.style.visibility = "hidden";
        }
    }

    function validateDateTimeCondition(){
        if (document.actualinputform.operators.selectedIndex == -1){
            alert("Please select a valid operator");
            document.actualinputform.operators.focus();
            return false;
        }
        var operator = document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value;
        if (operator == "Is In The Range") {
            var fromDateValue = getDateInMilliSeconds('0');
            var toDateValue = getDateInMilliSeconds('1');
            if (fromDateValue >= toDateValue){
                alert("Please enter valid date range...");
                return false;
            }
        }
        if (operator == "Is In The Last") {
            var lastduration = document.actualinputform.lastduration.value;
            if (isNumeric(lastduration) == false){
                alert("Please enter a valid duration...");
                document.actualinputform.lastduration.focus();
                return false;
            }            
        }
        if (operator == "Is In The Historical Range") {
            var startduration = document.actualinputform.startduration.value;
            if (isNumeric(startduration) == false){
                alert("Please enter a valid start duration...");
                document.actualinputform.startduration.focus();
                return false;
            }     
            var endduration = document.actualinputform.endduration.value;
            if (isNumeric(endduration) == false){
                alert("Please enter a valid end duration...");
                document.actualinputform.endduration.focus();
                return false;
            } 
        }   
        return true;
    }

    function changedays(index){
        yeardropdown = eval("document.actualinputform.year"+index)
        year = yeardropdown.options[yeardropdown.selectedIndex].value;
        monthdropdown = eval("document.actualinputform.month"+index)
        month = monthdropdown.options[monthdropdown.selectedIndex].value;
        daysdropdown = eval("document.actualinputform.day"+index)
        if (month == 1){
            //Month of Feb
            removeOption(daysdropdown, 30, 31);
            removeOption(daysdropdown, 29, 30);
            if (isLeapYear(year)){
                addOption(daysdropdown, 28, "29", 29);
            }
            else{
                removeOption(daysdropdown, 28, 29);
            }
        }
        else if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11){
            //Month has 31 days
            addOption(daysdropdown, 28, "29", 29);
            addOption(daysdropdown, 29, "30", 30);
            addOption(daysdropdown, 30, "31", 31);
        }
        else{
            addOption(daysdropdown, 28, "29", 29);
            addOption(daysdropdown, 29, "30", 30);
            removeOption(daysdropdown, 30, 31);
        }
    }

    function isLeapYear(year){
        if (year % 4 == 0){
            if (year % 100 == 0){
                if (year % 400 == 0){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    function addOption(combobox, index, label, value){
        try{
            var optValue = combobox.options[index].value;
            if (optValue == value){
                return;
            }
        }catch(e){
        }
        var oOption = document.createElement("OPTION");
        combobox.options.add(oOption);
        oOption.innerText = label;
        oOption.value = value;
    }

    function removeOption(combobox, index, value){
        try{
            var optValue = combobox.options[index].value;
            if (optValue == value){
                combobox.options.remove(index);
            }
        }catch(e){
        }
    }

    function getDateAsString(index){
        var year = getSelectedValue('document.actualinputform',"year" + index);
        var month = getSelectedValue('document.actualinputform',"month" + index);
        var day = getSelectedValue('document.actualinputform',"day" + index);
        var hour = parseInt(getSelectedValue('document.actualinputform',"hour" + index));
        var minute = getSelectedValue('document.actualinputform',"minutes" + index);
        var ampm = parseInt(getSelectedValue('document.actualinputform',"ampm" + index));
        hour = hour + ampm*12;
        if (hour < 10) {
            return year + "-" + month + "-" + day + " 0" + hour + ":" + minute;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }

    function getDateInMilliSeconds(index){
        var year = parseInt(getSelectedValue('document.actualinputform',"year" + index));
        var month = parseInt(getSelectedValue('document.actualinputform',"month" + index));
        var day = parseInt(getSelectedValue('document.actualinputform',"day" + index));
        var hour = parseInt(getSelectedValue('document.actualinputform',"hour" + index));
        var minute = parseInt(getSelectedValue('document.actualinputform',"minutes" + index));
        var ampm = parseInt(getSelectedValue('document.actualinputform',"ampm" + index));
        if (hour == 12) {
            if (ampm == 0){  
                //we are dealing with midnight 
                hour = 0;
            }
            else {
                //we are dealing with noon
                hour = 12;
            }
        }
        else {
            hour = hour + ampm*12;
        }
        var fielddate = new Date(year, month, day, hour, minute,0,0);
        return fielddate.getTime();
    }

    function getSelectedValue(formname,elementname){
        dropdown = eval(formname+"."+elementname);
        return dropdown.options[dropdown.selectedIndex].value;
    }

    function fixTheDaysDropDown() {
        try {
            changedays(0);
        } catch(e){
        }
        try {
            changedays(1);
        } catch(e) {
        }
    }
    
    function updateDateTimeCondition(params){
        var operator = document.actualinputform.operators.options[document.actualinputform.operators.selectedIndex].value;
        params.push(new Array("operator",operator));
        var values = new Array();
        if (operator == "Is In The Last") {
            var lastduration = document.getElementById("lastduration");
            var lastoperator = document.getElementById("lastoperator");
            values.push(lastduration.value);
            values.push(lastoperator.options[lastoperator.selectedIndex].value);
        }
        else if (operator == "Is In The Historical Range") {
            var startduration = document.getElementById("startduration");
            var startscale = document.getElementById("startscale");
            var endduration = document.getElementById("endduration");
            var endoperator = document.getElementById("endoperator");
            values.push(startduration.value + "~~" + startscale.options[startscale.selectedIndex].value);
            values.push(endduration.value + "~~" + endoperator.options[endoperator.selectedIndex].value);
        }
        else if (operator == "Is In The Range") {
            values.push(getDateInMilliSeconds('0'));
            values.push(getDateInMilliSeconds('1'));
        }
        else if (operator != "Is Today") {
            values.push(getDateInMilliSeconds('0'));
        }
        for (var i = 0 ; i < values.length ; i++){
            params.push(new Array("value",values[i]));
        }
    }