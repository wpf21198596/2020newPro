package com.example.demo.common.response;

public enum ResponseEnum {

    /*UPDATE_IS_ERROR(1007,"修改失败"),
    DEL_IS_ERROR(1006,"删除失败"),
    ADD_IS_ERROR(1005,"添加失败"),
    QUERY_IS_ERROR(1004,"信息查询失败"),
    USER_IS_LOCK(1003,"账号已锁定"),
    INPUTUSER_IS_NULL(1002,"输入账号或密码为空"),
    ContractNo*/
    CREATEDATE_IS_NULL(1013,"签订日期未填写"),
    TOTALPRICE_IS_NULL(1012,"总金额未填写"),
    SECONDEMAILADDRESS_IS_NULL(1011,"乙方联系人邮箱未填写"),
    SECONDPHONENUM_IS_NULL(1010,"乙方联系人电话未填写"),
    SECONDPEOPLENAME_IS_NULL(1009,"乙方联系人未填写"),
    SECONDADDRESS_IS_NULL(1008,"乙方地址未填写"),
    SECONDNAME_IS_NULL(1007,"乙方未填写"),
    FIRSTEMAILADDRESS_IS_NULL(1006,"甲方联系人邮箱未填写"),
    FIRSTPHONENUM_IS_NULL(1005,"甲方联系人电话未填写"),
    FIRSTPEOPLENAME_IS_NULL(1004,"甲方联系人未填写"),
    FIRSTADDRESS_IS_NULL(1003,"甲方地址未填写"),
    FIRSTNAME_IS_NULL(1002,"甲方未填写"),
    CONTRACTNO_IS_NULL(1001,"合同号未填写");

    private int code;

    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
