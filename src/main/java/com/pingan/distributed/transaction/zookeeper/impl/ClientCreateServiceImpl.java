package com.pingan.distributed.transaction.zookeeper.impl;

import com.pingan.distributed.transaction.zookeeper.ClientCreateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientCreateServiceImpl implements ClientCreateService {
    @Value("${zookeeper.connectionString}")
    private String connectionString;

    @Override
    public CuratorFramework getDefaultClient() {
        return createSimple(connectionString);
    }

    @Override
    public CuratorFramework createSimple(String connectionString) {
        return CuratorFrameworkFactory.newClient(connectionString,
                new ExponentialBackoffRetry(1000, 3));
    }

    @Override
    public CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs).build();
    }
}
