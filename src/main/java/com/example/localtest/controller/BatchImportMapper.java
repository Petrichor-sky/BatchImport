package com.example.localtest.controller;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchImportMapper {

    public void batchImport(User user);

    public void circulateImport(@Param("list") List<User> list);

    void deleteAll();
}
