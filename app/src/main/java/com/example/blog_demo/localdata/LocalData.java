package com.example.blog_demo.localdata;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.blog_demo.app.GsuiteApp;

import org.json.JSONObject;

import java.io.Serializable;

public class LocalData {


    private static final String PREFS_NAME = "Gsuite";

    private static final String LOGIN = "abskldjfiors";
    private static final String FIRSTLOGIN = "odfjsdopad";
    private static final String keepMeAliveStatus = "saildugfhpa";
    private static final String keepMeAliveTime = "kusyrgfiadf";
    private static final String timestamp = "iyvimhjbkh";
    private static final String fcm_token = "amnloaspefj";
    private static final String id = "id";
    private static final String json = "JSON";


    public static boolean setPreference(String key, String value) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public static boolean setPreference2(String key, String value) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public static String getPreference(String key) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "none");
    }
    public static Serializable getPreference2(String key) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "none");
    }
    public static boolean setPreferenceInt(String key, int value) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getPreferenceInt(String key) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }

    public static boolean setPreferenceLong(String key, Long value) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static Long getPreferenceLong(String key) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key,0L);
    }

    public static boolean setPreferenceFloat(String key, Float value) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static Float getPreferenceFloat(String key) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key,0f);
    }


    public static boolean setPreferenceBool(String key, Boolean value) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getPreferenceBool(String key) {
        SharedPreferences settings = GsuiteApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    public static void setLogin(String data){
        setPreference(LOGIN,data);

    }

    public static String getLoginString(){
        return getPreference(LOGIN);
    }


    //check if first Login
    public static void setFirstlogin(Boolean isFirstLogin){
        setPreferenceBool(FIRSTLOGIN,isFirstLogin);

    }
    public static Boolean isFirstlogin(){
        return getPreferenceBool(FIRSTLOGIN);
    }


    //==============================setting Keep Me Alive Timer============================================

    public static void setKeepMeAliveStatus(Boolean status){
        setPreferenceBool(keepMeAliveStatus,status);

    }

    public static Boolean getKeepMeAliveStatus(){
        return getPreferenceBool(keepMeAliveStatus);
    }


    //===================================== setLastKeepMeAliveTime============================================

    public static void setLastKeepMeAliveTime(long img){
        setPreferenceLong(keepMeAliveTime,img);

    }

    public static long getLastKeepMeAliveTime(){
        return getPreferenceLong(keepMeAliveTime);
    }


    // set Init Time WhileLogin

    public static void setTimestamp(Long timstamp){
        setPreferenceLong(timestamp,timstamp);

    }

    public static Long getTimestamp(){
        return getPreferenceLong(timestamp);
    }

    //==================== setting OnesignalID==============================

    public static void setFcmToken(String referalCode){
        setPreference(fcm_token,referalCode);

    }

    public static String getFcmToken() {
        return getPreference(fcm_token);
    }

    public static void setId(String referalCode){
        setPreference(id,referalCode);

    }

    public static String getId() {
        return getPreference(id);
    }

    public static void setJson(String referalCode){
        setPreference(json,referalCode);

    }
    public static String getJson() {
        return getPreference(json);
    }
//    public static JSONObject getJson() {
//        return (JSONObject) getPreference2(json);
//    }


}
