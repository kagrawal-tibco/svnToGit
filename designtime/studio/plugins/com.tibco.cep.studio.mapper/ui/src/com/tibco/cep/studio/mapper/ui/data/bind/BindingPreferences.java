package com.tibco.cep.studio.mapper.ui.data.bind;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;

/**
 * The set of user preferences for the binding window.
 */
class BindingPreferences {
   public BindingPreferences() {
   }

   public void read(UIAgent uiAgent) {
      inlineEditing = readBool(uiAgent, "binding.inlineEditing", inlineEditing);

      typeTypeBindSplit = readInt(uiAgent, "binding.type.TypeBindSplit", typeTypeBindSplit);
      typeLineBendSplit = readInt(uiAgent, "binding.type.LineBendSplit", typeLineBendSplit);
      typeBindTextSplit = readInt(uiAgent, "binding.type.BindTextSplit", typeBindTextSplit);

      dataDataBindSplit = readInt(uiAgent, "binding.data.DataBindSplit", dataDataBindSplit);
      dataLineBendSplit = readInt(uiAgent, "binding.data.LineBendSplit", dataLineBendSplit);
      dataBindTextSplit = readInt(uiAgent, "binding.data.BindTextSplit", dataBindTextSplit);
      dataDataTextSplit = readInt(uiAgent, "binding.data.DataTextSplit", dataDataTextSplit);
   }

   public void write(UIAgent uiAgent) {
	   uiAgent.setUserPreference("binding.inlineEditing", "" + inlineEditing);

	   uiAgent.setUserPreference("binding.type.TypeBindSplit", "" + typeTypeBindSplit);
	   uiAgent.setUserPreference("binding.type.LineBendSplit", "" + typeLineBendSplit);
	   uiAgent.setUserPreference("binding.type.BindTextSplit", "" + typeBindTextSplit);

	   uiAgent.setUserPreference("binding.data.DataBindSplit", "" + dataDataBindSplit);
	   uiAgent.setUserPreference("binding.data.LineBendSplit", "" + dataLineBendSplit);
	   uiAgent.setUserPreference("binding.data.BindTextSplit", "" + dataBindTextSplit);
	   uiAgent.setUserPreference("binding.data.DataTextSplit", "" + dataDataTextSplit);
   }

   private int readInt(UIAgent uiAgent, String name, int defVal) {
      return PreferenceUtils.readInt(uiAgent, name, defVal);
   }

   private boolean readBool(UIAgent uiAgent, String name, boolean defVal) {
      return PreferenceUtils.readBool(uiAgent, name, defVal);
   }

   // Overall:
   public boolean inlineEditing = true;

   // Type mode values:
   public int typeTypeBindSplit = 300;
   public int typeLineBendSplit = 230;
   public int typeBindTextSplit = 80;

   // Data mode values:
   public int dataDataTextSplit = 250;
   public int dataDataBindSplit = 250;
   public int dataLineBendSplit = 235;
   public int dataBindTextSplit = 130;
}

