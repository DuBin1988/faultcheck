package com.af.faultcheck.checker;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.SessionFactory;

import com.af.faultcheck.IChecker;
import com.af.faultcheck.MonitorCheckJob;

public class HandDiskSpaceChecker implements IChecker {

	static Logger log = Logger.getLogger(MonitorCheckJob.class);

	// �쳣��ʾ
	private String exceptionName;

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void txcheck(JSONObject object) {
		log.debug("��ʼִ��Ӳ�̿ռ���");
		File[] list = File.listRoots();
		for (File f : list) {
			String disk = f.getAbsolutePath();
			double freeDou = this.FormatFileSize(f.getFreeSpace());
			String freeStr = freeDou + "G";
			String total = this.FormatFileSize(f.getTotalSpace()) + "G";
			log.debug("����" + disk + ",�ܿռ�" + total + ",ʣ��ռ�" + freeStr);
			if (freeDou < 2) {
				Map exception = new HashMap();
				exception.put("f_exceptionname", this.exceptionName);
				exception.put("f_data", "����" + disk + ",�ܿռ�:" + total
						+ ",ʣ��ռ䣺" + freeDou);
				exception.put("f_date", new Date());
				sessionFactory.getCurrentSession().save("t_fault", exception);
			}
		}
		log.debug("Ӳ�̿ռ������");
	}

	public Double FormatFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		BigDecimal bg = new BigDecimal((double) fileS / 1073741824);
		double result = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}

}
