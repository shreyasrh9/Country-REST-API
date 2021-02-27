package com.itgma.shreyas.technicaltest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itgma.shreyas.technicaltest.entity.Country;
import com.itgma.shreyas.technicaltest.service.CountryService;

@RestController
public class CountryController {
	@Autowired
	private CountryService countryService;
	private static final String URI = "https://restcountries.eu/rest/v2/";
	private static final String ALL = "all/";
	private static final String REGION = "region/";
	private RestTemplate restTemplate = new RestTemplate();

	Logger logger = LoggerFactory.getLogger(CountryController.class);

	/*
	 * GET method - "Retrieve all countries from https://restcountries.eu/"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/countries")
	public List<Country> getAllCountries() {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(URI + ALL, String.class);
		return getCountryListFromJson(response);
	}

	/*
	 * GET method - "Retrieve countries based on region"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/countries/{region}")
	public List<Country> filterCountriesBasedOnRegion(@PathVariable String region) {
		String response = null;
		try {
			response = restTemplate.getForObject(URI + REGION + region, String.class);
		} catch (HttpClientErrorException.NotFound ex) {
			return Collections.EMPTY_LIST;
		}

		return getCountryListFromJson(response);
	}

	/*
	 * GET method - "Retrieve countries count based on region"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/countries/count")
	public Map<String, Integer> getCountriesCountBasedOnRegion() {
		String response = restTemplate.getForObject(URI + ALL, String.class);
		return getCountryCountByRegion(response);
	}

	/*
	 * Post method - "Add favourite country to DB"
	 */

	@RequestMapping(method = RequestMethod.POST, path = "/favourite-countries/add")
	public ResponseEntity<Object> addFavouriteCountry(@RequestBody Country country) {
		Country savedCountry = countryService.addFavouriteCountry(country);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCountry.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/*
	 * GET method - "Retrieve favourite countries from DB"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/favourite-countries/fetch")
	public List<Country> getFavouriteCountries() {
		return countryService.getFavouriteCountries();
	}

	/*
	 * GET method - "Retrieve favourite country by Id from DB"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/favourite-countries/fetch/{regionName}")
	public List<Country> getFavouriteCountriesByRegionName(@PathVariable String regionName) {
		return countryService.getFavouriteCountriesByRegionName(regionName);
	}

	/*
	 * Delete method - "Delete favourite country from DB"
	 */

	@RequestMapping(method = RequestMethod.DELETE, path = "/favourite-countries/delete/{countryName}")
	public void deleteFavouriteCountryByCountryName(@PathVariable String countryName) {
		countryService.deleteFavouriteCountryByCountryName(countryName);
	}

	private List<Country> getCountryListFromJson(String response) {
		List<Country> countriesList = new ArrayList<>();
		try {
			
			
			// Extract the list of countries from the response
			 
			JSONArray countryJsonArray = new JSONArray(response);

			for (int i = 0; i < countryJsonArray.length(); i++) {
				Country country = new Country();
				JSONObject objects = countryJsonArray.getJSONObject(i);
				country.setCountryName(objects.getString("name"));
				country.setCountryCapital(objects.getString("capital"));
				country.setCountryRegion(objects.getString("region"));
				country.setDefaultCurrencyCode(objects.getJSONArray("currencies").getJSONObject(0).optString("code"));
				country.setDefaultCurrencyName(objects.getJSONArray("currencies").getJSONObject(0).getString("name"));
				country.setDefaultCurrencySymbol(
						objects.getJSONArray("currencies").getJSONObject(0).optString("symbol"));
				country.setDefaultLanguagesName(objects.getJSONArray("languages").getJSONObject(0).getString("name"));
				countriesList.add(country);
			}

		} catch (JSONException e) {
			logger.error("getCountryListFromJson : " + e.getStackTrace());
		}
		return countriesList;
	}

	private Map<String, Integer> getCountryCountByRegion(String response) {
		Map<String, Integer> countryCount = new HashMap<>();
		try {
			
			// Extract and calculate the number of countries in a region
			
			JSONArray countryJsonArray = new JSONArray(response);

			for (int i = 0; i < countryJsonArray.length(); i++) {
				JSONObject objects = countryJsonArray.getJSONObject(i);
				String countryRegion = objects.getString("region");

				if (countryCount.containsKey(countryRegion)) {
					Integer count = countryCount.get(countryRegion);
					countryCount.put(countryRegion, count + 1);
				} else {
					countryCount.put(countryRegion, 1);
				}
			}

		} catch (JSONException e) {
			logger.error("getCountryCountByRegion : " + e.getStackTrace());
		}

		return countryCount;
	}

}
