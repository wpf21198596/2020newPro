package com.example.open.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.response.ServerResponse;
import com.example.open.dao.WXMyHelpDao;
import com.example.open.dao.WXMyRequestDao;
import com.example.open.dao.WXUserDao;
import com.example.open.entity.WXMyHelp;
import com.example.open.entity.WXMyRequest;
import com.example.open.entity.WXUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/open/bargain")
public class BarGainController {

    String appID = "wx6c779704fe2eef4f";
    String appSecret = "183d78347c4682bf84d62e086c6018d9";
    String strUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WXUserDao wxUserDao;
    @Autowired
    private WXMyHelpDao wxMyHelpDao;
    @Autowired
    private WXMyRequestDao myRequestDao;

    @PostMapping("/registerUser")
    public ServerResponse registerUser(String code,String nickName) {
        System.out.println(nickName+"访问");
        org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget;
        String openId="";
        try {
            String href=strUrl+"?appid="+appID+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
            URL url = new URL(href);
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            httpget = new HttpGet(uri);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);
            JSONObject jsonObject = JSON.parseObject(res);
            openId=jsonObject.getString("openid");
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
        if(StringUtils.isBlank(openId))return ServerResponse.error();
        WXUser wxUser = new WXUser();
        wxUser.setOpenId(openId);
        Example<WXUser> example = Example.of(wxUser);
        Optional<WXUser> one = wxUserDao.findOne(example);
        if (one.isPresent())return ServerResponse.success(one.get().getId());
        wxUser.setNickName(nickName);
        wxUser.setCreateTime(new Date());
        wxUserDao.save(wxUser);
        return  ServerResponse.success(wxUser.getId());
    }

    @GetMapping("/getMyHelpRecord")
    public ServerResponse getMyHelpRecord(String id) {
        List<WXMyHelp> list=wxMyHelpDao.findByHelper(id);
        if(list.size()==0)return ServerResponse.error();
        return  ServerResponse.success(list);
    }

    @PostMapping("/createRequest")
    public ServerResponse createRequest(String command,String userId) {
        WXMyRequest wxMyRequest = new WXMyRequest();
        wxMyRequest.setCommand(command);
        wxMyRequest.setCreateTime(new Date());
        wxMyRequest.setRequester(userId);
        wxMyRequest.setState(0);
        myRequestDao.save(wxMyRequest);
        if(StringUtils.isNotBlank(wxMyRequest.getId()))return ServerResponse.success();
        return  ServerResponse.error();
    }

    @PostMapping("/getMyRequest")
    public ServerResponse getMyRequest(String userId) {
        List<WXMyRequest> list= myRequestDao.findByRequester(userId);
        if(list.size()>0)return  ServerResponse.error();
        list.forEach((wxMyRequest)->{
            List<WXMyHelp> helpList=wxMyHelpDao.findByBillId(wxMyRequest.getId());
            wxMyRequest.setHelpList(helpList);
        });
        return  ServerResponse.success(list);
    }

}
