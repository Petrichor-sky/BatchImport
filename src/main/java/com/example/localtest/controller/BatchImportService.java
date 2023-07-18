package com.example.localtest.controller;

import cn.hutool.core.collection.ListUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class BatchImportService {

    private static final Logger logger = LoggerFactory.getLogger(BatchImportService.class);
    @Resource
    BatchImportMapper batchImportMapper;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Resource
    ThreadPoolTaskExecutor threadPoolExecutor;

    @Resource
    MybatisBatchUtils mybatisBatchUtils;

    @Transactional(rollbackFor = Exception.class)
    public void batchImport() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            User u = new User();
            u.setAddress("广州：" + i);
            u.setUserName("张三：" + i);
            u.setPassword("123：" + i);
            users.add(u);
        }

        long startTime = System.currentTimeMillis();
        mybatisBatchUtils.batchUpdateOrInsert(users, BatchImportMapper.class, (user, mapper) -> {
            mapper.batchImport(user);
            return null;
        });

        long endTime = System.currentTimeMillis();
        logger.info("batch模式 耗费时间 {}", (endTime - startTime));
    }

    @Transactional(rollbackFor = Exception.class)
    @Async("ThreadPoolExecutor")
    public void circulateImport() {

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            User u = new User();
            u.setAddress("广州：" + i);
            u.setUserName("张三：" + i);
            u.setPassword("123：" + i);
            users.add(u);
        }

        long startTime = System.currentTimeMillis();
        /*List<List<User>> partition = ListUtil.partition(users, 3000);
        CountDownLatch c = new CountDownLatch(partition.size());
        for (List<User> userList : partition) {
            threadPoolExecutor.submit(() -> {
                //batchImportMapper.circulateImport(userList);
                insertForSubPage(userList);
                c.countDown();
            });
        }
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //partition.parallelStream().forEach(s-> batchImportMapper.circulateImport(s));
        this.insertForSubPage(users);
        long endTime = System.currentTimeMillis();
        logger.info("分页foreach方式 耗费时间 {}", (endTime - startTime));
    }

    public void insertForSubPage(List<User> list) {
        int subSize = 1000;
        int subCount = list.size();
        int subPageTotal = (subCount / subSize) + ((subCount % subSize > 0) ? 1 : 0);
        // 根据页码取数据
        for (int i = 0, len = subPageTotal - 1; i <= len; i++) {
            // 分页计算
            int fromIndex = i * subSize;
            int toIndex = ((i == len) ? subCount : ((i + 1) * subSize));
            List<User> subList = list.subList(fromIndex, toIndex);

            batchImportMapper.circulateImport(subList);

        }
    }

    public void delete() {
        batchImportMapper.deleteAll();
    }
}
