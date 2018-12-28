package com.barracudapff.hoobes.flatter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SETTINGS_COUNT = 5;
    private static final int CONNECT_TYPE = 2;
    private static final int CHECK_TYPE = 3;
    private static final int CONNECT = 0;
    private static final int CHECK = 1;
    private static final int GROUP = 2;

    public static final String APP_PREFERENCES = "flatter";
    public static final String NOTIFICATION_ENABLE = "notification_enable";

    private SharedPreferences mSettings;
    private Activity activity;

    public SettingsAdapter(Activity activity) {
        this.activity = activity;
        mSettings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        switch (type) {
            case CONNECT:
                return new ViewHolderConnect(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_settings_connect, viewGroup, false));
            case CHECK:
                return new ViewHolderCheck(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_settings_check, viewGroup, false));
            case GROUP:
                return new ViewHolderGroup(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_settings_group, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        switch (viewHolder.getItemViewType()) {
            case CONNECT:
                ViewHolderConnect holderConnect = (ViewHolderConnect) viewHolder;
                if (pos == 0) {
                    holderConnect.onBind(R.drawable.ic_instagram
                            , "Instagram"
                            , v -> Toast.makeText(activity, "Not implemented yet", Toast.LENGTH_SHORT).show());
                } else if (pos == 1) {
                    holderConnect.onBind(R.drawable.ic_vk
                            , "Vk"
                            , v -> Toast.makeText(activity, "Not implemented yet", Toast.LENGTH_SHORT).show());
                }
                break;
            case CHECK:
                ViewHolderCheck holderCheck = (ViewHolderCheck) viewHolder;
                switch (pos) {
                    case 2:
                        holderCheck.onBind("Включить уведомления"
                                , NOTIFICATION_ENABLE);
                        break;
                }
                break;
            case GROUP:
                ViewHolderGroup holderGroup = (ViewHolderGroup) viewHolder;
                switch (pos) {
                    case 3:
                        holderGroup.onBind("Конфиденциальные настройки"
                                , v -> Toast.makeText(activity, "Not implemented yet", Toast.LENGTH_SHORT).show());
                        break;
                    case 4:
                        holderGroup.onBind("Настройки профиля"
                                , v -> MainActivity.basicStart(activity, ProfileSettingsActivity.class, 104));
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return SETTINGS_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < CONNECT_TYPE)
            return CONNECT;
        if (position < CHECK_TYPE)
            return CHECK;
        else
            return GROUP;
    }

    interface onSettingsActivityChangeInterface {
        void onSettingsActivityChange();
    }

    class ViewHolderConnect extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;
        TextView connect;

        ViewHolderConnect(@NonNull View v) {
            super(v);
            icon = v.findViewById(R.id.settings_icon);
            text = v.findViewById(R.id.settings_text);
            connect = v.findViewById(R.id.settings_connect);
        }

        void onBind(int iconId, String settingsText, View.OnClickListener listener) {
            icon.setImageResource(iconId);
            text.setText(settingsText);
            connect.setOnClickListener(listener);
        }
    }

    class ViewHolderCheck extends RecyclerView.ViewHolder {
        TextView text;
        CheckBox check;

        ViewHolderCheck(@NonNull View v) {
            super(v);
            text = v.findViewById(R.id.settings_text);
            check = v.findViewById(R.id.settings_check);
        }

        void onBind(String settingsText, String setting) {
            text.setText(settingsText);
            check.setChecked(mSettings.getBoolean(setting, false));
            check.setOnClickListener(v -> {
                mSettings.edit().putBoolean(setting, check.isChecked()).apply();
            });
        }
    }

    public class ViewHolderGroup extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView text;

        ViewHolderGroup(@NonNull View v) {
            super(v);
            text = v.findViewById(R.id.settings_text);
            layout = v.findViewById(R.id.settings_layout);
        }

        void onBind(String settingsText, View.OnClickListener listener) {
            text.setText(settingsText);
            layout.setOnClickListener(listener);
        }
    }
}
