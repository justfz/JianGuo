package com.jianguo.OA.View;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.Main.view.MainView;
import com.jianguo.OA.Presenter.OAPresenter;
import com.jianguo.OA.Presenter.OAPresenterImp;
import com.jianguo.OA.Presenter.OA_LoginPresenter;
import com.jianguo.OA.Presenter.OA_LoginPresenterImpl;
import com.jianguo.R;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OA_LoginFragment extends Fragment implements OA_LoginView, View.OnClickListener {

    private EditText oa_edit_teacher_id;
    private EditText oa_edit_teacher_pass;
    private EditText oa_edit_teacher_checkcode;
    private Button oa_button_login;
    private ProgressBar oa_progressBar;
    private ImageView oa_image;
    private OA_LoginPresenter oa_loginPresenter;
    private String teacher_id;
    private String teacher_pass;
    private String checkcode;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oa_loginPresenter = new OA_LoginPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        oa_edit_teacher_id = (EditText) view.findViewById(R.id.edit_number_id);
        oa_edit_teacher_pass = (EditText) view.findViewById(R.id.edit_pass_id);
        oa_edit_teacher_checkcode = (EditText) view.findViewById(R.id.edit_checkcode);
        oa_button_login= (Button) view.findViewById(R.id.bt_login);
        oa_image = (ImageView) view.findViewById(R.id.image_checkcode);
        view.findViewById(R.id.login_help_id).setVisibility(View.GONE);
        view.findViewById(R.id.login_help_pass).setVisibility(View.GONE);
        OKHttpUtils.clearCookie_oa();
        oa_loginPresenter.loadParameterView();
        oa_button_login.setOnClickListener(this);
        return view;
    }

    @Override
    public void setParameter(final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oa_image.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void success() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new OAFragment()).commit();
            }
        });
    }

    @Override
    public void fail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),"对不起登录失败",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                oa_loginPresenter.loadParameterView();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("正在登录...");
                progressDialog.show();
                teacher_id = oa_edit_teacher_id.getText().toString();
                teacher_pass = oa_edit_teacher_pass.getText().toString();
                checkcode=oa_edit_teacher_checkcode.getText().toString();
                oa_loginPresenter.Loginevent(teacher_id,teacher_pass,checkcode);
                break;
            case R.id.image_checkcode:
                oa_loginPresenter.loadParameterView();
                break;
        }

    }
}
