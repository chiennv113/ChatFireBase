package com.example.chatfire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatfire.Model.Model_User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpAct extends AppCompatActivity {

    private TextInputEditText mEdtFirstName;
    private TextInputEditText mEdtLastName;
    private TextInputEditText mEdtUser;
    private TextInputEditText mEdtPass;
    private Button mBtnSignUp;
    private Model_User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname = mEdtFirstName.getText().toString().trim();
                final String lastname = mEdtLastName.getText().toString().trim();
                final String user = mEdtUser.getText().toString().trim();
                final String pass = mEdtPass.getText().toString().trim();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference users = database.getReference("Users").child(user);

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue()==null){
                            Model_User model_user = new Model_User();
                            model_user.firstName = firstname;
                            model_user.lastName = lastname;
                            model_user.passWord = pass;

                            users.setValue(model_user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    Toast.makeText(SignUpAct.this, "Sign Up thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(SignUpAct.this, "UserName da ton tai, vui long chon username khac", Toast.LENGTH_SHORT).show();
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
        mEdtFirstName = findViewById(R.id.edtFirstName);
        mEdtLastName = findViewById(R.id.edtLastName);
        mEdtUser = findViewById(R.id.edtUser);
        mEdtPass = findViewById(R.id.edtPass);
        mBtnSignUp = findViewById(R.id.btnSignUp);
    }
}
