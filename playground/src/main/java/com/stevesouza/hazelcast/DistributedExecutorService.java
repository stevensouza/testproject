package com.stevesouza.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.Member;

import java.io.Serializable;

/**
 * code taken from hazelcast.org
 */

public class DistributedExecutorService {
    public static void main(String[] args) {
        Config config = new Config();
        HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
        IExecutorService ex = h.getExecutorService("my-distributed-executor");
        ex.submit(new MessagePrinter("message to any node"));
        Member firstMember = h.getCluster().getMembers().iterator().next();
        ex.executeOnMember(new MessagePrinter("message to very first member of the cluster"), firstMember);
        ex.executeOnAllMembers(new MessagePrinter("message to all members in the cluster"));
        ex.executeOnKeyOwner(new MessagePrinter("message to the member that owns the following key"), "key");
    }

    static class MessagePrinter implements Runnable, Serializable {
        final String message;

        MessagePrinter(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println(message);
        }
    }
}
