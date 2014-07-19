package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import weblogic.auddi.util.Logger;

/**
 * 完善下面三个方法，可以将所有的PrpcItem类继承此类，这样就可以直接通过字段值进行赋值和取值
 * getAttributes():取所有字段
 * setAttribute():通过字段名反射调用set方法
 * getAttribute():通过字段名反射调用get方法
 * @author tianxingjian
 *
 */
public class SuperVO {

	/**
	 * 取类里面的所有字段
	 * @return
	 */
	public String[] getAttributes(){
		Class cla = this.getClass();
		Field [] fields = cla.getDeclaredFields();
		String []results = new String[fields.length];
		for(int i = 0; i < results.length; i++){
			results[i] = fields[i].getName();
		}
		return results;
	}
	
	/**
	 * 找fieldName字段的set方法赋值
	 * @param fieldName
	 * @param o
	 */
	public void setAttribute(String fieldName, Object o){
		
		Class cla = this.getClass();
		try {
			Field field = cla.getDeclaredField(fieldName);
		} catch (Exception e1) {
			Logger.warning("不存在字段" + fieldName + "赋值不成功！");
		}
		
		StringBuffer sb = new StringBuffer();   
	    sb.append("set");   
	    sb.append(fieldName.substring(0, 1).toUpperCase());   
	    sb.append(fieldName.substring(1));   
		 
	    boolean isSuccess = false;
	    StringBuffer info = new StringBuffer();
		try {
			
			Method method = cla.getDeclaredMethod(sb.toString());
			try {
				method.invoke(this, o);//这里要类型转换，找找有通用的类型转换类没，apache好像有这个包
				isSuccess = true;
			} catch (Exception e) {
				info.append(fieldName + "赋值不成功:" + e.getMessage());
			}
			
		} catch (SecurityException e) {
			info.append(fieldName + "赋值不成功:" + e.getMessage());
		} catch (NoSuchMethodException e) {  //当setAcc 方法没找到时尝试找setacc方法
			sb = new StringBuffer();
			sb.append("get");
			sb.append(fieldName);
			try {
				Method method = cla.getDeclaredMethod(sb.toString());
				try {
					method.invoke(this, o);  //这里要类型转换，找找有通用的类型转换类没，apache好像有这个包
					isSuccess = true;
				} catch (Exception e2) {
					info.append(fieldName + "赋值不成功:" + e.getMessage());
				}
			} catch (Exception e1) {
				info.append(fieldName + "赋值不成功:" + e.getMessage());
			}
		}
		
		if(!isSuccess){
			Logger.warning(info.toString());
		}
	}
	
	/**
	 * 通过fieldName反射get方法，get + “fieldName”， get + “FieldName”， is + “fieldName”三种形式
	 * 没进行数据转换，实际调用完此方法后要一个数据转换，还有就是建议所有原生数据类型都用其对应的对象
	 * @param fieldName
	 * @return
	 */
	public Object getAttribute(String fieldName){
		Class cla = this.getClass();
		Object result = null;
		try {
			Field field = cla.getDeclaredField(fieldName);
		} catch (Exception e1) {
			Logger.warning("不存在字段" + fieldName + "赋值不成功！");
		}
		
		StringBuffer sb = new StringBuffer();   
	    sb.append("get");   
	    sb.append(fieldName.substring(0, 1).toUpperCase());   
	    sb.append(fieldName.substring(1));   
		 
	    boolean isSuccess = false;
	    StringBuffer info = new StringBuffer();
		try {
			Method method = cla.getDeclaredMethod(sb.toString());
			try {
				result = method.invoke(this);//这里要类型转换，找找有通用的类型转换类没，apache好像有这个包
				isSuccess = true;
			} catch (Exception e) {
				info.append(fieldName + "赋值不成功:" + e.getMessage());
			}
			
		} catch (SecurityException e) {
			info.append(fieldName + "赋值不成功:" + e.getMessage());
		} catch (NoSuchMethodException e) {  //当setAcc 方法没找到时尝试找setacc方法
			sb = new StringBuffer();
			sb.append("get");
			sb.append(fieldName);
			try {
				Method method = cla.getDeclaredMethod(sb.toString());
				try {
					result = method.invoke(this);  //这里要类型转换，找找有通用的类型转换类没，apache好像有这个包
					isSuccess = true;
				} catch (Exception e2) {
					info.append(fieldName + "赋值不成功:" + e.getMessage());
				}
			} catch (SecurityException e1) {
				info.append(fieldName + "赋值不成功:" + e.getMessage());
			} catch (NoSuchMethodException e1) {
				sb = new StringBuffer();
				sb.append("is"); //只用boolean类型才会走这
				sb.append(fieldName);
					
				try {
					Method method = cla.getDeclaredMethod(sb.toString());
					try {
						result = method.invoke(this);  //这里要类型转换，找找有通用的类型转换类没，apache好像有这个包
						isSuccess = true;
					} catch (Exception e2) {
						info.append(fieldName + "赋值不成功:" + e.getMessage());
					}
				}catch(Exception e3){
					info.append(fieldName + "赋值不成功:" + e.getMessage());
				}
			}
				
			
		}
		
		if(!isSuccess){
			Logger.warning(info.toString());
		}
		
		return result;
	}
	
}
