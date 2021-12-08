package com.example.demo.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vivek on 7/31/18.
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 0x62A6DA99AABDA8A8L;

	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer userId;
	@Column
	private String jiraID;
	@Column
	private String reqEmail;
	@Column
	private String appln;
	@Column
	private String env;
	@Column
	private String spnName;
	@Column
	private String azTenantID;
	@Column
	private String subscriptionID;
	@Column
	private String subscriptionNm;
	@Column
	private String resourceGp;
	@Column
	private String adSvc;
	@Column
	private String clientTenantID;

	/**
	 * @param userId
	 * @param jiraID
	 * @param reqEmail
	 * @param appln
	 * @param env
	 * @param spnName
	 * @param azTenantID
	 * @param subscriptionID
	 * @param subscriptionNm
	 * @param resourceGp
	 * @param adSvc
	 * @param clientTenantID
	 */

	public User(Integer userId, String jiraID, String reqEmail, String appln, String env, String spnName,
			String azTenantID, String subscriptionID, String subscriptionNm, String resourceGp, String adSvc,
			String clientTenantID) {
		super();
		this.userId = userId;
		this.jiraID = jiraID;
		this.reqEmail = reqEmail;
		this.appln = appln;
		this.env = env;
		this.spnName = spnName;
		this.azTenantID = azTenantID;
		this.subscriptionID = subscriptionID;
		this.subscriptionNm = subscriptionNm;
		this.resourceGp = resourceGp;
		this.adSvc = adSvc;
		this.clientTenantID = clientTenantID;
	}

	public User() {
	}

	@JsonIgnore
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getJiraID() {
		return jiraID;
	}

	public void setJiraID(String jiraID) {
		this.jiraID = jiraID;
	}

	public String getReqEmail() {
		return reqEmail;
	}

	public void setReqEmail(String reqEmail) {
		this.reqEmail = reqEmail;
	}

	public String getAppln() {
		return appln;
	}

	public void setAppln(String appln) {
		this.appln = appln;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getSpnName() {
		return spnName;
	}

	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}

	public String getAzTenantID() {
		return azTenantID;
	}

	public void setAzTenantID(String azTenantID) {
		this.azTenantID = azTenantID;
	}

	public String getSubscriptionID() {
		return subscriptionID;
	}

	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}

	public String getSubscriptionNm() {
		return subscriptionNm;
	}

	public void setSubscriptionNm(String subscriptionNm) {
		this.subscriptionNm = subscriptionNm;
	}

	public String getResourceGp() {
		return resourceGp;
	}

	public void setResourceGp(String resourceGp) {
		this.resourceGp = resourceGp;
	}

	public String getAdSvc() {
		return adSvc;
	}

	public void setAdSvc(String adSvc) {
		this.adSvc = adSvc;
	}

	public String getClientTenantID() {
		return clientTenantID;
	}

	public void setClientTenantID(String clientTenantID) {
		this.clientTenantID = clientTenantID;
	}

}
