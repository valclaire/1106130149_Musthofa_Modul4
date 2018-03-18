package com.example.musthofakamal.backgroundtask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class CariGambarActivity extends AppCompatActivity {
    //komponen
EditText urlimage;
Button isearch;
ImageView gambar;
ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);
        //refer komponen
        urlimage = (EditText)findViewById(R.id.imageurl);
        isearch = (Button)findViewById(R.id.ISearch);
        gambar = (ImageView)findViewById(R.id.image);


        //onClick method
        isearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageInit();
            }
        });}
    //search image
            private void loadImageInit() {
            String iurl = urlimage.getText().toString();
            new loadImage().execute(iurl);

            }

            private class loadImage extends AsyncTask<String, Void, Bitmap>{
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //load progress
                    loading = ProgressDialog.show(CariGambarActivity.this, "Loading", "Please Wait");
                    loading.setCancelable(true);
                    loading.setMax(100);
                    loading.setProgress(0);
                }

                @Override
                protected Bitmap doInBackground(String... params) {
                    Bitmap i = null;
                    try{
                        URL u = new URL(params[0]);
                        i = BitmapFactory.decodeStream((InputStream) u.getContent());
                    }catch (Exception e){
                        e.printStackTrace();
                    }return i;
                }

                @Override
                protected void onPostExecute(Bitmap i) {
                    super.onPostExecute(i);
                    if (i !=null){
                        ImageView imgs = new ImageView(CariGambarActivity.this);
                        gambar.setImageBitmap(i);
                        loading.dismiss();
                    }else{
                        loading.dismiss();
                        Toast.makeText(CariGambarActivity.this, "No Network or Image Does Not exist !",
                                Toast.LENGTH_LONG).show();
                    }


                }
            }
}
