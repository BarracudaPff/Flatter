package com.barracudapff.hoobes.flatter.fragments.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;

public class SettingsListFragment extends SettingsBaseFragment {
    RecyclerView recyclerView;

    public SettingsListFragment() {
        // Required empty public constructor
    }

    public static SettingsListFragment newInstance() {
        return new SettingsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_list, container, false);
        update();

        recyclerView = view.findViewById(R.id.settings_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolderGroup>() {
            @NonNull
            @Override
            public ViewHolderGroup onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolderGroup(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_settings_group, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolderGroup viewHolderGroup, int pos) {
                switch (pos) {
                    case 0:
                        viewHolderGroup.onBind("Имя и фамилия"
                                , v -> ((ProfileSettingsActivity) getActivity()).changePage(1));
                        break;
                    case 1:
                        viewHolderGroup.onBind("Пароль"
                                , v -> ((ProfileSettingsActivity) getActivity()).changePage(2));
                        break;
                    case 2:
                        viewHolderGroup.onBind("Возвраст"
                                , v -> ((ProfileSettingsActivity) getActivity()).changePage(3));
                        break;
                    case 3:
                        viewHolderGroup.onBind("О себе"
                                , v -> ((ProfileSettingsActivity) getActivity()).changePage(4));
                        break;
                    case 4:
                        viewHolderGroup.onBind("Фотографии"
                                , v -> ((ProfileSettingsActivity) getActivity()).changePage(5));
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
        return view;
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

    @Override
    public void update() {
        Toolbar bar = ((ProfileSettingsActivity) getActivity()).getBar();
        if (bar != null) {
            bar.setTitle("Настройки пользователя");
            bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
            bar.setNavigationOnClickListener(v -> getActivity().finish());
        }
    }
}
