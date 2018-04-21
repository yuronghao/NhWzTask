package com.emi.sys.core.format;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

/*** 将json串中的日期字符串转换为bean中的Timestamp */
public class DateMorpher extends AbstractObjectMorpher {
	/*** 日期字符串格式 */
	private String[] formats;

	public DateMorpher(String[] formats) {
		this.formats = formats;
	}

	public Object morph(Object value) {
		if (value == null || value.equals("")) {
			return null;
		}
		if (Date.class.isAssignableFrom(value.getClass())) {
			return (Date) value;
		}
		if (!supports(value.getClass())) {
			throw new MorphException(value.getClass() + " 是不支持的类型");
		}
		String strValue = (String) value;
		SimpleDateFormat dateParser = null;
		for (int i = 0; i < formats.length; i++) {
			if (null == dateParser) {
				dateParser = new SimpleDateFormat(formats[i]);
			} else {
				dateParser.applyPattern(formats[i]);
			}
			try {
				return new Date(dateParser.parse(strValue.toLowerCase())
						.getTime());
			} catch (ParseException e) {
				 e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Class morphsTo() {
		return Date.class;
	}

	public boolean supports(Class clazz) {
		return String.class.isAssignableFrom(clazz);
	}

}