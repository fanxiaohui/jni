/********************************************************************
 * 文件名称：ScreenManager.java
 * 所属项目名称：GreeSmart
 * 创建人：
 * 创建时间：2013-6-5 下午4:32:40
 * Copyright (c) 2013 BlackCrystal. All rights reserved.
 ********************************************************************/
package com.vision.smarthomeapi.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * 类名：ScreenManager <br>
 * 功能简介：用于管理所有的activity<br>
 * 完成日期：2012-10-17<br>
 * 
 * @author Lal<br>
 *         变更历史<br>
 */
public class ScreenManager {
	private static Stack<Activity> activityStack = null;
	private static ScreenManager screenManager = null;
	private static final String TAG = "ScreenManager";

	/**
	 * 方法名：addActivityToStack<br>
	 * 功能简介：想自定义栈内添加Activity<br>
	 * 完成日期：2012-10-17<br>
	 * 
	 * @param activity
	 *            需要添加入栈的Activity对象
	 */
	public void addActivityToStack(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
		OutPutMessage.LogCatInfo(TAG, "---->" + activity.getLocalClassName());
		// for(int i=0;i<activityStack.size();i++){
		// //OutPutMessage.outputSystem(i+"zhan",activityStack.get(i).toString());
		// }
	}

	/**
	 * 方法名：currentActivity<br>
	 * 功能简介：获取当前活动的activity对象(既栈顶端的Activity)<br>
	 * 完成日期：2012-10-17<br>
	 * 
	 * @return
	 */
	public Activity currentActivity() {
		if(activityStack == null){
			return null;
		}
		
		if (!activityStack.empty()) {
			Activity activity = activityStack.lastElement();
			OutPutMessage.LogCatInfo(TAG, "---->" + activity.getLocalClassName());
			return activity;
		} else {
			return null;
		}

	}

	public int getNum() {
		if (activityStack.empty()) {
			return 0;
		}
		OutPutMessage.LogCatInfo(TAG, "---->" + activityStack.get(0).getLocalClassName());
		return activityStack.size();
	}

	/**
	 * 方法名：popActivity<br>
	 * 功能简介：弹出栈中指定的Activity对象<br>
	 * 完成日期：2012-10-17<br>
	 * 
	 * @param activity
	 *            需要弹出的activity对象
	 */
	public void popActivity(Activity activity) {
//		//用来控制不是从NetWorkActivity切换回来的页面,这样可以控制返回和返回键不需要刷新的状态。
//		if (!(activity instanceof NetWorkActivity || activity instanceof WelcomeActivity)) {
//			OutPutMessage.outputSystem("ceshi", "到这了吗if?");
//			GreeApplication.HomeIntentNet = true;
//		}
		OutPutMessage.LogCatInfo(TAG, "---->" + activity.getLocalClassName() + "--->");
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}

	}

	/**
	 * 方法名：popActivity<br>
	 * 功能简介：根据类弹出Activity<br>
	 * 完成日期：2012-10-17<br>
	 * 
	 * @param cla
	 *            需要弹出的Activity的类
	 */
	public void popActivity(Class<?> cla) {
		if (activityStack != null) {
			for (int i = 0; i < activityStack.size(); i++) {
				Activity activity = activityStack.get(i);
				if (activity.getClass().equals(cla)) {
					popActivity(activity);
					OutPutMessage.LogCatInfo(TAG, "---->添加了一个" + activity.getLocalClassName());
				}
			}
		}
	}

	/**
	 * 函数名称：InactivityStack</br> 功能描述：查看在栈中是否有此activity
	 * 
	 * @author ZhaoQing 修改日志:</br>
	 *         <table>
	 *         <tr>
	 *         <th>版本</th>
	 *         <th>日期</th>
	 *         <th>作者</th>
	 *         <th>描述</th>
	 *         <tr>
	 *         <td>0.1</td>
	 *         <td>2013-12-7</td>
	 *         <td>ZhaoQing</td>
	 *         <td>初始创建</td>
	 *         </table>
	 */
	public boolean inActivityStack(Class<?> cla) {
		if (activityStack != null) {
			for (int i = 0; i < activityStack.size(); i++) {
				Activity activity = activityStack.get(i);
				if (activity.getClass().equals(cla)) {
					OutPutMessage.LogCatInfo(TAG, "---->是否有这个：" + activity.getLocalClassName());
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * 方法名：popOldAcitvity<br>
	 * 功能简介：删除除了站最低端的其他的较旧的对象<br>
	 * 完成日期：2012-10-17<br>
	 */
	public void popOldAcitvity() {
		if (activityStack.size() > 1) {
			int j = 1;
			while (true) {
				if (j >= 1 && j <= (activityStack.size() - 0)) {
					Activity activity = activityStack.get(j);
					OutPutMessage.LogCatInfo(TAG, "---->删除原有的那个：" + activity.getLocalClassName());
					popActivity(activity);
					j++;
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * 只剩站顶的对象
	 */
	public void popNewActivity(){
		
		if (activityStack.size() > 1) {
			
			for (int i = 0; i < activityStack.size()-1; i++) {
				OutPutMessage.LogCatInfo(TAG,"顺序" + i + "" + activityStack.get(i).getLocalClassName());
				Activity activity = activityStack.get(i);
				popActivity(activity);
			}
		}
	}

	/**
	 * 方法名：popAllAcitvity<br>
	 * 功能简介：弹出所有Activity对象<br>
	 * 完成日期：2012-10-17<br>
	 */
	public void popAllAcitvity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				OutPutMessage.LogCatInfo(TAG, "---->删除原有的那个：");
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			}
			popActivity(activity);

		}
	}

	/**
	 * 函数名称：poplastActiviy</br> 功能描述：弹出当前Activity
	 * 
	 * @author Administrator 修改日志:</br>
	 *         <table>
	 *         <tr>
	 *         <th>版本</th>
	 *         <th>日期</th>
	 *         <th>作者</th>
	 *         <th>描述</th>
	 *         <tr>
	 *         <td>0.1</td>
	 *         <td>2013-6-8</td>
	 *         <td>Administrator</td>
	 *         <td>初始创建</td>
	 *         </table>
	 */
	public void poplastActiviy() {
		Activity activity = activityStack.lastElement();
//		//用来限制绑定页面不解绑"返回"刷新
//		if (!(activity instanceof NetWorkActivity || activity instanceof WelcomeActivity)) {
//			GreeApplication.HomeIntentNet = true;
//		} else {
//			GreeApplication.HomeIntentNet = false;
//		}
		if (activity != null) {
			OutPutMessage.LogCatInfo("ceshi", "---->删除原有的那个：" + activity.getLocalClassName());
			activity.finish();

			activity = null;
		}
	}

	/**
	 * 函数名称：enterActivity</br> 功能描述：跳转到指定的activity 并传递参数
	 * 
	 * @param packageContext
	 *            当前页面的context
	 * @param cls
	 *            要跳转到的Activity
	 * @param msg
	 *            要传递的参数（目前支持String boolean int） 修改日志:</br>
	 *            <table>
	 *            <tr>
	 *            <th>版本</th>
	 *            <th>日期</th>
	 *            <th>作者</th>
	 *            <th>描述</th>
	 *            <tr>
	 *            <td>0.1</td>
	 *            <td>2013-6-8</td>
	 *            <td>Administrator</td>
	 *            <td>初始创建</td>
	 *            </table>
	 */
	public void enterActivity(Context packageContext, Class<?> cls, Object msg) {
		enterActivity(packageContext, cls, msg, "msg");
	}

	/**
	 * 函数名称：enterActivity</br> 功能描述：跳转到指定的activity 并传递参数
	 * 
	 * @param packageContext
	 *            当前页面的context
	 * @param cls
	 *            要跳转到的Activity
	 * @param msg
	 *            要传递的参数（目前支持String boolean int）
	 * @param msgName
	 *            要传递的参数名 修改日志:</br>
	 *            <table>
	 *            <tr>
	 *            <th>版本</th>
	 *            <th>日期</th>
	 *            <th>作者</th>
	 *            <th>描述</th>
	 *            <tr>
	 *            <td>0.1</td>
	 *            <td>2013-6-8</td>
	 *            <td>Administrator</td>
	 *            <td>初始创建</td>
	 *            </table>
	 */
	public void enterActivity(Context packageContext, Class<?> cls, Object msg,
			String msgName) {

		// 如果栈里有的话 ，先弹出来，然后再加入 PS在OnCreate里面

		for (int i = 0; i < activityStack.size(); i++) {

			Activity activity = activityStack.get(i);
			if (activity.getClass().equals(cls)) {
				popActivity(activity);

			}
		}

		Intent intent = new Intent(packageContext, cls);
		if (msg != null) {

			if (String.class.getSimpleName().equals(
					msg.getClass().getSimpleName())) {
				intent.putExtra(msgName, msg.toString());
			} else if (Boolean.class.getSimpleName().equals(
					msg.getClass().getSimpleName())) {
				intent.putExtra(msgName, ((Boolean) msg).booleanValue());
			} else if (int.class.getSimpleName().equals(
					msg.getClass().getSimpleName())) {
				intent.putExtra(msgName, ((Integer) msg).intValue());
			}
		}

		packageContext.startActivity(intent);

		// 如果跳入网络设置界面

//		OutPutMessage.LogCatInfo(cls.getName().getClass()+ "================w====");
//		if (cls.getName().equals("com.gree.smart.activity.NetWorkActivity")) {
//
//			// 判断用不用搜索 如果从Home界面进入的话需要搜索
//
//			// 再此需要判断是否是从后台回来的
//			if (packageContext instanceof HomeActivity) {
//
//				GreeApplication.HomeIntentNet = true;
//			} else {
//				GreeApplication.HomeIntentNet = false;
//			}
//
//
//		}


	}

	/**
	 * 函数名称：poplastActiviy</br> 功能描述：弹入指定Activity
	 * 
	 * @author Administrator 修改日志:</br>
	 *         <table>
	 *         <tr>
	 *         <th>版本</th>
	 *         <th>日期</th>
	 *         <th>作者</th>
	 *         <th>描述</th>
	 *         <tr>
	 *         <td>0.1</td>
	 *         <td>2013-6-8</td>
	 *         <td>Administrator</td>
	 *         <td>初始创建</td>
	 *         </table>
	 */
	public void enterActivityPutHashMap(Context packageContext, Class<?> cls,
			HashMap<String, String> map) {

		// 如果栈里有的话 ，先弹出来，然后再加入 PS在OnCreate里面

		for (int i = 0; i < activityStack.size(); i++) {

			Activity activity = activityStack.get(i);

			OutPutMessage.LogCatInfo(TAG, "---->删除原有的那个：" + activity.getLocalClassName());
			if (activity.getClass().equals(cls)) {
				popActivity(activity);

			}
		}
		Intent intent = new Intent(packageContext, cls);
		if (map != null) {
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				HashMap.Entry entry = (HashMap.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				intent.putExtra(key.toString(), val.toString());
			}
		}
		packageContext.startActivity(intent);

//		// 如果跳入网络设置界面
//		if (cls.getName().equals("com.gree.smart.activity.NetWorkActivity")) {
//
//			// 再此需要判断是否是从后台回来的
//			if (packageContext instanceof HomeActivity) {
//
//				GreeApplication.HomeIntentNet = true;
//			} else {
//				GreeApplication.HomeIntentNet = false;
//			}
//		}
//
//		// 由NetWorkActivity跳出重置
//		if (packageContext instanceof NetWorkActivity) {
//
//			GreeApplication.HomeIntentNet = false;
//		}
	}

	public static ScreenManager getScreenManager() {

		// TODO Auto-generated method stub
		if (screenManager == null) {
			screenManager = new ScreenManager();
		}
		return screenManager;
	}

}
