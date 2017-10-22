package com.gjj.igden.service.test.daostub;

import com.gjj.igden.dao.daoimpl.BarDaoImpl;
import com.gjj.igden.model.Bar;
import com.gjj.igden.dao.BarDao;
import com.gjj.igden.dao.daoUtil.DaoException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BarDaoStub {
  private static Map<Integer, Map<String, List<Bar>>> marketDataDbSimulator;
  private static final int WATCHLIST_ID = 1;

  static {
    List<Bar> barList1 = Stream.of(new Bar(1L, "AAPL@NASDAQ"),
      new Bar(2L, "AAPL@NASDAQ"), new Bar(3L, "AAPL@NASDAQ"))
      .collect(Collectors.toList());
    List<Bar> barList2 = Stream.of(new Bar(1L, "GOOG@NASDAQ"),
      new Bar(2L, "GOOG@NASDAQ"),
      new Bar(3L, "GOOG@NASDAQ"), new Bar(4L, "GOOG@NASDAQ"))
      .collect(Collectors.toList());
    List<Bar> barListORCL = Stream.of(new Bar(1L, "ORCL@NASDAQ"),
      new Bar(2L, "ORCL@NASDAQ"),
      new Bar(3L, "ORCL@NASDAQ"), new Bar(4L, "ORCL@NASDAQ"))
      .collect(Collectors.toList());
    Map<String, List<Bar>> barCollectionMap1 = Maps.newHashMap(ImmutableMap.of("AAPL@NASDAQ", barList1, "GOOG@NASDAQ", barList2, "ORCL@NASDAQ", barListORCL));
    Map<String, List<Bar>> barCollectionMap2 = Maps.newHashMap(ImmutableMap.of("ORCL@NASDAQ", barListORCL));
    int simulateDataSetId01 = 1;
    int simulateDataSetId02 = 2;
    marketDataDbSimulator = Maps.newHashMap(ImmutableMap.of(simulateDataSetId01, barCollectionMap1, simulateDataSetId02, barCollectionMap2));
  }

  public Bar getSingleBar(long mdId, String instId) {
    return marketDataDbSimulator.get(WATCHLIST_ID).get(instId)
      .stream().filter(p -> (p.getId().getId().equals(mdId))).findFirst().get();
  }

  public List<Bar> getBarList(String instId) {
    return marketDataDbSimulator.get(WATCHLIST_ID).get(instId);
  }

  public boolean updateBar(Bar bar) {
    marketDataDbSimulator.get(WATCHLIST_ID).get(bar.getInstId().toString())
      .stream().filter(p -> p.getId().getId().equals(bar.getId().getId())).findFirst()
      .ifPresent(m -> {
        m.reset();
        m.copy(bar);
      });
    return getSingleBar(bar.getId().getId(), bar.getInstId().toString()).equals(bar);
  }

  public boolean createBar(Bar bar) throws DaoException {
    return marketDataDbSimulator.get(WATCHLIST_ID).get(bar.getInstId().toString()).add(bar);
  }

  public boolean deleteBar(long mdId, String instId) {
    marketDataDbSimulator.get(WATCHLIST_ID).get(instId).stream()
      .filter(p -> p.getId().getId().equals(mdId))
      .findFirst().ifPresent(Bar::reset);
    return true;
  }

  public boolean update(Bar bar) {
    return updateBar(bar);
  }


  public boolean deleteBar(Bar bar) {
    return deleteBar(bar.getId().getId(), bar.getInstId().toString());
  }

  public List<String> searchTickersByChars(String tickerNamePart) {
    return null;
  }

  public void setNamedParamJbd(NamedParameterJdbcTemplate namedParamJbd) {

  }
}
