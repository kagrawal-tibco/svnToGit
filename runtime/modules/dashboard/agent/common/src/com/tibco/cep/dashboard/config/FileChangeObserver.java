package com.tibco.cep.dashboard.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.STATE;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.dashboard.timer.TimerProvider;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * ChangeNotificationService provides the services to be notified when something changes.
 * The "something" right now is whether a registered file has been modified based on its modification date.
 * No further examination will be done of fields within the file.
 *
 * CNS is currently started by ServiceNodeManager so it is available to all server processes.
 * To use this service the caller must get a handle to cns, create a handler to be invoked
 * when the file has changed and register the file to watch and the handler with cns. ie,
 * private ChangedEventHandler handler = new ChangedEventHandler(); ..
 * File file = new File(..); ..
 * _cns = ChangeNotificationService.getInstance();
 * _cns.addChangedEventListener(file, handler);
 *
 * The ChangeNotificationService fires a CNSEvent to all the FileChangedEventListeners registered for a specific file.
 *
 * @author kshenton
 */
public class FileChangeObserver extends ServiceDependent {

	private static FileChangeObserver instance;

	public static final synchronized FileChangeObserver getInstance() {
		if (instance == null) {
			instance = new FileChangeObserver();
		}
		return instance;
	}

	private Boolean _lockedForChanges = new Boolean(false);

	private long _frequency;

	private FileWatcherTask _watcherTask;

	// Define the filewatch structures required. One-to-one correspondence
	// between
	// filename, date and _fileListeners array. There can be many listeners on
	// the
	// same file, though. Thus the _listenerInstances array is needed. Could
	// create
	// an arraylist of objects each consisting of a file, date and listener
	// array.
	private ArrayList<File> _files = new ArrayList<File>();
	private ArrayList<Long> _fileDates = new ArrayList<Long>();
	private ArrayList<ArrayList<ChangedEventListener>> _fileListeners = new ArrayList<ArrayList<ChangedEventListener>>(); // Array

	protected FileChangeObserver() {
		super("filechangeobserver", "File Change Observer");
	}

	@Override
	protected void doInit() throws ManagementException {
		_frequency = (Long) ConfigurationProperties.FILE_CHANGE_OBSERVATION_FREQUENCY.getValue(properties);
	}

	@Override
	protected void doStart() throws ManagementException {
		if (_files.isEmpty() == false) {
			_watcherTask = new FileWatcherTask(logger, exceptionHandler);
			TimerProvider.getInstance().scheduleWithFixedDelay(_watcherTask, 0, _frequency * DateTimeUtils.SECOND, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	protected boolean doStop() {
		// cancel the _watcher task
		if (_watcherTask != null) {
			_watcherTask.cancel();
		}
		_watcherTask = null;
		for (int i = 0; i < _files.size(); i++) {
			_fileListeners.get(i).clear();
		}
		_fileListeners.clear();
		_fileListeners = null;
		_fileDates.clear();
		_fileDates = null;
		_files.clear();
		_files = null;
		instance = null;
		return true;
	}

	@Override
	protected void doPause() throws ManagementException {
		if (_watcherTask != null) {
			// cancel the _watcher task
			_watcherTask.cancel();
			_watcherTask = null;
		}
	}

	@Override
	protected void doResume() throws ManagementException {
		if (_watcherTask == null && _files.isEmpty() == false) {
			_watcherTask = new FileWatcherTask(logger, exceptionHandler);
			TimerProvider.getInstance().scheduleWithFixedDelay(_watcherTask, 0, _frequency * DateTimeUtils.SECOND, TimeUnit.MILLISECONDS);
		}
	}

	/***
	 * Adds the File and Listener to the list of files to watch. The listener is the callback to invoke when it is detected that the file has changed.
	 *
	 * @param File
	 *            the File object to watch
	 * @param ChangedEventListener
	 *            the callback to invoke.
	 ***/
	public void addChangedEventListener(File myFile, ChangedEventListener listener) {
		if (state != STATE.RUNNING) {
			throw new IllegalStateException(getDescriptiveName() + " is not running");
		}
		// logger.log(Level.DEBUG, "CNS adding listener for file: " + myFile.getName());
		ArrayList<ChangedEventListener> listeners;
		synchronized (_lockedForChanges) {
			int position = _files.indexOf(myFile);
			if (position == -1) {
				// Not in list already, add it
				_files.add(myFile);
				_fileDates.add(new Long(new Date().getTime()));
				listeners = new ArrayList<ChangedEventListener>();
				listeners.add(listener);
				_fileListeners.add(listeners);
				logger.log(Level.DEBUG, "CNS added listener for file: " + myFile.getName());
			} else {
				// Add this listener to the existing array of listeners for
				// this file
				listeners = _fileListeners.get(position);
				position = listeners.indexOf(listener);
				if (position == -1) {
					logger.log(Level.DEBUG, "CNS added to existing listeners for this file: " + myFile.getName());
					listeners.add(listener);
				} else {
					logger.log(Level.DEBUG, "CNS ignore: attempt to re-register same handler for same file");
				}
			}
		}
		// This is in unsynchronized section - start task if it is was not
		// running already because nothing was there to monitor.
		if (_watcherTask == null) {
			_watcherTask = new FileWatcherTask(logger, exceptionHandler);
			TimerProvider.getInstance().scheduleWithFixedDelay(_watcherTask, 0, _frequency * DateTimeUtils.SECOND, TimeUnit.MILLISECONDS);
		}
	}

	/***
	 * Removes the File and Listener from the list of files being watched.
	 *
	 * @param File
	 *            the File object to stop watching
	 * @param ChangedEventListener
	 *            the callback associated with the File.
	 ***/
	public void removeChangedEventListener(File myFile, ChangedEventListener listener) {
		logger.log(Level.DEBUG, "CNS removing listener for file: " + myFile.getName());
		ArrayList<ChangedEventListener> listeners;
		synchronized (_lockedForChanges) {
			int position = _files.indexOf(myFile);
			if (position != -1) {
				listeners = _fileListeners.get(position);
				// Check if this listener exists prior to removing it
				int lpos = listeners.indexOf(listener);
				if (lpos != -1) {
					listeners.remove(listeners.indexOf(listener));
					if (listeners.isEmpty()) {
						// Empty list of listeners, remove File entry
						_fileListeners.remove(position);
						_files.remove(position);
						_fileDates.remove(position);
					}
				} else {
					// This listener does not exist
					logger.log(Level.DEBUG, "CNS error: the listener for this file does not exist (" + myFile.getName() + ")");
				}
			} else {
				logger.log(Level.DEBUG, "CNS: the file " + myFile.getName() + " is not being watched");
			}
		}
		// If no entries to watch, stop the service until something is added
		if (_files.isEmpty() && _watcherTask != null) {
			_watcherTask.cancel();
			_watcherTask = null;
		}
	}

	// Removes all registered File objects and all registered listeners from the
	// service.
	public void removeAllListeners() {
		logger.log(Level.DEBUG, "CNS removing ALL listeners and stopping file checking thread");
		synchronized (_lockedForChanges) {
			if (_files.size() == 0) {
				logger.log(Level.DEBUG, "    CNS has nothing to clear");
				return;
			}
			for (int i = 0; i < _files.size(); i++) {
				_fileListeners.get(i).clear();
			}
			_fileDates.clear();
			_files.clear();
			_watcherTask.cancel();
			_watcherTask = null;
		}
	}

	/***
	 * Triggers one pass of the watcher task. This would be used if CNS is disabled and you want to update all watched files now.
	 ***/
	public void fireChangesNow() {
		logger.log(Level.DEBUG, "CNS fireChangesNow at user request");
		FileWatcherTask tempWatcher = new FileWatcherTask(logger, exceptionHandler);
		tempWatcher.run();
	}

	/***
	 * Private method used by the watcher task to fire the event to the callback for this file. It is up to the callback to reload the file or take other actions as needed.
	 *
	 * @param File
	 *            that was modified
	 * @param position
	 *            index of the file in the array for access to listeners array
	 ***/
	private void fireCNSFileEvent(File _file, int position) {
		// Invoke handler method for all "listeners". Note: we are already
		// synchronized
		// on changes via FileWatcherTask
		CNSEvent event = new CNSEvent(CNSEvent.FILE_CHANGED, _file);
		ArrayList<ChangedEventListener> listeners = _fileListeners.get(position);
		for (ChangedEventListener listener : listeners) {
			try {
				listener.handleCNSEvent(event);
			} catch (Throwable e) {
			}
		}
	}

	/***
	 * This task contains the meat of the Change Notification Service. It checks
	 * all registered files for a change in the modification date since the time of registry.
	 * If changed, an event is fired to the listener via the
	 * fireCNSFileEvent
	 ***/
	private class FileWatcherTask extends ExceptionResistentTimerTask {

		private File myFile;
		private Long fdate;

		public FileWatcherTask(Logger logger, ExceptionHandler exceptionHandler) {
			super("File Watcher", logger, exceptionHandler, false);
		}

		public void doRun() {
			// Check for files to check
			// logger.fine ("CNS watcher doing filecheck, there are " +
			// _files.size() + " files.");
			synchronized (_lockedForChanges) {
				for (int i = 0; i < _files.size(); i++) {
					myFile = (File) _files.get(i);
					// logger.fine ("CNS checking on file " +
					// myFile.getName());
					fdate = new Long(myFile.lastModified());
					if (fdate.compareTo((Long) _fileDates.get(i)) > 0) {
						// logger.fine ("CNS sending event for file: " +
						// myFile.getName());
						fireCNSFileEvent(myFile, i);
						_fileDates.set(i, fdate);
					}
				}
			}
		}

	} // end of FileWatcher task

	public String toString() {
		StringBuilder sb = new StringBuilder(FileChangeObserver.class.getName());
		sb.append("[state=");
		sb.append(state);
		sb.append(",running=");
		sb.append(_watcherTask != null);
		sb.append(",watchedfiles=");
		sb.append(_files.toString());
		sb.append("]");
		return sb.toString();
	}

}