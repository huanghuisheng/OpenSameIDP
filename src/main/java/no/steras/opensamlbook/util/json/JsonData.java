package no.steras.opensamlbook.util.json;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Collection;
import java.util.Date;

public class JsonData {
	private Long total;

	private Collection rows;

	public Collection getRows() {
		return rows;
	}

	public void setRows(Collection rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String toString() {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		return JSONObject.fromObject(this,config).toString();
	}
}
