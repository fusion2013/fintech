package com.gjj.igden.dao.daoimpl;

import com.gjj.igden.dao.BaseDao;
import com.gjj.igden.model.BaseModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("baseDao")
@Transactional
public class BaseDaoImpl implements BaseDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public BaseModel save(BaseModel model) {
        if (model.getId() == null) {
            entityManager.persist(model);
            return model;
        } else {
            return entityManager.merge(model);
        }
    }

    @Override
    public BaseModel findById(Long id) {
        return entityManager.find(BaseModel.class, id);
    }

    @Override
    public List<BaseModel> findAll() {
        return entityManager.createQuery("select b from BaseModel b", BaseModel.class).getResultList();
    }
}
