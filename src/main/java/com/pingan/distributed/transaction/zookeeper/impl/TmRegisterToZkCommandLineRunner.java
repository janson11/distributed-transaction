package com.pingan.distributed.transaction.zookeeper.impl;

import com.pingan.distributed.transaction.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TmRegisterToZkCommandLineRunner implements CommandLineRunner {
    @Autowired
    private ClientCreateServiceImpl clientCreateService;

    @Autowired
    private ZkCrudServiceImpl zkCrudService;

    @Autowired
    private ServerConfig serverConfig;

    @Value("${zookeeper.nodePath}")
    private String nodePath;

    @Value("${zookeeper.masterNodePath}")
    private String masterNodePath;

    @Override
    public void run(String... args) throws Exception {
        CuratorFramework client = clientCreateService.getDefaultClient();
        LeaderSelectorService leaderSelectorService = new LeaderSelectorService(client,
                zkCrudService, nodePath, masterNodePath, serverConfig.getUrl());
        client.start();
        leaderSelectorService.start();
    }
}
