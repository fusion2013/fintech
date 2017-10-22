package com.gjj.igden.dao;

import com.gjj.igden.model.BaseModel;

import java.util.List;

public interface BaseDao {
    BaseModel save(BaseModel model);

    BaseModel findById(Long id);

    List<BaseModel> findAll();
}
