package com.example.localtest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private String userName;
    private String address;
    private String password;
}
