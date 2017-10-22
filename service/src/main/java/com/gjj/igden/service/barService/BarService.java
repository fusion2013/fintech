package com.gjj.igden.service.barService;

import com.gjj.igden.dao.daoimpl.BarDaoImpl;
import com.gjj.igden.model.Bar;
import com.gjj.igden.dao.BarDao;
import com.gjj.igden.dao.daoUtil.DaoException;
import com.gjj.igden.service.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class BarService {

  @Autowired
  private BarDaoImpl barDaoImpl;

  public List<Bar> getBarList(String instId) {
    return barDaoImpl.getBarList(instId);
  }

  public Bar getSingleBar(long barId, String instId) {
    return barDaoImpl.getSingleBar(barId, instId);
  }

  public boolean update(Bar bar) {
    return barDaoImpl.updateBar(bar);
  }

  public boolean createBar(Bar bar) throws ServiceException {
    try {
      return barDaoImpl.createBar(bar);
    } catch (DaoException e) {
      throw new ServiceException.ExceptionBuilder().setException(e).build();
    }
  }

  public boolean deleteBar(Bar bar) {
    return barDaoImpl.deleteBar(bar);
  }

  public List<String> searchTickersByChars(String tickerNamePart) {
    return barDaoImpl.searchTickersByChars(tickerNamePart);
  }

  public List<Bar> findByTicker(String ticker) {
    return barDaoImpl.findByTicker(ticker);
  }
}
