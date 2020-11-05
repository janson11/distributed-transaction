package com.pingan.distributed.transaction.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * Zookeeper的CRUD操作接口
 */
public interface ZkCrudService {
    /**
     * 创建一个Zookeeper节点
     * @param client
     * @param path 路径
     * @param mode 节点类型
     * @param value 节点值
     */
    void create(CuratorFramework client, String path, CreateMode mode, String value);

    /**
     * 给节点设置值
     *
     * @param client
     * @param path
     * @param value
     */
    void setData(CuratorFramework client, String path, String value);

    /**
     * 获取节点数据
     *
     * @param client
     * @param path
     * @return
     */
    String getData(CuratorFramework client, String path);
}
