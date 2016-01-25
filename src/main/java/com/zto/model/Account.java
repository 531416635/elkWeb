package com.zto.model;

public class Account {
	private long account_number;
	private String address;
	private long age;
	private long balance;
	private String city;
	private String email;
	private String employer;
	private String firstname;
	private String gender;// 性别
	/*private long grade;// 评分，等级
*/	private String lastname;
	private String state;

	public long getAccount_number() {
		return account_number;
	}

	public void setAccount_number(long account_number) {
		this.account_number = account_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/*public long getGrade() {
		return grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}*/

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "account [account_number=" + account_number + ", address="
				+ address + ", age=" + age + ", balance=" + balance + ", city="
				+ city + ", email=" + email + ", employer=" + employer
				+ ", firstname=" + firstname + ", gender=" + gender
				/*+ ", grade=" + grade*/ + ", lastname=" + lastname + ", state="
				+ state + "]";
	}

}
