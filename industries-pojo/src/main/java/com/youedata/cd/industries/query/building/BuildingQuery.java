package com.youedata.cd.industries.query.building;

import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;

/**
 * Created by cdyoue on 2016/6/1.
 */
public class BuildingQuery extends EnterpriseQuery {
    private Integer orderTypeNum;//0:desc 1:asc
    private Integer orderByClauseNum;//0 :从业人数 1:税金 2:营收
    private String orderType;//排序规则
    private String orderByClause;//排序条件
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderByClause() {
        return "te."+orderByClause;
    }

    public Integer getOrderTypeNum() {
        return orderTypeNum;
    }

    public void setOrderTypeNum(Integer orderTypeNum) {
        if(orderTypeNum == 0 ){
            setOrderDesc();
        }else{
            setOrderAsc();
        }
        this.orderTypeNum = orderTypeNum;
    }

    public Integer getOrderByClauseNum() {
        return orderByClauseNum;
    }

    public void setOrderByClauseNum(Integer orderByClauseNum) {
        if(orderByClauseNum == 0){
            setOrderByClause("employee_count");
        }else if (orderByClauseNum == 1){
            setOrderByClause("tax");
        }else if (orderByClauseNum == 2){
            setOrderByClause("revenue");
        }
        orderByClauseNum = orderByClauseNum;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
    public void setOrderAsc() {
        this.setOrderType("asc");
    }
    public void setOrderDesc() {
        this.setOrderType("desc");
    }

}
