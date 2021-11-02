package com.TandK.core.support.threadlocal;


import com.TandK.model.po.UserPO;

/**
 * @author TandK
 */
public class UserThreadLocal {

    private static ThreadLocal<UserPO> userThreadLocal = new ThreadLocal();

    public static void set(UserPO userPO){
        userThreadLocal.set(userPO);
    }

    public static UserPO get(){
        return userThreadLocal.get();
    }

    public static void remove(){
        userThreadLocal.remove();
    }
}