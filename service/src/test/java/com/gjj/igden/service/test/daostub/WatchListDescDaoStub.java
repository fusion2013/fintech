package com.gjj.igden.service.test.daostub;

import com.gjj.igden.dao.WatchListDescDao;
import com.gjj.igden.model.IWatchListDesc;
import com.gjj.igden.model.WatchListDesc;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("watchListDaoStub")
public class WatchListDescDaoStub  {
  private static Map<Integer, List<WatchListDesc>> watchListDescsDb;

  static {
    List<WatchListDesc> watchListDescsAttachedToAccWithIdOne = Stream.of(new WatchListDesc(),
      new WatchListDesc(), new WatchListDesc(), new WatchListDesc()).collect(Collectors.toList());
    WatchListDesc theWatchListD = new WatchListDesc();
    theWatchListD.setWatchListName("test-aapl-5minBar-preMarketdata");
    List<WatchListDesc> watchListDescsAttachedToAccWithIdTwo = Lists.newArrayList(theWatchListD);
    watchListDescsDb = Maps.newHashMap(ImmutableMap
      .of(1, watchListDescsAttachedToAccWithIdOne,
        2, watchListDescsAttachedToAccWithIdTwo));
  }

  public List<String> getAllStockSymbols(int id) {
    return Stream.of("C@NYSE", "GS@NYSE").collect(Collectors.toList());
  }

  public List<WatchListDesc> getDataSetsAttachedToAcc(int id) {
    return watchListDescsDb.get(id);
  }

  public void setNamedParamJbd(NamedParameterJdbcTemplate namedParamJbd) {
  }

  public WatchListDesc getWatchListDesc(int dsId, int accId) {
    return watchListDescsDb.get(accId).get(dsId);
  }

  public boolean addTicker(int watchlistId, String tickerName) {
    return true;
  }

  public boolean deleteWatchListDesc(int dsId, int accId) {
    return false;
  }



  public boolean deleteWatchListDesc(WatchListDesc watchListDesc) {
    return watchListDescsDb.entrySet()
      .stream()
      .anyMatch(e -> e.getValue()
        .removeIf(p -> p.equals(watchListDesc)));
  }

  public boolean createWatchListDesc(WatchListDesc watchListDesc) {
    int id = watchListDesc.getId().getAccountId().intValue();
    return watchListDescsDb.get(id).add(watchListDesc);
  }

  public boolean updateWatchListDesc(WatchListDesc watchListDesc) {
    int id = watchListDesc.getId().getAccountId().intValue();
    watchListDescsDb.get(id).stream()
      .filter(a -> a.equals(watchListDesc))
      .findFirst()
      .ifPresent(p -> p.setWatchListName(watchListDesc.getWatchListName()));
    return true;
  }
}
