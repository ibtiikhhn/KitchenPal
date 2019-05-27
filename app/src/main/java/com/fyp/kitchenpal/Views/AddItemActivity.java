package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.fyp.kitchenpal.Model.FreezerItem;
import com.fyp.kitchenpal.Model.FridgeItem;
import com.fyp.kitchenpal.Model.PantryItem;
import com.fyp.kitchenpal.NotificationManager;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.ViewModels.FreezerViewModel;
import com.fyp.kitchenpal.ViewModels.FridgeViewModel;
import com.fyp.kitchenpal.ViewModels.PantryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddItemActivity extends AppCompatActivity {

    private static final int REQUEST_CAPTURE_IMAGE = 100;

    EditText editText;
    DatePicker datePicker;
    FloatingActionButton floatingActionButton;
    Spinner spinner;
    FridgeViewModel fridgeViewModel;
    FreezerViewModel freezerViewModel;
    PantryViewModel pantryViewModel;
    CircleImageView profileImage;
    FloatingActionButton newImage;
    Bitmap imageBitmap;
    String from ="";
    NotificationManager notificationManager;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        Intent intent = getIntent();
        from = intent.getStringExtra("from");

        notificationManager = new NotificationManager(getApplicationContext(), "foodtracker-id");

        fridgeViewModel = ViewModelProviders.of(this).get(FridgeViewModel.class);
        freezerViewModel = ViewModelProviders.of(this).get(FreezerViewModel.class);
        pantryViewModel = ViewModelProviders.of(this).get(PantryViewModel.class);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                Long expiryDate = getLongFromPicker();
                Long todayDate = Calendar.getInstance().getTimeInMillis();

                String bytes = getEncodedString(imageBitmap);

                if (name.isEmpty()) {
                    Toast.makeText(AddItemActivity.this, "Name cant be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imageBitmap==null) {
                    Toast.makeText(AddItemActivity.this, "Choose an Image first", Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (from) {
                    case "FreezerActivity": {
                        FreezerItem item = new FreezerItem(name, todayDate, expiryDate, bytes);
                        freezerViewModel.insertItem(item);
                        notificationManager.scheduleNotificationForFreezer(item);
                        finish();
                        break;
                    }
                    case "FridgeActivity": {
                        FridgeItem item = new FridgeItem(name, todayDate, expiryDate, bytes);
                        fridgeViewModel.insertItem(item);
                        notificationManager.scheduleNotificationForFridge(item);
                        finish();
                        break;
                    }
                    case "PantryActivity": {
                        PantryItem item = new PantryItem(name, todayDate, expiryDate, bytes);
                        pantryViewModel.insertItem(item);
                        notificationManager.scheduleNotificationForPantry(item);
                        finish();
                        break;
                    }
                    default:
                        Toast.makeText(AddItemActivity.this, "I am really sorry", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
            }
        });
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if(pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(imageBitmap);
            }
        }
    }

    public void initViews() {
        editText = findViewById(R.id.food_name);
        datePicker = findViewById(R.id.expiry_date);
        floatingActionButton = findViewById(R.id.save_food);
        profileImage = findViewById(R.id.profile_image);
        newImage = findViewById(R.id.newImage);
    }

    private Long getLongFromPicker() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        c.set(Calendar.MONTH, datePicker.getMonth());
        c.set(Calendar.YEAR, datePicker.getYear());
        return c.getTimeInMillis();
    }

    private String getEncodedString(Bitmap bitmap){

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, (100), os);

        byte[] imageArr = os.toByteArray();

        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }
}
