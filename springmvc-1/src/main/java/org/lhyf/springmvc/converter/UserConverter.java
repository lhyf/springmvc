package org.lhyf.springmvc.converter;

import org.lhyf.springmvc.entity.Address;
import org.lhyf.springmvc.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<String, User> {
    @Override
    public User convert(String source) {
        if (source != null) {
            String[] values = source.split("-");
            if (values != null && values.length == 6) {
                //xiaohong-123456-xiaohong@163.com-18-liaoning-shenyang
                String name = values[0];
                String password = values[1];
                String email = values[2];
                int age = Integer.parseInt(values[3]);
                String province = values[4];
                String city = values[5];

                User user = new User(null, name, password, email, age);
                Address address = new Address(null, province, city);
                user.setAddress(address);
                return user;
            }
        }
        return null;
    }
}
