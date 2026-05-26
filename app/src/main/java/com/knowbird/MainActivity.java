package com.knowbird;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.knowbird.fragment.GuideFragment;
import com.knowbird.fragment.RecognizeFragment;
import com.knowbird.settings.SettingsFragment;
import com.knowbird.settings.achievement.AchievementFragment;

public class MainActivity extends BaseActivity implements SettingsFragment.OnSettingsItemClickListener {

    private LinearLayout navRecognize, navList, navGuide, navSettings;
    private ImageView ivRecognize, ivList, ivGuide, ivSettingsNav;
    private TextView tvRecognize, tvList, tvGuide, tvSettings;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private RecognizeFragment recognizeFragment;
    private AchievementFragment achievementFragment;
    private GuideFragment guideFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected View getRootView() {
        return getWindow().getDecorView().getRootView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        initView();
        initFragments();
        showFragment(0);
        updateNavigationSelection(0);
    }

    private void initView() {
        navRecognize = findViewById(R.id.nav_recognize);
        navList = findViewById(R.id.nav_list);
        navGuide = findViewById(R.id.nav_guide);
        navSettings = findViewById(R.id.nav_settings);
        ivRecognize = findViewById(R.id.iv_recognize);
        ivList = findViewById(R.id.iv_list);
        ivGuide = findViewById(R.id.iv_guide);
        ivSettingsNav = findViewById(R.id.iv_settings_nav);
        tvRecognize = findViewById(R.id.tv_recognize);
        tvList = findViewById(R.id.tv_list);
        tvGuide = findViewById(R.id.tv_guide);
        tvSettings = findViewById(R.id.tv_settings);

        navRecognize.setOnClickListener(v -> {
            showFragment(0);
            updateNavigationSelection(0);
        });

        navList.setOnClickListener(v -> {
            showFragment(1);
            updateNavigationSelection(1);
        });

        navGuide.setOnClickListener(v -> {
            showFragment(2);
            updateNavigationSelection(2);
        });

        navSettings.setOnClickListener(v -> {
            showFragment(3);
            updateNavigationSelection(3);
        });
    }

    private void initFragments() {
        recognizeFragment = new RecognizeFragment();
        achievementFragment = new AchievementFragment();
        guideFragment = new GuideFragment();
        settingsFragment = new SettingsFragment();
        settingsFragment.setOnSettingsItemClickListener(this);

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, recognizeFragment, "recognize")
                .add(R.id.fragment_container, achievementFragment, "achievement")
                .hide(achievementFragment)
                .add(R.id.fragment_container, guideFragment, "guide")
                .hide(guideFragment)
                .add(R.id.fragment_container, settingsFragment, "settings")
                .hide(settingsFragment)
                .commit();
    }

    private void showFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        switch (index) {
            case 0:
                currentFragment = recognizeFragment;
                break;
            case 1:
                currentFragment = achievementFragment;
                break;
            case 2:
                currentFragment = guideFragment;
                break;
            case 3:
                currentFragment = settingsFragment;
                break;
        }

        if (currentFragment != null) {
            transaction.show(currentFragment);
        }

        transaction.commit();
    }

    private void updateNavigationSelection(int selectedIndex) {
        ivRecognize.setImageResource(R.drawable.ic_nav_recognize);
        tvRecognize.setTextColor(getResources().getColor(R.color.grey));
        ivList.setImageResource(R.drawable.ic_nav_list);
        tvList.setTextColor(getResources().getColor(R.color.grey));
        ivGuide.setImageResource(R.drawable.ic_nav_guide);
        tvGuide.setTextColor(getResources().getColor(R.color.grey));
        ivSettingsNav.setImageResource(R.drawable.ic_nav_settings);
        tvSettings.setTextColor(getResources().getColor(R.color.grey));

        switch (selectedIndex) {
            case 0:
                ivRecognize.setImageResource(R.drawable.ic_nav_recognize_selected);
                tvRecognize.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                ivList.setImageResource(R.drawable.ic_nav_list_selected);
                tvList.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                ivGuide.setImageResource(R.drawable.ic_nav_guide_selected);
                tvGuide.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                ivSettingsNav.setImageResource(R.drawable.ic_nav_settings_selected);
                tvSettings.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    @Override
    public void onAchievementClicked() {
        showFragment(1);
        updateNavigationSelection(1);
    }
}
