package com.weijiangzhu.minaserver.entity;

import java.util.List;

public class User {
	private int age;
	private List<K5Car> cars;

	public void setAge(int age) {
		this.age = age;
	}

	public User(int age) {
		this.age = age;
	}

	public User(int age, List<K5Car> cars) {
		this.age = age;
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "age=" + age + ",cars:" + cars;
	}

}
