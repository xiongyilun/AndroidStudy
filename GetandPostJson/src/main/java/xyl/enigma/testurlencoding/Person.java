package xyl.enigma.testurlencoding;

/**
 * Created by 一伦 on 2016/4/7.
 */
public class Person {
    private String name;
    private int age;
    private String address;
    private Boolean sex;//true male false female

    public Person() {

    }

    public Person(String name, int age, String address, Boolean sex) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.sex = sex;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return this.getAddress()+this.getName()+this.getSex()+this.getAge();
    }
}
