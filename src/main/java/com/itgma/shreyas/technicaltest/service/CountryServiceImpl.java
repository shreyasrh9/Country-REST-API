package com.itgma.shreyas.technicaltest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itgma.shreyas.technicaltest.country.repositories.CountryRepository;
import com.itgma.shreyas.technicaltest.entity.Country;

@Service
public class CountryServiceImpl implements CountryService{
	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public Country addFavouriteCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public List<Country> getFavouriteCountries() {
		return countryRepository.findAll();
	}

	@Override
	public List<Country> getFavouriteCountriesByRegionName(String regionName) {
		return countryRepository.findByCountryRegion(regionName);
	}

	@Override
	public void deleteFavouriteCountryByCountryName(String countryName) {
		countryRepository.deleteByCountryName(countryName);
	}
}
