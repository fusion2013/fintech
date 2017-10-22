package com.gjj.igden.dao.daoimpl;

import com.gjj.igden.dao.AccountByteArraysRowMapper;
import com.gjj.igden.dao.AccountRowMapper;
import com.gjj.igden.dao.AccountDao;
import com.gjj.igden.model.Account;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository("accountDao")
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    protected EntityManager entityManager;


    private NamedParameterJdbcTemplate namedParamJbd;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        namedParamJbd = new NamedParameterJdbcTemplate(dataSource);
    }

    public void setNamedParamJbd(
            NamedParameterJdbcTemplate namedParamJbd) {
        this.namedParamJbd = namedParamJbd;
    }

    public List<Account> getAllAccounts() {
        List<Account> list = entityManager.createNativeQuery("select * from account", Account.class).getResultList();
        System.out.println("Size of list " + list.size());
        return entityManager.createQuery("select a from Account a", Account.class).getResultList();
    }

    @Transactional
    public boolean delete(Account account) {
        boolean delete = false;
        try {
            Account a = entityManager.merge(account);
            entityManager.remove(a);
            delete = true;
        } catch (Exception e) {
            delete = false;
            e.printStackTrace();
        }
        return delete;
    }

    @Transactional
    public boolean delete(int id) {
        boolean delete = false;
        try {
            Account account = entityManager.find(Account.class, id);
            Account a = entityManager.merge(account);
            entityManager.remove(a);
            delete = true;
        } catch (Exception e) {
            delete = false;
            e.printStackTrace();
        }
        return  delete;
    }

    public Account getAccountById(int id) {
        return entityManager.find(Account.class, id);
    }

    @Transactional
    public boolean update(Account acc) {
        boolean update = false;
        try {
            entityManager.merge(acc);
            update = true;
        } catch (Exception e) {
            e.printStackTrace();
            update=false;
        }
        return  update;
    }

    @Transactional
    public boolean create(Account account) {
        boolean create = false;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            Date dateobj = new Date();
            String creationDate = df.format(dateobj);
            account.setCreationDate(creationDate);
            entityManager.persist(account);
            create = true;
        } catch (Exception e) {
            e.printStackTrace();
            create = false;
        }
        return  create;
    }

    @Transactional
    public boolean setImage(int accId, InputStream is) {
        boolean image = false;
        try {
            Account account = entityManager.find(Account.class,accId);
            account.setImage(IOUtils.toByteArray(is));
            entityManager.merge(account);
            image = true;
        } catch (Exception e) {
            e.printStackTrace();
            image=false;
        }
        return image;
    }

    @Override
    public byte[] getImage(int accId) {
        //getAccountById(accId);
       /* MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accId", accId, Types.INTEGER);
        String sqlQuery = "SELECT image from account WHERE account_id = :accId";
        byte[] bytes = namedParamJbd.query(sqlQuery, parameters, new AccountByteArraysRowMapper()).get(0);*/
        Account account = entityManager.find(Account.class, accId);
        return account.getImage();
    }
}
