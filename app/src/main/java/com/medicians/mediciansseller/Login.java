package com.medicians.mediciansseller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText username,password;
    String name,pass;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    public static final String PREF="mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);


        preferences=getSharedPreferences(PREF,MODE_PRIVATE);
        editor=preferences.edit();

        if(preferences.getString("username",null)!=null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }


    }

    public void login(View view){

        name=username.getText().toString();
        pass=password.getText().toString();


        checkpass();

    }

    private void checkpass( ){


        String url="http://medicians.herokuapp.com/sellerlogin/a";
        RequestQueue queue= Volley.newRequestQueue(Login.this);
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject;
                        try{
                            jsonObject=new JSONObject(response);
                            String correctPass=jsonObject.getString("password");
                            String seller_id=jsonObject.getString("sellerid");

                            if(check(correctPass)){
                                editor.putString("username",name);
                                editor.putString("sellerid",seller_id);
                                editor.commit();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            }
                            else
                                Toast.makeText(Login.this,"Wrong password",Toast.LENGTH_LONG).show();
                        }catch (JSONException e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,"Error;:"+error.toString(),Toast.LENGTH_LONG).show();

                    }
                });


        queue.add(request);
    }

    private boolean check(String checkpass){
        if(pass.compareTo(checkpass)==0)
            return true;
        return  false;
    }

}
