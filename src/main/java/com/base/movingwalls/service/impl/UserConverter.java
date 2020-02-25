package com.base.movingwalls.service.impl;

import com.base.movingwalls.common.core.Builder;
import com.base.movingwalls.model.user.User;
import com.base.movingwalls.model.user.UserInfo;

public class UserConverter {

    public static User convertToUser(final UserInfo source) {
        return User.builder()
                .on(user -> user.getUsername())
                .set(source.getUsername())
                .on(user -> user.getAuthorities())
                .set(source.getAuthorities())
                .on(user -> user.getPassword())
                .set(source.getPassword())
                .on(user -> user.getPassword())
                .set(source.getPassword())
                .on(user -> user.getSalary())
                .set(source.getSalary())
                .build();
    }


    public static UserInfo convertTo(final User source) {
        return new UserInfo(source.getFirstName(), source.getLastName(), source.getUsername(), source.getPassword(), source.getSalary(), source.getAge());
    }

    public static UserInfo convertToInfo(final User source) {
        return Builder.of(UserInfo.class)
                .with(UserInfo::getId, source.getId())
                .with(UserInfo::getFirstName, source.getFirstName())
                .with(UserInfo::getLastName, source.getLastName())
                .with(UserInfo::getAge, source.getAge())
                .with(UserInfo::getPassword, source.getPassword())
                .with(UserInfo::getSalary, source.getSalary())
                .with(UserInfo::getSalary, source.getUsername())
                .build();
    }

    public static User convertWithId(final UserInfo source, final Long id) {
        return User.builder()
                .on(category -> category.getId())
                .set(id)
                .on(category -> category.getFirstName())
                .set(source.getFirstName())
                .on(category -> category.getLastName())
                .set(source.getLastName())
                .on(category -> category.getUsername())
                .set(source.getUserName())
                .on(category -> category.getPassword())
                .set(source.getPassword())
                .on(category -> category.getSalary())
                .set(source.getSalary())
                .on(category -> category.getAge())
                .set(source.getAge())
                .build();
    }

}
