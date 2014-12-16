package com.niti.android.fotobesa.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.niti.android.fotobesa.R;
import com.niti.android.fotobesa.Utils.SharePreferences;
import com.niti.android.fotobesa.provider.FragmentTags;
import com.niti.android.fotobesa.ui.fragments.CustomizeFragment;
import com.niti.android.fotobesa.ui.fragments.FotoBesaFragment;


public class MainActivity extends ActionBarActivity implements CustomizeFragment.OnCustomizeListener {

    private boolean isOpenActivitiesActivated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            manageFragment(FotoBesaFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main, menu);
        MenuItem openActivities = menu.findItem(R.id.action_open_activities);
        openActivities.setChecked(isOpenActivitiesActivated);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_activities:
                onOpenActivitiesClick(item);
                break;
            case R.id.action_reset:
                resetLayout();
                break;
            case R.id.action_customize:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                break;
            case R.id.action_about:
                startActivityWith(AboutActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityWith(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void manageFragment(Fragment newInstanceFragment, FragmentTags tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment currentIntanceFragment = findFragmentByTag(tag);
        if (currentIntanceFragment == null || (currentIntanceFragment != null && currentIntanceFragment.isHidden())) {
            if (currentIntanceFragment != null) {
                ft.show(currentIntanceFragment);
            } else {
                currentIntanceFragment = newInstanceFragment;
                ft.add(R.id.container, currentIntanceFragment, tag.toString());
                if (addToBackStack) {
                    ft.addToBackStack(null);
                }
            }
        } else {
            ft.hide(currentIntanceFragment);
            fm.popBackStack();
        }
        ft.commit();
    }

    private Fragment findFragmentByTag(FragmentTags tag) {
        return getSupportFragmentManager().findFragmentByTag(tag.toString());
    }

    @Override
    public void setSpeed(int value) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setSpeed(value);
        }
    }

    @Override
    public void setGap(int value) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGap(value);
        }
    }

    @Override
    public void setGapColor(int color) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGapColor(color);
        }
    }

    @Override
    public void setDivider(Drawable drawable) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDivider(drawable);
        }
    }

    @Override
    public void setDividerHeight(int value) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDividerHeight(value);
        }
    }

    @Override
    public void setAutoScrollFaster(int option) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setAutoScrollFaster(option);
        }
    }

    @Override
    public void setScrollFaster(int option) {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setScrollFaster(option);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void resetLayout() {
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.resetLayout();
            reset();
            CustomizeFragment customizeFragment = (CustomizeFragment) findFragmentByTag(FragmentTags.CUSTOMIZE);
            if (customizeFragment != null) {
                customizeFragment.reset();
            }
        }
    }

    private void reset() {
        SharePreferences.reset();
    }

    public boolean onOpenActivitiesClick(MenuItem menuItem) {
        isOpenActivitiesActivated = !menuItem.isChecked();
        menuItem.setChecked(isOpenActivitiesActivated);
        FotoBesaFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setOpenActivities(isOpenActivitiesActivated);
        }

        return false;
    }

    private FotoBesaFragment getListBuddiesFragment() {
        return (FotoBesaFragment) findFragmentByTag(FragmentTags.LIST_BUDDIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
