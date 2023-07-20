package com.example.localtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/import")
public class BatchImport {
    @Resource
    BatchImportService batchImportService;

    @RequestMapping("/batchImport")
    public void batchImport() {
        batchImportService.batchImport();
    }
    @RequestMapping("/circulateImport")
    public void circulateImport() throws InterruptedException {
        batchImportService.circulateImport();
    }

    @RequestMapping("/delete")
    public void delete(){
        batchImportService.delete();
    }
    @RequestMapping("/transactionTest")
    public void transactionTest(){

        batchImportService.transactionTest();
    }

}
