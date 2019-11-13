package cn.edu.hfut.backend.service;

import cn.edu.hfut.backend.entity.Message;
import cn.edu.hfut.backend.entity.User;

import java.util.List;

public interface FriendService {

    List<User> getAllFriendById(Integer id);

    List<Message> getRecord(Integer userId,Integer friendId);

    User getInformationById(Integer id);
}
