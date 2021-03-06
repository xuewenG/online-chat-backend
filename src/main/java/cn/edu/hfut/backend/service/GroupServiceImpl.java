package cn.edu.hfut.backend.service;

import cn.edu.hfut.backend.dao.GroupMapper;
import cn.edu.hfut.backend.dao.MessageMapper;
import cn.edu.hfut.backend.dto.group.GetAllGroupRespBean;
import cn.edu.hfut.backend.entity.Group;
import cn.edu.hfut.backend.entity.Message;
import cn.edu.hfut.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cn.edu.hfut.backend.util.RandomUtil.createGroupAccount;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<GetAllGroupRespBean.GroupAndMessageTime> getAllGroup(Integer userId) {
        List<GetAllGroupRespBean.GroupAndMessageTime> groupAndMessageTimeList =
                new ArrayList<>();
        List<Group> groupList = groupMapper.getAllGroup(userId);
        groupList.forEach(group -> {
            GetAllGroupRespBean.GroupAndMessageTime newGroup = new GetAllGroupRespBean.GroupAndMessageTime();
            newGroup.setId(group.getId());
            newGroup.setIntroduction(group.getIntroduction());
            newGroup.setName(group.getName());
            newGroup.setState(group.getState());
            newGroup.setGroupAccount(group.getGroupAccount());
            newGroup.setAvatar(group.getAvatar());
            Message message = messageMapper.getLastMessageTime(group.getId());
            Timestamp lastMessageTime = null;
            if (message != null)
                lastMessageTime = message.getTime();
            newGroup.setLastMessageTime(lastMessageTime);
            Integer lastReadMessageId = messageMapper.getLastReadGroupMessageId(newGroup.getId(), userId);
            System.out.println(lastReadMessageId);
            if (lastReadMessageId == null) {
                lastReadMessageId = 0;
            }
            Integer newMessageNumber = messageMapper.getNewGroupMessageNumber(newGroup.getId(), lastReadMessageId);
            newGroup.setNewMessageNumber(newMessageNumber);
            groupAndMessageTimeList.add(newGroup);
        });
        return groupAndMessageTimeList;
    }

    @Override
    public void addGroup(Integer userId, Integer groupId, Timestamp now) {
        groupMapper.addGroup(userId, groupId, now);
    }

    @Override
    public List<User> getGroupUserList(Integer groupId) {
        return groupMapper.getGroupUserList(groupId);
    }

    @Override
    public List<Group> getGroupByAccount(Integer groupAccount) {
        return groupMapper.getGroupByAccount(groupAccount);
    }

    @Override
    public void modifyGroup(Integer id, String name, String introduction, String avatar) {
        groupMapper.modifyGroup(id, name, introduction, avatar);
    }

    @Override
    public Group getGroupInformById(Integer id) {
        return groupMapper.getGroupInformBy(null, id);
    }

    @Override
    public Group createGroup(String name, String avatar, String introduction, User user) {
        String groupAccount = createGroupAccount();
        Integer userId = user.getId();
        while (groupMapper.getGroupInformBy(groupAccount, null) != null) {
            groupAccount = createGroupAccount();
        }
        groupMapper.createGroup(name, groupAccount, avatar, introduction);

        // 创建者加入群
        Group group = groupMapper.getGroupInformBy(groupAccount, null);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        groupMapper.addGroup(userId, group.getId(), now);
        return group;
    }

    @Override
    public void updateLastReadMessageId(Integer groupId, Integer userId, Integer messageId) {
        groupMapper.updateLastReadMessageId(groupId, userId, messageId);
    }

    @Override
    public List<Integer> getAllGroupNum(Integer userId) {
        return groupMapper.getAllGroupNum(userId);
    }

    @Override
    public List<User> getUserList(Integer groupId) {
        return groupMapper.getUserList(groupId);
    }

    @Override
    public Integer getLastMessageId(Integer groupId, Integer userId) {
        return messageMapper.getLastMessageId(groupId, userId);
    }
}
