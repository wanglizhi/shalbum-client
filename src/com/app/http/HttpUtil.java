package com.app.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
 
public class HttpUtil {

	// 基础URL
	public static final String BASE_URL="http://172.17.211.15/~qinzhenning/test/test.php";

	// 获得Get请求对象request
	public static HttpGet getHttpGet(String url){

			HttpGet request = new HttpGet(url);
			return request;
	}

	// 获得Post请求对象request
	public static HttpPost getHttpPost(String url){
		HttpPost request = new HttpPost(url);
		return request;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;	
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}


	// 发送Post请求，获得响应查询结果
	public static String queryStringForPost(String url){
		// 根据url获得HttpPost对象
		HttpPost request =HttpUtil.getHttpPost(url);
		String result = null;
		
		try {
			// 获得响应对象
			HttpResponse response =HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if(response.getStatusLine().getStatusCode()==200){
				// 获得响应
				result =EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常!";
			return result;
		} catch (IOException e) {
		e.printStackTrace();
		result = "网络异常!";
		return result;
		}
		return null;
	}

	// 获得响应查询结果
	public static String queryStringForPost(HttpPost request){
	
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response =HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if(response.getStatusLine().getStatusCode()==200){
				// 获得响应
				result =EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 发送Get请求，获得响应查询结果
	public static String queryStringForGet(String url){
		
		// 获得HttpGet对象
		HttpGet request =HttpUtil.getHttpGet(url);
		
		String result = null;
		
		try {
			// 获得响应对象
			HttpResponse response =HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if(response.getStatusLine().getStatusCode()==200){
				// 获得响应
				result =EntityUtils.toString(response.getEntity());	
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}
 
//这个方法是用jSON请求获得JSON

	public static String getContent(String url){
	
		StringBuilder sb=new StringBuilder();
		HttpClient client=new DefaultHttpClient();
		HttpParams httpparams=client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpparams,3000);
		HttpConnectionParams.setSoTimeout(httpparams,5000);
		try {
			HttpResponse response=client.execute(new HttpGet(url));
			
			HttpEntity entity=response.getEntity();
			
			if(entity!=null){
				BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent(),"utf8"),8192);
				String line=null;
				while((line=reader.readLine())!=null){
					sb.append(line+"\n");
				}
				reader.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}