package com.af.faultcheck;

import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

public interface IChecker {

	
	
	/**
	 * ¼ì²é·½·¨
	 */
	public void txcheck(JSONObject object);
}
