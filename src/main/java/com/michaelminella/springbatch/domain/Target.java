package com.michaelminella.springbatch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="target")
public class Target {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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