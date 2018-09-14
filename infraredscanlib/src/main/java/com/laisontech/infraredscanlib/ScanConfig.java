package com.laisontech.infraredscanlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//config scan setting
public class ScanConfig {
	
	private Context context ;
	
	public ScanConfig(Context context){
		this.context = context ;
	}

	public boolean isOpen() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("open", false);
	}

	public void setOpen(boolean open) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("open", open) ;
		editor.apply() ;
	}

	public String getPrefix() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getString("prefix", "");
	}

	public void setPrefix(String prefix) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("prefix", prefix) ;
		editor.apply() ;
	}

	public String getSurfix() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getString("surfix", "");
	}

	public void setSurfix(String surfix) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("surfix", surfix) ;
		editor.apply() ;
	}

	public boolean isVoice() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("voice", false);
	}

	public void setVoice(boolean voice) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("voice", voice) ;
		editor.apply() ;
	}

	public boolean isF1() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f1", false);
	}

	public void setF1(boolean f1) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f1", f1) ;
		editor.apply() ;
	}

	public boolean isF2() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f2", false);
	}

	public void setF2(boolean f2) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f2", f2) ;
		editor.apply() ;
	}

	public boolean isF3() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f3", false);
	}

	public void setF3(boolean f3) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f3", f3) ;
		editor.apply() ;
	}

	public boolean isF4() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f4", false);
	}

	public void setF4(boolean f4) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f4", f4) ;
		editor.apply() ;
	}
	
	public boolean isF5() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f5", false);
	}

	public void setF5(boolean f5) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f5", f5) ;
		editor.apply(); ;
	}

	public boolean isF6() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f6", false);
	}

	public void setF6(boolean f6) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f6", f6) ;
		editor.apply() ;
	}

	public boolean isF7() {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		return shared.getBoolean("f7", true);
	}

	public void setF7(boolean f7) {
		SharedPreferences shared = context.getSharedPreferences("scanConfig", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putBoolean("f7", f7) ;
		editor.apply() ;
	}

	
	

}
