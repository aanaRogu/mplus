package com.liyh.mplus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by liyh on 2017/11/10.
 */

public class Utils {
	
	static class Json {
		private static Gson m_gson;
		static {
			// m_gson = new Gson();
            m_gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    // .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
                    .registerTypeAdapter(java.sql.Date.class, new SQLDateTypeAdapter()).create();
		}
		static <T> String serialize(T object) {
			return m_gson.toJson(object);
		}
		
		static <T> T deserialize(String jsonString, Type typeOfT){
			return m_gson.fromJson(jsonString, typeOfT);
		}

        private static class SQLDateTypeAdapter implements JsonSerializer<Date> {
            private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            public JsonElement serialize(java.sql.Date src, Type arg1, JsonSerializationContext arg2) {
                String dateFormatAsString = format.format(new java.sql.Date(src.getTime()));
                return new JsonPrimitive(dateFormatAsString);
            }
        }
		
//		static <T> T[] deserialize(JSONArray jsonArray, Class<T> clazz) {
//			if (jsonArray == null) {
//				return null;
//			}
//			Constructor constructor;
//			Field[] fields = clazz.getFields();
//			try {
//				constructor = clazz.getConstructor();
//			} catch (Exception ignored) {
//				return null;
//			}
//			T[] objects = (T[]) Array.newInstance(clazz, jsonArray.length());
//			for (int i = 0, count = jsonArray.length(); i < count; ++i) {
//				try {
//					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//					T object = (T) constructor.newInstance();
//					objects[i] = object;
//					for (Field field : fields) {
//						String name = field.getName();
//						Object value = jsonObject.opt(name);
//						if (value != null) {
//							// ????????????
//							if (value instanceof JSONArray && field.getType().isArray()) {
//								Object[] arrayValue = deserialize((JSONArray) value, field.getType().getComponentType());
//								value = arrayValue;
//							}
////                        // Date ??????
////                        if (field.getType().equals(Date.class) && value instanceof String) {
////                            value = m_format1.parse((String) value);
////                        }
//							try {
//								field.set(object, value);
//							} catch (Exception ignored) {
//							}
//						} else {
//							int idx = name.indexOf("_");
//							if (idx != -1 && idx != name.length() - 1) {
//								String subKeyName = name.substring(0, idx);
//								String subValueName = name.substring(idx + 1);
//								Object subKeyObject = jsonObject.opt(subKeyName);
//								if (subKeyObject != null && subKeyObject instanceof JSONObject) {
//									Object subValueObject = ((JSONObject) subKeyObject).opt(subValueName);
//									if (subValueObject != null) {
//										field.set(object, subValueObject);
//									}
//								}
//							}
//						}
//					}
//				} catch (Exception ignored) {
//				}
//			}
//			return objects;
//		}
		
//		static <T> T deserialize(JSONObject jsonObject, Class<T> clazz) {
//			if (jsonObject == null) {
//				return null;
//			}
//			T object;
//			try {
//				Constructor constructor = clazz.getConstructor();
//				object = (T) constructor.newInstance();
//			} catch (Exception ignored) {
//				return null;
//			}
//			Field[] fields = clazz.getFields();
//			for (Field field : fields) {
//				String name = field.getName();
//				Object value = jsonObject.opt(name);
//				if (value != null) {
//					try {
//						field.set(object, value);
//					} catch (Exception ignored) {
//					}
//				}
//			}
//			return object;
//		}
		
//		static Long jsLong(JSONObject object, String field) {
//			try {
//				return object.getLong(field);
//			} catch (JSONException e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//
//		static String jsString(JSONObject object, String field) {
//			try {
//				return object.getString(field);
//			} catch (JSONException e) {
//				e.printStackTrace();
//				return "";
//			}
//		}
//
//		static JSONObject jsObject(JSONObject object, String field) {
//			try {
//				return object.getJSONObject(field);
//			} catch (JSONException e) {
//				e.printStackTrace();
//				return new JSONObject();
//			}
//		}
//
//		static JSONArray jsArray(JSONObject object, String field) {
//			try {
//				return object.getJSONArray(field);
//			} catch (JSONException e) {
//				e.printStackTrace();
//				return new JSONArray();
//			}
//		}
//
//		static boolean isSuccessResult(JSONObject result) {
//			return "success".equals(jsString(result, "status"));
//		}
		
	}
	
	static boolean isNullOrEmpty(String text) {
		return text == null || text.equals("");
	}
	
	static boolean isMobileNumber(String mobiles) {
		/*
		 ?????????134???135???136???137???138???139???150???151???157(TD)???158???159???187???188
         ?????????130???131???132???152???155???156???185???186
         ?????????133???153???180???189??????1349?????????
         ????????????????????????????????????1?????????????????????3???5???8???????????????????????????0-9
         ------------------------------------------------
         13(???)?????????130???131???132???133???134???135???136???137???138???139
         14(???)?????????145???147
         15(???)?????????150???151???152???153???154???155???156???157???158???159
         17(???)?????????170???171???173???175???176???177???178
         18(3G)?????????180???181???182???183???184???185???186???187???188???189
         */
		if (isNullOrEmpty(mobiles)) return false;
		String telRegex = "[1][34578]\\d{9}";           //"[1]"?????????1????????????1???"[358]"????????????????????????3???4???5???7???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
		return mobiles.matches(telRegex);
	}
}
