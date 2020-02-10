package com.tibco.cep.studio.core.rules.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class RuleTemplatePresentationConverter {

	private String html;
	private StringWriter out;
	private RuleTemplate template;

	public RuleTemplatePresentationConverter(RuleTemplate template, String html) {
		this.template = template;
		this.out = new StringWriter();
		this.html = html;
		if (this.html != null) {
			parse(this.html);
		}
		String writtenHtml = this.out.getBuffer().toString();
		System.out.println(writtenHtml);
	}
	
	public String getConvertedHtml() {
		String writtenHtml = this.out.getBuffer().toString();
		return writtenHtml;
	}
	
	protected void parse(String html) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(html));
			new ParserDelegator().parse(reader, new CallbackHandler(), true);
		} catch (IOException ioe) {
			System.out.println("IOE: " + ioe);
		}
	}

	private class CallbackHandler extends HTMLEditorKit.ParserCallback {

		public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
			try {
				out.write("<" + tag);
				writeAttributes(attributes);
				out.write(">");
				out.flush();
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}

		public void handleEndTag(HTML.Tag tag, int position) {
			try {
				out.write("</" + tag + ">");
				out.flush();
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}



		public void handleComment(char[] text, int position) {
			try {
				out.write("<!-- ");
				out.write(text);
				out.write(" -->");
				out.flush();
			} catch (IOException ex) {
				System.err.println(ex);
			}

		}

		public void handleText(char[] text, int position) {

			try {
				out.write(text);
				out.flush();
			} catch (IOException ex) {
				System.err.println(ex);
			}

		}

		public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
			try {
				out.write("<");
				if ("binding".equals(tag.toString())) {
					if (attributes.containsAttribute(HTML.Attribute.ENDTAG, "true")) {
						out.write("/binding");
					} else {
						writeBinding(attributes);
					}
				} else {
					out.write(tag.toString());
					writeAttributes(attributes);
				}
				out.write(">");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void writeBinding(MutableAttributeSet attributes) throws IOException {
		String bindingId = (String) attributes.getAttribute(HTML.Attribute.ID);
		Binding binding = getBinding(bindingId);
		if (binding == null) {
			out.write("input");
			writeAttributes(attributes);
			return;
		}
		writeBindingType(binding);
	}

	private void writeBindingType(Binding binding) {
		String domainModelPath = binding.getDomainModelPath();
		if (domainModelPath != null) {
			Domain domain = CommonIndexUtils.getDomain(getRuleTemplate().getOwnerProjectName(), domainModelPath);
			if (domain == null) {
				writeNonDomainBinding(binding);
				return;
			}
			writeDomainBinding(binding, domain);
		} else {
			writeNonDomainBinding(binding);
		}
	}

	private void writeDomainBinding(Binding binding, Domain domain) {
		out.write("select name=\""+binding.getIdName()+"\">");
		
		String value = "text";
		DOMAIN_DATA_TYPES dataType = domain.getDataType();
		switch (dataType) {
		case BOOLEAN:
			value = "checkbox";
			break;
			
		case DOUBLE:
		case INTEGER:
		case LONG:
		case STRING:
			value = "text";
			break;
			
		case DATE_TIME:
			value = "text";
			
		default:
			break;
		}
		value = dataType.getLiteral();
		EList<DomainEntry> entries = domain.getEntries();
		for (DomainEntry domainEntry : entries) {
			Object entryValue = domainEntry.getValue();
			writeDomainEntry(value, domainEntry);
			out.write("\n");
		}
		out.write("</select");
	}

	/**
	 * This method is used to create drop down options for domain entries.
	 */
	private void writeDomainEntry(String value, DomainEntry domainEntry) {
		out.write("<option>");
		out.write(domainEntry.getValue().toString());
		out.write("</option>");
	}

	private void writeNonDomainBinding(Binding binding) {
		String name = "type";
		String value = "text";

		String type = binding.getType();
		if ("boolean".equals(type)) {
			value = "checkbox";
		} else if ("DateTime".equals(type)) {
			value = "text";
		}
		out.write("input");
		out.write(" " + name + "=\"" + value + "\"");
	}

	private Binding getBinding(String bindingId) {
		RuleTemplate template = getRuleTemplate();
		if (template == null) {
			return null;
		}
		EList<Binding> bindings = template.getBindings();
		for (Binding binding : bindings) {
			if (bindingId.equals(binding.getIdName())) {
				return binding;
			}
		}

		return null;
	}

	private RuleTemplate getRuleTemplate() {
		return template;
	}
	
	private void writeAttributes(AttributeSet attributes) throws IOException {

		Enumeration e = attributes.getAttributeNames();
		while (e.hasMoreElements()) {
			Object name = e.nextElement();
			Object value = attributes.getAttribute(name);
			try {
				if (name == HTML.Attribute.HREF || name == HTML.Attribute.SRC
						|| name == HTML.Attribute.LOWSRC || name == HTML.Attribute.CODEBASE) {
					URL u = new URL(value.toString());
					out.write(" " + name + "=\"" + u + "\"");
				} else {
					out.write(" " + name + "=\"" + value + "\"");
				}
			} catch (MalformedURLException ex) {
				System.err.println(ex);
				System.err.println(value);
				ex.printStackTrace();
			}
		}
	}
}
