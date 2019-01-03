package top.toly.zutils._pinyi.doman;


import top.toly.zutils._pinyi.PinyinUtil;

/**
 * 作者：张风捷特烈
 * 时间：2018/3/21:8:52
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Person implements Comparable<Person> {
    private String name;
    private String pinyin;


    public Person(String name) {
        this.name = name;
        //一开始就转化好拼音
        setPinyin(PinyinUtil.getPinyin(name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(Person o) {
        return getPinyin().compareTo(o.getPinyin());
    }
}
