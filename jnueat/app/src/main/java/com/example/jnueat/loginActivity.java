package com.example.jnueat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {
    private EditText ett_id, ett_pass;
    private Button btn_login, btn_register2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ett_id = findViewById(R.id.ett_id);
        ett_pass = findViewById(R.id.ett_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register2 = findViewById(R.id.btn_register2);

        btn_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = ett_id.getText().toString();
                String userPass = ett_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { //로그인 성공
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                Toast.makeText(getApplicationContext(), "로그인에 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);
                                startActivity(intent);
                            } else { //로그인에 실패
                                Toast.makeText(getApplicationContext(), "로그인에 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                queue.add(loginRequest);


            }
        });

    }
}