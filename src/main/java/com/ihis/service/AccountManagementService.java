package com.ihis.service;

import java.util.List;
import java.util.Map;

import com.ihis.bindings.CreateAccountForm;
import com.ihis.bindings.CreatePlanForm;
import com.ihis.entities.AppPlanEntity;
import com.ihis.entities.CaseWorkersAccountEntity;

public interface AccountManagementService {

	public String upsertAccount(CreateAccountForm createAccountForm);

	public String delAccount(int accountId);

	public String softDelAccount(int accountId);

	public List<CaseWorkersAccountEntity> viewAccounts();

	public String createPlan(CreatePlanForm createPlanForm);

	public List<AppPlanEntity> viewPlans();

	public Map<Integer, String> loadPlanCategory();

	public String delPlan(int planId);
	
	public String softDelPlan(int planId);
}
