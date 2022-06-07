package com.ihis.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihis.bindings.CreateAccountForm;
import com.ihis.bindings.CreatePlanForm;
import com.ihis.entities.AppPlanEntity;
import com.ihis.entities.CaseWorkersAccountEntity;
import com.ihis.entities.PlanCategoryEntity;
import com.ihis.repo.AppPlanRepository;
import com.ihis.repo.CaseWorkersAccountRepository;
import com.ihis.repo.PlanCategoryRepository;
import com.ihis.utils.EmailUtils;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {

	@Autowired
	private CaseWorkersAccountRepository repo;

	@Autowired
	private PlanCategoryRepository catRepo;

	@Autowired
	private AppPlanRepository planRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String delAccount(int accountId) {

		repo.deleteById(accountId);
		return "DELETED";
	}

	@Override
	public String softDelAccount(int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CaseWorkersAccountEntity> viewAccounts() {

		return repo.findAll();
	}

	@Override
	public List<AppPlanEntity> viewPlans() {

		return planRepo.findAll();
	}

	@Override
	public String delPlan(int planId) {

		planRepo.deleteById(planId);
		return "DELETED";
	}

	@Override
	public String softDelPlan(int planId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upsertAccount(CreateAccountForm createAccountForm) {

		CaseWorkersAccountEntity entity = new CaseWorkersAccountEntity();

		BeanUtils.copyProperties(createAccountForm, entity);

		entity.setActiveSwitch('Y');

		entity.setPassword(randomPasswordGenerator());

		CaseWorkersAccountEntity savedEntity = repo.save(entity);

		String fileName = "Account-Template.txt";
		String to = createAccountForm.getEmailId();
		String subject = "Account Created";
		String body = readMailBody(fileName, entity);

		boolean isSent = emailUtils.sendEmail(to, subject, body);

		if (savedEntity != null && isSent) {
			return "SUCCESS";
		}
		return "FAILED";
	}

	@Override
	public String createPlan(CreatePlanForm createPlanForm) {

		AppPlanEntity entity = new AppPlanEntity();

		entity.setActiveSwitch('Y');

		BeanUtils.copyProperties(createPlanForm, entity);

		AppPlanEntity savedEntity = planRepo.save(entity);

		if (savedEntity != null) {
			return "PLAN INSERTED SUCCESSFULLY";
		} else
			return "PLAN INSERTING FAILED";
	}

	@Override
	public Map<Integer, String> loadPlanCategory() {
		List<PlanCategoryEntity> category = catRepo.findAll();

		Map<Integer, String> categoryMap = new HashMap<>();

		for (PlanCategoryEntity entity : category) {
			categoryMap.put(entity.getCategoryId(), entity.getCategoryName());
		}

		return categoryMap;
	}

	private String randomPasswordGenerator() {

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	private String readMailBody(String fileName, CaseWorkersAccountEntity entity) {

		String mailBody = null;
		try {
			StringBuffer buffer = new StringBuffer();
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine();

			while (line != null) {
				buffer.append(line);
				line = br.readLine();
			}
			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FNAME}", entity.getFullName());
			mailBody = mailBody.replace("{PWD}", entity.getPassword());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmailId());

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}

}
