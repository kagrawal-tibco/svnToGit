package com.tibco.be.views.utils{
	
    import com.tibco.be.views.ui.mdaeditor.io.XMLRequestHelper;
    
    import flash.events.Event;
    import flash.events.FocusEvent;
    import flash.events.TextEvent;
    
    import mx.collections.IList;
    import mx.controls.CheckBox;
    import mx.controls.ComboBox;
    import mx.controls.NumericStepper;
    import mx.controls.RadioButton;
    import mx.controls.TextArea;
    import mx.controls.TextInput;
    import mx.core.UIComponent;
    import mx.events.ListEvent;
    import mx.events.NumericStepperEvent;
    
    public class XMLUIBinder
    {
        private var _element : XML = null;
        private var _property : String = null;

        private var _options : Object = null;
        
        private var _optionKey : String = null;
        
        private var _optionLabel : String = null;
        
        private var _ui : UIComponent = null;
        
        private var _commit : Boolean = false;
        
        private var _textOnChange : Boolean = false;
        
        private var _changeHandler : IChangeHandler = null;
        
        private var _value : Object = null;
        
        public function XMLUIBinder(i_Element : XML, i_property : String, i_ui : UIComponent, i_commit : Boolean, i_options : Object = null, i_optionKey : String = null, i_optionLabel : String = null, i_textOnChange : Boolean = false ) : void {
            this._element = i_Element;
            this._property = i_property;
            this._optionKey = i_optionKey;
            this._optionLabel = i_optionLabel;
            this._ui = i_ui;
            this._commit = i_commit;
            if (i_options != null) {
                if(!(i_options is Array) && !(i_options is XMLList) ) {
                    throw new Error("Options class cast exception");
                }
            }
            this._options = i_options;
            this._textOnChange = i_textOnChange;

            initValue();
            initUI();
            bind();
        }
        
        private function initValue () : void {
            if (_optionKey == null) {
                this._value = _element[_property];
            }
            else{
                for each (var option : Object in _options) {
                    if (option[_optionKey] == _element[_property]) {
                        this._value = option;
                        break;        
                    }
                }
            }
        }
        
        private function initUI() : void {
            if (_ui is ComboBox) {
                (_ui as ComboBox).dataProvider = _options;
                if (_optionLabel != null) {
                    (_ui as ComboBox).labelField = _optionLabel;
                }
            }
            else if (_ui is NumericStepper) {
                if (_options != null) {
                    (_ui as NumericStepper).minimum = Number(_options[0]);
                    (_ui as NumericStepper).maximum = Number(_options[1]);
                    if ((_options[0] as String).indexOf(".") > -1 ) {
                        (_ui as NumericStepper).stepSize = 0.1;
                    }
                }
            }
        }
        
        private function bind () : void {
            //model to ui
            updateUI();
            //ui to model
            initListeners();
            
        }
        
        private function updateUI () : void {
            var sValue:String = (_value == null) ? "" : String(_value);
 			if (_ui is ComboBox) {
 				var combo:ComboBox = _ui as ComboBox;
 				if (_optionKey == null) {
 				    //use string based
    	 		    if (sValue == "") {
    	 		       sValue = (combo.dataProvider as IList).getItemAt(0).toString();
    	 		       setValue(sValue);
    	 		    }
     				combo.selectedItem = sValue;
 				}
 				else{
 				    //use xml element based option
 				    var xValue : Object = _value;
 				    if (xValue == null) {
 				        xValue = (combo.dataProvider as IList).getItemAt(0);
 				        setValue(xValue);
 				    }
 				    combo.selectedItem = xValue;
 				}
 			}
 			else if (_ui is TextInput) {
 				(_ui as TextInput).text = sValue;
 			}
 			else if (_ui is TextArea) {
 				(_ui as TextArea).text = sValue;
 			}
            else if (_ui is NumericStepper) {
                (_ui as NumericStepper).value = Number(sValue);
            }
            else if (_ui is CheckBox) {
                (_ui as CheckBox).selected = (sValue == "true");
            }
            else if (_ui is RadioButton) {
                (_ui as RadioButton).selected = (sValue == _options[0]);
            }
        }
        
        private function initListeners () : void {
 			if (_ui is ComboBox) {
 				(_ui as ComboBox).addEventListener(ListEvent.CHANGE, updateModel);
 			}
 			else if (_ui is TextInput) {
 			    if (_textOnChange) {
     				(_ui as TextInput).addEventListener(TextEvent.TEXT_INPUT, updateModel);
 			    }
 			    else{
     				(_ui as TextInput).addEventListener(FocusEvent.FOCUS_OUT, updateModel);
 			    }
 			}
 			else if (_ui is TextArea) {
 			    if (_textOnChange) {
 				   (_ui as TextArea).addEventListener(TextEvent.TEXT_INPUT, updateModel);
 			    }
 			    else{
 				   (_ui as TextArea).addEventListener(FocusEvent.FOCUS_OUT, updateModel);
 			    }
 			}
            else if (_ui is NumericStepper) {
                (_ui as NumericStepper).addEventListener(NumericStepperEvent.CHANGE, updateModel);
            }
            else if (_ui is CheckBox) {
                (_ui as CheckBox).addEventListener(Event.CHANGE, updateModel);
            }
            else if (_ui is RadioButton) {
                (_ui as RadioButton).addEventListener(Event.CHANGE, updateModel);
            }
        }
        
 		private function updateModel(event : Event ) : void {
 		    var bChange : Boolean = true;
 		    
 			if (_ui is ComboBox) {
 				bChange = setValue((_ui as ComboBox).selectedItem);
 			}
 			else if (_ui is TextInput) {
 				bChange = setValue((_ui as TextInput).text);
 			}
 			else if (_ui is TextArea) {
 				bChange = setValue((_ui as TextArea).text);
 			}
            else if (_ui is NumericStepper) {
                bChange = setValue((_ui as NumericStepper).value);
            }
            else if (_ui is CheckBox) {
                bChange = setValue(String((_ui as CheckBox).selected));
            }
            else if (_ui is RadioButton) {
                var rdb : RadioButton = _ui as RadioButton;
                if (rdb.selected) {
                    bChange = setValue(_options[0]);
                }
                else{
                    return;
                }
            }
 			if (bChange && _changeHandler != null) {
 			    _changeHandler.dataChanged(this);
 			}
		}
		
		public function get value () : Object {
		    return _value;
		}
		
		private function setValue(i_value:Object ) : Boolean {
            if (_optionKey == null) {
                if (i_value == _value) return false;
                _value = i_value;
    		    if (_commit) {
    		        _element[_property] = _value;
    		    }
            }
            else{
                if (i_value == _value) return false;
                _value = i_value;
    		    if (_commit) {
    		        _element[_property] = _options[_optionKey];
    		    }
            }
            return true;
		}
		
		public function get element () : XML {
		    return _element;
		}
		
		public function get property () : String {
		    return _property;
		}
		
		public function get commit () : Boolean {
		    return _commit;
		}
		
		public function set changeHandler(handler : IChangeHandler ) : void {
		    _changeHandler = handler;
		}
		
		public function clear () : void {
 			if (_ui is ComboBox) {
 				(_ui as ComboBox).removeEventListener(ListEvent.CHANGE, updateModel);
 			}
 			else if (_ui is TextInput) {
 			    if (_textOnChange) {
     				(_ui as TextInput).removeEventListener(TextEvent.TEXT_INPUT, updateModel);
 			    }
 			    else{
     				(_ui as TextInput).removeEventListener(FocusEvent.FOCUS_OUT, updateModel);
 			    }
 			}
 			else if (_ui is TextArea) {
 			    if (_textOnChange) {
 				   (_ui as TextArea).removeEventListener(TextEvent.TEXT_INPUT, updateModel);
 			    }
 			    else{
 				   (_ui as TextArea).removeEventListener(FocusEvent.FOCUS_OUT, updateModel);
 			    }
 			}
            else if (_ui is NumericStepper) {
                (_ui as NumericStepper).removeEventListener(NumericStepperEvent.CHANGE, updateModel);
            }
            else if (_ui is CheckBox) {
                (_ui as CheckBox).removeEventListener(Event.CHANGE, updateModel);
            }
            else if (_ui is RadioButton) {
                (_ui as RadioButton).removeEventListener(Event.CHANGE, updateModel);
            }
		}
		
		public function get request () : XML {
		    if (_optionKey == null) {
		        return XMLRequestHelper.instance.createToRoot(_element, _property, _value);
		    }
		    else{
		        return XMLRequestHelper.instance.createToRoot(_element, _property, _value[_optionKey]);
		    }
		}
		
		public function reset() : void {
		    initValue();
		    updateUI();
		}
   }
}