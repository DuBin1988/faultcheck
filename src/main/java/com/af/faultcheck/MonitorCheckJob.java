package com.af.faultcheck;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.aote.listener.ContextListener;
import com.aote.rs.DBService;
import com.aote.rs.bank.bankreturn.BankExcelSet;

/**
 * ���ϼ������࣬����������õĹ��ϼ��ӿ�ʵ��
 * 
 * @author Administrator
 *
 */
public class MonitorCheckJob extends QuartzJobBean {

	static Logger log = Logger.getLogger(MonitorCheckJob.class);

	// ϵͳ��������
	private String deployName;

	public String getDeployName() {
		return deployName;
	}

	public void setDeployName(String deployName) {
		this.deployName = deployName;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			log.debug("���ϼ�⿪ʼִ��===>" + new Date());
			JSONObject toChecker = new JSONObject();
			toChecker.put("deployName", this.deployName);
			ServletContext sc = ContextListener.getContext();
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(sc);
			Map checkers = ctx.getBeansOfType(IChecker.class);
			Iterator key = checkers.keySet().iterator();
			while (key.hasNext()) {
				String beanNames = key.next().toString();
				log.debug("�����"+beanNames+"��ʼִ��==>" + new Date());
				IChecker c = (IChecker) checkers.get(beanNames);
				c.txcheck(toChecker);
				log.debug("�����"+beanNames+"ִ�н���==>" + new Date());
			}
		} catch (Exception e) {
			log.debug("�쳣" + e.getMessage());
			throw new RuntimeException(e);
		}
		log.debug("���ϼ�ⶨʱ�������===>" + new Date());
	}

}
