package com.zhangyue.zeus.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;

import com.zhangyue.zeus.vo.CheckResult;

/**
 * 验证用户查询表的权限
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public class CheckUserTablePermission {

    private static List<String> F = new ArrayList<String>();
    private static Map<String, String> map = new HashMap<String, String>();
    private static final String ALTER = "alter";
    private static final String DROP = "drop";
    private static final String INSERT = "insert";
    private static final String SELECT = "select";
    private static final String CREATE = "create";
    private static final String REGEPX = " ()\t\n\r\f";
    static {
        F.add("from");
        F.add("join");
        map.put(ALTER, ALTER);
        map.put(DROP, DROP);
        map.put(CREATE, CREATE);
        map.put(INSERT, INSERT);
    }

    private boolean result = false;
    private String msg = null;

    public CheckResult checkPermission(String sql, List<String> permitedTables) {
        CheckResult result = new CheckResult();
        // 验证只能select 操作
        if (StringUtils.isEmpty(sql)) {
            return result;
        }
        String item[] = sql.toLowerCase().split("\\s");
        for (String it : item) {
            if (map.containsKey(it)) {
                result.setResult(true);
                result.setMsg("你没有" + ALTER + "," + DROP + "," + CREATE + "," + INSERT + "操作权限或者sql语句中含有" + ALTER + ","
                              + DROP + "," + CREATE + "," + INSERT + "关键字");
                return result;
            }
        }
        permitedTables.add(SELECT);
        StringTokenizer st = new StringTokenizer(sql, REGEPX);
        String token = null, last = null;
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreElements()) {
            token = st.nextToken();
            token = token.toLowerCase();
            if (F.contains(last)) {
                if (!permitedTables.contains(token.replace(Constants.SEMICOLON, "").replace("；", ""))) {
                    sb.append(token + "\\");
                    result.setResult(true);
                    msg = sb.toString();
                    result.setMsg("请您检查你提交的sql,你的操作或者查询的表没有权限" + msg);
                    return result;
                }
            }
            last = token;
        }
        return result;
    }

    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) {

        String sql = "select * from table limit 10";
        String item[] = sql.toLowerCase().split("\\s");
        for (String str : item) {
            System.out.println(str);
        }

    }
}
