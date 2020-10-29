package com.pingan.distributed.transaction.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;

/**
 * 创建Curator Client
 */
public interface ClientCreateService {
    CuratorFramework getDefaultClient();

    CuratorFramework createSimple(String connectionString);

    CuratorFramework createWithOptions(String connectionString,
                                       RetryPolicy retryPolicy,
                                       int connectionTimeoutMs,
                                       int sessionTimeoutMs);
}
