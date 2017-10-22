package com.gjj.igden.dao.daoimpl;

import com.gjj.igden.dao.BarDao;
import com.gjj.igden.dao.daoUtil.DaoException;
import com.gjj.igden.model.Bar;
import com.gjj.igden.model.BarSetId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")

@Transactional
@Component
public class BarDaoImpl  {

  @Resource
  BarDao barDao;


  @Transactional
  public Bar getSingleBar(long md_id, String instId) {
    BarSetId barSetId = new BarSetId(md_id, instId);
    return barDao.findOne(barSetId);
  }

  public List<Bar> getBarList(String instId) {
    List<Bar> resultList = barDao.findByInstId(instId);
    System.out.print(resultList.size());
    return  resultList;
  }

  @Transactional
  public boolean createBar(Bar bar) throws DaoException {
    boolean created = false;
    try {
      barDao.saveAndFlush(bar);
      created=true;
    } catch (Exception e) {
      e.printStackTrace();
      created=false;
    }
    return  created;
  }

  @Transactional
  public boolean updateBar(Bar bar) {
    boolean update = false;
    try {
        barDao.saveAndFlush(bar);
        //entityManager.merge(bar);
        update = true;
    } catch (Exception e) {
      e.printStackTrace();
      update = false;
    }
    return update;
  }

  @Transactional
  public boolean deleteBar(long mdId, String instId) {
      boolean delete = false;
     try {
         barDao.delete(new BarSetId(mdId, instId));
         delete = true;
     } catch(Exception e) {
        e.printStackTrace();
         delete = false;

     }
      return delete;
  }

  @Transactional
  public boolean deleteBar(Bar bar) {
    return deleteBar(bar.getId().getId(), bar.getId().getInstId());
  }

  public List<String> searchTickersByChars(String tickerNamePart) {

    List<Object> list =  barDao.findByTicketName(tickerNamePart);
    List<String>  l = new ArrayList<String>();
    if(list != null && list.size()>0) {
      for(Object o : list) {
        String s = (String) o;
        l.add(s);
      }
    }
    return l;
   /* Map<String, Object> parameters = new HashMap<>();
    parameters.put("searchParam", tickerNamePart + "%");
    String sqlQuery = "	SELECT DISTINCT instId_fk FROM market_data WHERE instId_fk LIKE  " +
      " :searchParam ";
    return namedParamJbd.query(sqlQuery, parameters, new MarketDataRowMapper());*/
  }

  public List<Bar> findByTicker(String ticker) {
    return barDao.findByTickerName(ticker);
  }


}
