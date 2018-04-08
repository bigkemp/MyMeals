package com.kompa.pelleg.mymeals;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class picturemode extends AppCompatActivity {
    ImageView img;
    helper_sqlite myDb;
    helper_sqlite_FOOD myDb2;
    Button shoot,Go2Log;
    String Recodedtime;
    private Vision vision;
    Bitmap bitmap;
    String comments;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturemode);
        myDb2= new helper_sqlite_FOOD(this);
        if (myDb2.CheckifEmpty()) {

            myDb2.insertData("Hamburger","270");
            myDb2.insertData("Schnitzel","300");
            myDb2.insertData("French Fries","152");
            myDb2.insertData("Fish","142");
            myDb2.insertData("Egg","74");
            myDb2.insertData("Butter","102");
            myDb2.insertData("Salad","15");
            myDb2.insertData("Pizza","237");
            myDb2.insertData("Toast","72");
            myDb2.insertData("Bread","80");
            myDb2.insertData("Coca-Cola","100");
            myDb2.insertData("Chocolate","152");
            myDb2.insertData("Orange Juice","112");
            myDb2.insertData("Apple","72");
            myDb2.insertData("Orange","62");
            myDb2.insertData("Bamba","150");
            myDb2.insertData("Cucumber","1");
        }else {


        }
     /* myDb2.insertData("Hamburger","270");
        myDb2.insertData("Schnitzel","300");
        myDb2.insertData("French Fries","152");
       myDb2.insertData("Fish","142");
        myDb2.insertData("Egg","74");
        myDb2.insertData("Butter","102");
        myDb2.insertData("Salad","15");
        myDb2.insertData("Pizza","237");
        myDb2.insertData("Toast","72");
        myDb2.insertData("Bread","80");
       myDb2.insertData("Coca-Cola","100");
        myDb2.insertData("Chocolate","152");
        myDb2.insertData("Orange Juice","112");
        myDb2.insertData("Apple","72");
        myDb2.insertData("Orange","62");
        myDb2.insertData("Bamba","150");
        myDb2.insertData("Cucumber","1"); */
         myDb  = new helper_sqlite(this);
         shoot = (Button) findViewById(R.id.btnSHOOT);
         img = (ImageView) findViewById(R.id.shot);
        Go2Log = (Button)findViewById(R.id.btnLog);

        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                new AndroidJsonFactory(),
                null);

        visionBuilder.setVisionRequestInitializer(
                new VisionRequestInitializer("AIzaSyAAYGYX2nJKh4Gu-VmWTHPe6CUzCjIj7wA"));

        vision = visionBuilder.build();

        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);


                Vision.Builder visionBuilder = new Vision.Builder(
                        new NetHttpTransport(),
                        new AndroidJsonFactory(),
                        null);

                visionBuilder.setVisionRequestInitializer(
                        new VisionRequestInitializer("AIzaSyAAYGYX2nJKh4Gu-VmWTHPe6CUzCjIj7wA"));


            }
        });
Go2Log.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View v ) {
        Intent intent = new Intent(picturemode.this,Log.class);
        picturemode.this.startActivity(intent);
    }
});


    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
         bitmap = (Bitmap)data.getExtras().get("data");
img.setImageBitmap(bitmap);
SaveImage( this,bitmap);
    }
    @SuppressLint("NewApi")
    public void SaveImage( Context ctx, Bitmap ImageToSave){
        File filepath= Environment.getExternalStorageDirectory();
        String CurrentDateAndTime = getCurrentDateAndTime();
File dir = new File(filepath.getAbsolutePath()+"/DCIM/mymealspic");

        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (check == PackageManager.PERMISSION_GRANTED) {
            if (!dir.exists()){
                dir.mkdir();
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1024);
        }


File file = new File(dir,"yayziz"+CurrentDateAndTime+".jpg");
try{
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    ImageToSave.compress(Bitmap.CompressFormat.JPEG,85,fileOutputStream);
    fileOutputStream.flush();
    fileOutputStream.close();
    AddData(file);

} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
    }
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        Recodedtime=formattedDate;
        return formattedDate;
    }
    public  void AddData( final File file) {


     // faceDetection();
        AsyncTask.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0,bos);
                    byte[] bitmapdata = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                    //InputStream inputStream = bitmap;
                    //   byte[] photoData = IOUtils.toByteArray(inputStream);
                    //  byte[] photoData = byteBuffer.array();
                    Image inputImage = new Image();
                    inputImage.encodeContent(bitmapdata);

                    Feature desiredFeature = new Feature();
                    desiredFeature.setType("LABEL_DETECTION");

                    AnnotateImageRequest request = new AnnotateImageRequest();
                    request.setImage(inputImage);
                    request.setFeatures(Arrays.asList(desiredFeature));

                    BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
                    batchRequest.setRequests(Arrays.asList(request));

                    BatchAnnotateImagesResponse batchResponse =
                            vision.images().annotate(batchRequest).execute();

                    List<EntityAnnotation>  faces = batchResponse.getResponses()
                            .get(0).getLabelAnnotations();

                    int numberOfFaces = faces.size();
                    String CaloriesS="";
                    comments = "";
                    for(int i=0; i<numberOfFaces; i++) {
                        if (faces.get(i).getScore() > 0.50) {
                            String Calories = viewAll2(faces.get(i).getDescription());
                            if (Calories != null) {
                                CaloriesS = CaloriesS + " - "+Calories;
                            }else{
                                comments = comments + " - " + faces.get(i).getDescription();
                            }


                        }
                        if (i == 3)break;
                    }


                    String[] timearray =  Recodedtime.split("-");
                    boolean isInserted = myDb.insertData(timearray[1]+"-"+timearray[2]+"-"+timearray[0], timearray[3]+":"+timearray[4]+":"+timearray[5],
                            file.toString(),comments,CaloriesS   );

                    if (comments.matches("")){

                        comments="no matches found";
                    }
                    final String message =
                            comments;

                    if(isInserted == true)
                        Toast.makeText(picturemode.this,"Data Inserted",Toast.LENGTH_LONG).show();

                    else
                        Toast.makeText(picturemode.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    message, Toast.LENGTH_LONG).show();
                        }
                    });
comments=null;
                } catch(Exception e) {
                    android.util.Log.d("ERROR", e.getMessage());
                }
            }
        });



       //viewAll();

    }

    public String viewAll2( String option) {

        Cursor res = myDb2.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return option;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            // buffer.append("Id :"+ res.getString(0)+"\n");
            if (option.matches(res.getString(1)))
         return res.getString(2);

        }

        return null;
    }

    public void viewAll() {
       
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                           // buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Date :"+ res.getString(1)+"\n");
                            buffer.append("Time :"+ res.getString(2)+"\n");
                            buffer.append("Image :"+ res.getString(3)+"\n\n");
                            buffer.append("Comments :"+ res.getString(4)+"\n\n");
                            buffer.append("Calories :"+ res.getString(5)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private String faceDetection() {
//       AsyncTask.execute(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                try {
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG,0,bos);
//                    byte[] bitmapdata = bos.toByteArray();
//                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
//                    //InputStream inputStream = bitmap;
//                 //   byte[] photoData = IOUtils.toByteArray(inputStream);
//                  //  byte[] photoData = byteBuffer.array();
//                    Image inputImage = new Image();
//                    inputImage.encodeContent(bitmapdata);
//
//                    Feature desiredFeature = new Feature();
//                    desiredFeature.setType("LABEL_DETECTION");
//
//                    AnnotateImageRequest request = new AnnotateImageRequest();
//                    request.setImage(inputImage);
//                    request.setFeatures(Arrays.asList(desiredFeature));
//
//                    BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
//                    batchRequest.setRequests(Arrays.asList(request));
//
//                    BatchAnnotateImagesResponse batchResponse =
//                            vision.images().annotate(batchRequest).execute();
//
//                    List<EntityAnnotation>  faces = batchResponse.getResponses()
//                            .get(0).getLabelAnnotations();
//
//                    int numberOfFaces = faces.size();
//
//                     comments = "";
//                    for(int i=0; i<numberOfFaces; i++) {
//                        if (faces.get(i).getScore() > 0.70)
//                            comments= comments+ " - " + faces.get(i).getDescription();
//                    }
//
//                    if (comments.matches("")){
//
//                        comments="no matches found";
//                    }
//                    final String message =
//                            comments;
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    message, Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                } catch(Exception e) {
//                    android.util.Log.d("ERROR", e.getMessage());
//                }
//            }
//        });
        return  comments;
    }
}

