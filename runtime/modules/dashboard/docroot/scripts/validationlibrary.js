var numbers="-0123456789";

var numberswithdecimal="-0123456789.";

var aplhas="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

function trim(str){
	/*var trimmedstr = "";
	strlength = str.length;
	for (var i = 0 ; i < strlength ; i++){
		strchar = str.charAt(i);
		if (strchar != ' '){
			trimmedstr = trimmedstr + strchar;
		}
	}
	return trimmedstr;*/
	return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

function isNumeric(str){
	str = trim(str);
	strlength = str.length;
	if (strlength == 0){
		return false;
	}
	for (var i = 0 ; i < strlength ; i++){
		strchar = str.charAt(i);
		if (numbers.indexOf(strchar) == -1){
			return false;
		}
	}
	return true;
}

function isDecimal(str){
	str = trim(str);
	strlength = str.length;
	if (strlength == 0){
		return false;
	}
	for (var i = 0 ; i < strlength ; i++){
		strchar = str.charAt(i);
		if (numberswithdecimal.indexOf(strchar) == -1){
			return false;
		}
	}
	return true;
}

function isAlpha(str){
	str = trim(str);
	strlength = str.length;
	if (strlength == 0){
		return false;
	}
	for (var i = 0 ; i < strlength ; i++){
		strchar = str.charAt(i);
		if (aplhas.indexOf(strchar) == -1){
			return false;
		}
	}
	return true;
}

function isAlphaNumeric(str){
	str = trim(str);
	strlength = str.length;
	if (strlength == 0){
		return false;
	}
	for (var i = 0 ; i < strlength ; i++){
		strchar = str.charAt(i);
		if (aplhas.indexOf(strchar) == -1 && numbers.indexOf(strchar) == -1){
			return false;
		}
	}
	return true;
}

function validateCheckBoxSelectionWithName(form,elementname){
	var checkboxselection = false;
	for (var i = 0 ; i < form.elements.length ; i++){
		if (form.elements[i].type == "checkbox" && form.elements[i].name.indexOf(elementname) != -1 && form.elements[i].checked == true){
			checkboxselection = true;
		}
	}
	return checkboxselection;
}

function validateRadioButtonSelection(form,elementname){
	var radioselection = false;
	for (var i = 0 ; i < form.elements.length ; i++){
		if (form.elements[i].type == "radio" && form.elements[i].name == elementname && form.elements[i].checked == true){
			radioselection = true;
		}
	}
	return radioselection;
}

function validateSelectSelection(form,elementname){
	for (var i = 0 ; i < form.elements.length ; i++){
		if (form.elements[i].type.indexOf("select") != -1 && form.elements[i].name == elementname){
			return (form.elements[i].selectedIndex > -1);
		}
	}
	return false;
}