package com.a7space.commons.constant;
public class BaseCode {

	//用户类型
	public static String ACCESS_USER_TYPE_MANAGER_USER="0";	//平台账号
	public static String ACCESS_USER_TYPE_COMPANY_USER="1";	//公司账号
	public static String ACCESS_USER_TYPE_SPACE_USER="2";	//空间账号

	//审核标志
	public static String AUDIT_FLAG_PENDING="0";	//待审核
	public static String AUDIT_FLAG_IN_REVIEW="1";	//审核中
	public static String AUDIT_FLAG_APPROVE="2";	//通过
	public static String AUDIT_FLAG_REJECT="3";	//拒绝

	//账单周期
	public static String BILLING_CYCLE_THREE_BY_THREE="THREE_BY_THREE";	//押三付三
	public static String BILLING_CYCLE_TWO_BY_THREE="TWO_BY_THREE";	//押二付三

	//来电去电
	public static String CALL_INOUT_TYPE_IN_CALL="in_call";	//来电
	public static String CALL_INOUT_TYPE_OUT_CALL="out_call";	//去电

	//登记状态
	public static String CHECKIN_STATUS_APPLIED="1";	//已申请
	public static String CHECKIN_STATUS_IN_USE="2";	//使用中
	public static String CHECKIN_STATUS_RETURNED="3";	//已归还

	//企业规模
	public static String COMPANY_SCALE_BIG="1";	//大型
	public static String COMPANY_SCALE_MIDDLE="2";	//中型
	public static String COMPANY_SCALE_SMALL="3";	//小型
	public static String COMPANY_SCALE_TINY="4";	//微型

	//客户类型
	public static String CUSTOMER_TYPE_ENTERPRISE="1";	//企业客户
	public static String CUSTOMER_TYPE_PERSONAL="2";	//个人客户

	//删除状态
	public static String DEL_FLAG_NOT_DELETED="0";	//未删除
	public static String DEL_FLAG_DELETED="1";	//已删除

	//压金状态
	public static String DEPOSIT_STATUS_WAIT_FOR_PAY="1";	//待付款
	public static String DEPOSIT_STATUS_PAID="2";	//已付款
	public static String DEPOSIT_STATUS_REFUNDED="3";	//已退款

	//采集器类型
	public static String DEVICE_COLLECT_TYPE_SQL2000="sql2000";	//sql2000
	public static String DEVICE_COLLECT_TYPE_SQL2005="sql2005";	//sql2005
	public static String DEVICE_COLLECT_TYPE_SQL2008="sql2008";	//sql2008
	public static String DEVICE_COLLECT_TYPE_SQL2012="sql2012";	//sql2012
	public static String DEVICE_COLLECT_TYPE_SQL2014="sql2014";	//sql2014
	public static String DEVICE_COLLECT_TYPE_SQL2016="sql2016";	//sql2016
	public static String DEVICE_COLLECT_TYPE_MYSQL="mysql";	//mysql

	//是否启用
	public static String ENABLE_FLAG_DISABLED="0";	//已禁用
	public static String ENABLE_FLAG_ENABLED="1";	//已启用

	//账务项目
	public static String FINANCIAL_ITEM_CONTRACT_DEPOSIT="1";	//合同押金
	public static String FINANCIAL_ITEM_CONTRACT_RECEIVABLES="2";	//合同应收款
	public static String FINANCIAL_ITEM_CARD_DEPOSIT="3";	//门卡押金
	public static String FINANCIAL_ITEM_MATERIAL_DEPOSIT="4";	//物品押金
	public static String FINANCIAL_ITEM_PRINTER="6";	//打印费用
	public static String FINANCIAL_ITEM_OTHER="7";	//杂项
	public static String FINANCIAL_ITEM_SHIPMENT="11";	//快递费账单
	public static String FINANCIAL_ITEM_PHONE="8";	//电话费用
	public static String FINANCIAL_ITEM_CONFERENCEROOMUSE="10";	//会议室账单
	public static String FINANCIAL_ITEM_MAIN="9";	//主账单

	//账务状态
	public static String FINANCIAL_STATUS_WAIT_FOR_SETTLE="1";	//待结清
	public static String FINANCIAL_STATUS_PARTIAL_SETTLED="2";	//部分结清
	public static String FINANCIAL_STATUS_FINISHED_SETTLE="3";	//已结清

	//账务类型
	public static String FINANCIAL_TYPE_OUTCOME="1";	//支出
	public static String FINANCIAL_TYPE_INCOME="2";	//收入

	//发票类型
	public static String INVOICE_TYPE_SPECIAL_INVOICE="1";	//增值税专用发票
	public static String INVOICE_TYPE_NORMAL_INVOICE="2";	//普通发票

	//冻结状态
	public static String LOCK_STATUS_NOT_FROZE="0";	//未冻结
	public static String LOCK_STATUS_IS_FROZE="1";	//已冻结

	//物料类型
	public static String MATERIAL_TYPE_DESK="1";	//办公桌
	public static String MATERIAL_TYPE_CHAIR="2";	//办公椅
	public static String MATERIAL_TYPE_CABLE="3";	//网线
	public static String MATERIAL_TYPE_CARD="4";	//门禁卡
	public static String MATERIAL_TYPE_TELEPHONE="5";	//电话机
	public static String MATERIAL_TYPE_TRASH_CAN="6";	//垃圾桶

	//支付方式
	public static String PAY_TYPE_BANK_TRANSFER="1";	//银行转账
	public static String PAY_TYPE_CASH="2";	//现金

	//通话接通状态
	public static String PHONE_CALL_STATUS_FAILED="FAILED";	//呼叫失败
	public static String PHONE_CALL_STATUS_BUSY="BUSY";	//占线
	public static String PHONE_CALL_STATUS_ANSWERED="ANSWERED";	//已接听
	public static String PHONE_CALL_STATUS_NO_ANSWER="NO_ANSWER";	//未接听

	//出租状态
	public static String RENT_FLAG_NOT_RENTED="0";	//未出租
	public static String RENT_FLAG_IS_RENTED="1";	//已出租

	//房间类型
	public static String ROOM_TYPE_OFFICE="0";	//办公室
	public static String ROOM_TYPE_BOARD_ROME="1";	//会议室

	//性别
	public static String SEX_FLAG_MALE="1";	//男
	public static String SEX_FLAG_FEMALE="2";	//女

	//快递类型
	public static String SHIP_TYPE_SENDER="1";	//代发
	public static String SHIP_TYPE_RECEIVE="0";	//代收

	//空间采集设备类型
	public static String SPACE_DEVICE_TYPE_ELASTIX_SERVER="elastix_server";	//elastix电话服务器
	public static String SPACE_DEVICE_TYPE_AURORA_PRINT_SERVER="aurora_print_server";	//震旦打印服务器

	//发票签收状态
	public static String SPACE_INVOICE_RECEIVER_STATE_HAS_RECEIVER="1";	//已经签收
	public static String SPACE_INVOICE_RECEIVER_STATE_NOT_SIGNED="2";	//未签收

	//空间单价类型
	public static String UNIT_PRICE_TYPE_SHIPMENT_COST="SHIPMENT_COST";	//快递手续费（百分比）
	public static String UNIT_PRICE_TYPE_ELECTRICITY="electricity";	//电费
	public static String UNIT_PRICE_TYPE_INCOME="income";	//来电
	public static String UNIT_PRICE_TYPE_CALL_LAN="call_lan";	//内线
	public static String UNIT_PRICE_TYPE_CALL_LOCAL="call_local";	//市话
	public static String UNIT_PRICE_TYPE_CALL_CHINA="call_china";	//国内长途
	public static String UNIT_PRICE_TYPE_CALL_WORLD="call_world";	//国际长途
	public static String UNIT_PRICE_TYPE_BLANK_A3_PRINT="blank_A3_print";	//黑白A3打印
	public static String UNIT_PRICE_TYPE_BLANK_A4_PRINT="blank_A4_print";	//黑白A4打印
	public static String UNIT_PRICE_TYPE_BLANK_A3_COPY="blank_A3_copy";	//黑白A3复印
	public static String UNIT_PRICE_TYPE_BLANK_A4_COPY="blank_A4_copy";	//黑白A4复印
	public static String UNIT_PRICE_TYPE_COLOR_A3_PRINT="color_A3_print";	//彩色A3打印
	public static String UNIT_PRICE_TYPE_COLOR_A4_PRINT="color_A4_print";	//彩色A4打印
	public static String UNIT_PRICE_TYPE_COLOR_A3_COPY="color_A3_copy";	//彩色A3复印
	public static String UNIT_PRICE_TYPE_COLOR_A4_COPY="color_A4_copy";	//彩色A4复印
	public static String UNIT_PRICE_TYPE_SCAN="scan";	//扫描
	public static String UNIT_PRICE_TYPE_FAX="fax";	//传真

	//是否
	public static String YES_OR_NO_YES="yes";	//是
	public static String YES_OR_NO_NO="no";	//否

}