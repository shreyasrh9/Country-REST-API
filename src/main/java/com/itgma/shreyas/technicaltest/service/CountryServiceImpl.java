package com.itgma.shreyas.technicaltest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itgma.shreyas.technicaltest.country.Country;
import com.itgma.shreyas.technicaltest.country.repositories.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService{
	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public Country save(Country country) {
		// TODO Auto-generated method stub
		return countryRepository.save(country);
	}

	@Override
	public List<Country> findAll() {
		// TODO Auto-generated method stub
		return countryRepository.findAll();
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		countryRepository.deleteById(id);
	}

	@Override
	public List<Country> getFavouriteCountryByRegionName(String regionName) {
		// TODO Auto-generated method stub
		return countryRepository.findByCountryRegion(regionName);
	}

}
