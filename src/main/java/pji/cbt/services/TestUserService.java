package pji.cbt.services;

import java.util.List;

import pji.cbt.entities.TestUser;

public interface TestUserService {
	
	public List<TestUser> findTestByUserId(int userId);
	
	public List<TestUser> findTestHaveAssign(int userId);
	
	public void saveTest(TestUser testUser);
	
	public List<TestUser> findUserSummaryScore();
	
	public List<TestUser> findTestAssignment(int userId);
	
	public void deleteByIdUserAndIdCategory(TestUser testUser);
	
	public void updateStartTest(TestUser testUser);
	
	public void updateEndTest(TestUser testUser);
}
