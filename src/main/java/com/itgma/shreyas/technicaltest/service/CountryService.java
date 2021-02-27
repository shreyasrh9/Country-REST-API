package com.itgma.shreyas.technicaltest.service;

import java.util.List;

import com.itgma.shreyas.technicaltest.country.Country;

public interface CountryService {
	public Country save(Country country);
	public List<Country> findAll();
	public void deleteById(int id);
	public List<Country> getFavouriteCountryByRegionName(String regionName);
}
