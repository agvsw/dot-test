package com.agus.dot.exception.handler;

import com.agus.dot.dto.CommonResponse;
import com.agus.dot.exception.ProvinceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ProvinceNotFoundException.class)
    public ResponseEntity<CommonResponse> resp(ProvinceNotFoundException e){
        LOGGER.error(e.getMessage());
        LOGGER.warn(e.getMessage());
        LOGGER.info(e.getMessage());
        LOGGER.debug(e.getMessage());
        return new ResponseEntity<>(new CommonResponse(e.getCode(), e.getMessage()), HttpStatus.OK);
    }

}
