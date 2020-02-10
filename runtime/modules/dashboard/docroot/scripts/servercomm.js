function getXMLHTTPRequest() {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
    return new ActiveXObject("Microsoft.XMLHTTP");
}

function parseXML(xml){
    if (window.DOMParser) {
          var parser = new DOMParser();
          parser.parseFromString(xml,"text/xml");
          return parser;
      }
    var parser = new ActiveXObject("Microsoft.XMLDOM");
    parser.async = "false";
    parser.loadXML(xml);
    return parser;
}

function sendRequest(url, token, sessionid, command, parameters, callbackfn){
    var request = createRequest(token, sessionid, command, parameters);
    var httpRequest = getXMLHTTPRequest();
    httpRequest.open("POST", url, false);
    httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httpRequest.setRequestHeader("Content-length", request.length);
    
    if (callbackfn) {
        httpRequest.onreadystatechange = function() {
            if (httpRequest.readyState == 4) {
                callbackfn(httpRequest.responseText);
            }
        }
        httpRequest.send(request);
    }
    else {
        httpRequest.send(request);
        if (httpRequest.readyState == 4){
            if (httpRequest.status == 200) {
                return httpRequest.responseText;
            }
            throw httpRequest.statusText;
        }
        throw "Incomplete Request Processing";
    }
}

function createRequest(token, sessionid, command, parameters){
    var request = "<request>";
    if (token != null) {
        request = request + "<parameter name=\"stoken\">"+token+"</parameter>";
    }
    if (sessionid != null) {
        request = request + "<parameter name=\"sessionid\">"+sessionid+"</parameter>";
    }
    if (command != null){
        request = request + "<parameter name=\"command\">"+command+"</parameter>";
    }
    if (parameters != null){
        for (var i = 0 ; i < parameters.length ; i++){
            request = request + "<parameter name=\""+parameters[i][0]+"\">"+parameters[i][1]+"</parameter>";
        }
    }
    request = request + "</request>";
    return request;
}