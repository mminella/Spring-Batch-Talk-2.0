package com.michaelminella.springbatch.domain;

public class Target {

	private long id;
	// in a real environment this should be stored as a long
	private String ip;
	private int port;
	private boolean connected;
	private String banner;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public boolean isConnected() {
		return this.connected;
	}
	public void setConnected(boolean con) {
		this.connected = con;
	}
}