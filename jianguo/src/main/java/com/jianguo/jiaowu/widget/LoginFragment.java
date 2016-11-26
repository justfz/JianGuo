package com.jianguo.jiaowu.widget;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jianguo.Cookie.MyApplication;
import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.Main.view.MainView;
import com.jianguo.Main.widget.MainActivity;
import com.jianguo.R;
import com.jianguo.jiaowu.presenter.LoginPresenter;
import com.jianguo.jiaowu.presenter.LoginPresenterImpl;
import com.jianguo.jiaowu.view.LoginView;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public class LoginFragment extends Fragment implements LoginView {

    private EditText edit_student_id;
    private EditText edit_student_pass;
    private EditText edit_checkcode;
    private ImageView image_checkcode;
    private Button bt_login;
    private LoginPresenter mloginPresenter;
    private Handler mHandler = new Handler();
    private NavigationView navigationView;
    private TextView login_help_id;
    private TextView login_help_pass;
    private TextView student_name;
    private TextView student_id;
    private ProgressDialog progressDialog;
    private MainActivity mainView;
    @SuppressLint({"NewApi", "ValidFragment"})
    public LoginFragment(NavigationView navigationView, MainActivity mainView) {
        this.navigationView = navigationView;
        this.mainView=mainView;
    }

    public LoginFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mloginPresenter = new LoginPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        edit_student_id = (EditText) view.findViewById(R.id.edit_number_id);
        edit_student_pass = (EditText) view.findViewById(R.id.edit_pass_id);
        edit_checkcode = (EditText) view.findViewById(R.id.edit_checkcode);
        image_checkcode = (ImageView) view.findViewById(R.id.image_checkcode);
        bt_login = (Button) view.findViewById(R.id.bt_login);
        login_help_id = (TextView) view.findViewById(R.id.login_help_id);
        login_help_pass = (TextView) view.findViewById(R.id.login_help_pass);
        student_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        student_id = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_id);
        OKHttpUtils.clearCookie();
        mloginPresenter.loadParameterView();
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mloginPresenter.loginevent(
                                edit_student_id.getText().toString(),
                                edit_student_pass.getText().toString(),
                                edit_checkcode.getText().toString());
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("正在登录...");
                        progressDialog.show();
                    }
                }, 1000);

            }
        });
        image_checkcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mloginPresenter.loadCheckCodeView();
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        login_help_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("获取学号密码");
                builder.setMessage("请询问班长获取学号,初始密码即学号");
                builder.setPositiveButton("确定", null);
                builder.setCancelable(false);
                builder.show();
            }
        });
        login_help_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("忘记密码");
                builder.setMessage("重置密码需验证身份证、学号、姓名,重置后密码即学号");
                builder.setPositiveButton("确定", null);
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void showCheckCode(final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                image_checkcode.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void success(final String user, final String id) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_quit).setVisible(true);
                student_id.setText(id);
                student_name.setText(user);
                getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).edit().putBoolean("isLogin", true).commit();
                getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).edit().putString("student_id", id).commit();
                getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).edit().putString("student_name", user).commit();
                Toast.makeText(MyApplication.getContext(), "登录成功", Toast.LENGTH_LONG).show();
                mainView.setToolbarTitle("教务系统");
                navigationView.setCheckedItem(R.id.nav_jiaowu);
                getFragmentManager().beginTransaction().replace(R.id.framelayout, new JWFragment()).commit();
            }
        });
    }

    @Override
    public void fail(final String fail) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //OKHttpUtils.clearCookie();
                mloginPresenter.loadCheckCodeView();
                progressDialog.dismiss();
                Toast.makeText(MyApplication.getContext(), "登录失败:" + fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
