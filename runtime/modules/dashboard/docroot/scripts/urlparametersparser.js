function URLParametersParser(s,escapeValue) {
    if (s.length > 1){
        var idx = s.indexOf("?");
        if (idx != -1) {
            this.queryString = s.substring(idx+1, s.length);
        }
        else {
            this.queryString = s;
        }
    }
    else {
        this.queryString = null;
    }
    this.urlParameters = new Array();
    if (this.queryString != null){
        var individualParams = this.queryString.split("&");
        for(var i=0; i < individualParams.length; i++) {
            var keyValuePair = individualParams[i].split("=");
            if (escapeValue == true) {
                this.urlParameters[i] = new URLParameter(keyValuePair[0],unescape(keyValuePair[1]));
            }
            else {
                this.urlParameters[i] = new URLParameter(keyValuePair[0],keyValuePair[1]);
            }
        }
    }

    //assign functions to the object
    this.size = function () {
        return urlParameters.length;
    }

    this.keys = function () {
        var keys = new Array();
        for(var i=0; i < this.urlParameters.length; i++) {
            keys[i] = this.urlParameters[i].key;
        }
        return keys;
    }

    this.values = function () {
        var values = new Array();
        for(var i=0; i < this.urlParameters.length; i++) {
            values[i] = this.urlParameters[i].value;
        }
        return values;
    }

    this.getValue = function (key) {
        for(var i=0; i < this.urlParameters.length; i++) {
            if (this.urlParameters[i].key == key) {
                return this.urlParameters[i].value;
            }
        }
        return null;
    }

    this.getKeyValuePairs = function () {
        var keyValuePairs = new Array();
        for(var i=0; i < this.urlParameters.length; i++) {
            keyValuePairs[i] = new Array(this.urlParameters[i].key,this.urlParameters[i].value);
        }
        return keyValuePairs;
    }

}

function URLParameter(key,value){
	//alert(key+"="+value);
    this.key = key;
    this.value = value;
}