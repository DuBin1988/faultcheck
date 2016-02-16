package com.af.sqlservice;

import junit.framework.TestCase;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class TestSqlQuery extends TestCase {
	public void testOne() throws Exception {
		String root = "http://127.0.0.1:8080/rs/";
		String personExpression = "{'sql':'test.sql','params':{'name':'system'}}";
        //String encoderJson = URLEncoder.encode(personExpression, HTTP.UTF_8);
		
		// 用httpclient直接访问服务
		HttpPost httpPost = new HttpPost(root + "sql");
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");        
        
        StringEntity se = new StringEntity(personExpression);
        se.setContentType("text/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);
        
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpPost);
		int code = response.getStatusLine().getStatusCode();
		if(code != 200) {
			throw new Exception("获取用户信息失败。");
		}
		String result = EntityUtils.toString(response.getEntity(), "UTF8");
	}
}
