package com.shenshanlaoyuan.mobilesafe.domain;
/**
 * 手机联系人的信息封装
 * @author hp
 *
 */
public class ContantBean {

	private String phone;
	private String name;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ContantBean [phone=" + phone + ", name=" + name
				+ ", getPhone()=" + getPhone() + ", getName()=" + getName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
