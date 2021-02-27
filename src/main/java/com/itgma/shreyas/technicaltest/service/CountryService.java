package com.itgma.shreyas.technicaltest.service;

import java.util.List;

import com.itgma.shreyas.technicaltest.country.Country;

public interface CountryService {
	public List<Country> getFavouriteCountryByRegionName(String regionName);
	public Country addFavouriteCountry(Country country);
	public List<Country> getFavouriteCountries();
	public void deleteFavouriteCountryById(int id);
}
