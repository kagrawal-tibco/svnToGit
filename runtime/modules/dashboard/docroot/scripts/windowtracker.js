var arrOpenedWindows = new Array();

/**
  * Registers a newly opened windows as a trackable window
  * @param openedWindow The window which needs to be tracked
  */
function newWindowOpened(openedWindow) {
    var rootWindow = openedWindow;
    while(rootWindow.top.opener != null) {
        rootWindow = rootWindow.top.opener;
    }
    rootWindow.top.addToList(openedWindow);
}

/**
  * Unloads the current window from the trackable window list
  */
function openedWindowUnLoaded() {
    newWindowClosed(window);
}

/**
  * Unregisters a previously opened/trackable windows
   * @param closedWindow The window which needs to be removed
   */
function newWindowClosed(closedWindow) {
    for(i=0; i<arrOpenedWindows.length; i++)  {
        try {
            if (arrOpenedWindows[i] == closedWindow) {
                arrOpenedWindows[i] = null;
            }
        } catch(e) {
        }
    }
}

/**
  * Adds a window to the tracking list
  * @param openedWindow The window which is to be tracked
  */
function addToList(openedWindow) {
    arrOpenedWindows[arrOpenedWindows.length] = openedWindow;
}

/**
  * Closes all tracked windows
  */
function closeAll() {
    for(i=0; i<arrOpenedWindows.length; i++) {
        try {
            if (arrOpenedWindows[i] != null) {
                arrOpenedWindows[i].close();
            }
        } catch(e) {
        }
    }
    arrOpenedWindows = new Array();
}