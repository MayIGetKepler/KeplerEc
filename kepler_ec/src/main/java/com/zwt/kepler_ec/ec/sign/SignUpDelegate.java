package com.zwt.kepler_ec.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_ec.ec.R;

import java.util.Objects;

/**
 * @author ZWT
 */
public class SignUpDelegate extends KeplerDelegate implements View.OnClickListener {
    private TextInputEditText mEditSignUpName;
    private TextInputEditText mEditSignUpEmail;
    private TextInputEditText mEditSignUpPwd;
    private TextInputEditText mEditSignUpRePwd;
    private TextInputEditText mEditSignUpPhone;
    private AppCompatButton mBtnSignUp;
    private AppCompatTextView mTvLinkSignIn;
    private TextInputLayout mLayoutPwd;

    private ISignListener mISignListener;

    private void findViews(View rootView) {
        mEditSignUpName = rootView.findViewById(R.id.edit_sign_up_name);
        mEditSignUpEmail = rootView.findViewById(R.id.edit_sign_up_email);
        mEditSignUpPwd = rootView.findViewById(R.id.edit_sign_up_pwd);
        mEditSignUpRePwd = rootView.findViewById(R.id.edit_sign_up_re_pwd);
        mEditSignUpPhone = rootView.findViewById(R.id.edit_sign_up_phone);
        mBtnSignUp = rootView.findViewById(R.id.btn_sign_up);
        mTvLinkSignIn = rootView.findViewById(R.id.tv_link_sign_in);
        mLayoutPwd = rootView.findViewById(R.id.layout_sign_up_pwd);

        mBtnSignUp.setOnClickListener(this);
        mTvLinkSignIn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == mBtnSignUp) {
            if (checkInput()) {
                RestClient.Builder()
                        .url("http://192.168.1.105:8080/KeplerEc/userProfile")
                        .params("id","1")
                        .loader(getContext())
                        .success(body -> SignHandler.onSignUp(body,mISignListener))
                        .failure(failureMsg -> Toast.makeText(getContext(), failureMsg, Toast.LENGTH_SHORT).show())
                        .build()
                        .post();
            }
        } else if (v == mTvLinkSignIn) {
            startWithPop(new SignInDelegate());
        }
    }

    private boolean checkInput() {
        mEditSignUpName.clearFocus();
        mEditSignUpEmail.clearFocus();
        mEditSignUpPwd.clearFocus();
        mEditSignUpRePwd.clearFocus();
        mEditSignUpPhone.clearFocus();
        final String name = Objects.requireNonNull(mEditSignUpName.getText()).toString();
        final String email = Objects.requireNonNull(mEditSignUpEmail.getText()).toString();
        final String password = Objects.requireNonNull(mEditSignUpPwd.getText()).toString();
        final String rePassword = Objects.requireNonNull(mEditSignUpRePwd.getText()).toString();
        final String phone = Objects.requireNonNull(mEditSignUpPhone.getText()).toString();

        boolean isSuccess = true;

        if (name.isEmpty()) {
            mEditSignUpName.setError("请输入用户名");
            isSuccess = false;
        } else {
            mEditSignUpName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditSignUpEmail.setError("邮箱格式错误");
            isSuccess = false;
        } else {
            mEditSignUpEmail.setError(null);
        }
        if (password.contains(" ") || password.length() < 6) {
            mLayoutPwd.setErrorEnabled(true);
            mLayoutPwd.setError("密码不能少于6位，不能包含空格");
            isSuccess = false;
        } else {
            mLayoutPwd.setErrorEnabled(false);
        }
        if (!rePassword.equals(password)) {
            mEditSignUpRePwd.setError("密码不一致");
            isSuccess = false;
        } else {
            mEditSignUpRePwd.setError(null);
        }
        if (phone.contains(" ") || phone.length() != 11 || !Patterns.PHONE.matcher(phone).matches()) {
            mEditSignUpPhone.setError("手机号错误");
            isSuccess = false;
        } else {
            mEditSignUpPhone.setError(null);
        }
        return isSuccess;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    private void initView(View rootView) {
        findViews(rootView);
    }
}
