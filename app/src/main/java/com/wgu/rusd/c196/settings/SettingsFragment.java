package com.wgu.rusd.c196.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.wgu.rusd.c196.R;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}