function QueryParser(q) {
	if(q.length > 1) {
		this.q = q.substring(1, q.length);
	}
	else {
		this.q = null;
	}
	this.keyValuePairs = new Array();
	if(this.q) {
		var params = this.q.split("&");
		
		for(var i=0; i < params.length; i++) {
			var nameValuePair = params[i].split("=");
			if (nameValuePair[0].charAt(0) == '?'){
				nameValuePair[0] = nameValuePair[0].substr(1);
			}
			this.keyValuePairs[i] = nameValuePair;
		}
	}
	
	this.getKeyValuePairs = function() { 
		return this.keyValuePairs; 
	}
	
	this.getValue = function(s) {
		for(var j=0; j < this.keyValuePairs.length; j++) {
			if(this.keyValuePairs[j][0] == s)
			{
				return this.keyValuePairs[j][1];
			}
		}
		return false;
	}

	this.getParameters = function() {
		var a = new Array(this.getLength());
		for(var j=0; j < this.keyValuePairs.length; j++) {
			a[j] = this.keyValuePairs[j][0];
		}
		return a;
	}

	this.getLength = function() { 
		return this.keyValuePairs.length; 
	}
}

function queryString(key){
	var page = new PageQuery(window.location.search);
	return unescape(page.getValue(key));
}

function displayItem(key){
	if(queryString(key)=='false')
	{
		document.write("you didn't enter a ?name=value querystring item.");
	}
	else
	{
		document.write(queryString(key));
	}
}
