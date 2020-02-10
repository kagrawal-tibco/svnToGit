/**
  * Launches a trackable window with a unique name to render the  URL
  * @param linkURL The URL to be shown
   */
function showLink(linkURL){
    //create a unique name for the window
    var wName = (new Date()).getTime();
    launchDrilldownLink(wName,linkURL);
}

/**
  * Handles disconnection from the streaming server
  * @param serverURL The URL which is to be used to relaunch the application
  */
function handleDisconnect(serverURL){
    if (serverURL == 'none'){
        window.alert("The connection to the server has been lost.  Please re-login to connect to the server again.  If still unable to connect, contact your System Admin");
        window.location.reload(false);
    }
    else {
        window.alert("The connection to the server has been lost.  Click Ok to connect to next available server. If still unable to connect, contact your System Admin");
        window.top.location.href = serverURL;
    }
}

/**
  * Handles session timeout
  * TODO : getting the logout URL as argument
  * @param timeOutMessage The message which is to shown to the user [do we need this?]
  */
function handleTimeOut(timeOutMessage){
    window.alert(timeOutMessage);
    window.top.location.reload(false);
}

/**
  *  Launches a window to show drilldown page
  *  @param wName The name for the window
  *  @param drilldownLinkURL The URL which represents the drilldown page
  */
function launchDrilldownLink(wName,drilldownLinkURL) {
	//alert(drilldownLinkURL);
    //Pops up the window
    var drilldownwindow = window.open(drilldownLinkURL,wName,'height=600,width=1100,resizable=yes,location=yes,menubar=no,toolbar=no,status=yes,scrollbars=yes');
    newWindowOpened(drilldownwindow);
}