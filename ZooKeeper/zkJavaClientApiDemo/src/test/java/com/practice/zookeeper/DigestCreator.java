package com.practice.zookeeper;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class DigestCreator {


    public static void main(String[] args) throws NoSuchAlgorithmException {

        String s = DigestAuthenticationProvider.generateDigest("jike:1234");
        System.out.println("s = " + s);
    }
}
