package com.gjj.igden.service.watchlist;

import com.gjj.igden.dao.daoimpl.WatchListDescDaoImpl;
import com.gjj.igden.model.IWatchListDesc;
import com.gjj.igden.model.WatchListDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchListDescServiceService {

  @Autowired
  private WatchListDescDaoImpl watchListDescDao;

  public List<WatchListDesc> getDataSetsAttachedToAcc(int id) {
    return watchListDescDao.getDataSetsAttachedToAcc(id);
  }

  public List<String> getStockSymbolsList(int id) {
    return watchListDescDao.getAllStockSymbols(id);
  }

  public boolean delete(int accId, int watchListId) {
    return watchListDescDao.deleteWatchListDesc(watchListId, accId);
  }

  public boolean delete(WatchListDesc watchListDesc) {
    return watchListDescDao.deleteWatchListDesc(watchListDesc);
  }

  public boolean create(WatchListDesc watchListDesc) {
    watchListDesc.setStockSymbolsListFromOperationList(watchListDesc.getOperationParameterses());
    return watchListDescDao.createWatchListDesc(watchListDesc);
  }

  public WatchListDesc getWatchListDesc(int dsId, int accId) {
    return watchListDescDao.getWatchListDesc(dsId, accId);
  }

  public boolean update(WatchListDesc watchListDesc) {
    return watchListDescDao.updateWatchListDesc(watchListDesc);
  }
}
