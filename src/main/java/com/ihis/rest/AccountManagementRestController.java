package com.ihis.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihis.bindings.CreateAccountForm;
import com.ihis.bindings.CreatePlanForm;
import com.ihis.entities.AppPlanEntity;
import com.ihis.entities.CaseWorkersAccountEntity;
import com.ihis.service.AccountManagementService;

@RestController
public class AccountManagementRestController {

	@Autowired
	private AccountManagementService accService;

	@PostMapping("/upsertaccount")
	public String upsertAccount(CreateAccountForm createAccountForm) {
		return accService.upsertAccount(createAccountForm);
	}

	@GetMapping("/viewAccounts")
	public List<CaseWorkersAccountEntity> viewAccounts() {
		return accService.viewAccounts();
	}

	@DeleteMapping("/delaccount/{accountId}")
	public String deleteAccount(@PathVariable("accountId") int accountId) {
		return accService.delAccount(accountId);
	}

	@PostMapping("/softdelaccount/{accountId}")
	public String softDelAccount(@PathVariable("accountId") int accountId) {
		return accService.softDelAccount(accountId);
	}

	@GetMapping("/plancategory")
	public Map<Integer, String> loadPlanCategory() {
		return accService.loadPlanCategory();
	}

	@PostMapping("/upsertplan")
	public String upsertPlan(CreatePlanForm createPlanForm) {
		return accService.createPlan(createPlanForm);
	}

	@GetMapping("/viewPlans")
	public List<AppPlanEntity> viewPlans() {
		return accService.viewPlans();
	}

	@DeleteMapping("/delplan/{planId}")
	public String delPlan(@PathVariable("planId") int planId) {
		return accService.delPlan(planId);
	}

	@PostMapping("/softdelplan/{planId}")
	public String softDelPlan(@PathVariable("planId") int planId) {
		return accService.softDelPlan(planId);
	}
}
