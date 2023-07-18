package com.example.localtest.controller;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String userName;
    private String address;
    private String password;

}
