package com.zhangyue.zeus.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;


/**
 * 分页公共类
 * @date 2013-9-6
 * @author rongneng
 */
public class Page<T> {

    // -- 公共变量 --//
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    // -- 分页参数 --//
    public int pageNo = 1;
    public int pageSize = 15;
    public String orderBy = null;
    public String order = null;
    public boolean autoCount = true;

    // -- 返回结果 --//
    public List<T> result = new ArrayList<T>();
    public long totalCount = -1;

    // -- 构造函数 --//
    public Page(){
    }

    public Page(int pageNo, int pageSize){
        super();
        if (pageNo == 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
        if (pageSize == 0) {
            this.pageSize = 15;
        } else {
            this.pageSize = pageSize;

        }

    }

    public Page(int pageSize){
        this.pageSize = pageSize;
    }

    // -- 访问查询参数函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    public Page<T> pageNo(final int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为1.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;

        if (pageSize < 1) {
            this.pageSize = 1;
        }
    }

    public Page<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * 获得排序字段,无默认值.多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置排序字段,多个排序字段时用','分隔.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public Page<T> orderBy(final String theOrderBy) {
        setOrderBy(theOrderBy);
        return this;
    }

    /**
     * 获得排序方向.
     */
    public String getOrder() {
        return order;
    }

    /**
     * 设置排序方式向.
     * 
     * @param order 可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setOrder(final String order) {
        // 检查order字符串的合法值
        String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) throw new IllegalArgumentException(
                "排序方向" + orderStr + "不是合法值");
        }

        this.order = StringUtils.lowerCase(order);
    }

    public Page<T> order(final String theOrder) {
        setOrder(theOrder);
        return this;
    }

    /**
     * 是否已设置排序字段,无默认值.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(final boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    // -- 访问查询结果函数 --//

    /**
     * 取得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalCount < 0) return -1;

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) return pageNo + 1;
        else return pageNo;
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) return pageNo - 1;
        else return pageNo;
    }
    /**
     * 实现百度分页
     * @param urlQuery
     * @return
     */
    public String getPageString(String urlQuery) {
        if (!urlQuery.equals("")) {
            urlQuery = urlQuery + "&";
        }
        StringBuffer sb = new StringBuffer();
        int pageNow = getPageNo(); // 当前页
        long pageCount = getTotalPages(); // 总页数
        int pagesize = getPageSize();
        int total = 8; // 分页条中有多少个超链接
        if (pageCount == 0) {
            return "暂无任务";
        }
        // // 中间的那个超链接距离边缘链接的间隔a的个数 例如：共11个分页 那么这个就是5
        long padding = (int) Math.ceil(total / 2);
        if (pageCount - pageNow <= padding && pageNow > padding + 1) {
            padding = total - (pageCount - pageNow);
        }
        long start = pageNow - padding, end = start + pagesize;
        if (pageNow - 1 > 0) {
            sb.append("<li style=\"float:left;\"><a style=\"border-width: 1px 1px 1px 1px;line-height: 20px;\" href=?"
                      + urlQuery + "page=" + (pageNow - 1) + "&pageSize=" + getPageSize() + ">上一页</a> </li>");
        }
        for (long i = start; i <= end; i++) {
            if (i <= 0) {
                end += Math.abs(i);
                i = 1;
            }
            String cl = (i == pageNow) ? "active" : "";
            sb.append("<li class=" + cl
                      + " style=\"float:left;\"><a style=\"border-width: 1px 1px 1px 1px;line-height: 20px;\" href=?"
                      + urlQuery + "page=" + i + "&pageSize=" + getPageSize() + ">" + i + "</a> </li>");
            if (i == pageCount) {
                break;
            }
        }
        if (pageNow - pageCount < 0) {
            sb.append("<li style=\"float:left;\"><a style=\"border-width: 1px 1px 1px 1px;line-height: 20px;\"  href=?"
                      + urlQuery + "page=" + (pageNow + 1) + "&pageSize=" + getPageSize() + ">下一页</a> </li>");
        }
        return sb.toString();
    }

}
