package com.task.vasilyevanton.task.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.task.vasilyevanton.task.Info.NewUserActivity;
import com.task.vasilyevanton.task.R;
import com.task.vasilyevanton.task.ShowUser.UserActivity;
import com.task.vasilyevanton.task.adapter.PeopleAdapter;
import com.task.vasilyevanton.task.pojos.PeopleData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private MainPresenter presenter;
    private ListView peopleListView;
    private PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.list);
        peopleListView = (ListView) findViewById(R.id.people_list);
        peopleListView.setOnItemClickListener(this);
        peopleListView.setOnItemLongClickListener(this);

        presenter = new MainPresenterImpl(this, this);
        presenter.initializeDB();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_user) {
            startActivity(new Intent(MainActivity.this, NewUserActivity.class));
        }
        if (id == android.R.id.home) {
            presenter.getPeopleInfo();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        return super.onOptionsItemSelected(item);
    }


    //to Presenter
    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        presenter.onResume();
        super.onResume();
    }


    //from Presenter
    @Override
    public void setPeopleInfoData(ArrayList<PeopleData> list) {
        adapter = new PeopleAdapter(MainActivity.this, R.layout.people_list_item, list);
        peopleListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final PeopleData data = (PeopleData) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("name", data.getFullName());
        intent.putExtra("age", data.getAge());
        intent.putExtra("salary", data.getSalary());
        intent.putExtra("position", data.getPosition());
        intent.putExtra("department", data.getDepartment());
        intent.putExtra("id", data.getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final PeopleData data = (PeopleData) adapterView.getItemAtPosition(i);
        presenter.getDepartmentInfo(data.getDepartment());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.rotate_3d);
        return true;
    }
}
