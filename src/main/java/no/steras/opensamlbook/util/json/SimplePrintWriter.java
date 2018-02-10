package no.steras.opensamlbook.util.json;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Des:输出格式化数据 <br>
 * Logic:<br>
 * personHealth com.bdcc.hoffice.personhealth.web.util Wuhao created the Class
 * at 2010-8-17
 */
public class SimplePrintWriter {
	private PrintWriter out = null;

	/**
	 * 构造一个输出流
	 * 
	 * @param out
	 */
	public SimplePrintWriter(PrintWriter out) {
		this.out = out;
	}

	/**
	 * 格式化字符串 如果string 为null 返回"" Des:<br>
	 * Des:<br>
	 * Logic:<br>
	 * 
	 * @param string
	 *            <br>
	 *            Wuhao crated the method at 2010-8-17
	 */
	public void write(String string) {
		out.write(SimpleDataFormat.format(string));
	}

	/**
	 * 格式化yyyy-MM-dd日期 Des:<br>
	 * Logic:<br>
	 * 
	 * @param date
	 *            <br>
	 *            Wuhao crated the method at 2010-8-17
	 */
	public void write(Date date) {
		out.write(SimpleDataFormat.format(date));
	}

	/**
	 * 
	 * 格式化yyyy-MM-dd HH:mm:ss日期 Des:<br>
	 * Logic:<br>
	 * 
	 * @param date
	 *            <br>
	 *            Wuhao crated the method at 2010-8-17
	 */
	public void writeDateTime(Date date) {
		out.write(SimpleDataFormat.formatDateTime(date));
	}

	/**
	 * 
	 * Des:如果number为null就返回-1<br>
	 * Logic:<br>
	 * 
	 * @param number
	 *            <br>
	 *            Wuhao crated the method at 2010-8-17
	 */
	public void write(Long number) {
		out.write(SimpleDataFormat.format(number));
	}

	/**
	 * 
	 * Des:关闭输出流<br>
	 * Logic:<br>
	 * <br>
	 * Wuhao crated the method at 2010-8-17
	 */
	public void close() {
		out.flush();
		out.close();
	}
}
