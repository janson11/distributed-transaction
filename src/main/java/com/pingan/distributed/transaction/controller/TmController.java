package com.pingan.distributed.transaction.controller;

import com.pingan.distributed.transaction.config.ServerConfig;
import com.pingan.distributed.transaction.zookeeper.ClientCreateService;
import com.pingan.distributed.transaction.zookeeper.ZkCrudService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tm")
@RestController
@Slf4j
public class TmController {
    @Autowired
    private ClientCreateService clientCreateService;

    @Autowired
    private ZkCrudService zkCrudService;

    @Autowired
    private ServerConfig serverConfig;

    private static final String MASTER_TM_PATH = "/zookeeper/test";

    @PostMapping("/start")
    public String startGlobalTransaction() {
        log.info("start global transaction");
        CuratorFramework defaultClient = clientCreateService.getDefaultClient();

        // 1.判断当前TM角色是否为主
        // 2.如果不是主TM，返回主TM的IP PORT，让RM服务选择主TM进行发送
        String data = zkCrudService.getData(defaultClient, MASTER_TM_PATH);
        String localUrl = serverConfig.getUrl();
        if (!data.equals(localUrl)) {
            return data;
        }

        // 3.如果是主TM，返回全局事务ID
        String XID = "123456";
        return XID;
    }
}
