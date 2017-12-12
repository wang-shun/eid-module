package org.aiav.astoopsdk.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

import org.apache.commons.beanutils.PropertyUtils;

public class JsonUtil {

	/**
	 * <p>
	 * Convert a JSON string to a map.
	 * </p>
	 * 
	 * @param jsonStr
	 * @return Map<String, String>
	 */
	public static Map<String, String> jsonStrToMap(String jsonStr) {
		return jsonObjToMap(JSONObject.fromObject(jsonStr));
	}

	/**
	 * <p>
	 * Convert a map to a JSON string.
	 * </p>
	 * 
	 * @param map
	 * @return String
	 */
	public static String mapToJsonStr(Map<String, String> map) {
		return mapToJsonObj(map).toString();
	}

	/**
	 * <p>
	 * Convert an object to a JSON string.
	 * </p>
	 * 
	 * @param obj
	 * @return String
	 */
	public static String objToJsonStr(Object obj) {
		return JSONObject.fromObject(obj).toString();
	}

	/**
	 * <p>
	 * Convert a JSON string to a java pojo.
	 * </p>
	 * 
	 * @param jsonStr
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object JsonStrToObj(String jsonStr, Class beanName)
			throws Exception {
		return JsonObjToObj(JSONObject.fromObject(jsonStr), beanName);
	}

	/**
	 * <p>
	 * Convert a list to a JSON string.
	 * </p>
	 * 
	 * @param list
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String listToJsonStr(List list) {
		return JSONArray.fromObject(list).toString();
	}

	public static String listToJsonStr(Object list) {
		return JSONArray.fromObject(list).toString();
	}

	/**
	 * 
	 * @param jsonStr
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List JsonStrToList(String jsonStr, Class beanName)
			throws Exception {
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		Iterator iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject tmp = JSONObject.fromObject(iter.next());
			list.add(JsonStrToObj(tmp.toString(), beanName));
		}

		return list;
	}

	/**
	 * <p>
	 * Convert a JSON object to a map.
	 * </p>
	 * 
	 * @param jsonObj
	 * @return Map<String, String>
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> jsonObjToMap(JSONObject jsonObj) {
		if (jsonObj != null && !jsonObj.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			String key = null;
			Iterator iter = jsonObj.keySet().iterator();
			while (iter.hasNext()) {
				key = iter.next().toString();
				map.put(key, FuncUtil.isEmpty(jsonObj.get(key)) ? null
						: jsonObj.getString(key));
			}
			return map;
		}
		return null;
	}

	/**
	 * <p>
	 * Convert a map to a JSON object.
	 * </p>
	 * 
	 * @param map
	 * @return JSONObject
	 */
	public static JSONObject mapToJsonObj(Map<String, String> map) {
		JSONObject jsonObj = new JSONObject();
		List<String> keys = new ArrayList<String>(map.keySet());

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			jsonObj.put(key, map.get(key));
		}

		return jsonObj;
	}

	/**
	 * 
	 * <p>
	 * Convert a JSON object to a java pojo.
	 * </p>
	 * 
	 * @param jsonObj
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object JsonObjToObj(JSONObject jsonObj, Class beanName)
			throws Exception {
		JsonConfig jc = new JsonConfig();
		jc.setRootClass(beanName);

		jc.setPropertySetStrategy(new PropertySetStrategy() {
			@Override
			public void setProperty(Object source, String name, Object value) {
				try {
					source.getClass().getDeclaredField(name);
					PropertyUtils.setSimpleProperty(source, name, value);
				} catch (NoSuchFieldException e) {
					throw new JSONException(e);
				} catch (SecurityException e) {
					throw new JSONException(e);
				} catch (IllegalAccessException e) {
					throw new JSONException(e);
				} catch (InvocationTargetException e) {
					throw new JSONException(e);
				} catch (NoSuchMethodException e) {
					throw new JSONException(e);
				}
			}
		});
		return JSONObject.toBean(jsonObj, jc);
	}

}
