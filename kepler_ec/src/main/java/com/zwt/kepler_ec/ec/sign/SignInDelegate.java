package com.zwt.kepler_ec.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_ec.ec.R;

import java.util.Objects;

/**
 * @author ZWT
 */
public class SignInDelegate extends KeplerDelegate implements View.OnClickListener {

    private TextInputEditText mEmail;
    private TextInputEditText mPwd;
    private AppCompatButton mBtnSignIn;
    private AppCompatTextView mTvLinkSignUp;
    private AppCompatImageButton mBtnWeChat;

    private ISignListener mISignListener;

    private void findViews(View rootView) {
        mEmail = rootView.findViewById(R.id.edit_sign_in_email);
        mPwd = rootView.findViewById(R.id.edit_sign_in_pwd);
        mBtnSignIn = rootView.findViewById(R.id.btn_sign_in);
        mTvLinkSignUp = rootView.findViewById(R.id.tv_link_sign_up);
        mBtnWeChat = rootView.findViewById(R.id.btn_sign_we_chat);

        mBtnSignIn.setOnClickListener(this);
        mTvLinkSignUp.setOnClickListener(this);
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
        return R.layout.delegate_sign_in;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    private void initView(View rootView) {
        findViews(rootView);

    }

    @Override
    public void onClick(View view) {
        if (view == mBtnSignIn){
            if (checkInput()){
                RestClient.Builder()
                        .url("http://192.168.1.105:8080/KeplerEc/userProfile")
                        .params("email",Objects.requireNonNull(mEmail.getText()).toString())
                        .params("password",Objects.requireNonNull(mPwd.getText()).toString())
                        .success(body -> SignHandler.onSignIn(body,mISignListener))
                        .loader(getContext())
                        .build()
                        .post();
            }
        }
        else if (view == mTvLinkSignUp){
            startWithPop(new SignUpDelegate());
        }
    }

    private boolean checkInput() {
        mEmail.clearFocus();
        mPwd.clearFocus();
        final String email = Objects.requireNonNull(mEmail.getText()).toString();
        final String password = Objects.requireNonNull(mPwd.getText()).toString();

        boolean isSuccess = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("邮箱格式错误");
            isSuccess = false;
        } else {
            mEmail.setError(null);
        }
        if (password.contains(" ") || password.length() < 6) {
            mPwd.setError("密码不能少于6位，不能包含空格");
            isSuccess = false;
        } else {
            mPwd.setError(null);
        }
        return isSuccess;
    }
}
