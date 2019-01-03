package top.toly.zutils.core.domain;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/12:16:46
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class SMSBean {
    public String address;
    public String date;
    public String body;

    @Override
    public String toString() {
        return "SMSBean{" +
                "address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
