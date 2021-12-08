package com.example.demo.service.impl;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import edu.umd.cs.findbugs.annotations.CheckForNull;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHDeployKey;
import org.kohsuke.github.GHRef;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vivek on 8/21/18.
 */
@Service
public class UserServiceimpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto getUserById(Integer userId) {
		return UserConverter.entityToDto(userRepository.getOne(userId));
	}

	@Override
	public void saveUser(UserDto userDto) {
		
		if (userDto.getEnv()!=null) {

			if (userDto.getEnv().equalsIgnoreCase("dev") || userDto.getEnv().equalsIgnoreCase("qa")
					|| userDto.getEnv().equalsIgnoreCase("perf")) {

				userDto.setAzTenantID("cbf6410b-fe4c-4b4c-8420-3f69a6ce199e");
				userDto.setSubscriptionID("b62b39b7-71df-44f0-a7fe-ae78cf18c2b8");
				userDto.setSubscriptionNm("alpha-eng-sbx");
				userDto.setResourceGp("alphaeng-qa-rg-001-dev-eastus");

			} else if (userDto.getEnv().equalsIgnoreCase("d1")) {

				userDto.setAzTenantID("f7d9f3f1-3841-4bfc-9cd4-c0e1964b5f86");
				userDto.setSubscriptionID("4cdde026-a1dc-48ef-98be-b48892c3f96d");
				userDto.setSubscriptionNm("agrid-01-d1-spk-crd-sub-001");
				userDto.setResourceGp("agrid-01-d1-tenant-rg-001");

			} else {

				userDto.setAzTenantID("cbf6410b-fe4c-4b4c-8420-3f69a6ce199e");
				userDto.setSubscriptionID("9db93dfd-6382-4fe2-8fe6-9d2b4d79e1db");
				userDto.setSubscriptionNm("crd-eng-platform");
				userDto.setResourceGp("cpe-rg-dr-eng-001-eastus2");

			}

		}
		
		User user = UserConverter.dtoToEntity(userDto);


		// Printing the contents of a file
		if (user.getJiraID() != null && user.getReqEmail() != null && user.getAppln() != null && user.getEnv() != null
				&& user.getSpnName() != null && user.getAzTenantID() != null && user.getSubscriptionID() != null
				&& user.getSubscriptionNm() != null && user.getResourceGp() != null && user.getAdSvc() != null
				&& user.getClientTenantID() != null) {
			
			System.out.println("JIRA #=" + user.getJiraID());
			System.out.println("REQUESTOR's EMAIL=" + user.getReqEmail());
			System.out.println("APPLICATION=" + user.getAppln());
			System.out.println("ENV=" + user.getEnv());
			System.out.println("SPN NAME=" + user.getSpnName());
			System.out.println("AZURE TENANT ID=" + user.getAzTenantID());
			System.out.println("SUBSCRIPTION ID=" + user.getSubscriptionID());
			System.out.println("SUBSCRIPTION NAME=" + user.getSubscriptionNm());
			System.out.println("RESOURCE GROUP=" + user.getResourceGp());
			System.out.println("AD SVC=" + user.getAdSvc());
			System.out.println("CLIENT TENANT ID=" + user.getClientTenantID());

			pushToRemoteRepo(user);

			// Display message to be printed on the console
			// window after successful execution of the
			// program
			System.out.println("Config file is created successfully and pushed.");

			userRepository.save(user);			
		}
		
	}

	private void pushToRemoteRepo(User user) {

		final String githubUrl = "https://azgithub.ops.crd.com/api/v3/";
		final String org = "Alpha-Grid-Platform-Engineering";
		final String repo = "agrid-ado-tenant-shell-configs";
		final String configDir = "/configs";
		String login = "crdops_azgitops_svc";
		String accessToken = "dc88eacb247fc76cab9256e21d47c5c841df7613";
		
		final String configContent = "JIRA_TICKET=" + user.getJiraID() + "\nREQUESTOR_EMAIL=" + user.getReqEmail()
		+ "\nAPPLICATION=" + user.getAppln() + "\nENV=" + user.getEnv() + "\nSPN_NAME="
		+ user.getSpnName() + "\nTENANT_ID=" + user.getAzTenantID() + "\nSUBSCRIPTION_ID="
		+ user.getSubscriptionID() + "\nSUBSCRIPTION_NAME=" + user.getSubscriptionNm()
		+ "\nRESOURCE_GROUP=" + user.getResourceGp() + "\nAD_SVC=" + user.getAdSvc()
		+ "\nCLIENT_TENANT_ID=" + user.getClientTenantID()+"\n";
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");

		try {

			// Connect to GitHub Enterprise API
			GitHub gitHub = GitHub.connectToEnterpriseWithOAuth(githubUrl, login, accessToken);		
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			// Create branch version - timestamp with client tenant id
			gitHub.getOrganization(org).getRepository(repo).createRef("refs/heads/" + user.getClientTenantID() +"-"+sdf.format(timestamp),
					"1420f6bebab8ac79ef6805ca0a31bf7f98d5cc22");
			
			// Create config txt with the content and push it to the client tenant id branch
			gitHub.getOrganization(org).getRepository(repo).createContent()
					.content(configContent)
					.message("Updated " + user.getClientTenantID() + " config.txt")
					.sha("1420f6bebab8ac79ef6805ca0a31bf7f98d5cc22")
					.path("configs/" + user.getClientTenantID() + "-config.txt")
					.branch(user.getClientTenantID()+"-"+sdf.format(timestamp))
					.commit();
			
			//Create a PR for the client tenant id to master branch
			gitHub.getOrganization(org).getRepository(repo).createPullRequest(
					"Create PR for " + user.getClientTenantID()+"-"+sdf.format(timestamp), user.getClientTenantID() + "-" + sdf.format(timestamp),
					"master", configContent);

		}

		// Catch block to handle if exception occurs
		catch (IOException e) {
			// Print the exception
			System.out.print(e.getMessage());
		}

	}
	
	@Override
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(UserConverter::entityToDto).collect(Collectors.toList());

	}
}
