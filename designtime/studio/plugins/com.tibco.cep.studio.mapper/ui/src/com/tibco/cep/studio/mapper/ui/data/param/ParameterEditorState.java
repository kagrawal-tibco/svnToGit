package com.tibco.cep.studio.mapper.ui.data.param;

import com.tibco.cep.studio.mapper.ui.data.TreeState;

/**
 * The editing state of a {@link ParameterEditor} instance, in particular, the tree-expansion state.<br>
 * This can be used, cross editing-instance, to preserve the state of editing without actually keeping the component.
 * Used with {@link ParameterEditor#getState} and {@link ParameterEditor#setState}.<br>
 * External clients should treat this as an opaque object.
 */
public class ParameterEditorState {
   TreeState m_treeState; // internal, external users should treat this as an opaque object.

   ParameterEditorState(TreeState state) {
      m_treeState = state;
   }
}
