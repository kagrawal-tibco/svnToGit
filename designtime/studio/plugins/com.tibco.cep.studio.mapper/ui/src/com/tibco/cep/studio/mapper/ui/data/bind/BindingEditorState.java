package com.tibco.cep.studio.mapper.ui.data.bind;

import com.tibco.cep.studio.mapper.ui.data.TreeState;

/**
 * Preserves the state (i.e. tree expansions, etc.) of a particular template editing session.<br>
 * This can be used to save/restore the state so that the editor 'looks' like it did when the user last touched it.
 */
public class BindingEditorState {
   TreeState m_leftState;
   TreeState m_rightState;

   BindingEditorState(TreeState left, TreeState right) {
      m_leftState = left;
      m_rightState = right;
   }
}
