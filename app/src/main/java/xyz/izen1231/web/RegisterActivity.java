package xyz.izen1231.web;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private String userID;
    private String userPassword;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        final EditText idText = (EditText)findViewById(R.id.edit1);
        final EditText passwordText = (EditText)findViewById(R.id.edit2);
        final EditText emailText = (EditText)findViewById(R.id.edit5);




        final Button validateButton = (Button)findViewById(R.id.phone);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate){
                    return;
                }

                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("ID is empty")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                //검증시작
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("you can use ID")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                                validate = true;//검증완료

                            }else{//사용할 수 없는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("alreay used ID")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };


                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button registerButton = (Button)findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                String userEmail = emailText.getText().toString();


                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("First Check ID plz")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                if(userID.equals("")||userPassword.equals("")||userEmail.equals(""){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("Empty text exist")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){//사용할 수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("Register Your ID")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                finish();//액티비티를 종료시킴(회원등록 창을 닫음)
                            }else{//사용할 수 없는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("Register fail")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword,userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}


