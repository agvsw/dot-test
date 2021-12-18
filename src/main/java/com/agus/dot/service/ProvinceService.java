package com.agus.dot.service;

import com.agus.dot.exception.ProvinceNotFoundException;
import com.agus.dot.model.ProvinceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProvinceService {
    List<ProvinceModel> getAllProvince();
    ProvinceModel getById(String id) throws ProvinceNotFoundException;
}
