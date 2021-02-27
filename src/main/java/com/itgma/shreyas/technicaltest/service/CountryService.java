package com.itgma.shreyas.technicaltest.service;

import java.util.List;

import com.itgma.shreyas.technicaltest.entity.Country;

public interface CountryService {
	public Country addFavouriteCountry(Country country);
	public List<Country> getFavouriteCountries();
	List<Country> getFavouriteCountriesByRegionName(String regionName);
	public void deleteFavouriteCountryByCountryName(String countryName);
}
