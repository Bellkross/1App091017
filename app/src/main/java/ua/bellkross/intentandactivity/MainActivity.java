package ua.bellkross.intentandactivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.Parcel;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFY_ID = 1;
    private static final int GALLERY_REQUEST = 1;

    private EditText etName, etSurname, etNumber, etAge, etSocialNetwork, etAbout;
    private ImageView ivPhoto;
    private String currentPhoto;
    private boolean[] notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notify = new boolean[7];
        for (boolean item:notify) {
            item = false;
        }

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etAge = (EditText) findViewById(R.id.etAge);
        etSocialNetwork = (EditText) findViewById(R.id.etSocialNetwork);
        etAbout = (EditText) findViewById(R.id.etAbout);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        currentPhoto = "nouri";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.setGroupCheckable(R.id.menu_group, true, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int idOfElem = 0;
        item.setChecked(!item.isChecked());

        switch (id){
            case R.id.box_photo:
                idOfElem = 0;
                break;
            case R.id.box_name:
                idOfElem = 1;
                break;
            case R.id.box_surname:
                idOfElem = 2;
                break;
            case R.id.box_number:
                idOfElem = 3;
                break;
            case R.id.box_age:
                idOfElem = 4;
                break;
            case R.id.box_snetwork:
                idOfElem = 5;
                break;
            case R.id.box_about:
                idOfElem = 6;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        notify[idOfElem] = !notify[idOfElem];
        return true;
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
        String[] valueArray = new String[]{currentPhoto,name,surname,number,age,socialNetwork,about};
        boolean checkRes = emptyToastCheck(valueArray);
        notifyCheck(valueArray, checkRes);
        if(checkRes) {
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

    private boolean emptyToastCheck(String[] values){
        String toastText = "";

        int emptyElems = 0;

        for (int i = 1; i < values.length; i++)
            if(values[i].equals("")) {
                if(emptyElems == 0)
                    switch (i){
                    case 1:
                        if(!notify[i])
                            toastText+="U've not input ur name";
                        break;
                    case 2:
                        if(!notify[i])
                            toastText+="U've not input ur surname";
                        break;
                    case 3:
                        if(!notify[i])
                            toastText+="U've not input ur number";
                        break;
                    case 4:
                        if(!notify[i])
                            toastText+="U've not input ur age";
                        break;
                    case 5:
                        if(!notify[i])
                            toastText+="U've not input ur social network";
                        break;
                    case 6:
                        if(!notify[i])
                            toastText+="U've not input an information about u";
                        break;
                }
                if(!notify[i])
                    ++emptyElems;
            }

        if(emptyElems==0 && !values[0].equals("nouri")){
            return true;
        } else {
            Toast toast;
            if(values[0].equals("nouri")&&!notify[0]){
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

    private void notifyCheck(String[] values, boolean checkRes){
        String notifyText = "";
        String title = "";

        int notifyElems = 0;
        for (int i = 1; i < values.length; i++)
            if(values[i].equals("")) {
                if(notifyElems==0)
                switch (i) {
                    case 1:
                        if (notify[i])
                            notifyText += "U've not input ur name";
                        break;
                    case 2:
                        if (notify[i])
                            notifyText += "U've not input ur surname";
                        break;
                    case 3:
                        if (notify[i])
                            notifyText += "U've not input ur number";
                        break;
                    case 4:
                        if (notify[i])
                            notifyText += "U've not input ur age";
                        break;
                    case 5:
                        if (notify[i])
                            notifyText += "U've not input ur social network";
                        break;
                    case 6:
                        if (notify[i])
                            notifyText += "U've not input an information about u";
                        break;
                }
                if(notify[i])
                    notifyElems++;
            }

            if(notifyElems==0&&!notify[0]){
                return;
            }else {
                if (notifyElems > 1) {
                    notifyText += " and ( " + (notifyElems - 1) + " )";
                }
                if (!checkRes)
                    title = "You left in main activity";
                else
                    title = "You go to inf activity";

                pushNotification("U have a news . . .", title, notifyText, 12345, notify[0]);
            }

    }

    private void pushNotification(String tricker, String title, String body, int id, boolean addActionPhoto){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);;
        //build the notification
        notification.setSmallIcon(R.mipmap.ic_notify);
        notification.setTicker(tricker);
        notification.setContentTitle(title);
        notification.setContentText(body);
        notification.setWhen(System.currentTimeMillis());
        notification.setAutoCancel(true);
        //Без этой дичи,     впринципе, оно работает, но не будет переходить на экран при нажатии
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(addActionPhoto)
            notification.addAction(R.mipmap.ic_notify, "Add photo", pendingIntent);
        notification.setContentIntent(pendingIntent);


        //builds notification and issues it
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification.build());
    }

}