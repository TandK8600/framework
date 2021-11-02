package com.TandK.core.constant;

/**
 * 常用公共常量
 *
 * @author caood
 * @date 2021/04/06 17:43
 */
public interface CommonConst {
    interface IsDeleted {
        int NO = 0;
        int YES = 1;
    }

    /**
     * Status : 状态 0：正常；1：禁用
     */
    interface Status {
        /**
         * 0
         */
        int NORMAL = 0;
        /**
         * 1
         */
        int DISABLE = 1;

        /**
         * 1
         */
        int ONE = 1;

        /**
         * 2
         */
        int TWO = 2;

        /**
         * 3
         */
        int THREE = 3;
    }

    interface SystemOperator {
        String NAME = "系统操作";
        Long ACCOUNT_ID_LONG = -1L;
        Integer ACCOUNT_ID = -1;

    }

}
