package com.kompa.pelleg.mymeals;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Log extends AppCompatActivity {
AdapterCustom yay;
ArrayList<Data> arrayList;
    helper_sqlite myDb;
    Button clear,back;
    ListView listView;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        myDb  = new helper_sqlite(this);
        arrayList = new ArrayList<Data>();
        arrayList= getData2Array();
if (arrayList!=null){


    listView = (ListView) findViewById(R.id.lvLOG);
    listView.setVisibility(View.VISIBLE);
    yay = new AdapterCustom(this, arrayList);
    listView.setAdapter(yay);

}



        clear = (Button) findViewById(R.id.btnclear);
back = (Button) findViewById(R.id.btnback);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View v ) {
        Intent intent = new Intent(Log.this,picturemode.class);
        Log.this.startActivity(intent);
    }
});
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                DeleteAllData();
            }
        });
    }

    private ArrayList<Data> getData2Array() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            // buffer.append("Id :"+ res.getString(0)+"\n");
            Data info = new Data(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
            arrayList.add(info);
//            buffer.append("Date :"+ res.getString(1)+"\n");
//            buffer.append("Time :"+ res.getString(2)+"\n");
//            buffer.append("Image :"+ res.getString(3)+"\n\n");
//            buffer.append("Comments :"+ res.getString(4)+"\n\n");
//            buffer.append("Calories :"+ res.getString(5)+"\n\n");
        }

//        // Show all data
       showMessage("All Data was loaded","Now Showing Data...");

        return arrayList;
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData() {

                        boolean isUpdate = myDb.updateData("","","","");
                        if(isUpdate == true)
                            Toast.makeText(Log.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Log.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }



    public void DeleteData() {

                        Integer deletedRows = myDb.deleteData("");
                        if(deletedRows > 0)
                            Toast.makeText(Log.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Log.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }

    public void DeleteAllData() {

        Boolean deletedRows = myDb.deleteAllData();
        if(deletedRows) {
            Toast.makeText(Log.this, "Data Deleted", Toast.LENGTH_LONG).show();
  listView.setVisibility(View.INVISIBLE);
        }
        else
            Toast.makeText(Log.this,"Data not Deleted",Toast.LENGTH_LONG).show();
    }
                }


