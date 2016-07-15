package com.task.vasilyevanton.task.ShowUser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.task.vasilyevanton.task.DataBaseHandler;
import com.task.vasilyevanton.task.R;
import com.task.vasilyevanton.task.pojos.PeopleData;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, age, salary, department, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        name = (EditText) findViewById(R.id.user_id);
        age = (EditText) findViewById(R.id.user_age);
        salary = (EditText) findViewById(R.id.user_salary);
        department = (EditText) findViewById(R.id.user_department);
        position = (EditText) findViewById(R.id.user_position);
        getSupportActionBar().setTitle("Просмотр/Ред. пользователя");

        setUserData();

        Button saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);
    }

    private void setUserData() {
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        name.clearFocus();
        age.setText(intent.getStringExtra("age"));
        age.clearFocus();
        salary.setText(intent.getStringExtra("salary"));
        salary.clearFocus();
        department.setText(intent.getStringExtra("department"));
        department.clearFocus();
        position.setText(intent.getStringExtra("position"));
        position.clearFocus();
    }

    private void updateUserDB(PeopleData contact) {
        DataBaseHandler db = new DataBaseHandler(this);
        db.updateContact(contact);
    }

    @Override
    public void onClick(View view) {
        PeopleData contact = new PeopleData();
        contact.setId(getIntent().getIntExtra("id",0));
        contact.setFullName(name.getText().toString());
        contact.setPosition(position.getText().toString());
        contact.setDepartment(department.getText().toString());
        contact.setAge(age.getText().toString());
        contact.setSalary(salary.getText().toString());
        updateUserDB(contact);
        finish();
    }
}

