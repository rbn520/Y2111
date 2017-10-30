package com.cn.qq.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping("qq")
public class LoginController {
	@RequestMapping("do_login.chm")
	public void doLogin(HttpServletRequest request,HttpServletResponse response) 
		throws IOException,QQConnectException {
			//调取用户授权页面
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
			
		}
	@RequestMapping("login")
	public void login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		AccessToken accessToken=new Oauth().getAccessTokenByQueryString(request.getQueryString(), request.getParameter("state"));
		
		String token=null;
		
		long expirIn;
		token=accessToken.getAccessToken();
		
		expirIn=accessToken.getExpireIn();
		
		OpenID openIDObj=new OpenID(token);
		
		String openid=openIDObj.getUserOpenID();
		
		UserInfo qzone=new UserInfo(token, openid);
		
		UserInfoBean qzoneUser=qzone.getUserInfo();
		
		response.getWriter().write(qzoneUser.toString());
		
		
		
		
		
		
	}
	}

