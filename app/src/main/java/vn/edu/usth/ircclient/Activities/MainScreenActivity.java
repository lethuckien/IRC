package vn.edu.usth.ircclient.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import vn.edu.usth.ircclient.Adapter.ChatFragmentAdapter;
import vn.edu.usth.ircclient.Fragments.ChatFragment;
import vn.edu.usth.ircclient.R;

public class MainScreenActivity extends AppCompatActivity {
    private ChatFragmentAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String nickname = "AndroidUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        setIDs();
        createFirstPage();
    }

    private void createFirstPage() {
        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        addPage(title);
    }

    private void setIDs() {
        adapter = new ChatFragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.chat_pager);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.server_tab);
        viewPager.setOffscreenPageLimit(10);
    }

    private void addPage(String title) {
        ChatFragment chatFragment = new ChatFragment();
        adapter.addFragment(chatFragment, title);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0) {
            tabLayout.setupWithViewPager(viewPager);
        }
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    private void removePage() {
        int position = viewPager.getCurrentItem();
        adapter.removeFragment(position);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    private void addServer() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_add_server);

        TextView freenode = (TextView) dialog.findViewById(R.id.freenode_popup);
        TextView epiknet = (TextView) dialog.findViewById(R.id.epiknet_popup);
        TextView quakenet = (TextView) dialog.findViewById(R.id.quakenet_popup);
        TextView undernet = (TextView) dialog.findViewById(R.id.undernet_popup);
        TextView kottnet = (TextView) dialog.findViewById(R.id.kottnet_popup);

        freenode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPage("freenode");
                dialog.dismiss();
            }
        });

        epiknet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPage("EpiKnet");
                dialog.dismiss();
            }
        });

        quakenet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPage("QuakeNet");
                dialog.dismiss();
            }
        });

        undernet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPage("UnderNet");
                dialog.dismiss();
            }
        });

        kottnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPage("KottNet");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addChannel() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_join_channel);

        TextView cancel = (TextView) dialog.findViewById(R.id.cancel_join_channel);
        TextView join = (TextView) dialog.findViewById(R.id.join_channel);
        EditText editChannel = (EditText) dialog.findViewById(R.id.input_channel);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String channelTitle = editChannel.getText().toString();
                if (channelTitle.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a channel", Toast.LENGTH_SHORT).show();
                } else {
                    addPage(channelTitle);
                    dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeNickname() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_change_nickname);

        TextView cancel = (TextView) dialog.findViewById(R.id.cancel_change_nickname);
        TextView change = (TextView) dialog.findViewById(R.id.change_nickname);
        EditText editNickname = (EditText) dialog.findViewById(R.id.input_nickname);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicknameTemp = editNickname.getText().toString();
                if (nicknameTemp.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
                } else {
                    nickname = nicknameTemp;
                    dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting: {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.about: {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.exit: {
                this.finishAffinity();
                System.exit(0);
                return true;
            }

            case R.id.add_server: {
                    addServer();
                return true;
            }

            case R.id.nickname: {
                changeNickname();
                return true;
            }

            case R.id.channel: {
                addChannel();
                return true;
            }

            case R.id.leave: {
                removePage();
                return true;
            }

            default: {
                super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}