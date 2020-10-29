package com.pingan.distributed.transaction.zookeeper.impl;

import com.pingan.distributed.transaction.zookeeper.ZkCrudService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ZkCrudServiceImpl implements ZkCrudService {

    @Override
    public void create(CuratorFramework client, String path, CreateMode mode, String value) {
        if (client == null || StringUtils.isNotEmpty(path) || StringUtils.isNotEmpty(value)) {
            log.error("ZkCrudServiceImpl create params invalid!");
            return;
        }

        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(mode).forPath(path, value.getBytes());
        } catch (Exception e) {
            log.error("ZkCrudServiceImpl create error, ", e);
        }
    }

    @Override
    public void setData(CuratorFramework client, String path, String value) {
        if (client == null || StringUtils.isNotEmpty(path) || StringUtils.isNotEmpty(value)) {
            log.error("ZkCrudServiceImpl setData params invalid!");
            return;
        }

        try {
            client.setData().forPath(path, value.getBytes());
        } catch (Exception e) {
            log.error("ZkCrudServiceImpl create error, ", e);
        }
    }

    @Override
    public String getData(CuratorFramework client, String path) {
        if (client == null || StringUtils.isNotEmpty(path)) {
            log.error("ZkCrudServiceImpl getData params invalid!");
            return null;
        }

        try {
            return new String(client.getData().forPath(path));
        } catch (Exception e) {
            log.error("ZkCrudServiceImpl create error, ", e);
        }

        return null;
    }
}
