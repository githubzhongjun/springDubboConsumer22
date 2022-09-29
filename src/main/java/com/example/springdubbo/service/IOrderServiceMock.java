package com.example.springdubbo.service;

public class IOrderServiceMock implements IOrderService{
    @Override
    public String test(String name) {
        return "我是服务降级！！";
    }
}

