package com.match.app.message.bean;

/**
 * 描述:
 * 获取申请配对的列表
 *
 * @author Dengfei
 * @create 2018-12-02 19:51
 */
public class B336Request extends BaseRequest {

    public B336Request() {
        setActionCode("B336");
    }

    private Integer page;       // 页号

    private Integer pageSize;   // 页号大小，0或者null为不分页

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}