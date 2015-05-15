package com.tan.oldphone.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class SharedManager {

	private static SharedManager share = null;
	private SharedPreferences mSharedPreferences = null;
	private Editor edit = null;
	private Context mContext;

	public static SharedManager getInstance(Context context) {
		if (share == null) {
			share = new SharedManager(context);
		}
		return share;
	}

	private SharedManager(Context context) {
		mContext = context;
		mSharedPreferences 	= context.getSharedPreferences("type", Context.MODE_PRIVATE);
		edit = mSharedPreferences.edit();
	}
	
	public void setServerIP(String serverIP){
		edit.putString("serverIP", serverIP);
		edit.commit();
	}
	
	public void setMQTTServerIP(String mqttServerIP){
		edit.putString("mqttServerIP", mqttServerIP);
		edit.commit();
	}
	
	public void setServerPort(String serverPort){
		edit.putString("serverPort", serverPort);
		edit.commit();
	}
	
	public void setMQTTServerPort(String mqttServerPort){
		edit.putString("mqttServerPort", mqttServerPort);
		edit.commit();
	}
	
	public void setChannelInfo(String channelInfo){
		edit.putString("channelInfo", channelInfo);
		edit.commit();
	}
	
	public void setBootNumber(long bootNumber){
		edit.putLong("bootNumber", bootNumber);
		edit.commit();
	}
	
/*	public void setTypeData(TypeData mTypeData){
		if(mTypeData==null)
			return;
		setMemberTypeMap(mTypeData.getMemberTypeMap());
		setIndustryTypeMap(mTypeData.getIndustryTypeMap());
		setProjectPhaseMap(mTypeData.getProjectPhaseMap());
		setInvestmentScaleMap(mTypeData.getInvestmentScaleMap());
		setGoodFieldMapMap(mTypeData.getGoodFieldMap());
		setCounselingModeMap(mTypeData.getCounselingModeMap());
	}*/

	public void setMemberTypeMap(Map<String,String> memberTypeMap){
		Iterator<Map.Entry<String, String>> it = memberTypeMap.entrySet().iterator();
		String memberTypeID = "";
		String memberTypeContent = "";
		List<String> list = Util.getInstance(mContext).MapSort(memberTypeMap);
		for (int i = 0; i < list.size(); i++) {
			memberTypeID = memberTypeID + list.get(i)+",";
			memberTypeContent = memberTypeContent + memberTypeMap.get(list.get(i))+",";
		}
		edit.putString("memberTypeID", memberTypeID.substring(0, memberTypeID.length()-1));
		edit.putString("memberTypeContent", memberTypeContent.substring(0, memberTypeContent.length()-1));
		edit.commit();
  
	}
	
	public void setIndustryTypeMap(Map<String,String> industryTypeMap){
		Iterator<Map.Entry<String, String>> it = industryTypeMap.entrySet().iterator();
		String industryTypeID = "";
		String industryTypeContent = "";
		List<String> list = Util.getInstance(mContext).MapSort(industryTypeMap);
		for (int i = 0; i < list.size(); i++) {
			industryTypeID = industryTypeID + list.get(i)+",";
			industryTypeContent = industryTypeContent + industryTypeMap.get(list.get(i))+",";
		}
		edit.putString("industryTypeID", industryTypeID.substring(0, industryTypeID.length()-1));
		edit.putString("industryTypeContent", industryTypeContent.substring(0, industryTypeContent.length()-1));
		edit.commit();
	}
	
	public void setProjectPhaseMap(Map<String,String> projectPhaseMap){
		Iterator<Map.Entry<String, String>> it = projectPhaseMap.entrySet().iterator();
		String projectPhaseID = "";
		String projectPhaseContent = "";
		List<String> list = Util.getInstance(mContext).MapSort(projectPhaseMap);
		for (int i = 0; i < list.size(); i++) {
			projectPhaseID = projectPhaseID + list.get(i)+",";
			projectPhaseContent = projectPhaseContent + projectPhaseMap.get(list.get(i))+",";
		}
		edit.putString("projectPhaseID", projectPhaseID.substring(0, projectPhaseID.length()-1));
		edit.putString("projectPhaseContent", projectPhaseContent.substring(0, projectPhaseContent.length()-1));
		edit.commit();
	}
	
	public void setInvestmentScaleMap(Map<String,String> investmentScaleMap){
		Iterator<Map.Entry<String, String>> it = investmentScaleMap.entrySet().iterator();
		String investmentScaleID = "";
		String investmentScaleContent = "";
		List<String> list = Util.getInstance(mContext).MapSort(investmentScaleMap);
		for (int i = 0; i < list.size(); i++) {
			investmentScaleID = investmentScaleID + list.get(i)+",";
			investmentScaleContent = investmentScaleContent + investmentScaleMap.get(list.get(i))+",";
		}
		edit.putString("investmentScaleID", investmentScaleID.substring(0, investmentScaleID.length()-1));
		edit.putString("investmentScaleContent", investmentScaleContent.substring(0, investmentScaleContent.length()-1));
		edit.commit();
	}
	
	public void setGoodFieldMapMap(Map<String,String> goodFieldMap){
		Iterator<Map.Entry<String, String>> it = goodFieldMap.entrySet().iterator();
		String goodFieldID = "";
		String goodFieldContent = "";
		List<String> list = Util.getInstance(mContext).MapSort(goodFieldMap);
		for (int i = 0; i < list.size(); i++) {
			goodFieldID = goodFieldID + list.get(i)+",";
			goodFieldContent = goodFieldContent + goodFieldMap.get(list.get(i))+",";
		}
		edit.putString("goodFieldID", goodFieldID.substring(0, goodFieldID.length()-1));
		edit.putString("goodFieldContent", goodFieldContent.substring(0, goodFieldContent.length()-1));
		edit.commit();
	}
	
	public void setCounselingModeMap(Map<String,String> counselingModeMap){
		Iterator<Map.Entry<String, String>> it = counselingModeMap.entrySet().iterator();
		String counselingModeID = "";
		String counselingModeContent = "";
		List<String> list = Util.getInstance(mContext).MapSort(counselingModeMap);
		for (int i = 0; i < list.size(); i++) {
			counselingModeID = counselingModeID + list.get(i)+",";
			counselingModeContent = counselingModeContent + counselingModeMap.get(list.get(i))+",";
		}
		edit.putString("counselingModeID", counselingModeID.substring(0, counselingModeID.length()-1));
		edit.putString("counselingModeContent", counselingModeContent.substring(0, counselingModeContent.length()-1));
		edit.commit();
	}
	
	public void setMemberId(String member_id){
		edit.putString("member_id", member_id);
		edit.commit();
	}
	
	public void setMemberLevel(String member_level){
		edit.putString("member_level", member_level);
		edit.commit();
	}
	
	public void setSave(boolean isSave){
		edit.putBoolean("isSave", isSave);
		edit.commit();
	}
	
	public void setUserName(String name){
		edit.putString("UserName", name);
		edit.commit();
	}
	
	public void setUserPwd(String pwd){
		edit.putString("UserPwd", pwd);
		edit.commit();
	}
	
	public String getUserName(){
		return mSharedPreferences.getString("UserName","");
	}
	
	public String getUserPwd(){
		return mSharedPreferences.getString("UserPwd", "");
	}
	
	public boolean getSave(){
		return mSharedPreferences.getBoolean("isSave", false);
	}
	
	public String getMemberId(){
		return mSharedPreferences.getString("member_id", "");
	}
	
	public String getMemberLevel(){
		return mSharedPreferences.getString("member_level", "");
	}
	
	public String getMemberTypeID(){
		return mSharedPreferences.getString("memberTypeID", "");
	}
	
	public String getMemberTypeContent(){
		return mSharedPreferences.getString("memberTypeContent", "");
	}
	
	public String getIndustryTypeID(){
		return mSharedPreferences.getString("industryTypeID", "");
	}
	
	public String getIndustryTypeContent(){
		return mSharedPreferences.getString("industryTypeContent", "");
	}
	
	public String getProjectPhaseID(){
		return mSharedPreferences.getString("projectPhaseID", "");
	}
	
	public String getProjectPhaseContent(){
		return mSharedPreferences.getString("projectPhaseContent", "");
	}
	
	public String getInvestmentScaleID(){
		return mSharedPreferences.getString("investmentScaleID", "");
	}
	
	public String getInvestmentScaleContent(){
		return mSharedPreferences.getString("investmentScaleContent", "");
	}
	
	public String getGoodFieldID(){
		return mSharedPreferences.getString("goodFieldID", "");
	}
	
	public String getGoodFieldContent(){
		return mSharedPreferences.getString("goodFieldContent", "");
	}
	
	public String getCounselingModeID(){
		return mSharedPreferences.getString("counselingModeID", "");
	}
	
	public String getCounselingModeContent(){
		return mSharedPreferences.getString("counselingModeContent", "");
	}
	
	public String getChannelInfo(){
		return mSharedPreferences.getString("channelInfo", "");
	}
	
	public long getBootNumber(){
		return mSharedPreferences.getLong("bootNumber", 0L);
	}
	
	public String getServerIP(){
		return mSharedPreferences.getString("serverIP", "");
	}
	
	public String getMQTTServerIP(){
		return mSharedPreferences.getString("mqttServerIP", "");
	}
	
	public String getServerPort(){
		return mSharedPreferences.getString("serverPort", "");
	}
	
	public String getMQTTServerPort(){
		return mSharedPreferences.getString("mqttServerPort", "");
	}
	
}
