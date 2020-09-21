package com.mitocode.security;

import java.util.Date;

public class ErrorLogin {

	private String menaje;
	private Date timeStamp;
	
	public ErrorLogin(String menaje, Date timeStamp) {
		this.menaje = menaje;
		this.timeStamp = timeStamp;
	}

	public String getMenaje() {
		return menaje;
	}

	public void setMenaje(String menaje) {
		this.menaje = menaje;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
