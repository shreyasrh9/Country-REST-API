package com.itgma.shreyas.technicaltest.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.itgma.shreyas.technicaltest.controller.CountryController;
import com.itgma.shreyas.technicaltest.country.Country;
import com.itgma.shreyas.technicaltest.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
	@InjectMocks
	CountryController countrycontroller;
	
	@Mock
	CountryService countryService;
	
	@Test
	public void saveCountryTest() {
		Country country = new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan");
		when(countryService.addFavouriteCountry(any(Country.class))).thenReturn(country);
        ResponseEntity<Object> responseEntity = countrycontroller.addFavouriteCountry(country);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/376");
    }
	
	@Test
	public void getFavouriteCountriesTest() {
		List<Country> countries = new ArrayList<Country>();
		Country country = new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan");
		countries.add(country);
		when(countryService.getFavouriteCountries()).thenReturn(countries);
		List<Country> returnedCountries = countrycontroller.getFavouriteCountries();
		assertThat(returnedCountries).isEqualTo(countries);
    }
	
	@Test
	public void getFavouriteCountryByRegionNameTest() {
		List<Country> countries = new ArrayList<Country>();
		Country country = new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan");
		countries.add(country);
		when(countryService.getFavouriteCountryByRegionName(any(String.class))).thenReturn(countries);
		List<Country> returnedCountries = countrycontroller.getFavouriteCountryByRegionName("Europe");
		assertThat(returnedCountries).isEqualTo(countries);
	}
	
	@Test
	public void deleteCountryById() {
		countrycontroller.deleteFavouriteCountryById(376);
		verify(countryService, times(1)).deleteFavouriteCountryById(376);
	}
	
}
