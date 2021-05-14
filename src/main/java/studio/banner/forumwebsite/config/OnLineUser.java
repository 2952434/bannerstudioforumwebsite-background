package studio.banner.forumwebsite.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:00
 */
@WebListener
public class OnLineUser implements HttpSessionBindingListener {

    public OnLineUser(){

    }
    private Vector<HashMap> listenUser = new Vector<HashMap>();
    public int getCount(){
        listenUser.trimToSize();  // 调整Vector listenUser的容量为Vector listenUser的大小
        // 返回listenUser的容量
        return listenUser.capacity();
    }
    private boolean existUser(String userID){
        listenUser.trimToSize();
        boolean existUser = false;
        for (int i = 0; i<listenUser.capacity(); i++){
            if (userID.equals(listenUser.get(i).get("userId").toString())){
                existUser = true;
                break;
            }
        }
        return existUser;
    }
    public boolean deleteUser(String userId){
        listenUser.trimToSize();
        if (existUser(userId)){
            int currUserIndex = -1;
            for (int i = 0; i < listenUser.capacity(); i++){
                if (userId.equals(listenUser.get(i).get("userId").toString())){
                    currUserIndex = i;
                    break;
                }
            }
            if (currUserIndex != -1){
                listenUser.remove(currUserIndex);
                listenUser.trimToSize();
                return true;
            }
        }
        return false;
    }
    public Vector<HashMap> getOnLineUser(){
        return listenUser;
    }
    @Override
    public void valueBound(HttpSessionBindingEvent e){
        listenUser.trimToSize();
        System.out.println(e.getName() + "   登录到系统"+(new Date()));
        System.out.println("    在线用户数为："+ getCount());

    }
    @Override
    public void valueUnbound(HttpSessionBindingEvent e){
        listenUser.trimToSize();
        String userId = e.getName();
        deleteUser(userId);
        System.out.println(userId+ "   退出系统"+(new Date()));
        System.out.println("     在线用户数为："+getCount());
    }
}


