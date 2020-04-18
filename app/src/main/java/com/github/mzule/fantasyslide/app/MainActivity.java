package com.github.mzule.fantasyslide.app;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.SimpleFantasyListener;
import com.github.mzule.fantasyslide.Transformer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HEY";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.Text123);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + view.getId());
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(indicator);

        setListener();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });
    }

    private void setListener() {
        SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
        leftSideBar.setFantasyListener(new SimpleFantasyListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                if (view == null)
                    return false;
                Log.d(TAG, "onHover: " + view.getId());
                return false;
            }

            @Override
            public boolean onSelect(View view, int index) {


                switch (view.getId()) {
                    case R.id.Text123:
                        Intent intent = new Intent(view.getContext(), Activity2.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void setTransformer() {
        final float spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        SideBar rightSideBar = (SideBar) findViewById(R.id.rightSideBar);
        rightSideBar.setTransformer(new Transformer() {
            private View lastHoverView;

            @Override
            public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
                boolean hovered = itemView.isPressed();
                if (hovered && lastHoverView != itemView) {
                    animateIn(itemView);
                    animateOut(lastHoverView);
                    lastHoverView = itemView;
                }
            }

            private void animateOut(View view) {
                if (view == null) {
                    return;
                }
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -spacing, 0);
                translationX.setDuration(200);
                translationX.start();
            }

            private void animateIn(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -spacing);
                translationX.setDuration(200);
                translationX.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

//    public void function(View v) {
//        Log.d(TAG, "fun: Function");
//    }

//    public void onClick(View view) {
//        if (view instanceof TextView) {
//            String title = ((TextView) view).getText().toString();
//            if (title.startsWith("星期")) {
//                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
//            } else {
//                startActivity(UniversalActivity.newIntent(this, title));
//            }
//        } else if (view.getId() == R.id.userInfo) {
//            startActivity(UniversalActivity.newIntent(this, "个人中心"));
//        }
//    }
}
