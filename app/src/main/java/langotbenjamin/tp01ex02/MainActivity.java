package langotbenjamin.tp01ex02;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private Button buttonAndroid;
    private Button buttonApple;
    private Button buttonWindows;
    private ImageView img;
    private Bitmap bmp;
    private Button buttonPhoto;
    private Bitmap bitMap;
    private static int TAKE_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAndroid = (Button) findViewById(R.id.buttonAndroid);
        buttonApple = (Button) findViewById(R.id.buttonApple);
        buttonWindows = (Button) findViewById(R.id.buttonWindows);
        img = (ImageView) findViewById(R.id.image);
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
    }

    //Affiche l'image android
    public void showAndroid(View view)
    {
        buttonAndroid.setEnabled(false);
        buttonApple.setEnabled(true);
        buttonWindows.setEnabled(true);
        img.setImageResource(R.drawable.android);
    }

    //Affiche l'image Apple
    public void showApple(View view)
    {
        buttonAndroid.setEnabled(true);
        buttonApple.setEnabled(false);
        buttonWindows.setEnabled(true);
        img.setImageResource(R.drawable.apple);
    }

    //Affiche l'image Windows phone
    public void showWindows(View view)
    {
        buttonAndroid.setEnabled(true);
        buttonApple.setEnabled(true);
        buttonWindows.setEnabled(false);
        img.setImageResource(R.drawable.windows);
    }

    //Passage a l'activité "Activity_FullScreen" avec en paramètre le int identifiant le "R.drawable" selectionnée
    public void showInFullScreen(View view)
    {
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache(true);
        try
        {
            //Création du fichier image à mettre en plein écran
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            img.getDrawingCache(true).compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Fermeture du stream
            stream.close();

            //Lancement de l'activité en passant le chemin de l'image crée
            Intent intent = new Intent(this, Activity_FullScreen.class);
            intent.putExtra("image", filename);
            startActivity(intent);
            img.setDrawingCacheEnabled(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addImageToGallery(final String filePath, final Context context)
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }


    //Dois activer l'activité permettant de prendre en photo mais je n'ai pas pu tester cette partie
    public void takePhoto(View view)
    {
        // create intent with ACTION_IMAGE_CAPTURE action
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start camera activity
        startActivityForResult(intent, TAKE_PICTURE);
    }

    //Dois recuperer le resultat de l'activité ayant pris une photo et l'afficher sur l'imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == TAKE_PICTURE && resultCode== RESULT_OK && intent != null){
            // get bundle
            Bundle extras = intent.getExtras();
            // get bitmap
            bitMap = (Bitmap) extras.get("data");
            img.setImageBitmap(bitMap);
        }
    }
}
