package ua.bellkross.intentandactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 1;
    private EditText etName, etSurname, etNumber, etAge, etSocialNetwork, etAbout;
    private Button btnSend, btnClear;
    private ImageView ivPhoto;
    private String currentPhoto;
    private String LOG_TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etAge = (EditText) findViewById(R.id.etAge);
        etSocialNetwork = (EditText) findViewById(R.id.etSocialNetwork);
        etAbout = (EditText) findViewById(R.id.etAbout);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnClear = (Button) findViewById(R.id.btnClear);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        currentPhoto = new String("nouri");
    }

    public void pickPhoto(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        currentPhoto = imageReturnedIntent.getData().toString();
        ivPhoto.setImageURI(Uri.parse(currentPhoto));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void send(View view){
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("etName", etName.getText().toString());
        intent.putExtra("etSurname", etSurname.getText().toString());
        intent.putExtra("etNumber", etNumber.getText().toString());
        intent.putExtra("etAge", etAge.getText().toString());
        intent.putExtra("etSocialNetwork", etSocialNetwork.getText().toString());
        intent.putExtra("etAbout", etAbout.getText().toString());
        intent.putExtra("photoUri", currentPhoto.toString());
        startActivity(intent);
        this.finish();
    }

    public void clear(View view){
        etName.setText("");
        etSurname.setText("");
        etNumber.setText("");
        etAge.setText("");
        etSocialNetwork.setText("");
        etAbout.setText("");
        ivPhoto.setImageDrawable(getDrawable(R.drawable.myphoto));
    }
}