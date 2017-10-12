package ua.bellkross.intentandactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    private TextView tvName, tvSurname, tvNumber, tvAge, tvSocialNetwork, tvAbout;
    private ImageView ivSecondPhoto;
    private String photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tvName = (TextView) findViewById(R.id.tvName);
        tvSurname = (TextView) findViewById(R.id.tvSurname);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvSocialNetwork = (TextView) findViewById(R.id.tvSocialNetwork);
        tvAbout = (TextView) findViewById(R.id.tvAbout);

        ivSecondPhoto = (ImageView) findViewById(R.id.ivSecondPhoto);

        download();
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void download(){
        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("etName"));
        tvSurname.setText(intent.getStringExtra("etSurname"));
        tvNumber.setText(intent.getStringExtra("etNumber"));
        tvAge.setText(intent.getStringExtra("etAge"));
        tvSocialNetwork.setText(intent.getStringExtra("etSocialNetwork"));
        tvAbout.setText(intent.getStringExtra("etAbout"));
        photoUri = intent.getStringExtra("photoUri");
        if (!photoUri.equals("nouri")) {
            ivSecondPhoto.setImageURI(Uri.parse(photoUri));
        } else {
            ivSecondPhoto.setImageDrawable(getDrawable(R.drawable.myphoto));
        }
    }
}
