package com.app.mylibertarestaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.app.mylibertarestaurant.R;
import com.app.mylibertarestaurant.databinding.ActivityItemDescriptionBinding;

import java.util.ArrayList;

public class ItemDescriptionActivity extends AppCompatActivity {
    ActivityItemDescriptionBinding binder;
    PopupMenu popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_item_description);
        binder.setClick(new Click());
        initMenu();
    }

    void initMenu() {
        popup = new PopupMenu(ItemDescriptionActivity.this, binder.ivThreeDot);
        popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.edit:
                        startActivity(new Intent(ItemDescriptionActivity.this, EditItemActivity.class));
                        break;
                    case R.id.copy:
                        break;
                    case R.id.delete:
                        break;
                }
                return false;
            }
        });
    }

    public class Click {
        public void onBack(View v) {
            finish();

        }

        public void onPopupClick(View v) {
            popup.show();
        }
    }
}
