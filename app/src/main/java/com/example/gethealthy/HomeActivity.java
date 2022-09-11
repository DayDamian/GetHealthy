package com.example.gethealthy;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.gethealthy.fragment.DishesFragment;
import com.example.gethealthy.fragment.HealthyFragment;
import com.example.gethealthy.fragment.HomeFragment;
import com.example.gethealthy.fragment.ProductFragment;
import com.example.gethealthy.fragment.WaterFragment;
import com.example.gethealthy.menu.DrawerAdapter;
import com.example.gethealthy.menu.DrawerItem;
import com.example.gethealthy.menu.SimpleItem;
import com.example.gethealthy.menu.SpaceItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    private static final int POS_HOME = 0;
    private static final int POS_HEALTHY = 1;
    private static final int POS_PRODUCTS = 2;
    private static final int POS_DISHES = 3;
    private static final int POS_WATER = 4;
    private static final int POS_NIGHTMODE = 5;
    private static final int POS_SETTINGS = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_HEALTHY),
                createItemFor(POS_PRODUCTS),
                createItemFor(POS_DISHES),
                createItemFor(POS_WATER),
                createItemFor(POS_NIGHTMODE),
                new SpaceItem(250),
                createItemFor(POS_SETTINGS)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    @Override
    public void onItemSelected(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == POS_HOME){
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.container, homeFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        } else if (position == POS_HEALTHY){
            HealthyFragment healthyFragment = new HealthyFragment();
            transaction.replace(R.id.container, healthyFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HealthyFragment()).commit();
        }else if (position == POS_PRODUCTS){
            ProductFragment productFragment = new ProductFragment();
            transaction.replace(R.id.container, productFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProductFragment()).commit();
        }else if (position == POS_DISHES){
            DishesFragment dishesFragment = new DishesFragment();
            transaction.replace(R.id.container, dishesFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new DishesFragment()).commit();
        }else if (position == POS_WATER){
            WaterFragment waterFragment = new WaterFragment();
            transaction.replace(R.id.container, waterFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new WaterFragment()).commit();
        }else if (position == POS_SETTINGS){
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        }


        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.style_secondary))
                .withTextTint(color(R.color.style_secondary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

}
