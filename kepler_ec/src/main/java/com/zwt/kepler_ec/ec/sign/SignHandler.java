package com.zwt.kepler_ec.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwt.kepler_core.application.AccountManager;
import com.zwt.kepler_ec.ec.database.DatabaseManager;
import com.zwt.kepler_ec.ec.database.UserProfile;

/**
 * @author ZWT
 */
public class SignHandler {
    public static void onSignUp(String response, ISignListener signListener) {
        JSONObject jsonObject = JSON.parseObject(response);
        final long id = jsonObject.getLong("id");
        final String name = jsonObject.getString("name");
        final String avatar = jsonObject.getString("avatar");
        final String address = jsonObject.getString("address");
        final String gender = jsonObject.getString("gender");

        final UserProfile userProfile = new UserProfile(id, name, avatar, address, gender);
        DatabaseManager.getInstance().getUserProfileDao().insertOrReplace(userProfile);

        //设置已登录
        AccountManager.setSignState(true);

        if (signListener != null){
            signListener.onSignUpSuccess();
        }
    }

    public static void onSignIn(String response, ISignListener signListener) {
        JSONObject jsonObject = JSON.parseObject(response);
        final long id = jsonObject.getLong("id");
        final String name = jsonObject.getString("name");
        final String avatar = jsonObject.getString("avatar");
        final String address = jsonObject.getString("address");
        final String gender = jsonObject.getString("gender");

        final UserProfile userProfile = new UserProfile(id, name, avatar, address, gender);
        DatabaseManager.getInstance().getUserProfileDao().insertOrReplace(userProfile);

        //设置已登录
        AccountManager.setSignState(true);

        if (signListener != null){
            signListener.onSignInSuccess();
        }

    }
}
