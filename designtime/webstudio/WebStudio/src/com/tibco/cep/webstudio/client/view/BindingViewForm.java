package com.tibco.cep.webstudio.client.view;

import static com.tibco.cep.webstudio.client.IWebStudioWorkbenchConstants.PROP_SAVE;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.menu.Menu;
import com.tibco.cep.webstudio.client.diff.MergedDiffHelper;
import com.tibco.cep.webstudio.client.diff.MergedDiffModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationType;
import com.tibco.cep.webstudio.client.diff.SyncMergeHelper;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.DomainInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.ViewInfo;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.client.widgets.DateTimePicker;
import com.tibco.cep.webstudio.model.rule.instance.Binding;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.impl.BindingImpl;

/**
 * Widget to display view for Rule Template Instance containing view bindings.
 * 
 */
public class BindingViewForm extends Canvas {

	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(BindingViewForm.class.getName());
	private ViewInfo viewInfo;
	private RuleTemplateInstance ruleTemplateInstance;
	private AbstractEditor editor;
	private HTMLPanel html;
	private static GlobalMessages globalMsg = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private String ruleTemplateView;
	private String ruleTemplateName;
	/**
	 * Used to show tooltip on a widget in RTIView diff mode.
	 */
	private PopupPanel popupPanel;

	/**
	 * Default constructor
	 * 
	 * @param ruleTemplateInstance
	 * @param info
	 * @param editor
	 */
	public BindingViewForm(RuleTemplateInstance ruleTemplateInstance, ViewInfo info, AbstractEditor editor) {
		this.ruleTemplateInstance = ruleTemplateInstance;
		this.editor = editor;
		this.viewInfo = info;
		initialize();
	}

	/**
	 * Initialize Binding view form
	 */
	private void initialize() {
		this.setShowEdges(true);
		this.setOverflow(Overflow.AUTO);
		this.setEdgeMarginSize(20);
		this.setEdgeOffset(20);
		this.setMargin(10);
		this.setHeight100();
		this.setWidth100();
		
		html = new HTMLPanel(this.viewInfo.getHtml());
		html.setSize("100%", "100%");
		
		this.ruleTemplateView = RuleTemplateHelper.getArtifactName(this.ruleTemplateInstance.getName()) + "_";
		this.ruleTemplateName = RuleTemplateHelper.getArtifactName(this.ruleTemplateInstance.getImplementsPath()) + "_";
		setupBindingWidgets();
		
		this.addChild(html);
		
		Timer t = new Timer() {
		      public void run() {
		    	  scripting();
		      }
		    };
		 t.schedule(0);
		
	}
	
	private void scripting() {
		String prefix = RuleTemplateHelper.getArtifactName(this.ruleTemplateInstance.getName());
		String script = this.viewInfo.getScript();
		RuleTemplateHelper.checkExistingScriptAndRemove(prefix);
		createAndInjectScript(script, prefix);
	}
	
	/**
	 * Setup widgets in place of the actual binding
	 * @param html
	 */
	private void setupBindingWidgets() {
		List<BindingInfo> bindings = this.viewInfo.getBindings();
		logger.info("Replacing bindings with widgets for " + bindings.size() + " entries.");
		for (BindingInfo bindingInfo : bindings) {
			String id = bindingInfo.getId();
			if (html.getElementById(id) != null) {
				Widget widget = createBindingWidget(bindingInfo);
				
				if (widget != null) {
					final ModificationEntry modificationEntry = ((RuleTemplateInstanceEditor) this.editor).getBindingModifications().get(bindingInfo);
					
					Widget wrappedWidget = widget;
					if (modificationEntry instanceof MergedDiffModificationEntry && modificationEntry.getModificationType() == ModificationType.MODIFIED) {
						disableWidget(widget);
						String mergeDiffToolTip = MergedDiffHelper.getTooltipString((MergedDiffModificationEntry)modificationEntry);
						if(mergeDiffToolTip != null) {
							wrappedWidget = addTooltip(widget, mergeDiffToolTip);
						}
					}
					else if (modificationEntry != null && modificationEntry.getModificationType() == ModificationType.MODIFIED) {
						disableWidget(widget);
						wrappedWidget = addTooltip(widget, "<b>" + globalMsg.text_previousValue() + ":</b><br/><nobr>" + modificationEntry.getPreviousValue() + "</nobr>");
					}
					
					//Add a context menu for Sync Merge
					if (modificationEntry instanceof MergedDiffModificationEntry && ((RuleTemplateInstanceEditor) this.editor).isSyncMerge()) {
						final Menu menu = SyncMergeHelper.createSyncMergeContextMenu((MergedDiffModificationEntry)modificationEntry,
								widget, bindingInfo, (RuleTemplateInstanceEditor) this.editor, this);
						if (menu != null) {
							//Context menu wont work for disabled-Widget, adding to wrapped widget.
							wrappedWidget.sinkEvents(Event.ONCONTEXTMENU);
							wrappedWidget.addHandler(new ContextMenuHandler() {
								@Override
								public void onContextMenu(ContextMenuEvent event) {
									event.preventDefault();
									event.stopPropagation();
									if (!modificationEntry.isApplied()) {
										menu.setTop(event.getNativeEvent().getClientY());
										menu.setLeft(event.getNativeEvent().getClientX());
										menu.show();
									}
								}
							}, ContextMenuEvent.getType());
						}
					}
					MergedDiffHelper.applyDiffCSSStyle(modificationEntry, widget);
					
					html.addAndReplaceElement(wrappedWidget, id);
				}
			}
		}
		
	}
	
	/**
	 * This method adds a tooltip similar to SmartGWT tooltip, to a GWT
	 * widget. Used to show previous value in RTIView diff.
	 * 
	 * @return
	 */
	private Widget addTooltip(Widget widget, final String tooltip) {
		if (popupPanel == null) {
			popupPanel = new PopupPanel(true);
			popupPanel.setHeight("40px");
			popupPanel.getElement().getStyle().setPosition(Position.FIXED);
			popupPanel.setVisible(false);
			popupPanel.setStylePrimaryName("gridHover");
			html.add(popupPanel);
		}
		FocusPanel focusPanel = new FocusPanel();
		focusPanel.getElement().getStyle().setDisplay(Display.INLINE);
		focusPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		focusPanel.getElement().getStyle().setWidth(100, Unit.PCT);
		focusPanel.getElement().getStyle().setHeight(100, Unit.PCT);
		focusPanel.getElement().getStyle().setOpacity(0);
		focusPanel.getElement().getStyle().setPadding(2, Unit.PX);
		focusPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				popupPanel.setVisible(false);
				int mouse_x = event.getNativeEvent().getClientX();
				int mouse_y = event.getNativeEvent().getClientY();
				popupPanel.setPopupPosition(mouse_x + 5, mouse_y + 5);
				popupPanel.setWidget(new InlineHTML(tooltip));
				popupPanel.setVisible(true);
			}
		});
		focusPanel.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				popupPanel.setVisible(false);
			}
		});
		AbsolutePanel horizontalPanel = new AbsolutePanel();
		horizontalPanel.getElement().getStyle().setDisplay(Display.INLINE);
		horizontalPanel.add(focusPanel);
		horizontalPanel.add(widget);
		return horizontalPanel.asWidget();
	}
	
	/**
	 * Create various widgets based on view definition
	 * 
	 * @param bindingInfo
	 * @return
	 */
	private Widget createBindingWidget(final BindingInfo bindingInfo) {
		DomainInfo domainInfo = bindingInfo.getDomainInfo();
		final Binding binding = getBinding(bindingInfo.getId());
		
		if (binding == null && bindingInfo.getValue() != null) addNewBinding(bindingInfo, null, false);
		
		if (domainInfo != null) {
			return createDropdownBox(bindingInfo, binding, domainInfo.getValues());
		}
		
		String type = bindingInfo.getType();
		if ("int".equals(type)) {
			return createTextBox(bindingInfo, binding);
		} else if ("long".equals(type)) {
			return createTextBox(bindingInfo, binding);
		} else if ("double".equals(type)) {
			return createTextBox(bindingInfo, binding);
		} else if ("String".equals(type)) {
			return createTextArea(bindingInfo, binding);
		} else if ("boolean".equals(type)) {
			return createCheckBox(bindingInfo, binding);
		} else if ("DateTime".equals(type)) {
			return createDateTimePicker(bindingInfo, binding);
		}
		return null;
	}
	
	/**
	 * Create Listbox widget for domain models
	 * 
	 * @param bindingInfo
	 * @param binding
	 * @param domainValues
	 * @return
	 */
	private Widget createDropdownBox(final BindingInfo bindingInfo, Binding binding, Map<String, String> domainValues) {
		final ListBox list = new ListBox(false);
		String value = (binding != null)? binding.getValue() : bindingInfo.getValue();
		Element element = list.getElement();
		element.setId(ruleTemplateView + bindingInfo.getId());
		element.addClassName(ruleTemplateName + bindingInfo.getId());
		setBindingStyle(element, bindingInfo);
		if (value == null) {
			list.insertItem("Select", -1);
		}
		
		for (String id : domainValues.keySet()) {
			String val = domainValues.get(id);
			list.addItem(val);
			if (value != null && val.equals(value)) {
				list.setSelectedIndex(list.getItemCount()-1);
			}
		}
		
		list.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String val = list.getValue(list.getSelectedIndex());
				Binding binding = getBinding(bindingInfo.getId());
				if (binding != null) {
					binding.setValue(val);
					editor.makeDirty();
				} else {
					addNewBinding(bindingInfo, val, true);
				}
			}
		});
		logger.debug("Created Dropdown widget.");
		return list;
	}
	

	/**
	 * Create checkbox widget for boolean bindings
	 * 
	 * @param bindingInfo
	 * @param binding
	 * @return
	 */
	private Widget createCheckBox(final BindingInfo bindingInfo, Binding binding) {
		final CheckBox checkBox = new CheckBox();
		String value = (binding != null) ? binding.getValue() : bindingInfo.getValue();
		checkBox.setValue(Boolean.valueOf(value));
		
		Element element = checkBox.getElement();
		element.setId(ruleTemplateView + bindingInfo.getId());
		element.addClassName(ruleTemplateName + bindingInfo.getId());
		setBindingStyle(element, bindingInfo);
		checkBox.addClickHandler(new ClickHandler() {
		
			@Override
			public void onClick(ClickEvent event) {
				Boolean b = checkBox.getValue();
				Binding binding = getBinding(bindingInfo.getId());
				if (binding != null) {
					binding.setValue(b.toString());
					editor.makeDirty();
				} else {
					addNewBinding(bindingInfo, b.toString(), true);
				}
			}
		});
		logger.debug("Created CheckBox widget.");
		return checkBox;
	}
	
	/**
	 * Create DateTimePicker widget for DateTime bindings
	 * 
	 * @param bindingInfo
	 * @param binding
	 * @return
	 */
	private Widget createDateTimePicker(final BindingInfo bindingInfo, Binding binding) {
		DateTimePicker dateTimePicker = new DateTimePicker(null);
		Element element = dateTimePicker.getElement();
		element.setId(ruleTemplateView + bindingInfo.getId());
		element.addClassName(ruleTemplateName + bindingInfo.getId());
		setBindingStyle(element, bindingInfo);
		if (bindingInfo != null && bindingInfo.getValue() != null) {
			try {
				Date date = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).parse(bindingInfo.getValue());
				if (date != null) {
					dateTimePicker.setValue(date);
				}
			}
			catch(Exception e) {
				logger.error("Error while parsing dateTime binding - " + e.getMessage());
			}
		}
		dateTimePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date newDate = event.getValue();
				if (newDate == null) {
					return;
				}
				Binding binding = getBinding(bindingInfo.getId());
				String formattedDate = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).format(newDate);
				if (binding != null) {
					binding.setValue(formattedDate);
					editor.makeDirty();
				} else {
					addNewBinding(bindingInfo, formattedDate, true);
				}
			}
		});
		return dateTimePicker;
	}
	
	/**
	 * Create a textbox widget for string bindings
	 * 
	 * @param bindingInfo
	 * @param binding
	 * @return
	 */
	private Widget createTextBox(final BindingInfo bindingInfo, Binding binding) {
		final TextBox newTextBox = new TextBox();
		String value = (binding != null) ? binding.getValue() : bindingInfo.getValue();
		newTextBox.setText(value);
		Element element = newTextBox.getElement();
		element.setId(ruleTemplateView + bindingInfo.getId());
		element.addClassName(ruleTemplateName + bindingInfo.getId());
		setBindingStyle(element, bindingInfo);
		
		newTextBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				try {
					String val = newTextBox.getValueOrThrow();
					clearError(newTextBox);
					
					Binding binding = getBinding(bindingInfo.getId());
					if (binding != null) {
						binding.setValue(val);
						editor.makeDirty();
					} else {
						addNewBinding(bindingInfo, val, true);
					}
					setBindingStyle(newTextBox.getElement(), bindingInfo);
				} catch (ParseException parseException) {
					setError(newTextBox);
					logger.error(parseException);
				}
			}
		});
		
		overrideSelectStartEvent(newTextBox.getElement());
		
		logger.debug("Created TextBox widget.");
		return newTextBox;
	}
	
	/**
	 * Create a textarea widget for string bindings
	 * 
	 * @param bindingInfo
	 * @param binding
	 * @return
	 */
	private Widget createTextArea(final BindingInfo bindingInfo, Binding binding) {
		final TextArea newTextArea = new TextArea();
		String value = (binding != null) ? binding.getValue() : bindingInfo.getValue();
		if (value != null) {
			int startingIndexOfQuote = value.indexOf("\"");
			int endingIndexOfQuote = value.lastIndexOf("\"");
			if (startingIndexOfQuote == 0 && endingIndexOfQuote == value.length() - 1) {
				value = value.replace("\"", "");
			} 
		}
		newTextArea.setValue(value);
		newTextArea.setCharacterWidth(20);
		newTextArea.setVisibleLines(5);
		Element element = newTextArea.getElement();
		element.setId(ruleTemplateView + bindingInfo.getId());
		element.addClassName(ruleTemplateName + bindingInfo.getId());
		setBindingStyle(element, bindingInfo);
		newTextArea.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				try {
					String val = newTextArea.getValueOrThrow();
					Binding binding = getBinding(bindingInfo.getId());
					if (binding != null) {
						binding.setValue(val);
						editor.makeDirty();
					} else {
						addNewBinding(bindingInfo, val, true);
					}
					setBindingStyle(newTextArea.getElement(), bindingInfo);
				} catch (ParseException parseException) {
					setError(newTextArea);
					logger.error(parseException);
				}
			}
		});
//		newTextArea.addKeyDownHandler(new KeyDownHandler() {
//
//			@Override
//			public void onKeyDown(KeyDownEvent event) {
//				try {
//					String val = newTextArea.getValueOrThrow();
//					clearError(newTextArea);
//					Binding binding = getBinding(bindingInfo.getId());
//					if (binding != null) {
//						binding.setValue(val);
//						editor.makeDirty();
//					} else {
//						addNewBinding(bindingInfo, val, true);
//					}
//					setBindingStyle(newTextArea.getElement(), bindingInfo);
//				} catch (ParseException parseException) {
//					setError(newTextArea);
//					logger.error(parseException);
//				}
//			}
//		});
		
		overrideSelectStartEvent(newTextArea.getElement());
		logger.debug("Created TextArea widget.");
		return newTextArea;
	}
	
	/**
	 * Override the selectstart event and stop propagation of the event, 
	 * needed to fix an issue in IE where user is not able to select content of textbox.
	 * @param inp
	 */
	public static native void overrideSelectStartEvent(Element inp) /*-{
		inp.onselectstart = function(e){ e.stopPropagation(); };
	}-*/;
	
	/**
	 * Get bindings by Id
	 * 
	 * @param id
	 * @return
	 */
	public Binding getBinding(String id) {
		List<Binding> bindings = ruleTemplateInstance.getBindings();
		for (Binding binding : bindings) {
			if (binding.getId().equals(id)) {
				return binding;
			}
		}
		return null;
	}
	
	/**
	 * Add Error condition
	 * @param widget
	 */
	private void setError(Widget widget) {
		widget.getElement().setAttribute("style", "border: 1px solid red;");
	}
	
	/**
	 * Clear the set error
	 * @param widget
	 */
	private void clearError(Widget widget) {
		widget.getElement().removeAttribute("style");
	}
	
	/**
	 * Returns the HTML panes width
	 * @return
	 */
	public int getHTMLPaneWidth() {
		return html.getOffsetWidth();
	}
	
	/**
	 * Disable all the widgets in the HMTL pane
	 */
	public void disableHTMLWidgets() {
		for(int i = 0; i < html.getWidgetCount(); i++) {
			Widget widget = html.getWidget(i);
			if (widget instanceof TextBox) {
				TextBox textBox = (TextBox) widget;
				textBox.setEnabled(false);
			} else if (widget instanceof CheckBox) {
				CheckBox checkBox = (CheckBox) widget;
				checkBox.setEnabled(false);
			} else if (widget instanceof ListBox) {
				ListBox listBox = (ListBox) widget;
				listBox.setEnabled(false);
			} else if (widget instanceof DateTimePicker) {
				DateTimePicker dtp = (DateTimePicker) widget;
				dtp.setEnabled(false);
			} else if (widget instanceof TextArea) {
				TextArea textArea = (TextArea) widget;
				textArea.setEnabled(false);
			}
		} 
	}
	
	/**
	 * Add a new binding value
	 * 
	 * @param bindingInfo
	 * @param value
	 */
	private void addNewBinding(BindingInfo bindingInfo, String value, boolean makeDirty) {
		Binding newBinding = new BindingImpl();
		newBinding.setId(bindingInfo.getId());
		String val = (value != null)? value : bindingInfo.getValue();
		newBinding.setValue(val);
		
		ruleTemplateInstance.addBinding(newBinding);
		
		if (!makeDirty) {
			editor.setPropertyID(PROP_SAVE);
			editor.updateTitle();
		}
	}
	
	private void disableWidget(Widget widget) {
		if (widget instanceof TextBox) {
			TextBox textBox = (TextBox) widget;
			textBox.setEnabled(false);
		} else if (widget instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) widget;
			checkBox.setEnabled(false);
		} else if (widget instanceof ListBox) {
			ListBox listBox = (ListBox) widget;
			listBox.setEnabled(false);
		} else if (widget instanceof DateTimePicker) {
			DateTimePicker dtp = (DateTimePicker) widget;
			dtp.setEnabled(false);
		} else if (widget instanceof TextArea) {
			TextArea textArea = (TextArea) widget;
			textArea.setEnabled(false);
		}
	}
	
	/**
	 * To fetch the attributes and its corresponding values from CSS.
	 * @param style.
	 * @return cssAttributes. 
	 */
	private static HashMap<String, String> parseStyle(String style) {
		HashMap<String, String> cssAttributes = new HashMap<String, String>();
		String styleAttributes[] = style.split(";");
		for (int i = 0; i < styleAttributes.length; i++) {
			String key = styleAttributes[i].split(":")[0].trim();
			String value = styleAttributes[i].split(":")[1].trim();
			String keySplit[] = key.split("-");
			if (keySplit.length > 1) {
				String partKey = keySplit[0].toLowerCase();
				key = partKey;
				for (int j = 1; j < keySplit.length; j++) {
					partKey = keySplit[j].toLowerCase();
					partKey = partKey.substring(0, 1).toUpperCase() + partKey.substring(1);
					key += partKey;
				}
			}
			cssAttributes.put(key, value);
		}
		return cssAttributes;
	}
	
	/**
	 *	To apply CSS to the element.
	 * @param element, bindingInfo
	 * @return
	 */
	private void setBindingStyle(Element element, BindingInfo bindingInfo) {
		if (bindingInfo.getStyle() != null) {
			HashMap<String, String>  style = parseStyle(bindingInfo.getStyle());
			for (String key : style.keySet()) {
				element.getStyle().setProperty(key, style.get(key));
			}	
		}
	}
	

	/**
	 * Creates new script element with given id and script. This script
	 * element is appended to the head element.
	 * 
	 * @param script
	 * @param id
	 */
	private void createAndInjectScript(String script, String id) {
		ScriptElement scriptElement = Document.get().createScriptElement();
		scriptElement.setText(script);
		scriptElement.setId(id);
		com.google.gwt.dom.client.Element element = Document.get().getElementsByTagName("head").getItem(0);
		HeadElement head = HeadElement.as(element);
		head.appendChild(scriptElement);
	}
	
}