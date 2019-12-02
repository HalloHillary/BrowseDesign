package com.cch.mobileshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.cch.mobileshop.R;
import com.cch.mobileshop.bean.LoginResponse;
import com.cch.mobileshop.bean.LoginResponse2;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_username)  EditText et_username;
    @BindView(R.id.et_email)  EditText et_email;
    @BindView(R.id.et_pwd)  EditText et_pwd;
    @BindView(R.id.et_pwd2)  EditText et_pwd2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.bt_register)
    void register(){
       String username= et_username.getText().toString();
       if (TextUtils.isEmpty(username)){
           Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
           return;
       }
       String email= et_username.getText().toString();
       if (TextUtils.isEmpty(email)){
           Toast.makeText(this,"请输入邮箱",Toast.LENGTH_SHORT).show();
           return;
       }
       String pwd= et_username.getText().toString();
       if (TextUtils.isEmpty(pwd)){
           Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
           return;
       }
       String pwd2= et_username.getText().toString();
       if (TextUtils.isEmpty(pwd2)){
           Toast.makeText(this,"请确认密码",Toast.LENGTH_SHORT).show();
           return;
       }
       if (!pwd.equals(pwd2)){
           Toast.makeText(this,"输入密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
           return;
       }
        OkHttpUtils.post().url("http://10.10.16.23:8080/MobileShop/member")
                .addParams("username",username).addParams("password",pwd).addParams("email",email)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(RegisterActivity.this,"注册失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                       LoginResponse2 loginResponse2= gson.fromJson(response, LoginResponse2.class);
                       if (loginResponse2!=null&&loginResponse2.getStatus()==1){
                           Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                           finish();
                       }
                       else {
                           Toast.makeText(RegisterActivity.this,"注册失败:"+loginResponse2.getMsg(),Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }
}
