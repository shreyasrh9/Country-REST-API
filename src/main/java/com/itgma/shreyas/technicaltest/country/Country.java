package com.itgma.shreyas.technicaltest.country;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Country details")
@Entity
public class Country {
	@JsonIgnore
	@Id
	@GeneratedValue
	private Integer id;
	
	private String countryName;
	
	private String countryCapital;
	
	private String countryRegion;
	
	private String defaultCurrencyCode;
	
	private String defaultCurrencyName;
	
	private String defaultCurrencySymbol;
	
	private String defaultLanguagesName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCapital() {
		return countryCapital;
	}

	public void setCountryCapital(String countryCapital) {
		this.countryCapital = countryCapital;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getDefaultCurrencyCode() {
		return defaultCurrencyCode;
	}

	public void setDefaultCurrencyCode(String defaultCurrencyCode) {
		this.defaultCurrencyCode = defaultCurrencyCode;
	}

	public String getDefaultCurrencyName() {
		return defaultCurrencyName;
	}

	public void setDefaultCurrencyName(String defaultCurrencyName) {
		this.defaultCurrencyName = defaultCurrencyName;
	}

	public String getDefaultCurrencySymbol() {
		return defaultCurrencySymbol;
	}

	public void setDefaultCurrencySymbol(String defaultCurrencySymbol) {
		this.defaultCurrencySymbol = defaultCurrencySymbol;
	}

	public String getDefaultLanguagesName() {
		return defaultLanguagesName;
	}

	public void setDefaultLanguagesName(String defaultLanguagesName) {
		this.defaultLanguagesName = defaultLanguagesName;
	}
	
	
}
