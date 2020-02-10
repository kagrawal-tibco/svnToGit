package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.jface.viewers.ISelection;

import com.tomsawyer.drawing.geometry.shared.TSConstPoint;

public interface IGraphInfo {

	TSConstPoint getLocationLastMouseButtonActivity();

	ISelection getSelectionAt(TSConstPoint point);


}
