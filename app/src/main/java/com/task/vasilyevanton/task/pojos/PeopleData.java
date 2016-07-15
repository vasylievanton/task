package com.task.vasilyevanton.task.pojos;

public class PeopleData {
    private String mFullName;
    private String mPosition;
    private String mDepartment;
    private String mAge;
    private String mSalary;
    private String mUserPic;
    private int mId;

    public PeopleData(String fullName, String position, String department, String age, String salary, String userPic, int id) {

        mFullName = fullName;
        mPosition = position;
        mDepartment = department;
        mAge = age;
        mSalary = salary;
        mUserPic = userPic;
        mId = id;
    }

    public PeopleData() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getSalary() {
        return mSalary;
    }

    public void setSalary(String salary) {
        mSalary = salary;
    }
}
