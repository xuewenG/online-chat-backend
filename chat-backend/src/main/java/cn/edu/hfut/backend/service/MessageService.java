package cn.edu.hfut.backend.service;

import cn.edu.hfut.backend.entity.Message;

import java.sql.Timestamp;
import java.util.List;


public interface MessageService {

    List<Message> getMessage(Integer userId, Integer friendId);

    void sendMessage(Integer userId, Integer friendId, Integer groupId, Integer type, String content, Timestamp timestamp);
}