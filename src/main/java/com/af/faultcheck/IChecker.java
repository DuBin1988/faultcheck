package com.af.faultcheck;

import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

public interface IChecker {

	
	
	/**
	 * ��鷽��
	 */
	public void txcheck(JSONObject object);
}
