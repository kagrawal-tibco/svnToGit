	var baseUrl = "";
	function makeAjaxCall(url, method, body, formData, asynch, showFileDownloadPopup, callbackResponse) {
		var response = {};
		var req = null;
		if (typeof XDomainRequest != "undefined") {
			req = new XDomainRequest();
			req.open(method, url);
		}
		else {
			req = new XMLHttpRequest();
			req.open(method, url, asynch);
		}
		
		req.onreadystatechange = function() {
			if (req.readyState == 4) {
				response.statusCode = req.status;
				if (req.status == 200) {
					response.content = req.responseText;
					if (showFileDownloadPopup) {
                                               if (url.indexOf("/multiexport.") !== -1) {
                                                  multiExportForm(url, {data: body});
                                               } else {
					           window.location = url;
                                               }
					}
				}
				else {
					response.content = "";
				}
				callbackResponse(response);
			}
		}
		//set request headers
		req.setRequestHeader("Accept", "application/xml");
		
		req.timeout = 30000;//30 sec timeout
	    req.ontimeout = function () {alert("Request timeout.");}
		
		if (body != null) {
			req.setRequestHeader("Content-Type", "application/xml");
			req.send(body);
		}
		else if (formData != null) {
			//Need not set the content-type so that the browser sets it automatically along with a boundary value for multipart. 
			req.send(formData);
		}
		else {
			req.setRequestHeader("Content-Type", "application/xml");
			req.send();
		}
	}


        function multiExportForm(path, params, method='post') {

            const form = document.createElement('form');
            form.method = method;
            form.action = path;

            for (const key in params) {
              if (params.hasOwnProperty(key)) {
                const hiddenField = document.createElement('input');
                hiddenField.type = 'hidden';
                hiddenField.name = key;
                hiddenField.value = params[key];

               form.appendChild(hiddenField);
              }
            }

           document.body.appendChild(form);
           form.submit();
       } 
	
	/**
	 * Toggles the expanded/collapsed state of the passed element.
	 */
	function toggleVisibility(element) {
		if (element.classList.contains("collapsed")) {
			element.className = element.className.replace("collapsed", "expanded");
		}
		else {
			element.className = element.className.replace("expanded", "collapsed");
		}
	}
	
	function makeVisible(element) {
		element.className = element.className.replace("collapsed", "expanded");//TODO: add class expanded even if collpased not present
	}
	
	function makeInvisible(element) {
		element.className = element.className.replace("expanded", "collapsed");//TODO: add class collpased even if expanded not present
	}
	
	/**
	 * Initializes all functions, listeners etc.
	 */
	function init() {
		String.prototype.encodeXML = function () {
			return this.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&apos;');
		};
		  
		//Add collapse/expand toggle click listener on api title.
		var apis = document.getElementsByClassName('apiTitle');
		for (l=0; l<apis.length; l++) {
			apis[l].addEventListener('click', function (event) {
				var ths = event.target;
				toggleVisibility(ths);
				toggleVisibility(ths.nextElementSibling);
			});
		}
		
		//Add collapse/expand toggle click listener on group title.
		var groups = document.getElementsByClassName('groupTitle');
		for (l=0; l<groups.length; l++) {
			groups[l].addEventListener('click', function (event) {
				var ths = event.target;
				toggleVisibility(ths);
				toggleVisibility(ths.nextElementSibling);
			});
		}
		
		//Add formSumit listener.
		var formSubmitButtons = document.getElementsByClassName('formSubmit');
		for (l=0; l<formSubmitButtons.length; l++) {
			formSubmitButtons[l].addEventListener('click', function (event) {
				var ths = event.target;
				var formNode = ths;
				while (formNode.nodeName != "FORM"){
					formNode = formNode.parentNode;
				}
				
				var body = null;
				var action = formNode.getAttribute("action");
				var formFields = formNode.getElementsByClassName("formField");
				var formData = null;
				
				var requestType = action.lastIndexOf(".") != -1 ? action.substring(action.lastIndexOf(".") + 1) : null;
				if (requestType == null) {
					alert("Select a request type - xml/json");
					return;
				}
				
				for (i=0; i<formFields.length; i++) {
					if (formFields[i].value == null || formFields[i].value.trim() == "") {//Empty value
						if (formFields[i].getAttribute("required") == "required") {
							alert("Value for '" + formFields[i].getAttribute("name") + "' is required.");
							return;
						}
						else {
							continue;
						}
					}
					
					//TODO: URL encoding
					if (formFields[i].getAttribute("paramtype") == "path") {
						action = action.replace("{" + formFields[i].getAttribute("name") + "}", formFields[i].value);
					}
					else if (formFields[i].getAttribute("paramtype") == "query") {

						//check for date type
						if(formFields[i].getAttribute("name") == "afterDate" || formFields[i].getAttribute("name") == "beforeDate")
						{
							var actionDate = new Date(formFields[i].value);
							
							// considering the Time field is next to date field
							
							if(formFields[i+1].value==""){
								//Setting default values for the time field in case not entered
									actionDate.setHours(0);
									actionDate.setMinutes(0);
								}
							else
								{
									var actionTime = formFields[i+1].value.split(':');
									actionDate.setHours(parseInt(actionTime[0]));
									actionDate.setMinutes(parseInt(actionTime[1]));
									
								}
								
							var actionDateEpoc = actionDate.getTime();
							
							action += (action.indexOf("?") == -1 ? "?" : "&");
							action += formFields[i].getAttribute("name") + "=" + actionDateEpoc;
							// incrementing pointer to skip setting time in URL
							i++;
						
						}
						else{
							action += (action.indexOf("?") == -1 ? "?" : "&");
							action += formFields[i].getAttribute("name") + "=" + formFields[i].value;
						}
					}
					else if (formFields[i].getAttribute("paramtype") == "body") {
						body = formFields[i].value;
					}
					else if (formFields[i].getAttribute("paramtype") == "form") {
						if (formData == null) {
							formData = new FormData();
						}
						if (formFields[i].getAttribute("type") == "file") {
							formData.append(formFields[i].getAttribute("name"), formFields[i].files[0]);
						}
						else {
							formData.append(formFields[i].getAttribute("name"), formFields[i].value);
						}

					}
				}
				
				action = baseUrl + action;
				//show computed url in apiUrl
				formNode.getElementsByClassName("apiResponseSection")[0].getElementsByClassName("apiUrl")[0].innerHTML = action;
				
				makeInvisible(formNode.getElementsByClassName("apiResponseSection")[0]);//TODO: show loading image
				//Whether a file download pop be opened or the response be shown in response body section below "try" button
				var showFileDownloadPopup = false;
				if ((hiddenValues = formNode.getElementsByClassName("hidden_value")) != null) {
					for (i=0; i<hiddenValues.length; i++) {
						if (hiddenValues[i].getAttribute("name") == "responseType" && hiddenValues[i].getAttribute("value") == "file") {
							showFileDownloadPopup = true;
						}
					}
				}
				
				var tryButton = event.target;
				tryButton.setAttribute("disabled", "");
				makeAjaxCall(action, formNode.getAttribute("method"), body, formData, true, showFileDownloadPopup, function(response) {
					tryButton.removeAttribute("disabled");
					var content = (showFileDownloadPopup && response.content !== "") ? "File Downloaded!!" : response.content;
					var statusCode = response.statusCode;
					
					makeVisible(formNode.getElementsByClassName("apiResponseSection")[0]);
					
					content = convertPrettyPrintXML(content.encodeXML());
					
					formNode.getElementsByClassName("apiResponseSection")[0].getElementsByClassName("apiResponse")[0].innerHTML = content;
				});
			});
		}
	}
	
	/**
	 * Converts the pretty printing of xml, eg converts all "\n" to "<br/>", spaces to "&nbsp;"
	 */
	function convertPrettyPrintXML(content) {
		while (content.indexOf("\n") != -1) {
			content = content.replace("\n", "<br/>");
		}
		while (content.indexOf("    ") != -1) {
			content = content.replace("    ", "&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return content;
	}

	/**
	 * Updates the current api to adjust to the selected request type (json/xml)
	 * - Updates the css
	 * - Updates form's action attribute with a newly computed url
	 */
	function updateRequestType(selectNode) {
		var apiNode = null;
		var parent = selectNode.parentNode;
		while (parent != null) {
			if (parent.classList.contains("api")) {
				apiNode = parent;
				break;
			}
			parent = parent.parentNode;
		}
		if (apiNode == null) {
			return;
		}
		
		if (selectNode.value == "xml") {
			apiNode.classList.remove("requestType_json");
			apiNode.classList.add("requestType_xml");
		}
		else if (selectNode.value == "json") {
			apiNode.classList.remove("requestType_xml");
			apiNode.classList.add("requestType_json");
		}
		
		var formNode = apiNode.getElementsByTagName("form")[0];
		if (formNode == null) {
			return;
		}
		var currentAction = formNode.getAttribute("action");
		formNode.setAttribute("action", currentAction.substring(0, currentAction.lastIndexOf(".")) + "." + selectNode.value);
	}
	
	/**
	 * Shows sample data depending on the request type.
	 */
	function showSampleData(type, ths) {
		try {
			var formNode = ths;
			while (formNode.nodeName != "FORM"){
				formNode = formNode.parentNode;
			}
			
			var sampleDataNode = formNode.getElementsByClassName("hiddenSampleData")[0].getElementsByClassName(type == "xml" ? "type_xml" : "type_json")[0];
			var ltReg = new RegExp("&lt;", 'g');
			var gtReg = new RegExp("&gt;", 'g');
			formNode.getElementsByTagName("textarea")[0].value = sampleDataNode.innerHTML.replace(ltReg, "<").replace(gtReg, ">");;
		}
		catch(e) {
			
		}
	}
