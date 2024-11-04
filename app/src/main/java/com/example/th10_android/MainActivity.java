package com.example.th10_android;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Khai báo các biến giao diện
    CustomAdapter customAdapter;
    ArrayList<String> arr_id, arr_malop, arr_tenlop, arr_siso;
    RecyclerView recyclerView;
    EditText edtmalop, edttenlop, edtsiso;
    Button btninsert, btndelete, btnupdate, btnquery;
    int id_class;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        edtmalop = findViewById(R.id.malopETmain);
        edttenlop = findViewById(R.id.tenlopETmain);
        edtsiso = findViewById(R.id.sisoETmain);
        btninsert = findViewById(R.id.insertBTmain);
        btndelete = findViewById(R.id.deleteBTmain);
        btnupdate = findViewById(R.id.updateBTmain);
        btnquery = findViewById(R.id.queryBTmain);

        //Hien thi Recycle View
        recyclerView= findViewById(R.id.qlsvRVmain);
        arr_id= new ArrayList<>();
        arr_malop= new ArrayList<>();
        arr_tenlop= new ArrayList<>();
        arr_siso= new ArrayList<>();
        customAdapter= new CustomAdapter(MainActivity.this,arr_id,arr_malop,arr_tenlop,arr_siso);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mydatabase = openOrCreateDatabase("qlsinhvien.db",MODE_PRIVATE,null);

        // Tạo Table để chứa dữ liệu
        try {
            String sql = "CREATE TABLE tbllop(malop TEXT primary key,tenlop TEXT, siso TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception e)
        {
            Log.e("Error","Table đã tồn tại");
        }
        //button insert
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String malop = edtmalop.getText().toString();
                String tenlop = edttenlop.getText().toString();
                String siso = edtsiso.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("malop",malop);
                myvalue.put("tenlop",tenlop);
                myvalue.put("siso",siso);
                String msg = "";
                if (mydatabase.insert("tbllop",null,myvalue) == -1)
                {
                    msg = "Fail to Insert Record!";
                }
                else {
                    msg ="Insert record Sucessfully";
                }
                Toast.makeText(MainActivity.this,
                        msg,Toast.LENGTH_SHORT).show();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String malop = edtmalop.getText().toString();
                int n = mydatabase.delete("tbllop","malop = ?",new
                        String[]{malop});
                String msg ="";
                if (n == 0)
                {
                    msg = "No record to Delete";
                }
                else {
                    msg = n +" record is deleted";
                }Toast.makeText(MainActivity.this, msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String siso = edtsiso.getText().toString();
                String malop = edtmalop.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("siso",siso);
                int n = mydatabase.update("tbllop",myvalue,"malop =?",new String[]{malop});
                String msg = "";
                if (n == 0)
                {
                    msg = "No record to Update";
                }
                else {
                    msg = n+ " record is updated";
                }
                Toast.makeText(MainActivity.this, msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_class = 1;
                arr_id.clear();
                arr_malop.clear();
                arr_tenlop.clear();
                arr_siso.clear();
                Cursor c =
                        mydatabase.query("tbllop", null, null, null, null, null, null);
                c.moveToNext
                        ();
                while (c.isAfterLast() == false) {
                    arr_id.add(String.valueOf(id_class));
                    arr_malop.add(c.getString(0));
                    arr_tenlop.add(c.getString(1));
                    arr_siso.add(c.getString(2));
                    c.moveToNext();
                    id_class += 1;
                }
                c.close();
                customAdapter = new
                        CustomAdapter(MainActivity.this, arr_id, arr_malop, arr_tenlop, arr_siso);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new
                        LinearLayoutManager(MainActivity.this));
            }
        });
    }
}

