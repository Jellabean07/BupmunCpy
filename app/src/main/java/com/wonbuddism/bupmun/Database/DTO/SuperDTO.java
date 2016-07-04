package com.wonbuddism.bupmun.Database.DTO;

/**
 * Created by user on 2016-01-09.
 */
public abstract class SuperDTO {
    public String[] query; // 쿼리
    public abstract String[] getQuery();
}
