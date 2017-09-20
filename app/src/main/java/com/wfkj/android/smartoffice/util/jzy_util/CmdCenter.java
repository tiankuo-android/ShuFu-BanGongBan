/**
 * Project Name:GizSdkV4AppBase
 * File Name:CmdCenter.java
 * Package Name:com.gizwits.framework.sdk
 * Date:2015-1-27 14:47:19
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.wfkj.android.smartoffice.util.jzy_util;

import android.content.Context;
import android.util.Log;

import com.gizwits.gizwifisdk.api.GizWifiCentralControlDevice;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiGroup;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.api.GizWifiSubDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


// TODO: Auto-generated Javadoc

/**
 * ClassName: Class CmdCenter. <br/>
 * 控制指令类 <br/>
 * date: 2014-12-15 12:09:02 <br/>
 * 
 * @author Lien
 */
public class CmdCenter {

	/** The Constant TAG. */
	private static final String TAG = "CmdCenter";

	/**
	 * The xpg wifi sdk.
	 */
	private static GizWifiSDK xpgWifiGCC;

	/**
	 * The m center.
	 */
	private static CmdCenter mCenter;

	/**
	 * The m setting manager.
	 */
	private SettingManager mSettingManager;


	private GizWifiDevice mXpgWifiDevice;
	/**
	 * Instantiates a new cmd center.
	 * 
	 * @param c
	 *            the c
	 */
	private CmdCenter(Context c) {
		if (mCenter == null) {
			init(c);
		}
	}

	/**
	 * Gets the single instance of CmdCenter.
	 * 
	 * @param c
	 *            the c
	 * @return single instance of CmdCenter
	 */
	public static CmdCenter getInstance(Context c) {
		if (mCenter == null) {
			mCenter = new CmdCenter(c);
		}
		return mCenter;
	}

	/**
	 * Inits the.
	 * 
	 * @param c
	 *            the c
	 */
	private void init(Context c) {
		mSettingManager = new SettingManager(c);

		xpgWifiGCC = GizWifiSDK.sharedInstance();

	}

	/**
	 * Gets the Giz wifi sdk.
	 * 
	 * @return the Giz wifi sdk
	 */
	public GizWifiSDK getGizWifiSDK() {
		return xpgWifiGCC;
	}

	// =================================================================
	//
	// 关于账号的指令
	//
	// =================================================================

	/**
	 * 注册账号.
	 * 
	 * @param phone
	 *            注册手机号
	 * @param code
	 *            验证码
	 * @param password
	 *            注册密码
	 */
	public void cRegisterPhoneUser(String phone, String code, String password) {
		xpgWifiGCC.registerUserByPhoneAndCode(phone, password, code);
	}

	/**
	 * C register mail user.
	 * 
	 * @param mailAddr
	 *            the mail addr
	 * @param password
	 *            the password
	 */
	public void cRegisterMailUser(String mailAddr, String password) {
		xpgWifiGCC.registerUserByEmail(mailAddr, password);
	}

	/**
	 * C register mail user.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public void cRegisterDefaultUser(String username, String password) {
		xpgWifiGCC.registerUser(username, password);
	}

	/**
	 * 匿名登录
	 * <p/>
	 * 如果一开始不需要直接注册账号，则需要进行匿名登录.
	 */
	public void cLoginAnonymousUser() {
		xpgWifiGCC.userLoginAnonymous();
	}

	/**
	 * 账号注销.
	 */
	public void cLogout() {
		Log.e(TAG, "cLogout:uesrid=" + mSettingManager.getUid());
		xpgWifiGCC.userLogout(mSettingManager.getUid());
	}

	/**
	 * 账号登陆.
	 * 
	 * @param name
	 *            用户名
	 * @param psw
	 *            密码
	 */
	public void cLogin(String name, String psw) {
		xpgWifiGCC.userLoginWithUserName(name, psw);
	}

	/**
	 * 忘记密码.
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @param newPassword
	 *            the new password
	 */
	public void cChangeUserPasswordWithCode(String phone, String code,
			String newPassword) {
		xpgWifiGCC.changeUserPasswordByCode(phone, code, newPassword);
	}

	/**
	 * 修改密码.
	 * 
	 * @param token
	 *            令牌
	 * @param oldPsw
	 *            旧密码
	 * @param newPsw
	 *            新密码
	 */
	public void cChangeUserPassword(String token, String oldPsw, String newPsw) {
		xpgWifiGCC.changeUserPassword(token, oldPsw, newPsw);
	}

	/**
	 * 根据邮箱修改密码.
	 * 
	 * @param email
	 *            邮箱地址
	 */
	public void cChangePassworfByEmail(String email) {
		xpgWifiGCC.changeUserPasswordByEmail(email);
	}

	/**
	 * 请求向手机发送验证码.
	 * 
	 * @param phone
	 *            手机号
	 */
	public void cRequestSendSMSCode(String appSecret, String phone) {
		xpgWifiGCC.requestSendPhoneSMSCode(appSecret, phone);
	}


	/**
	 * 绑定后刷新设备列表，该方法会同时获取本地设备以及远程设备列表.
	 * 
	 * @param uid
	 *            用户名
	 * @param token
	 *            令牌
	 */
	public void cGetBoundDevices(String uid, String token,List<String> specialProductKey) {
		xpgWifiGCC.getBoundDevices(uid, token, specialProductKey);
		// xpgWifiSdk.getBoundDevices(uid, token);
	}

	/**
	 * 绑定设备.
	 * 
	 * @param uid
	 *            用户名
	 * @param token
	 *            密码
	 * @param did
	 *            did
	 * @param passcode
	 *            passcode
	 * @param remark
	 *            备注
	 */
	public void cBindDevice(String uid, String token, String did,
			String passcode, String remark) {

		xpgWifiGCC.bindDevice(uid, token, did, passcode, remark);
	}

	// =================================================================
	//
	// 关于控制设备的指令
	//
	// =================================================================

	/**
	 * 发送指令.
	 *
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void cWrite(GizWifiDevice xpgWifiDevice, String key, Object value) {

//		try {
//			final JSONObject jsonsend = new JSONObject();
//			JSONObject jsonparam = new JSONObject();
//			jsonsend.put("cmd", 1);
//			jsonparam.put(key, value);
//			jsonsend.put(JsonKeys.KEY_ACTION, jsonparam);
//			Log.i("sendjson", jsonsend.toString());
//			System.out.println("----------------xpg-----------------"+xpgWifiDevice);
//			System.out.println("----------------json-----------------"+jsonsend.toString());
//			xpgWifiDevice.write(jsonsend.toString());

			// 订阅设备并变为可控状态后，执行开灯动作
			int sn = 0;
			ConcurrentHashMap<String, Object> command = new ConcurrentHashMap<String, Object> ();
			command.put(key, value);
			xpgWifiDevice.write(command, sn);
			System.out.println("--------key------"+key+"----value-----"+value);

//		} catch (JSONException e) {
//		e.printStackTrace();
//	}

	}
	
	/**
	 * 发送指令.
	 * 
	 *            the xpg wifi device
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void cSubWrite(GizWifiSubDevice xpgWifiSubDevice, String key, Object value) {

		try {
			final JSONObject jsonsend = new JSONObject();
			JSONObject jsonparam = new JSONObject();
			jsonsend.put("cmd", 1);
			jsonparam.put(key, value);
			jsonsend.put(JsonKeys.KEY_ACTION, jsonparam);
			Log.i("sendjson", jsonsend.toString());
			xpgWifiSubDevice.write(jsonsend.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 获取子设备状态.
	 * 
	 * @param xpgWifiSubDevice
	 *            the xpg wifi sub device
	 */
	public void cGetSubStatus(GizWifiSubDevice xpgWifiSubDevice){
		JSONObject json = new JSONObject();
		try {
			json.put("cmd", 2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		xpgWifiSubDevice.write(json.toString());
	}

	/**
	 * 获取设备状态.
	 *
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	public void cGetStatus(GizWifiDevice xpgWifiDevice) {
		JSONObject json = new JSONObject();
		try {
			json.put("cmd", 2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		xpgWifiDevice.write(json.toString());
	}


	/**
	 * 断开连接.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	public void cDisconnect(GizWifiDevice xpgWifiDevice) {
		xpgWifiDevice.disconnect();
	}

	/**
	 * 解除绑定.
	 * 
	 * @param uid
	 *            the uid
	 * @param token
	 *            the token
	 * @param did
	 *            the did
	 * @param passCode
	 *            the pass code
	 */
	public void cUnbindDevice(String uid, String token, String did,
			String passCode) {
		xpgWifiGCC.unbindDevice(uid, token, did, passCode);
	}

	/**
	 * 更新备注.
	 * 
	 * @param uid
	 *            the uid
	 * @param token
	 *            the token
	 * @param did
	 *            the did
	 * @param passCode
	 *            the pass code
	 * @param remark
	 *            the remark
	 */
	public void cUpdateRemark(String uid, String token, String did,
			String passCode, String remark) {
		xpgWifiGCC.bindDevice(uid, token, did, passCode, remark);
	}

	// =================================================================
	//
	// 中控网关相关
	//
	// =================================================================

	/**
	 * 获取子设备列表.
	 * 
	 * @param centralControlDevice
	 *            中控设备类
	 */
	public void cGetSubDevicesList(
			GizWifiCentralControlDevice centralControlDevice) {
		centralControlDevice.getSubDevices();
	}

	/**
	 * 添加子设备.
	 * 
	 * @param centralControlDevice
	 *            中控设备类
	 */
	public void cAddSubDevice(GizWifiCentralControlDevice centralControlDevice) {
		centralControlDevice.addSubDevice();
	}

	/**
	 * 删除子设备.
	 * 
	 * @param centralControlDevice
	 *            中控设备类
	 * @param subDevice
	 *            子设备类
	 */
	public void cDeleteSubDevice(
			GizWifiCentralControlDevice centralControlDevice,
			GizWifiSubDevice subDevice) {
		centralControlDevice.deleteSubDevice(subDevice.getSubDid());
	}
	
	/**
	 * 添加分组
	 * 
	 * @param uid
	 *            用户id
	 * @param token
	 *            授权令牌
	 * @param productKey
	 *            指定productkey
	 * @param groupName
	 *            分组名称
	 * @param specilalDevices
	 *            指定specilalDevices          
	 * */
	public void cAddGroup(String uid, String token,
			String productKey,String groupName,List<ConcurrentHashMap<String, String>> specilalDevices) {
		xpgWifiGCC.addGroup(uid, token, productKey, groupName, specilalDevices);
	}
	
	/**
	 * 修改分组
	 * 
	 * @param uid
	 *            用户id
	 * @param token
	 *            授权令牌
	 * @param gid
	 *            指定group id
	 * @param groupName
	 *            分组名称
	 * @param specilalDevices
	 *            指定specilalDevices          
	 * */
	public void cEditGroup(String uid, String token,
			String gid,String groupName,List<ConcurrentHashMap<String, String>> specilalDevices) {
		xpgWifiGCC.editGroup(uid, token, gid, groupName, specilalDevices);
	}

	/**
	 * 获取分组列表
	 * 
	 * @param uid
	 *            用户id
	 * @param token
	 *            授权令牌
	 * @param specialProductKey
	 *            指定productkey
	 * */
	public void cGetGroups(String uid, String token,
			List<String> specialProductKey) {
		xpgWifiGCC.getGroups(uid, token, specialProductKey);
	}

	/**
	 * 删除分组
	 * 
	 * @param uid
	 *            用户id
	 * @param token
	 *            授权令牌
	 * @param xpgWifiGroup
	 *            分组类
	 * */
	public void cDeleteGroups(String uid, String token,
			GizWifiGroup xpgWifiGroup) {
		xpgWifiGCC.removeGroup(uid, token, xpgWifiGroup.gid);
	}

	/**
	 * 添加子设备到分组
	 * 
	 *            组
	 *            子设备
	 * */
	public void cAddToGroup(GizWifiGroup xpgWifiGroup,
			String did, String subDid) {
		xpgWifiGroup.addDevice(did, subDid);
	}

	/**
	 * 从分组删除子设备
	 * 
	 *            组
	 *            子设备
	 * */
	public void cRemoveFromGroup(GizWifiGroup xpgWifiGroup,
			String did, String subDid) {
		xpgWifiGroup.removeDevice(did,subDid);
	}
	
	/**
	 * 从分组获取子设备
	 * 
	 * @param xpgWifiGroup
	 *            组
	 * */
	public void cGetGroupDevices(GizWifiGroup xpgWifiGroup){
		xpgWifiGroup.getDevices();
	}

	// =================================================================
	//
	// 智能灯网关控制相关
	//
	// =================================================================
	/**
	 * C switch on.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @param isOn
	 *            the is on
	 */
	public void cSwitchOn(GizWifiDevice xpgWifiDevice, String name, boolean isOn) {
		cWrite(xpgWifiDevice, name, isOn);
	}

	/**
	 * lightness.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @param lightness
	 *            亮度级别
	 */
	public void cLightness(GizWifiDevice xpgWifiDevice, int lightness) {
		cWrite(xpgWifiDevice, JsonKeys.LIGHTNESS, lightness);
	}

	public void setXpgWifiDevice(GizWifiDevice xpgWifiDevice )
	{
		mXpgWifiDevice = xpgWifiDevice;
	}

	public GizWifiDevice getXpgWifiDevice()
	{
		return mXpgWifiDevice;
	}
}
