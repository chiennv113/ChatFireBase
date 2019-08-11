package com.example.chatfire;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatfire.Model.Model_User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText mEdtUsername;
    private TextInputEditText mEdtPassWord;
    private Button mBtnSignIn;
    private Button mButton2;
    private TextView mTvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mTvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpAct.class));
            }
        });

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = mEdtUsername.getText().toString().trim();
                final String passWord = mEdtPassWord.getText().toString().trim();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference users = firebaseDatabase.getReference("Users").child(userName);

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(MainActivity.this, "Sai tai khoan", Toast.LENGTH_SHORT).show();
                        } else {
                            Model_User user = dataSnapshot.getValue(Model_User.class);

                            if (user.passWord.equals(passWord)){
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Sai mat khau", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void initView() {
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassWord = findViewById(R.id.edtPassWord);
        mBtnSignIn = findViewById(R.id.btnSignIn);
        mButton2 = findViewById(R.id.button2);
        mTvSignUp = findViewById(R.id.tvSignUp);
    }
}
