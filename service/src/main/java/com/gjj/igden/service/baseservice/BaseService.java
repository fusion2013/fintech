package com.gjj.igden.service.baseservice;

import com.gjj.igden.dao.BaseDao;
import com.gjj.igden.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {

    @Autowired
    BaseDao baseDao;

    public BaseModel save(BaseModel model) {
        return baseDao.save(model);
    }


    public BaseModel findById(Long id) {
        return baseDao.findById(id);
    }


    public List<BaseModel> findAll() {
        return baseDao.findAll();
    }
}
