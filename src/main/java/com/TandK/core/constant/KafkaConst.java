package com.TandK.core.constant;

public interface KafkaConst {
    /**
     * 分区数(和线上kafka集群数对应)
     */
    int GLOBAL_PARTITION_COUNT = 3;
    /**
     * 副本数(和线上kafka集群数对应)
     */
    short GLOBAL_REPLICATION_FACTOR = 3;

    interface Topic {
        /**
         * 到达上架时间时处理商品上架状态
         */
        String PRODUCT_PUT_ON_SALE = "tp_product_put_on_sale";
        /**
         * 到达下架架时间时处理商品下架状态
         */
        String PRODUCT_PULL_ON_SALE = "tp_product_pull_on_sale";


        /**
         * 订单支付成功后 发出订单数据
         */
        String ORDER_PAYMENT_ORDER_DATA = "tp_payment_order_data";

        /**
         * 订单退款成功后 发出订单数据
         */
        String ORDER_REFUND_ORDER_DATA = "tp_refund_order_data";

        String CUSTOMER_ORDER_COUNT_UPDATE = "tp_customer_order_count_update";

        /**
         * 订单数据上传到履行系统
         */
        String ORDER_DATA_FULFILLMENT_PUSH = "tp_order_data_fulfillment_push";

        /**
         * 订单退款时,撤销履行
         */
        String ORDER_REFUND_FULFILLMENT_PUSH = "tp_order_refund_cancel_fulfillment_push";

        /**
         * 订单数据 计算相关收益
         */
        String ORDER_DATA_FULFILLMENT_FINANCE = "tp_order_data_fulfillment_finance";

        /**
         * 订单超时取消
         */
        String CUSTOMER_ORDER_TIME_OUT_QUIT = "tp_customer_order_time_out_quit";

        /**
         * 到达生效开始时间时处理商品库存
         */
        String PLAN_BEGIN_TIME_UPDATE_STOCK = "tp_plan_begin_time_update_stock";

        /**
         * 到达生效开始时间时处理商品库存
         */
        String PURCHASE_ORDER_EFFECTIVE_BEGIN_UPDATE_STOCK = "tp_purchase_order_effective_begin_update_stock";


        /**
         * 到达生效截止时间时撤回商品库存
         */
        String PLAN_END_TIME_CANCEL_STOCK = "tp_plan_end_time_cancel_stock";

        /**
         * 快递订单商品填写快递信息
         */
        String ORDER_ITEM_FILL_EXPRESS_INFO = "tp_order_item_fill_express_info";

        /**
         * 快递订单商品签收
         */
        String ORDER_ITEM_EXPRESS_SIGN = "tp_order_item_express_sign";

        /**
         * 订单收益到账
         */
        String ORDER_FULFILLMENT_FINANCE_CALCULATE = "tp_order_fulfillment_finance_calculate";

        /**
         * 通知物流同步订单用于发货
         */
        String SYNC_ORDER_FOR_LOGISTICS = "tp_sync_order_for_logistics";
    }

    interface GroupId {
        String GLOBAL_GROUP_ID = "tudgo-group";
        /**
         * 订单履行
         */
        String ORDER_FULFILLMENT_ORDER_DATA = "tudgo-order-fulfillment-order-data";
        /**
         * 每日配送
         */
        String ORDER_FULFILLMENT_DELIVERY = "tudgo-order-fulfillment-delivery";
        String MONTH_GOAL = "month-goal";
        /**
         * 处理sku总销量的消费组
         */
        String PRODUCT_SALES_GROUP_ID = "product_sales_group";

        /**
         * 订单收益到账
         */
        String ORDER_FULFILLMENT_FINANCE_CALCULATE = "order_fulfillment_finance_calculate";

        /**
         * 用户下单超过五个小区禁用
         */
        String CUSTOMER_DISABLE_BY_PARTNER_COUNT = "customer_disable_by_partner_count";

    }
}
