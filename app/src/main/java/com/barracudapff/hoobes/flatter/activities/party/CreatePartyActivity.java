package com.barracudapff.hoobes.flatter.activities.party;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.Party;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreatePartyActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private MapView partyMap;
    private EditText partyName;
    private TextView partyAdress;
    private TextView partyAuthor;
    private EditText partyTime;
    private TextInputLayout partyTimeLayout;
    private CheckBox partyFree;
    private EditText partyAbout;
    private ImageView partyImage;
    private CardView partyImageHolder;
    private Button partyCreate;

    private LatLng position;

    private User user;
    private String myFormat = "dd MMMM yyyy";
    private long time;
    private boolean isChangedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        position = new LatLng(getIntent().getDoubleExtra("POSITION_LAT", 0),
                getIntent().getDoubleExtra("POSITION_LON", 0));

        user = User.getCurrent(getBaseContext());

        initViews(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews(Bundle savedInstanceState) {
        partyMap = findViewById(R.id.create_party_map);
        partyName = findViewById(R.id.create_party_name);
        partyAdress = findViewById(R.id.create_party_address);
        partyAuthor = findViewById(R.id.create_party_author);
        partyTime = findViewById(R.id.create_party_time);
        partyTimeLayout = findViewById(R.id.textInputLayoutTime);
        partyFree = findViewById(R.id.create_party_free);
        partyAbout = findViewById(R.id.create_party_about);
        partyImage = findViewById(R.id.create_party_image);
        partyImageHolder = findViewById(R.id.card);
        partyCreate = findViewById(R.id.create_party_create);

        initMap(savedInstanceState);
        initAddress();
        initAuthor();
        partyFree.setChecked(true);

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);
            partyTime.setText(sdf.format(myCalendar.getTime()));
            time = myCalendar.getTime().getTime();
        };

        partyTime.setOnClickListener(view -> new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        partyImageHolder.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Отправить нн"), 106);
        });

        partyCreate.setOnClickListener(this);
    }

    private long getDateTime() {
        try {
            String string = partyTime.getText().toString();
            DateFormat formatter = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);
            Date date = formatter.parse(string);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void initMap(Bundle savedInstanceState) {
        MapsInitializer.initialize(getApplicationContext());
        partyMap.onCreate(savedInstanceState);
        partyMap.onResume();
        partyMap.getMapAsync(this);
    }

    private void initAuthor() {
        String s = user.first_name + " " + user.second_name;
        partyAuthor.setText(s);
    }

    private void initAddress() {
        partyAdress.setText(getCompleteAddressString(position.latitude, position.longitude));
    }

    private String getCompleteAddressString(double latitude, double longitude) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String address = addresses.get(0).getAddressLine(0);
            if (!address.contains(", Россия,"))
                return "Не доступен";
            String res = address.split(", Россия,")[0];
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "Не доступен";
        }
    }

    private void sendToFirebase(Bitmap bitmapFull, Bitmap bitmapLow25, String key) {

        ByteArrayOutputStream baosFull = new ByteArrayOutputStream();
        bitmapFull.compress(Bitmap.CompressFormat.JPEG, 50, baosFull);
        byte[] dataFull = baosFull.toByteArray();
        ByteArrayOutputStream baosLow = new ByteArrayOutputStream();
        bitmapLow25.compress(Bitmap.CompressFormat.JPEG, 25, baosLow);
        byte[] dataLow = baosLow.toByteArray();

        StorageReference storageRefLow = FirebaseStorage.getInstance().getReference().child("parties").child(key).child(Party.PROFILE_IMAGE + "_low_25");
        uploadPicture(storageRefLow, dataLow, true, key);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("parties").child(key).child(Party.PROFILE_IMAGE);
        uploadPicture(storageRef, dataFull, false, key);

    }

    void uploadPicture(StorageReference storageRef, byte[] data, boolean isFull, String key) {
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            if (isFull) {
                Toast.makeText(this, "Error upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(taskSnapshot -> {
            if (isFull) {
                FirebaseDatabase.getInstance().getReference()
                        .child("images")
                        .child("parties")
                        .child(key)
                        .child(Party.PROFILE_IMAGE)
                        .setValue(true);
                Toast.makeText(this, "Success upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!validates(partyName, partyAbout))
            return;

        if (!validateTime(partyTime))
            return;

        if (!isChangedImage) {
            Toast.makeText(this,"Нужно выбрать фотографию", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> members = new ArrayList<>();
        members.add(user.uid);

        String key = FirebaseDatabase.getInstance().getReference()
                .child("parties")
                .push().getKey();

        FirebaseDatabase.getInstance().getReference().child("user-parties")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(key).setValue(true);
        if (isChangedImage)
            sendToFirebase(((BitmapDrawable) partyImage.getDrawable()).getBitmap()
                    , ((BitmapDrawable) partyImage.getDrawable()).getBitmap(), key);

        Party party = new Party(position.latitude
                , position.longitude
                , partyName.getText().toString()
                , getDateTime()
                , partyAdress.getText().toString()
                , user.first_name + " " + user.second_name
                , user.uid
                , isChangedImage
                , members
                , partyFree.isChecked()
                , partyAbout.getText().toString()
                , key);

        FirebaseDatabase.getInstance().getReference()
                .child("parties")
                .child(key)
                .setValue(party)
                .addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent();
                    Party.putInIntent(intent, party);
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(this, "Ошибка при создании вечеринки", Toast.LENGTH_SHORT).show();
                });
    }

    private boolean validates(TextView... views) {
        boolean result = true;
        for (TextView view : views) {
            result = validate(view, result);
        }
        return result;
    }

    private boolean validateTime(TextView view) {
        if (time < System.currentTimeMillis()) {
            try {
                view.setError(getString(R.string.too_early));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            try {
                view.setError(null);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private boolean validate(TextView view, boolean result) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            try {
                view.setError(getString(R.string.required));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            try {
                view.setError(null);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.addMarker(new MarkerOptions().position(position));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                position
                , 13));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 106 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                partyImage.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                partyImage.setImageBitmap(bitmap);
                isChangedImage = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
