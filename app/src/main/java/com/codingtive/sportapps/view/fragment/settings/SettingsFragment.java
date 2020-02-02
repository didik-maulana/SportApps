package com.codingtive.sportapps.view.fragment.settings;

import android.os.Bundle;
import android.widget.Toast;

import com.codingtive.sportapps.R;
import com.codingtive.sportapps.reminder.DailyReminderReceiver;
import com.codingtive.sportapps.reminder.ReleaseReminderReceiver;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
    private static final String KEY_DAILY_REMINDER = "key_daily_reminder";
    private static final String KEY_RELEASE_REMINDER = "key_release_reminder";
    private DailyReminderReceiver dailyReminderReceiver;
    private ReleaseReminderReceiver releaseReminderReceiver;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReminderReceiver();
        setupSwitchPreference();
    }

    private void initReminderReceiver() {
        dailyReminderReceiver = new DailyReminderReceiver();
        releaseReminderReceiver = new ReleaseReminderReceiver();
    }

    private void setupSwitchPreference() {
        SwitchPreferenceCompat switchDailyReminder = findPreference(KEY_DAILY_REMINDER);
        SwitchPreferenceCompat switchReleaseReminder = findPreference(KEY_RELEASE_REMINDER);

        if (switchDailyReminder != null && switchReleaseReminder != null) {
            switchDailyReminder.setOnPreferenceChangeListener(this);
            switchReleaseReminder.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object object) {
        String prefKey = preference.getKey();
        boolean isActive = (boolean) object;

        if (prefKey.equals(KEY_DAILY_REMINDER) && getContext() != null) {
            if (isActive) {
                showMessage("Daily reminder enabled");
                dailyReminderReceiver.setDailyReminder(getContext());
            } else {
                showMessage("Daily reminder disabled");
                dailyReminderReceiver.cancelReminder(getContext());
            }
        } else if (prefKey.equals(KEY_RELEASE_REMINDER) && getContext() != null) {
            if (isActive) {
                showMessage("Release reminder enabled");
                releaseReminderReceiver.setReleaseReminder(getContext());
            } else {
                showMessage("Release reminder disabled");
                releaseReminderReceiver.cancelReminder(getContext());
            }
        }
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
