package top.toly.zutils.core.domain;

import java.io.Serializable;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/26:12:13
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Person implements Serializable {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
