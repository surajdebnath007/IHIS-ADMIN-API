package com.ihis.bindings;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CreatePlanForm {

	private String planName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;
	private int categoryId;
}
