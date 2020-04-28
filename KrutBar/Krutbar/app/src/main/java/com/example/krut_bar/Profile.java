package com.example.krut_bar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class Profile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView name;
    private TextView points;
    private TextView ref;
    private String id;
    private Button btm;
    private ImageView copy;
    private ImageView share;
    private ImageView FAQ;
    private ImageView change;
    private ImageView progress;
    private FrameLayout background;
    Animation rotate = null;
    private TextView code;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    Profile(String id) {
        this.id = id;
    }

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, null);
        name = v.findViewById(R.id.name);
        points = v.findViewById(R.id.points);
        ref = v.findViewById(R.id.ref);
        btm = v.findViewById(R.id.btn_code);
        copy = v.findViewById(R.id.copy);
        share = v.findViewById(R.id.share);
        FAQ = v.findViewById(R.id.faq);
        change = v.findViewById(R.id.change);
        code = v.findViewById(R.id.code);
        background = v.findViewById(R.id.background);
        progress = v.findViewById(R.id.progress);
        mSwipeRefreshLayout = v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.black,
                R.color.orange,
                android.R.color.black,
                R.color.orange);
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = generate_code();
                HashMap<String, Object> map = new HashMap<>();
                map.put(c, id);
                root.child("code").updateChildren(map);
                code.setText(c);
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("TAG", id);
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(getActivity(), "Скопировано в буфер обмена", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Использлй мою ссылку " + id + " и получи 25 баллов";
                String shareSub = "Зарегестрируйся!";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Что использовать?"));
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameName();
            }
        });
        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFAQ();
            }
        });
        read(true);

        return v;
    }

    private String generate_code() {
        StringBuilder code = new StringBuilder(String.valueOf(new Random().nextInt(10000)));
        while (code.length() < 4) {
            code.insert(0, '0');
        }
        return code.toString();
    }

    private void read(final boolean flag) {
        if (flag) {
            rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            btm.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
            background.setVisibility(View.VISIBLE);
            progress.setAnimation(rotate);
            rotate.setRepeatCount(100);
        }
        root.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                HashMap<String, Object> map = dataSnapshot.getValue(t);
                assert map != null;
                name.setText(map.get("name").toString());
                points.setText("Мои баллы : " + map.get("points").toString());
                ref.setText("Промокод : " + id);
                if (flag) {
                    btm.setVisibility(View.VISIBLE);
                    rotate.setRepeatCount(0);
                    progress.setVisibility(View.INVISIBLE);
                    background.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getPoints(int point) {
        int a = point % 10;
        if (a == 0 || a >= 6) {
            return "баллов";
        }
        if (a == 1) {
            return "балл";
        } else {
            return "балла";
        }
    }

    private void renameName() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getContext());
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setText(name.getText());
        quitDialog.setView(input);
        quitDialog.setTitle("Вы хотите изменить имя?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                root.child("users").child(id).child("name").setValue(input.getText().toString());
                read(false);
                dialog.cancel();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        quitDialog.show();
    }

    private void setFAQ() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getContext());
        quitDialog.setTitle("Правила");
        quitDialog.setMessage("Делись своим кодом с друзьями, если они зарегестрируются по твоей ссылке и сделают первый заказ, ты и твой друг получат по 50 бонусных баллов!");

        quitDialog.setNegativeButton("Понял", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        quitDialog.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                read(false);
            }
        }, 4000);
    }
}
