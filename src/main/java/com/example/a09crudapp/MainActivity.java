package com.example.a09crudapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    EditText editTextid,editTextname,editTextemail,editTextcc;
    Button buttonadd,buttongetdata,buttonupdate,buttondelete,buttonviewall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb=new DatabaseHelper(this);

        editTextid=findViewById(R.id.editText_id);
        editTextcc=findViewById(R.id.editText_CC);
        editTextname=findViewById(R.id.editText_name);
        editTextemail=findViewById(R.id.editText_email);
        buttonadd=findViewById(R.id.button_add);
        buttongetdata=findViewById(R.id.button_view);
        buttonupdate=findViewById(R.id.button_update);
        buttonviewall=findViewById(R.id.button_viewAll);
        buttondelete=findViewById(R.id.button_delete);

        addData();
        getData();
        viewAllData();
        updateData();
        deleteData();
    }

    private void deleteData() {
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd=editTextid.getText().toString();
                if(idd.equals("")){
                    editTextid.setError("Enter id");
                    return;
                }
                int deleterows=mydb.deleteData(idd);

                if(deleterows>0){
                    Toasty.success(MainActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toasty.error(MainActivity.this,"ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData() {
        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=mydb.updateData(editTextid.getText().toString(),editTextname.getText().toString(),editTextemail.getText().toString(),editTextcc.getText().toString());
                if(isUpdate==true){
                    Toasty.success(MainActivity.this,"Update Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toasty.error(MainActivity.this,"ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewAllData() {
        buttonviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=mydb.getAllData();
                if(cursor.getCount()==0)
                {
                    Toasty.error(MainActivity.this,"ERROR MESSAGE", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (cursor.moveToNext())
                {
                    buffer.append("ID :"+cursor.getString(0)+"\n");
                    buffer.append("NAME :"+cursor.getString(1)+"\n");
                    buffer.append("EMAIL :"+cursor.getString(2)+"\n");
                    buffer.append("Course_Count :"+cursor.getString(3)+"\n\n");
                }
                showMessage("ALL DATA",buffer.toString());
            }
        });
    }

    private void getData() {
        buttongetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=editTextid.getText().toString();
                if(id.equals("")){
                    editTextid.setError("Enter id");
                    return;
                }

                Cursor cursor=mydb.getData(id);
                String data=null;

                if(cursor.moveToNext()){
                    data="ID: "+cursor.getString(0)+"\n"+
                    "Name: "+cursor.getString(1)+"\n"+
                    "E-Mail: "+cursor.getString(2)+"\n"+
                    "Course Count: "+cursor.getString(3);
                }
                showMessage("Data",data);
            }
        });
    }

    private void addData() {
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=mydb.insertData(editTextname.getText().toString(),editTextemail.getText().toString(),editTextcc.getText().toString());
                if(res==true){
                    Toasty.success(MainActivity.this,"Data Added Successfuly",Toast.LENGTH_SHORT).show();
                }else{
                    Toasty.error(MainActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void showMessage(String title,String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }
}
