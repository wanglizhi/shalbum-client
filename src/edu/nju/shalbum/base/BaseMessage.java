package edu.nju.shalbum.base;
/**
 * 与服务器端消息的封装类，将JSON对象解析为code，message以及result三个属性
 * @author wlz
 */
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import edu.nju.shalbum.util.AppUtil;

public class BaseMessage {
	
	private String code;
	private String message;
	//字符串消息
	private String resultSrc;
	//Model对象结果
	private Map<String, BaseModel> resultMap;
	//Model列表结果
	private Map<String, ArrayList<? extends BaseModel>> resultList;
	
	public BaseMessage () {
		this.resultMap = new HashMap<String, BaseModel>();
		this.resultList = new HashMap<String, ArrayList<? extends BaseModel>>();
	}
	
	@Override
	public String toString () {
		return code + " | " + message + " | " + resultSrc;
	}
	
	public String getCode () {
		return this.code;
	}
	
	public void setCode (String code) {
		this.code = code;
	}
	
	public String getMessage () {
		return this.message;
	}
	
	public void setMessage (String message) {
		this.message = message;
	}
	
	public String getResult () {
		return this.resultSrc;
	}
	//通过键名取得model对象
	public Object getResult (String modelName) throws Exception {
		Object model = this.resultMap.get(modelName);
		// catch null exception
		if (model == null) {
			throw new Exception("Message data is empty");
		}
		return model;
	}
	//通过键名取得model列表
	public ArrayList<? extends BaseModel> getResultList (String modelName) throws Exception {
		ArrayList<? extends BaseModel> modelList = this.resultList.get(modelName);
		// catch null exception
		if (modelList == null || modelList.size() == 0) {
			throw new Exception("Message data list is empty");
		}
		return modelList;
	}
	
	//解析JSON对象
	@SuppressWarnings("unchecked")
	public void setResult (String result) throws Exception {
		this.resultSrc = result;
		if (result.length() > 0) {
			JSONObject jsonObject = null;
			jsonObject = new JSONObject(result);
			Iterator<String> it = jsonObject.keys();
			while (it.hasNext()) {
				// initialize
				String jsonKey = it.next();
				String modelName = getModelName(jsonKey);
				String modelClassName = "edu.nju.shalbum.model." + modelName;
				JSONArray modelJsonArray = jsonObject.optJSONArray(jsonKey);
				// JSONObject
				if (modelJsonArray == null) {
					JSONObject modelJsonObject = jsonObject.optJSONObject(jsonKey);
					if (modelJsonObject == null) {
						throw new Exception("Message result is invalid");
					}
					this.resultMap.put(modelName, json2model(modelClassName, modelJsonObject));
				// JSONArray
				} else {
					ArrayList<BaseModel> modelList = new ArrayList<BaseModel>();
					for (int i = 0; i < modelJsonArray.length(); i++) {
						JSONObject modelJsonObject = modelJsonArray.optJSONObject(i);
						modelList.add(json2model(modelClassName, modelJsonObject));
					}
					this.resultList.put(modelName, modelList);
				}
			}
		}
	}
	
	/**
	 * 将JSON对象转换成ModelClassName对应的Model对象
	 * @param modelClassName
	 * @param modelJsonObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private BaseModel json2model (String modelClassName, JSONObject modelJsonObject) throws Exception  {
		ObjectMapper om = new ObjectMapper();
		BaseModel modelObj=(BaseModel) om.readValue(modelJsonObject.toString(), Class.forName(modelClassName));
		return modelObj;
	}
	
	private String getModelName (String str) {
		String[] strArr = str.split("\\W");
		if (strArr.length > 0) {
			str = strArr[0];
		}
		return AppUtil.ucfirst(str);
	}
	
}