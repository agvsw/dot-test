package com.agus.dot.dto;

import com.agus.dot.dto.CommonModel;

public class Response<T> {
    private CommonModel<T> rajaongkir;

    public CommonModel<T> getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(CommonModel<T> rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
