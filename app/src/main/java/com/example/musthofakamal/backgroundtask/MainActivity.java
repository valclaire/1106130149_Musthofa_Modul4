package com.example.musthofakamal.backgroundtask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listmahasiswa;
    Button BStart, Search;
    ProgressDialog progressDialog;
    //Array Nama Mahasiswa
    private String [] nama = {"Ana", "Budi", "Yudi", "Udin", "Agus", "mamat", "abcd", "efgh", "ijkl", "mnop"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //refer listview, button
        listmahasiswa = (ListView)findViewById(R.id.listmahasiswa);
        listmahasiswa.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new ArrayList<String>()));
        Search = (Button)findViewById(R.id.ImgSearch);
        BStart = (Button)findViewById(R.id.BStart);
        //onClick pada search akan pindah pada act selanjutnya
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(MainActivity.this, CariGambarActivity.class);
                startActivity(move);
            }
        });
        BStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute();
            }
        });
    }
    private class MyTask extends AsyncTask <Void, String, String>{
        ArrayAdapter<String> adapter;
        int count;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress dialog
            adapter = (ArrayAdapter<String>)listmahasiswa.getAdapter();
            progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "Please Wait");
            progressDialog.setCancelable(true);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            count = 0;
        }
// background progress
        @Override
        protected String doInBackground(Void... voids) {
            //variable Array
            for (String Nama : nama){
                publishProgress(Nama);
                try{
                    //sleep
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return "Name Has Been Added";
        }
//setelah progress
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            adapter.add(values[0]);
            count ++;
            progressDialog.setProgress(count);
        }

        @Override
        protected void onPostExecute(String result) {
           // super.onPostExecute();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}
