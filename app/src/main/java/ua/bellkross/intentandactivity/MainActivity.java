package ua.bellkross.intentandactivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 1;
    private EditText etName, etSurname, etNumber, etAge, etSocialNetwork, etAbout;
    private ImageView ivPhoto;
    private String currentPhoto;

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

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        currentPhoto = "nouri";
    }

    public void pickPhoto(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(imageReturnedIntent!=null) {
            currentPhoto = imageReturnedIntent.getData().toString();
            ivPhoto.setImageURI(Uri.parse(currentPhoto));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void send(View view){
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String socialNetwork = etSocialNetwork.getText().toString().trim();
        String about = etAbout.getText().toString();
        Intent intent = new Intent(this, InformationActivity.class);

        intent.putExtra("etName", name);
        intent.putExtra("etSurname", surname);
        intent.putExtra("etNumber", number);
        intent.putExtra("etAge", age);
        intent.putExtra("etSocialNetwork", socialNetwork);
        intent.putExtra("etAbout", about);
        intent.putExtra("photoUri", currentPhoto);
        if(emptyCheck(new String[]{currentPhoto,name,surname,number,age,socialNetwork,about})) {
            startActivity(intent);
            this.finish();
        }
    }

    public void clear(View view){
        etName.setText("");
        etSurname.setText("");
        etNumber.setText("");
        etAge.setText("");
        etSocialNetwork.setText("");
        etAbout.setText("");
        ivPhoto.setImageDrawable(getDrawable(R.drawable.myphoto));
        currentPhoto = "nouri";
    }

    private boolean emptyCheck(String[] values){
        String toastText = "";
        int emptyElems = 0;
        for (int i = 1; i < values.length; i++)
            if(values[i].equals("")) {
                if(emptyElems == 0) switch (i){
                    case 1:
                        toastText+="U've not input ur name";
                        break;
                    case 2:
                        toastText+="U've not input ur surname";
                        break;
                    case 3:
                        toastText+="U've not input ur number";
                        break;
                    case 4:
                        toastText+="U've not input ur age";
                        break;
                    case 5:
                        toastText+="U've not input ur social network";
                        break;
                    case 6:
                        toastText+="U've not input an information about u";
                        break;
                }
                ++emptyElems;
            }

        if(emptyElems==0 && !values[0].equals("nouri")){
            return true;
        } else {
            Toast toast;
            if(values[0].equals("nouri")){
                toastText="You haven't taken a photo and ( " + emptyElems + " )";
                toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
                LinearLayout toastContainer = (LinearLayout) toast.getView();
                ImageView ivPhoto = new ImageView(getApplicationContext());
                ivPhoto.setImageResource(R.drawable.havenophotosm);
                toastContainer.addView(ivPhoto,0);
            }else{
                if (emptyElems > 1) {
                    toastText+=" and ( " + (emptyElems-1) + " )";
                }
                toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        return false;
    }
}