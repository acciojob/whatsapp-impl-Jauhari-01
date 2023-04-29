package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private HashMap<String, User> userHashMap;
    private HashMap<Integer,Message> messageHashMap;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.userHashMap = new HashMap<>();
        this.messageHashMap = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name,String mobile) throws Exception{
        //If mobile number exists in database , throw "User already exists"
        //Otherwise,create the user and return success

        if(userMobile.contains(mobile)){
            throw new Exception("User already exists");
        }

        User user = new User();
        user.setName(name);
        user.setMobile(mobile);

        userMobile.add(mobile);
        userHashMap.put(user.getName(),user);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users){
        Group group = new Group();

        if(users.size() == 2){
            group.setName(users.get(1).getName());
            group.setNumberOfParticipants(2);
        }else{
            customGroupCount++;
            String groupName = "Group"+customGroupCount;
            group.setName(groupName);
            group.setNumberOfParticipants(users.size());
        }

        groupUserMap.put(group,users);
        adminMap.put(group,users.get(0));
        return group;
    }

    public int createMessage(String content){
        messageId++;
        Message message = new Message(messageId,content);
        messageHashMap.put(messageId,message);
        return messageId;
    }

    public int sendMessage(Message message,User sender,Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        if(groupUserMap.containsKey(group)){
            List<User> list = groupUserMap.get(group);

            if(list.contains(sender)){
                List<Message> messages;
                if(groupMessageMap.containsKey(group)){
                    messages = groupMessageMap.get(group);
                }else{
                    messages = new ArrayList<>();
                }
                messages.add(message);
                groupMessageMap.put(group,messages);
                return messages.size();
            }else{
                throw new Exception("You are not allowed to send message");
            }
        }else{
            throw new Exception("Group does not exist");
        }
    }

    public String changeAdmin(User approver,User user,Group group)throws Exception{

         //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". 
        //Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }

        if(!approver.equals(adminMap.get(group))){
            throw new Exception("Approver does not have rights");
        }

        List<User> list = groupUserMap.get(group);
        Boolean flag = false;

        for(User st : list){
            if(st.equals(user)){
                flag = true;
            }
        }

        if(flag == false){
            throw new Exception("User is not a participant");
        }

        adminMap.put(group,user);
        return "SUCCESS";
    }

    private boolean isSame(User user1,User user2){
        if(user1.getMobile().equals(user2.getMobile()) && 
           user1.getName().equals(user2.getName())){
            return true;
           }
        return false;
    }
    public int removeUser(User user) throws Exception{
        //This is a bonus problem and does not contains any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
        User userr = null;
        for(int i=0 ; i<groupMessageMap.size();i++){
            for(User u : groupUserMap.get(i)){
                if(isSame(user,u)){
                    userr = u;
                    break;
                }
            }
        }
        if(userr == null){
            throw new Exception("");
        }
        for(User u : adminMap.values()){
            if(isSame(u, userr)){
                throw new Exception("");
            }
        }

        //delete the user here from 3 hashmaps
        userHashMap.remove(userr.getName());
        for(Map.Entry<Message,User> msg: senderMap.entrySet()){
            if(msg.getValue().getName().equals(userr.getName()) && msg.getValue().getMobile().equals(userr.getMobile())){
               senderMap.remove(msg.getKey());
            }
        }

        Group grpToBeRemoved=null;
        for(Map.Entry<Group,List<User>> mp: groupUserMap.entrySet()){
            for(User user1 : mp.getValue()) {
                if (isSame(user1, userr)) {
                    grpToBeRemoved = mp.getKey();
                    break;
                }
            }
            if(grpToBeRemoved!=null)
                break;
        }
        List<User> userList = groupUserMap.get(grpToBeRemoved);
        List<User> newList = null;        

        for(User user1: userList)
            if(!isSame(user1,userr))
                newList.add(user1);

        groupUserMap.put(grpToBeRemoved,newList);

        return 0;
    }

    public String findMessage(Date start,Date end,int k)throws Exception{
         //This is a bonus problem and does not contains any marks
        // Find the Kth latest message between start and end (excluding start and end)
        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception
        return "";
    }
}
