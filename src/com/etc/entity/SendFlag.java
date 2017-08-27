package com.etc.entity;

public class SendFlag {
	private int isNeedBottom =0;
	private boolean sendflag=false;
	
	public int getIsNeedBottom() {
		return isNeedBottom;
	}
	public void setIsNeedBottom(int isNeedBottom) {
		this.isNeedBottom = isNeedBottom;
	}
	public boolean isSendflag() {
		return sendflag;
	}
	public void setSendflag(boolean sendflag) {
		this.sendflag = sendflag;
	}
	
	public void addIsNeedBottom(){
		isNeedBottom++;
	}
	
}
