package com.zwt.kepler_core.application;

import com.zwt.kepler_core.util.storage.KeplerPreference;

/**
 * @author ZWT
 */
public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }


    //设置用户是否登录了
    public static void setSignState(boolean state){
        KeplerPreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    private static boolean isSignIn(){
        return KeplerPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker userChecker){
        if (isSignIn()){
            userChecker.onSignIn();
        }else {
            userChecker.onNotSignIn();
        }
    }
}
