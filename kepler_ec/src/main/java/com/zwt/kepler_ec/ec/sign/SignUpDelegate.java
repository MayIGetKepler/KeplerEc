package com.zwt.kepler_ec.ec.sign;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;

import com.zwt.kepler_core.delegates.KeplerDelegate;
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
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSignUp) {
            checkInput();
        }
    }

    private void checkInput() {
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

        if (name.isEmpty()) {
            mEditSignUpName.setError("请输入用户名");
        } else {
            mEditSignUpName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditSignUpEmail.setError("邮箱格式错误");
        } else {
            mEditSignUpEmail.setError(null);
        }
        if (password.contains(" ") || password.length() < 6) {
//            mEditSignUpPwd.setError("密码不能少于6位，不能包含空格");
            mLayoutPwd.setErrorEnabled(true);
            mLayoutPwd.setError("密码不能少于6位，不能包含空格");
        } else {
//            mEditSignUpPwd.setError(null);
            mLayoutPwd.setErrorEnabled(false);
        }
        if (!rePassword.equals(password)) {
            mEditSignUpRePwd.setError("密码不一致");
        } else {
            mEditSignUpRePwd.setError(null);
        }
        if (phone.contains(" ") || phone.length() != 11 || !Patterns.PHONE.matcher(phone).matches()) {
            mEditSignUpPhone.setError("手机号错误");
        } else {
            mEditSignUpPhone.setError(null);
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
