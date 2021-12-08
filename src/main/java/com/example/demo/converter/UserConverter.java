package com.example.demo.converter;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

/**
 * Created by Vivek on 11/16/21.
 */
public class UserConverter {
	public static User dtoToEntity(UserDto userDto) {		
	
		User user = new User(userDto.getUserId(), userDto.getJiraID(), userDto.getReqEmail(), userDto.getAppln(),
				userDto.getEnv(), userDto.getSpnName(), userDto.getAzTenantID(), userDto.getSubscriptionID(),
				userDto.getSubscriptionNm(), userDto.getResourceGp(), userDto.getAdSvc(), userDto.getClientTenantID());
		
		user.setUserId(userDto.getUserId());
		user.setJiraID(userDto.getJiraID());
		user.setReqEmail(userDto.getReqEmail());
		user.setAppln(userDto.getAppln());
		user.setEnv(userDto.getEnv());
		user.setSpnName(userDto.getSpnName());
		user.setAzTenantID(userDto.getAzTenantID());
		user.setSubscriptionID(userDto.getSubscriptionID());
		user.setSubscriptionNm(userDto.getSubscriptionNm());
		user.setResourceGp(userDto.getResourceGp());
		user.setAdSvc(userDto.getAdSvc());
		user.setClientTenantID(userDto.getClientTenantID());

		return user;
	}

	public static UserDto entityToDto(User user) {
		UserDto userDto = new UserDto(user.getUserId(), user.getJiraID(), user.getReqEmail(), user.getAppln(),
				user.getEnv(), user.getSpnName(), user.getAzTenantID(), user.getSubscriptionID(),
				user.getSubscriptionNm(), user.getResourceGp(), user.getAdSvc(), user.getClientTenantID());
		return userDto;
	}
}
