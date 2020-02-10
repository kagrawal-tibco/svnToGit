package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;

/**
 * WCETODO --- move this somewhere else.
 */
public class GenericFindWindow extends JSplitPane {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JButton mSearch;
	private UIAgent uiAgent;
	private GenericFindResultsPanel mResults;
   private FindWindowPlugin mPlugin;
   private FindRunner mCurrentRunner;
//   private ObjectProvider mOptionalProvider; // set if is a global search, otherwise not.

   public GenericFindWindow(UIAgent uiAgent, FindWindowPlugin plugin, GenericFindSelectionHandler fsh, boolean singleClickResultSelect/*, ObjectProvider optionalProvider*/) {
      super();
//      mOptionalProvider = optionalProvider;
      mPlugin = plugin;
      setOrientation(JSplitPane.VERTICAL_SPLIT);
      this.uiAgent = uiAgent;
      if (uiAgent == null) {
         throw new RuntimeException();
      }
      JPanel top = new JPanel(new BorderLayout());
      top.add(mPlugin.getFindParametersPanel(), BorderLayout.CENTER);

      mResults = new GenericFindResultsPanel(mPlugin,fsh,singleClickResultSelect, true); //SSTODO - ObjectProvider
      setLeftComponent(top);
      setRightComponent(mResults);
      reshow();
      mPlugin.setEditorChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            find();
         }
      });
   }

   public static Color getDefaultBackgroundColor(boolean sel) {
      if (sel) {
         return getDefaultSelectionBackgroundColor();
      }
      else {
         return getDefaultNonSelectionBackgroundColor();
      }
   }

   public static Color getDefaultForegroundColor(boolean sel) {
      if (sel) {
         return getDefaultSelectionForegroundColor();
      }
      else {
         return getDefaultNonSelectionForegroundColor();
      }
   }

   public static Color getDefaultSelectionBackgroundColor() {
      return UIManager.getColor("Tree.selectionBackground");
   }

   public static Color getDefaultNonSelectionBackgroundColor() {
      return UIManager.getColor("Tree.textBackground");
   }

   public static Color getDefaultSelectionForegroundColor() {
      return UIManager.getColor("Tree.selectionForeground");
   }

   public static Color getDefaultNonSelectionForegroundColor() {
      return UIManager.getColor("Tree.textForeground");
   }

   public void reshow() {
      mPlugin.getPrimaryEntryComponent().requestFocus();
   }

   public void dispose() {
      synchronized (this) {
         if (mCurrentRunner != null) {
            mCurrentRunner.cancel();
         }
         mCurrentRunner = null;
      }
   }

   /**
    * Re-runs the find (if there's an active search thread, it is cancelled)
    */
   private void find() {
      synchronized (this) {
         if (mCurrentRunner != null) {
            mCurrentRunner.cancel();
         }
         mCurrentRunner = null;
         Object findData;
         try {
            findData = mPlugin.getFindData();
         }
         catch (Exception e) {
            // can't search; bad find data, get message:
            mResults.setBlank(e.getMessage());
            return;
         }
         mResults.setRunning();
         FindRunner fr = new FindRunner(findData);
         mCurrentRunner = fr;
         Thread th = new Thread(fr);
         th.setPriority(Thread.MIN_PRIORITY); // be nice.
         th.start();
      }
   }

   /**
    * When called, it is as if the user reselects the preview item.
    */
   public void refreshPreview() {
      mResults.refresh();
   }

   private class FindRunner implements Runnable, FindResultHandler {
      private Object mFindData;
      private boolean mCancel;
      private ArrayList<FindWindowResult> mResultsSoFar = new ArrayList<FindWindowResult>();
      private String mCurrentLocation;

      public FindRunner(Object findData) {
         mFindData = findData;
      }

      public boolean hasBeenCancelled() {
         return mCancel;
      }

      public void result(Object searchResult) {
         mResultsSoFar.add(new FindWindowResult(mCurrentLocation, searchResult));
      }

      public void cancel() {
         mCancel = true;
      }

      public void run() {
          try
          {
              runLocal();

          }
         catch (Throwable t) {
            // dump it, but don't crash whole thing:
            t.printStackTrace(System.err);
         }
         synchronized (GenericFindWindow.this) {
            if (!mCancel) // so if it's been cancelled, it won't overwrite...
            {
               mResults.setResults(buildResults());
            }
            mCurrentRunner = null;
         }
      }

      private FindWindowResult[] buildResults() {
         return mResultsSoFar.toArray(new FindWindowResult[mResultsSoFar.size()]);
      }

      private void runLocal() {
         // local search:
          // local search:
          File file = new File(uiAgent.getRootProjectPath());
          processDirectory(file);
          
          // better to recurse over the locations?
//          Iterator locations = uiAgent.getTnsCache().getLocations();
//          while (locations.hasNext()) {
//          	String loc = (String) locations.next();
//          	mCurrentLocation = loc;
//          	mPlugin.find(this, null, loc, mFindData);
//          }

//         mPlugin.find(this, null, null, mFindData);
      }

      private void processDirectory(File parentDir) {
      	File[] listFiles = parentDir.listFiles();
      	for (File file : listFiles) {
      		String absLocation = file.getAbsolutePath();
      		mCurrentLocation = absLocation;
      		mPlugin.find(this, null, absLocation, mFindData);
      		if (file.isDirectory()) {
      			processDirectory(file);
      		}
      	}
		}

//      private void runGlobal() {
          // global search:
          //SSTODO - Use our Indexer for search???
//          Iterator i = mOptionalProvider.getAllProjects();
//          while (i.hasNext())
//          {
//              VFileFactory vff = (VFileFactory) i.next();
//              VFileDirectory vfd = vff.getRootDirectory();
//              runDirectory(vfd);
//              if (hasBeenCancelled())
//              {
//                  break;
//              }
//          }
//      }

//      private void runDirectory(VFileDirectory dir) {
//         try {
//            Iterator i = dir.getChildren();
//            while (i.hasNext()) {
//               if (hasBeenCancelled()) {
//                  break;
//               }
//               VFile vf = (VFile) i.next();
//               if (!(vf instanceof VFileDirectory)) {
//                  mCurrentLocation = vf.getFullURI();
//                  try {
//                     mPlugin.find(this, mOptionalProvider, mCurrentLocation, mFindData);
//                  }
//                  catch (Exception e) {
//                     // dump it, but don't crash the whole thing...
//                     e.printStackTrace(System.err);
//                  }
//               }
//               else {
//                  runDirectory((VFileDirectory) vf);
//               }
//            }
//         }
//         catch (ObjectRepoException ore) {
//            // eat it, they're tasty!
//         }
//      }
   }
}
