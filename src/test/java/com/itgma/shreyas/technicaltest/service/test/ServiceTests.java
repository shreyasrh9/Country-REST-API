package com.itgma.shreyas.technicaltest.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.itgma.shreyas.technicaltest.country.repositories.CountryRepository;
import com.itgma.shreyas.technicaltest.entity.Country;
import com.itgma.shreyas.technicaltest.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Autowired
	private CountryService countryService;

	@MockBean
	private CountryRepository countryRepository;

	@Test
	public void addFavouriteCountryTest() {
		Country country = new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan");
		when(countryRepository.save(country)).thenReturn(country);
		assertEquals(country, countryService.addFavouriteCountry(country));
	}

	@Test
	public void getFavouriteCountries() {
		when(countryRepository.findAll()).thenReturn(
				Stream.of(new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan"))
						.collect(Collectors.toList()));
		assertEquals(1, countryService.getFavouriteCountries().size());
	}

	@Test
	public void getCountryByRegionTest() {
		String region = "Europe";
		when(countryRepository.findByCountryRegion(region)).thenReturn(
				Stream.of(new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan"))
						.collect(Collectors.toList()));
		assertEquals(1, countryService.getFavouriteCountriesByRegionName(region).size());
	}

	@Test
	public void deleteById() throws Exception {
		countryService.deleteFavouriteCountryByCountryName("Andorra");
		verify(countryRepository, times(1)).deleteByCountryName("Andorra");
	}

	@Test
	public void getAllCountries() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "https://restcountries.eu/rest/v2/all";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void getCountriesByRegion() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "https://restcountries.eu/rest/v2/region/europe";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
}
