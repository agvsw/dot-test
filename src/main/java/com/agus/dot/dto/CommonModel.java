package com.agus.dot.dto;

public class CommonModel<T> {
    private Object[] query;
    private Status status;
    private T result;

    public Object[] getQuery() {
        return query;
    }

    public void setQuery(Object[] query) {
        this.query = query;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
