package org.lhyf.entity;

import java.io.Serializable;

/****
 * @author YF
 * @date 2019-04-08 14:38
 * @desc RequestEntity
 *
 * 封装查询条件
 **/
public class RequestEntity implements Serializable {

    private static final long serialVersionUID = 7067942374024456106L;

    private String name;
    private String number;
    private Long serial;

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", serial=" + serial +
                '}';
    }
}
