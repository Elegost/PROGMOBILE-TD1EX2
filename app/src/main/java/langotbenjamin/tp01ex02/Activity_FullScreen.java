package langotbenjamin.tp01ex02;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileInputStream;

public class Activity_FullScreen extends AppCompatActivity {

    private Bitmap bmp;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__full_screen);
        chargeImage();
    }

    private void chargeImage()
    {
        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image"); //on récupére le chemin du fichier
        try {
            FileInputStream is = this.openFileInput(filename); //On lit le fichier
            bmp = BitmapFactory.decodeStream(is); //on créé un bitmap
            is.close(); //on ferme ce qui se charge de lire le fichier
            img = (ImageView) findViewById(R.id.imageViewFullScreen);
            img.setImageBitmap(bmp); //on place au bon endroit l'image
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Fonction qui permet de retourner a l'activité appelante
    public void retour(View view)
    {
        finish();
    }
}
