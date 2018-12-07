package com.match.app.message.bean;

/**
 * 描述:
 * 搜索用户列表
 *
 * @author Dengfei
 * @create 2018-11-27 16:31
 */
public class B332Request extends BaseRequest {

    private String keyword; // 搜索关键字

    private Integer page;       // 页号

    private Integer pageSize;   // 页号大小，0或者null为不分页

    public B332Request() {
        setActionCode("B332");
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

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