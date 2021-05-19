package com.example.customworkouts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;
import com.example.customworkouts.Data;
import com.example.customworkouts.adapters.ProfileRecyclerViewAdapter;

public class ProfileFragment extends Fragment {


    private Context context = getContext();
    private ProfileRecyclerViewAdapter profileAdapter = new ProfileRecyclerViewAdapter();
    private Data profileData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View profilesPageView = inflater.inflate(R.layout.profile_container_fragment, container, false);

        profileData = Data.getInstance(context);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView profilesRecyclerView = profilesPageView.findViewById(R.id.profilesRecyclerView);

        profilesRecyclerView.setLayoutManager(linearLayoutManager);
        profileAdapter.setWorkouts(profileData.getProfiles());
        profileAdapter.setProfileData(profileData);

        profilesRecyclerView.setAdapter(profileAdapter);




        return profilesPageView;
    }
}
