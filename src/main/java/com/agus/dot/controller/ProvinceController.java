package com.agus.dot.controller;

import com.agus.dot.dto.CommonResponse;
import com.agus.dot.exception.ProvinceNotFoundException;
import com.agus.dot.model.ProvinceModel;
import com.agus.dot.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;


    @GetMapping("/provinces")
    public CommonResponse<List<ProvinceModel>> getAllProvince(){
        CommonResponse<List<ProvinceModel>> response = new CommonResponse<>();
        List<ProvinceModel> provinceModel = provinceService.getAllProvince();
        response.setData(provinceModel);
        return response;
    }

    @GetMapping("/province")
    public CommonResponse<ProvinceModel> getProvinceById(
            @RequestParam String id
    ) throws ProvinceNotFoundException {
        CommonResponse<ProvinceModel> response = new CommonResponse<>();
        ProvinceModel provinceModel = provinceService.getById(id);
        response.setData(provinceModel);
        return response;
    }
}
