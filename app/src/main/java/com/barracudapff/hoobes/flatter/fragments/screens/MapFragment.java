package com.barracudapff.hoobes.flatter.fragments.screens;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.party.CreatePartyActivity;
import com.barracudapff.hoobes.flatter.database.models.Party;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    protected MapView mapView;
    protected GoogleMap mMap;
    protected Geocoder geocoder;
    protected BottomSheetBehavior sheetBehavior;

    protected ArrayList<Party> parties = new ArrayList<>();

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                moveCamera(location);
                mLocationManager.removeUpdates(mLocationListener);
            } else {
                System.out.println("Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private LocationManager mLocationManager;
    private BottomViewHolder bottmViewHolder;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        MapsInitializer.initialize(getActivity().getApplicationContext());
        geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        mapView = view.findViewById(R.id.mapView);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        bottmViewHolder = new BottomViewHolder(view);

        ConstraintLayout layoutBottomSheet = view.findViewById(R.id.bottom_sheet);
        layoutBottomSheet.setOnClickListener(v -> {

        });
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Loading all markers
        loadMarkers();

        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        getCurrentLocation();

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled))
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        else {
            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            moveCamera(location);
        }
    }

    private void moveCamera(Location location) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude())
                    , 12));
        }

    }

    private static MarkerOptions toMarkerOptions(Party marker) {
        return new MarkerOptions().position(new LatLng(marker.latitude, marker.longitude)).title(marker.name);
    }

    private void loadMarkers() {
        Query recentPostsQuery = FirebaseDatabase.getInstance().getReference().child("parties");
        recentPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Party party = singleSnapshot.getValue(Party.class);
                    parties.add(party);
                    mMap.addMarker(toMarkerOptions(party));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "DB error",
                        Toast.LENGTH_SHORT).show();
            }
        });

        for (Party marker : parties) {
            mMap.addMarker(toMarkerOptions(marker));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentLocation();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);
        mBuilder.setCancelable(true)
                .setTitle("Создать новою вечеринку")
                .setNegativeButton(R.string.cancel,
                        (dialog, which) -> {
                        });
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            mBuilder.setPositiveButton(R.string.add,
                    (dialog, which) -> {
                        Toast.makeText(getContext(), "Нужно авторизоваться, чтобы создать свою вечеринку", Toast.LENGTH_SHORT).show();
                    });
        else
            mBuilder.setPositiveButton(R.string.add,
                    (dialog, which) -> {
                        LatLng touchPosition = new LatLng(latLng.latitude, latLng.longitude);
                        Intent intent = new Intent(getActivity(), CreatePartyActivity.class);
                        intent.putExtra("POSITION_LAT", touchPosition.latitude);
                        intent.putExtra("POSITION_LON", touchPosition.longitude);
                        startActivityForResult(intent, 105);
                    });

        mBuilder.create().show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.println(marker.getTitle());
        for (Party party : parties) {
            if (party.name.equals(marker.getTitle())) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottmViewHolder.onBindView(getContext(), party);
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //btnBottomSheet.setText("Expand sheet");
                }
                return false;
            }
        }
        return false;
    }

    public void addMarker(Party party) {
        parties.add(party);
        System.out.println(party);
        mMap.addMarker(toMarkerOptions(party));
    }

    class BottomViewHolder {
        private TextView name;
        private TextView freeEntry;
        private TextView date;
        private TextView address;
        private TextView author;
        private ImageView profile;
        private ImageView authProfileImage;
        private ImageView member1ProfileImage;
        private ImageView member2ProfileImage;
        private ImageView member3ProfileImage;
        private ImageView memberMoreProfileImage;
        private TextView memberMoreProfile;
        private ConstraintLayout contentLayout;

        public BottomViewHolder(View view) {
            this.name = view.findViewById(R.id.party_name);
            this.freeEntry = view.findViewById(R.id.party_free_entry);
            this.date = view.findViewById(R.id.party_date);
            this.address = view.findViewById(R.id.party_address);
            this.author = view.findViewById(R.id.party_author);
            this.profile = view.findViewById(R.id.party_profile);
            this.authProfileImage = view.findViewById(R.id.party_author_image);
            this.member1ProfileImage = view.findViewById(R.id.party_member_1);
            this.member2ProfileImage = view.findViewById(R.id.party_member_2);
            this.member3ProfileImage = view.findViewById(R.id.party_member_3);
            this.memberMoreProfileImage = view.findViewById(R.id.imageMore);
            this.memberMoreProfile = view.findViewById(R.id.party_member_more);
            this.contentLayout = view.findViewById(R.id.content);
        }

        public void onBindView(Context context, Party party) {
            name.setText(party.name);
            if (party.isFree) {
                freeEntry.setText("Свободный вход");
                freeEntry.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else {
                freeEntry.setText("Закрытый вход");
                freeEntry.setTextColor(ContextCompat.getColor(context, R.color.common_google_signin_btn_text_light_default));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", getResources().getConfiguration().locale);
            date.setText(sdf.format(party.dateTime));
            address.setText(party.address);
            author.setText(party.author);
            int count = party.members.size();
            if (count < 5)
                switch (count) {
                    case 1:
                        member1ProfileImage.setVisibility(View.INVISIBLE);
                    case 2:
                        member2ProfileImage.setVisibility(View.INVISIBLE);
                    case 3:
                        member3ProfileImage.setVisibility(View.INVISIBLE);
                    case 4:
                        memberMoreProfileImage.setVisibility(View.INVISIBLE);
                        memberMoreProfile.setVisibility(View.INVISIBLE);
                }
            else {
                memberMoreProfileImage.setVisibility(View.VISIBLE);
                memberMoreProfile.setVisibility(View.VISIBLE);
                member3ProfileImage.setVisibility(View.VISIBLE);
                member2ProfileImage.setVisibility(View.VISIBLE);
                member1ProfileImage.setVisibility(View.VISIBLE);
                String s = "+" + (count - 4);
                memberMoreProfile.setText(s);
            }
            if (!party.profile_image)
                profile.setVisibility(View.GONE);
            else
                loadImage(party.uid, Party.PROFILE_IMAGE, profile);
            contentLayout.setOnClickListener(v -> {
                Toast.makeText(context, "Open party", Toast.LENGTH_SHORT).show();
            });
        }

        private void loadImage(String uid, String name, ImageView imageView) {
            FirebaseStorage.getInstance().getReference()
                    //.child("images")
                    .child("parties")
                    .child(uid)
                    //.child(Party.PROFILE_IMAGE)
                    .child(name)
                    .getDownloadUrl()
                    .addOnCompleteListener(task -> {
                        Picasso.get().load(task.getResult())
                                //.placeholder(R.color.lightGray)
                                .error(R.color.lightGray)
                                .into(imageView);
                    });
        }
    }
}