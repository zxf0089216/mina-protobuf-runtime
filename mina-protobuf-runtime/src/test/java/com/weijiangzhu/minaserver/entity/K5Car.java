package com.weijiangzhu.minaserver.entity;

/**
 * 用于证明支持继承
 * @author zhuweijiang
 *
 */
public class K5Car extends Car {
	public K5Car(String name, String country) {
		super(name);
		this.country = country;
	}

	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
