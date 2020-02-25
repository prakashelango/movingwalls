package com.base.movingwalls.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

@ApiModel("User Info details")
@Getter
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7597602634402038857L;
    @ApiModelProperty(value = "First Name", required = true)
    private String firstName;
    @ApiModelProperty(value = "Last Name", required = true)
    private String lastName;
    @ApiModelProperty(value = "User Name", required = true, example = "User's Preferred Name to maintain")
    private String userName;
    @ApiModelProperty(value = "Password", required = true)
    private String password;
    @ApiModelProperty(value = "Salary", required = true, example = "Number Representing User's Salary 10000")
    private long salary;
    @ApiModelProperty(value = "Age", required = true, example = "Number Representing user's age ex: 32")
    private int age;

    public UserInfo() {
    }

    public UserInfo(final String firstName, final String lastName, final String userName, final String password, final long salary, final int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.salary = salary;
        this.age = age;
    }

}
