package com.example.dingshuo_yang_myruns3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class FragmentSettings : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)

        val profilePreference: Preference? = findPreference("user_profile")

        profilePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
            true
        }
        val webpagePreference: Preference? = findPreference("webpage")
        webpagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val uri = Uri.parse("http://www.google.com") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            true

        }
    }
}
