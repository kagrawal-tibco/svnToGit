package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractDomainFormViewer extends AbstractMasterDetailsFormViewer {

	protected Domain domain;
	protected org.eclipse.jface.action.Action dependencyDiagramAction;
	
	protected DOMAIN_DATA_TYPES selectedDataType;
	
	protected Text domainDescText;
	protected Combo domainDataTypesCombo;
	protected Button button;
	protected Text domainInheritsText;
	protected TableViewer viewer; 	
	protected DomainFormEditor editor;
	
	protected TableEditor tableEditor;
	
	protected static final String NAME_PROPERTY = "name";
	protected static final String VALUE_PROPERTY = "value";
	
	protected ScrolledPageBook viewerScrollPageBook;

	protected CLabel addLabel;
	protected CLabel removeLabel;
	protected Text simpleIntValueText;
	protected Text simpleValueText;

	protected Button rangeBut;
	protected Button singeBut;

	protected Button intRangeLoIncBtn;
	protected Button intRangeUpIncBtn;
	protected Button boolTrueBtn;
	protected Button boolFalseBtn;
	protected Button realRangeLoIncBtn;
	protected Label booleanLabel;
	

	protected Button realRangeUpIncBtn;
	protected Button longRangeLoIncBtn;
	protected Button longRangeUpIncBtn;
	protected Button dateTimeRangeLoIncBtn;
	protected Button dateTimeRangeUpIncBtn;
	protected Button loDatebutton;
	protected Button hiDatebutton;
	protected Button simpleDatebutton;

	protected Text intRangeLoText;
	protected Text intRangeUpText;
	protected Text simpleDateValueText;
	protected Text simpleLongValueText;
	protected Text simpleDoubleValueText;
	protected Text realRangeLoText;
	protected Text realRangeUpText;
	protected Text longRangeLoText;
	protected Text longRangeUpText;
	protected Text dateTimeRangeLoText;
	protected Text dateTimeRangeUpText;
	protected ScrolledPageBook valueScrollPageBook;

	protected Section detailsSection;

	protected Composite separator;
	
	protected TableColumn descColumn;
	protected TableColumn valueColumn;

	protected Hyperlink inheritlink;
	
	protected DomainRangePageModifyListener domainRangePageModifyListener;


	/**
	 * 
	 * @param toolkit
	 * @param composite
	 */
	protected void createEmptyEntryDetailsInfo(FormToolkit toolkit, Composite composite) {
		FillLayout fl = new FillLayout();
		fl.marginHeight = 10;
		fl.marginWidth = 10;
		composite.setLayout(fl);
		FormText txt = toolkit.createFormText(composite, false);
		txt.setText(Messages.getString("domain.editor.empty.details.info"), false,false);
		txt.setForeground(txt.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
	}
	
	public Domain getDomain() {
		return domain;
	}
	
	public DOMAIN_DATA_TYPES getSelectedDataType() {
		return selectedDataType;
	}

	public void setSelectedDataType(DOMAIN_DATA_TYPES selectedDataType) {
		this.selectedDataType = selectedDataType;
	}
	
	public Composite getSeparator() {
		return separator;
	}
	public Combo getDomainDataTypesCombo() {
		return domainDataTypesCombo;
	}
	public void setDomainDataTypesCombo(Combo domainDataTypesCombo) {
		this.domainDataTypesCombo = domainDataTypesCombo;
	}
	public TableViewer getViewer() {
		return viewer;
	}
	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}
	public ScrolledPageBook getViewerScrollPageBook() {
		return viewerScrollPageBook;
	}
	public void setViewerScrollPageBook(ScrolledPageBook viewerScrollPageBook) {
		this.viewerScrollPageBook = viewerScrollPageBook;
	}

	public CLabel getRemoveLabel() {
		return removeLabel;
	}
	public void setRemoveLabel(CLabel removeLabel) {
		this.removeLabel = removeLabel;
	}
	public Text getSimpleIntValueText() {
		return simpleIntValueText;
	}
	public void setSimpleIntValueText(Text simpleIntValueText) {
		this.simpleIntValueText = simpleIntValueText;
	}
	public Text getSimpleValueText() {
		return simpleValueText;
	}
	public void setSimpleValueText(Text simpleValueText) {
		this.simpleValueText = simpleValueText;
	}
	public Button getRangeBut() {
		return rangeBut;
	}
	public void setRangeBut(Button rangeBut) {
		this.rangeBut = rangeBut;
	}
	public Button getSingeBut() {
		return singeBut;
	}
	public void setSingeBut(Button singeBut) {
		this.singeBut = singeBut;
	}
	public Button getIntRangeLoIncBtn() {
		return intRangeLoIncBtn;
	}
	public void setIntRangeLoIncBtn(Button intRangeLoIncBtn) {
		this.intRangeLoIncBtn = intRangeLoIncBtn;
	}
	public Button getIntRangeUpIncBtn() {
		return intRangeUpIncBtn;
	}
	public void setIntRangeUpIncBtn(Button intRangeUpIncBtn) {
		this.intRangeUpIncBtn = intRangeUpIncBtn;
	}
	public Button getBoolTrueBtn() {
		return boolTrueBtn;
	}
	public void setBoolTrueBtn(Button boolTrueBtn) {
		this.boolTrueBtn = boolTrueBtn;
	}
	public Button getBoolFalseBtn() {
		return boolFalseBtn;
	}
	public void setBoolFalseBtn(Button boolFalseBtn) {
		this.boolFalseBtn = boolFalseBtn;
	}
	public Button getRealRangeLoIncBtn() {
		return realRangeLoIncBtn;
	}
	public void setRealRangeLoIncBtn(Button realRangeLoIncBtn) {
		this.realRangeLoIncBtn = realRangeLoIncBtn;
	}
	public Button getRealRangeUpIncBtn() {
		return realRangeUpIncBtn;
	}
	public void setRealRangeUpIncBtn(Button realRangeUpIncBtn) {
		this.realRangeUpIncBtn = realRangeUpIncBtn;
	}
	public Button getLongRangeLoIncBtn() {
		return longRangeLoIncBtn;
	}
	public void setLongRangeLoIncBtn(Button longRangeLoIncBtn) {
		this.longRangeLoIncBtn = longRangeLoIncBtn;
	}
	public Button getLongRangeUpIncBtn() {
		return longRangeUpIncBtn;
	}
	public void setLongRangeUpIncBtn(Button longRangeUpIncBtn) {
		this.longRangeUpIncBtn = longRangeUpIncBtn;
	}
	public Button getDateTimeRangeLoIncBtn() {
		return dateTimeRangeLoIncBtn;
	}
	public void setDateTimeRangeLoIncBtn(Button dateTimeRangeLoIncBtn) {
		this.dateTimeRangeLoIncBtn = dateTimeRangeLoIncBtn;
	}
	public Button getDateTimeRangeUpIncBtn() {
		return dateTimeRangeUpIncBtn;
	}
	public void setDateTimeRangeUpIncBtn(Button dateTimeRangeUpIncBtn) {
		this.dateTimeRangeUpIncBtn = dateTimeRangeUpIncBtn;
	}
	public Button getLoDatebutton() {
		return loDatebutton;
	}
	public void setLoDatebutton(Button loDatebutton) {
		this.loDatebutton = loDatebutton;
	}
	public Button getHiDatebutton() {
		return hiDatebutton;
	}
	public void setHiDatebutton(Button hiDatebutton) {
		this.hiDatebutton = hiDatebutton;
	}
	public Button getSimpleDatebutton() {
		return simpleDatebutton;
	}
	public void setSimpleDatebutton(Button simpleDatebutton) {
		this.simpleDatebutton = simpleDatebutton;
	}
	public Text getIntRangeLoText() {
		return intRangeLoText;
	}
	public void setIntRangeLoText(Text intRangeLoText) {
		this.intRangeLoText = intRangeLoText;
	}
	public Text getIntRangeUpText() {
		return intRangeUpText;
	}
	public void setIntRangeUpText(Text intRangeUpText) {
		this.intRangeUpText = intRangeUpText;
	}
	public Text getSimpleDateValueText() {
		return simpleDateValueText;
	}
	public void setSimpleDateValueText(Text simpleDateValueText) {
		this.simpleDateValueText = simpleDateValueText;
	}
	public Text getSimpleLongValueText() {
		return simpleLongValueText;
	}
	public void setSimpleLongValueText(Text simpleLongValueText) {
		this.simpleLongValueText = simpleLongValueText;
	}
	public Text getSimpleDoubleValueText() {
		return simpleDoubleValueText;
	}
	public void setSimpleDoubleValueText(Text simpleDoubleValueText) {
		this.simpleDoubleValueText = simpleDoubleValueText;
	}
	public Text getRealRangeLoText() {
		return realRangeLoText;
	}
	public void setRealRangeLoText(Text realRangeLoText) {
		this.realRangeLoText = realRangeLoText;
	}
	public Text getRealRangeUpText() {
		return realRangeUpText;
	}
	public void setRealRangeUpText(Text realRangeUpText) {
		this.realRangeUpText = realRangeUpText;
	}
	public Text getLongRangeLoText() {
		return longRangeLoText;
	}
	public void setLongRangeLoText(Text longRangeLoText) {
		this.longRangeLoText = longRangeLoText;
	}
	public Text getLongRangeUpText() {
		return longRangeUpText;
	}
	public void setLongRangeUpText(Text longRangeUpText) {
		this.longRangeUpText = longRangeUpText;
	}
	public Text getDateTimeRangeLoText() {
		return dateTimeRangeLoText;
	}
	public void setDateTimeRangeLoText(Text dateTimeRangeLoText) {
		this.dateTimeRangeLoText = dateTimeRangeLoText;
	}
	public Text getDateTimeRangeUpText() {
		return dateTimeRangeUpText;
	}
	public void setDateTimeRangeUpText(Text dateTimeRangeUpText) {
		this.dateTimeRangeUpText = dateTimeRangeUpText;
	}
	public ScrolledPageBook getValueScrollPageBook() {
		return valueScrollPageBook;
	}
	public void setValueScrollPageBook(ScrolledPageBook valueScrollPageBook) {
		this.valueScrollPageBook = valueScrollPageBook;
	}
	public DomainFormEditor getEditor() {
		return editor;
	}
	
	public TableEditor getTableEditor() {
		return tableEditor;
	}
	
	protected void readOnlyWidgets(){
		domainDescText.setEditable(false);
		button.setEnabled(false);
		domainInheritsText.setEditable(false);

		domainDataTypesCombo.setEnabled(false);
		dependencyDiagramAction.setEnabled(false);
		simpleIntValueText.setEditable(false);
		simpleValueText.setEditable(false);

		rangeBut.setEnabled(false);
		singeBut.setEnabled(false);

		intRangeLoIncBtn.setEnabled(false);
		intRangeUpIncBtn.setEnabled(false);
		
		booleanLabel.setVisible(false);
		boolTrueBtn.setVisible(false);
		boolFalseBtn.setVisible(false);

		realRangeLoIncBtn.setEnabled(false);
		realRangeUpIncBtn.setEnabled(false);
		longRangeLoIncBtn.setEnabled(false);
		longRangeUpIncBtn.setEnabled(false);
		dateTimeRangeLoIncBtn.setEnabled(false);
		dateTimeRangeUpIncBtn.setEnabled(false);
		loDatebutton.setEnabled(false);
		hiDatebutton.setEnabled(false);
		simpleDatebutton.setEnabled(false);

		intRangeLoText.setEditable(false);
		intRangeUpText.setEditable(false);
		simpleDateValueText.setEditable(false);
		simpleLongValueText.setEditable(false);
		simpleDoubleValueText.setEditable(false);
		realRangeLoText.setEditable(false);
		realRangeUpText.setEditable(false);
		longRangeLoText.setEditable(false);
		longRangeUpText.setEditable(false);
		dateTimeRangeLoText.setEditable(false);
		dateTimeRangeUpText.setEditable(false);

		addRowButton.setEnabled(false);
		removeRowButton .setEnabled(false);
		inheritlink.setEnabled(false);
	}
	
	public DomainRangePageModifyListener getDomainRangePageModifyListener() {
		return domainRangePageModifyListener;
	}

	public void setDomainRangePageModifyListener(
			DomainRangePageModifyListener domainRangePageModifyListener) {
		this.domainRangePageModifyListener = domainRangePageModifyListener;
	}
}