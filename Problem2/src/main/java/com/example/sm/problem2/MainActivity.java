package com.example.sm.problem2;
import android.renderscript.RSRuntimeException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DB db;

    ScrollView sv;
    TextView tv2;
    int salary2=0;

    MyBaseAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this,"test.db",null,1);
        sv = (ScrollView) findViewById(R.id.scrollview);

        tv2 = (TextView) findViewById(R.id.textView2);
        // need something here
        //ArrayList<Employee> emp_list = null;
        //adapter = new MyBaseAdapter(this, emp_list);
        /*listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)adapter);*/
    }
    @Override
    public void onClick(View v){
        EditText edit_name = (EditText) findViewById(R.id.edit_name);
        EditText edit_age = (EditText) findViewById(R.id.edit_age);
        EditText edit_salary = (EditText) findViewById(R.id.edit_salary);
        Employee employee;
        int salary=0;
        switch (v.getId()){
            case R.id.btn_inc:
                try {
                    salary = Integer.parseInt(edit_salary.getText().toString());
                    salary += 10000;
                    String temp = String.valueOf(salary);
                    edit_salary.setText(temp);
                }
                catch(RuntimeException e) {
                    Toast.makeText(this, "연봉에 숫자를 입력해주세요." , Toast.LENGTH_SHORT).show();
                }
                // need something here
                break;

            case R.id.btn_dec:
                try {
                    salary = Integer.parseInt(edit_salary.getText().toString());
                    salary -= 10000;
                    String temp = String.valueOf(salary);
                    edit_salary.setText(temp);
                }
                catch(RuntimeException e) {
                    Toast.makeText(this, "연봉에 숫자를 입력해주세요." , Toast.LENGTH_SHORT).show();
                }
                // need something here
                break;

            case R.id.btn_store: // create 버튼
                String name , age;
                name = edit_name.getText().toString();
                age = edit_age.getText().toString();
                try {
                    salary = Integer.parseInt(edit_salary.getText().toString());
                }
                catch(RuntimeException e) {
                    Toast.makeText(this, "연봉에 숫자를 입력해주세요." , Toast.LENGTH_SHORT).show();
                }
                if(salary != 0)
                    db.insert(name , age , salary);

                tv2.setText(db.print());
                break;

            case R.id.btn_modify:
                // need something here
                break;

            case R.id.btn_delete:
                Toast.makeText(this, "모든 데이터를 삭제했습니다." , Toast.LENGTH_SHORT).show();
                db.drop();
                tv2.setText(db.print());
                // need something here
                break;
        }
    }
}

interface Payment {
    void increase();
    void decrease();
}
