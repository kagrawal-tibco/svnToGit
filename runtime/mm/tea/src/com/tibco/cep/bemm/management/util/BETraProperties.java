package com.tibco.cep.bemm.management.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class BETraProperties extends Properties {
	private static final long serialVersionUID = 849960817166043642L;
	static final String COMMENT = "#";
	static final String DEFAULT_PROPERTY_SEPERATOR = "=";
	private byte[] field_byteArray = null;
	private String field_byte = "=: \t\r\n\f#!";
	private static final String field_for = "= \t\r\n\f";
	private static final String field_case = " \t";
	private static final String field_if = "#";
	private String a = "=";
	protected List mRemovedKeys = new ArrayList();
	private List field_do = null;
	private List field_char = null;
	private List field_try = new ArrayList();
	private static final char[] field_new = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
			'C', 'D', 'E', 'F' };

	public BETraProperties() throws IOException {
	}

	public synchronized void load(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[16384];
		int nRead;
		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		field_byteArray = buffer.toByteArray();
		super.load(inputStream);
	}

	public synchronized void store(OutputStream outputStream) throws IOException {
		HashSet var3 = new HashSet();
		Vector var2 = this.a();
		BufferedWriter var4 = new BufferedWriter(new OutputStreamWriter(outputStream));
		Iterator var5 = this.field_try.iterator();

		String var6;
		while (var5.hasNext()) {
			var6 = (String) var5.next();
			var4.write(var6);
			var4.newLine();
		}

		String var7;
		for (int var17 = 0; var17 < var2.size(); ++var17) {
			var7 = ((String) var2.get(var17)).trim();
			boolean var8 = true;
			if (var7.length() == 0) {
				var8 = false;
			}

			int var10 = var7.length();

			int var9;
			for (var9 = 0; var9 < var10 && " \t".indexOf(var7.charAt(var9)) != -1; ++var9) {
				;
			}

			if (var10 > 0 && "#".indexOf(var7.charAt(var9)) != -1) {
				var8 = false;
			}

			if (var8) {
				int var11;
				for (var11 = var9; var11 < var10; ++var11) {
					char var12 = var7.charAt(var11);
					if (var12 == 92) {
						++var11;
					} else if ("= \t\r\n\f".indexOf(var12) != -1) {
						break;
					}
				}

				String var19 = var7.substring(var9, var11).trim();
				if (this.mRemovedKeys.contains(var19)) {
					var7 = "";
				} else if (this.field_do != null && this.field_do.contains(var19)) {
					var7 = "#" + var7;
				} else if (this.field_char == null || !this.field_char.contains(var19)) {
					Enumeration var13 = this.keys();

					while (var13.hasMoreElements()) {
						String var14 = (String) var13.nextElement();
						String var15 = this.a(var14, true);
						String var16 = (String) this.get(var14);
						if (var19.equals(var15)) {
							var7 = var15 + this.a + this.a(var16, false);
							var3.add(var14);
							break;
						}
					}
				}
			}
			if(!var7.isEmpty()){
				var4.write(var7);
				var4.newLine();
			}
		}

		var6 = "";
		var7 = "";
		Enumeration var18 = this.keys();
		while (var18.hasMoreElements()) {
			var6 = (String) var18.nextElement();
			if (this.get(var6) != null) {
				var7 = (String) this.get(var6);
			}

			if (!var3.contains(var6)) {
				var4.write(this.a(var6, true) + this.a + this.a(var7, false));
				var4.newLine();
			}
		}

		var4.flush();
		var4.close();
	}

	public void removeKey(Object var1) {
		if (!this.mRemovedKeys.contains(var1)) {
			this.mRemovedKeys.add(var1);
		}
		this.remove(var1);
	}

	public void commentOutKey(Object var1) {
		if (this.field_do == null) {
			this.field_do = new ArrayList();
		}
		this.field_do.add(var1);
		this.remove(var1);
	}

	public void ignoreKey(Object var1) {
		if (this.field_char == null) {
			this.field_char = new ArrayList();
		}
		this.field_char.add(var1);
	}

	public void setPropertySeparatorChar(String var1) {
		this.a = var1;
	}

	public void addPrefixLine(String var1) {
		if (null != var1) {
			this.field_try.add(var1);
		} else {
			this.field_try.add("");
		}
	}

	public void addPrefixProp(String var1, String var2) {
		this.field_try.add(var1 + "=" + var2);
	}

	public synchronized Enumeration keys() {
		Enumeration var1 = super.keys();
		Vector var2 = new Vector();
		while (var1.hasMoreElements()) {
			var2.add(var1.nextElement());
		}
		Collections.sort(var2);
		return var2.elements();
	}

	private synchronized Vector a() throws IOException {
		Vector var2 = new Vector();
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(field_byteArray);
			BufferedReader var4 = new BufferedReader(new InputStreamReader(bais));
			String var3;
			while ((var3 = var4.readLine()) != null) {
				var2.add(var3);
			}
			var4.close();
			return var2;
		} catch (IOException var5) {
			throw var5;
		}
	}

	private String a(String var1, boolean var2) {
		int var3 = var1.length();
		StringBuffer var4 = new StringBuffer(var3 * 2);

		for (int var5 = 0; var5 < var3; ++var5) {
			char var6 = var1.charAt(var5);
			switch (var6) {
			case '\t':
				var4.append('\\');
				var4.append('t');
				break;
			case '\n':
				var4.append('\\');
				var4.append('n');
				break;
			case '\f':
				var4.append('\\');
				var4.append('f');
				break;
			case '\r':
				var4.append('\\');
				var4.append('r');
				break;
			case ' ':
				if (var5 == 0 || var2) {
					var4.append('\\');
				}
				var4.append(' ');
				break;
			case '\\':
				var4.append('\\');
				var4.append('\\');
				break;
			default:
				if (var6 >= 32 && var6 <= 126) {
					if (this.field_byte.indexOf(var6) != -1) {
						var4.append('\\');
					}
					var4.append(var6);
				} else {
					var4.append('\\');
					var4.append('u');
					var4.append(a(var6 >> 12 & 15));
					var4.append(a(var6 >> 8 & 15));
					var4.append(a(var6 >> 4 & 15));
					var4.append(a(var6 & 15));
				}
			}
		}
		return var4.toString();
	}

	private static char a(int var0) {
		return field_new[var0 & 15];
	}
}
