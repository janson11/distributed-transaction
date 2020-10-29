package com.pingan.distributed.transaction.zookeeper.impl;

import com.pingan.distributed.transaction.zookeeper.ZkCrudService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.zookeeper.CreateMode;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LeaderSelectorService extends LeaderSelectorListenerAdapter implements Closeable {
    private final String name;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount = new AtomicInteger();
    private final ZkCrudService zkCrudService;
    private final String nodePath;
    private final String masterNodePath;

    public LeaderSelectorService(CuratorFramework client,
                                 ZkCrudService zkCrudService,
                                 String path,
                                 String masterNodePath,
                                 String name) {
        this.zkCrudService = zkCrudService;
        this.nodePath = path;
        this.masterNodePath = masterNodePath;
        this.name = name;
        leaderSelector = new LeaderSelector(client, path, this);
        leaderSelector.autoRequeue();
    }

    public void start() {
        leaderSelector.start();
    }

    @Override
    public void close() {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework client) {
        log.info("name={} has been leader {} time(s) before.", name, leaderCount.getAndIncrement());
        zkCrudService.create(client, masterNodePath, CreateMode.EPHEMERAL, name);
        log.info("nodePath={}", zkCrudService.getData(client, nodePath));
        log.info("masterNodePath={}", zkCrudService.getData(client, masterNodePath));
    }
}
