package com.af.faultcheck.checker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.af.faultcheck.IChecker;
import com.af.faultcheck.MonitorCheckJob;
import com.aote.rs.util.FileHelper;
import com.aote.rs.util.SqlHelper;

public class SqlChecker implements IChecker {

	static Logger log = Logger.getLogger(MonitorCheckJob.class);

	// 文件路径
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// 异常标示
	private String exceptionName;

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	@Autowired
	private HibernateTemplate hibernateTemplate;

	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void txcheck(JSONObject json) {
		try {
			String path = this.getClass().getClassLoader().getResource("/sqls")
					.getPath();
			path += fileName;
			String sql = FileHelper.readToEnd(path);
			Session session = sessionFactory.getCurrentSession();
			JSONArray array = SqlHelper.query(
					sessionFactory.getCurrentSession(), sql);
			log.debug("执行语句" + sql + ",结果数" + array.length());
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				String data = obj.toString();
				Map exception = new HashMap();
			//	exception.put("f_deployname", json.get("f_deployname"));
				exception.put("f_exceptionname", this.exceptionName);
				exception.put("f_data", data);
				exception.put("f_date", new Date());
		 		sessionFactory.getCurrentSession().save("t_fault", exception);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
