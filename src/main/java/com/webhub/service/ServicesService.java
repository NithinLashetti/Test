package com.webhub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.webhub.config.SquidexConfig;
import com.webhub.model.DServices;
import com.webhub.model.ServicesCategoryResponse;
import com.webhub.model.workshopD;

@Service
public class ServicesService {

	@Value("${squidex.base.url}")
	private String squidexBaseUrl;

	@Autowired
	private SquidexConfig squidexConfig;

	@Autowired
	private RestTemplate restTemplatee;

	// it will get all the services
	public ResponseEntity<?> getAllServices() {
		DServices service = new DServices();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.squidexConfig.getToken());
		headers.add("X-Flatten", "True");
		headers.add("X-Languages", "en");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<>(headers);
		String url = this.squidexBaseUrl + "d-serviceses/";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		ResponseEntity<SquidexBaseResponse> responseBaseEntity;
		try {
			responseBaseEntity = restTemplatee.exchange(uriBuilder.toUriString(), HttpMethod.GET, request,
					SquidexBaseResponse.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.ofNullable("Invalid Profile details");

		}

		SquidexBaseResponse squidexBaseResponse = responseBaseEntity.getBody();
		List<SquidexItem> item = squidexBaseResponse.getItems();
		List<Map<String, Object>> responseData = new ArrayList<>();
		if (item != null) {
			for (SquidexItem itemm : item) {
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("data", itemm.getData());
				responseMap.put("id", itemm.getId());
				System.out.println("All data in d-servicess: " + itemm.getData());
				responseData.add(responseMap);

			}
		}
		return ResponseEntity.ok(responseData);

	}

	// it will get the services only if true is there
	public ResponseEntity<?> getAllServicesByFlag() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.squidexConfig.getToken());
		headers.add("X-Flatten", "True");
		headers.add("X-Languages", "en");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<>(headers);
		String url = this.squidexBaseUrl + "d-serviceses/";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		ResponseEntity<SquidexBaseResponse> responseBaseEntity;
		try {
			responseBaseEntity = restTemplatee.exchange(uriBuilder.toUriString(), HttpMethod.GET, request,
					SquidexBaseResponse.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.ofNullable("Invalid Profile details");

		}

		SquidexBaseResponse squidexBaseResponse = responseBaseEntity.getBody();
		List<SquidexItem> item = squidexBaseResponse.getItems();
		List<Map<String, Object>> responseData = new ArrayList<>();
		if (item != null) {
			for (SquidexItem itemm : item) {
				System.out.println("All data in d-servicess: " + itemm.getData());
				HashMap<String, Object> data = itemm.getData();
				if (data != null && Boolean.TRUE.equals(data.get("defaultPromotingService"))) {
					responseData.add(data);

				}

			}
		}
		/*
		 * service.setId(item.getId()); HashMap<String, Object> detail = item.getData();
		 * service.setIcon(detail.get("icon"));
		 * service.setShortDescription(detail.get("shortDescription"));
		 * service.setServiceName(detail.get("serviceName"));
		 * service.setCategory(detail.get("category")); System.out.println(service);
		 */

		return ResponseEntity.ok(responseData);

	}
	
	//this will get all the categories
	public ResponseEntity<?> getAllCategories() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.squidexConfig.getToken());
		headers.add("X-Flatten", "True");
		headers.add("X-Languages", "en");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<>(headers);
		String url = this.squidexBaseUrl + "d-service-categories/";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		ResponseEntity<SquidexBaseResponse> responseBaseEntity;
		try {
			responseBaseEntity = restTemplatee.exchange(uriBuilder.toUriString(), HttpMethod.GET, request,
					SquidexBaseResponse.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.ofNullable("Invalid Profile details");

		}

		SquidexBaseResponse squidexBaseResponse = responseBaseEntity.getBody();
		List<SquidexItem> item = squidexBaseResponse.getItems();
		
		List<Map<String, Object>> responseData1 = new ArrayList<>();
		if (item != null) {
			for (SquidexItem itemm : item) {
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("id", itemm.getId());
				responseMap.put("data", itemm.getData());
				responseData1.add(responseMap);
			}
		}
		return ResponseEntity.ok(responseData1);
	}

	// it will get all the services
	public ResponseEntity<?> getServiceWithCategory() {
		
		//1. here first i will get the data from d-services api and extract category id 
		//2. and pass that id to d-service-category api and get the data
		//3. then i will send the final response with both service data and category data
		
		DServices service = new DServices();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.squidexConfig.getToken());
		headers.add("X-Flatten", "True");
		headers.add("X-Languages", "en");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<>(headers);
		String url = this.squidexBaseUrl + "d-service-categories/";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		ResponseEntity<SquidexBaseResponse> responseBaseEntity;
		try {
			responseBaseEntity = restTemplatee.exchange(uriBuilder.toUriString(), HttpMethod.GET, request,
					SquidexBaseResponse.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.ofNullable("Invalid Profile details");

		}

		SquidexBaseResponse squidexBaseResponse = responseBaseEntity.getBody();
		List<SquidexItem> item = squidexBaseResponse.getItems();
		List<Map<String, Object>> responseData = new ArrayList<>();
		
		
		for(SquidexItem itemm:item) {
			Map<String, Object> data=new HashMap<>();
			data.put("id", itemm.getId());
			data.put("data", itemm.getData());
			responseData.add(data);
		}
		
		
		
		
		/*List<Map<String, Object>> responseData = new ArrayList<>();
	
		if (item != null) {
			for (SquidexItem itemm : item) {
				Map<String, Object> responseMap = new HashMap<>();
				List<Object> li=(List<Object>) itemm.getData().get("category");
				
				for(Object list:li) {
					System.out.println(list);
					
				}
				System.out.println(li.get(0));//here it is getting first category id, if multiple are there
				System.out.println(itemm.getData().get("category"));
				responseMap.put("data", itemm.getData());
				responseMap.put("id", itemm.getId());
				System.out.println("All data in d-servicess: " + itemm.getData());
				responseData.add(responseMap);

			}*/
		
		return ResponseEntity.ok(responseData);

	}

	public ResponseEntity<?> serviceCategory(String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.squidexConfig.getToken()); 
		headers.add("X-Flatten", "True");
		headers.add("X-Languages", "en");
		HttpEntity<String> request = new HttpEntity<>(headers); 
		String url = this.squidexBaseUrl + "d-serviceses/"; 
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		uriBuilder.queryParam("$filter", "data/category/iv+eq+'" + id + "'");
		ResponseEntity<SquidexBaseResponse> responseBaseEntity = restTemplatee.exchange(uriBuilder.toUriString(),
				HttpMethod.GET, request, SquidexBaseResponse.class);// to the Rest Template we are passing full URL with
																	// all end points, GET request,Headers

		SquidexBaseResponse squidexBaseResponse = responseBaseEntity.getBody();
		List<SquidexItem> it=squidexBaseResponse.getItems();
		
		return ResponseEntity.ok(it);
	}

}
