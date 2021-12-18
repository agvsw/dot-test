package com.agus.dot.service.impl;

import com.agus.dot.exception.ProvinceNotFoundException;
import com.agus.dot.model.ProvinceModel;
import com.agus.dot.repository.ProvinceRepository;
import com.agus.dot.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private Environment env;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<ProvinceModel> getAllProvince() {
        List<ProvinceModel> provinceModelList;
        provinceModelList = provinceRepository.findAll();
        if (provinceModelList.isEmpty()){
            getAllFromService();
            provinceModelList = provinceRepository.findAll();
        }
        return provinceModelList;
    }

    @Override
    public ProvinceModel getById(String id) throws ProvinceNotFoundException {
        ProvinceModel provinceModel = new ProvinceModel();
        Optional<ProvinceModel> model;
        model = provinceRepository.findById(id);
        if (model.isEmpty()){
            provinceModel = getByIdFromService(id);
        }else {
            long different = new Date().getTime() - model.get().getUpdatedAt().getTime();
            different = TimeUnit.MILLISECONDS.toMinutes(different);
            // check if data in cache more than 10 minutes, if true will get from api and update in cache
            if(different >= 10){
                provinceModel = getByIdFromService(id);
            }else {
                provinceModel = model.get();
            }
        }
        return provinceModel;
    }

    private ProvinceModel getByIdFromService(String id) throws ProvinceNotFoundException {

        final String url = env == null ? "" :env.getProperty("raja-ongkir-url");
        final String apiKey = env == null ? "" :env.getProperty("api-key");

        HttpHeaders headers = new HttpHeaders();
        headers.set("key", apiKey);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url+ "/province")
                .queryParam("id",id).build();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                requestEntity, Map.class);

        Map<String, Object> res = (Map<String, Object>) response.getBody().get("rajaongkir");
        ProvinceModel responseModel = new ProvinceModel();

        try{
            Map<String, Object> result = (Map<String, Object>) res.get("results");
            responseModel.setProvince_id((String) result.get("province_id"));
            responseModel.setProvince((String) result.get("province"));
            provinceRepository.save(responseModel);
        }catch (ClassCastException e){
//            ClassCastException because if data nothing result will return an array [] so can't be cast to map<object, object>
            throw new ProvinceNotFoundException(44, String.format("Province with id %s not found", id));
        }
        return responseModel;
    }

    private void getAllFromService() {
        final String url = env == null ? "" :env.getProperty("raja-ongkir-url");
        final String apiKey = env == null ? "" :env.getProperty("api-key");

        HttpHeaders headers = new HttpHeaders();
        headers.set("key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(url+ "/province", HttpMethod.GET,
                entity, Map.class);

        Map<String, Object> res = (Map<String, Object>) response.getBody().get("rajaongkir");

        List<Map<String, Object>> result = (List<Map<String, Object>>) res.get("results");

        List<ProvinceModel> res1 = new ArrayList<>();
        for (Map model: result){
            ProvinceModel provinceModel = new ProvinceModel();
            provinceModel.setProvince_id((String) model.get("province_id"));
            provinceModel.setProvince((String) model.get("province"));
            res1.add(provinceModel);
        }
        provinceRepository.saveAll(res1);
    }
}
