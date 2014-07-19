package cn.com.jsoup;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

/**
 * CSDN发博文
 * @author 杨尚川
 */
public class CsdnBloger {
    public static void main(String[] args) throws Exception{
        Map<String, String> cookies = login("bubaxiu", "csdn123");
        String title = "Java应用级产品开发平台APDPlat";
        String content = "APDPlat是Application Product Development Platform（应用级产品开发平台）的缩写。APDPlat提供了应用容器、多模块架构、代码生成、安装程序、认证授权、备份恢复、数据字典、web service、系统监控、操作审计、统计图、报表、机器绑定、防止破解、数据安全、内置搜索、数据转换、maven支持、WEB组件、内容管理、工作流、Web资源优化等功能。";
        String tags = "APDPlat";
        publishBlog(cookies, title, content, tags);
    }    
    public static void publishBlog(Map<String, String> cookies, String title, String content, String tags) throws Exception{
        String url = "http://write.blog.csdn.net/postedit?edit=1&isPub=1";
        Connection conn = Jsoup.connect(url)
                        .header("Accept", "*/*")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
                        .header("Connection", "keep-alive")
                        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")                        
                        .header("Host", "write.blog.csdn.net")
                        .header("Pragma", "no-cache")
                        .header("Referer", "http://write.blog.csdn.net/postedit")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0")                        
                        .header("X-Requested-With", "XMLHttpRequest")
                        .data("tags", tags)
                        .data("titl", title)
                        .data("typ", "1")
                        .data("cont", content)
                        .data("desc", "")
                        .data("flnm", "")
                        .data("chnl", "0")
                        .data("comm", "2")
                        .data("level", "0")
                        .data("tag2", "")
                        .data("artid", "0")
                        .data("stat", "publish")
                        .ignoreContentType(true);
        for(String cookie : cookies.keySet()){
            conn.cookie(cookie, cookies.get(cookie));
        }
        String text = conn.post().text();
        System.out.println(text);
    }    
    public static Map<String, String> login(String userName, String password) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        map.put("u", userName);
        map.put("p", password);
        map.put("t", "log");
        map.put("remember", "0");
        map.put("f", "http%3A%2F%2Fwww.csdn.net%2F");
        map.put("rand", "0.4835865827484527");
        Connection conn = Jsoup.connect("https://passport.csdn.net/ajax/accounthandler.ashx");
        conn.header("Accept", "*/*");
        conn.header("Accept-Encoding", "gzip, deflate");
        conn.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        conn.header("Host", "passport.csdn.net");
        conn.header("Referer", "https://passport.csdn.net/account/login");        
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
        conn.header("X-Requested-With", "XMLHttpRequest");
        Response response = conn.ignoreContentType(true).method(Method.POST).data(map).execute();
        System.out.println("用户登录返回信息："+response.body());
        Map<String, String> cookies = response.cookies();
        System.out.println("*******************************************************cookies start:");
        for(String key : cookies.keySet()){
        	System.out.println("key: " + key + "\tvalue: " + cookies.get(key));
        }
       
        System.out.println("*******************************************************cookies end:");
        return cookies;
    }
}
