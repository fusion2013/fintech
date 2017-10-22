package com.gjj.igden.dao.daoimpl;

import com.gjj.igden.dao.WatchListDescDao;
import com.gjj.igden.dao.WatchListTickersDao;
import com.gjj.igden.model.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class WatchListDescDaoImpl  {

    @Resource
    WatchListDescDao watchListDescDao;

    @Resource
    WatchListTickersDao watchListTickersDao;


    public List<String> getAllStockSymbols(int watchListDescId) {
        long id = watchListDescId;
        List<Object> list = watchListTickersDao.findByWatchListId(id);
        List<String> resultList = new ArrayList<String>();
        if(list != null && list.size()>0) {
            for(Object o : list) {
                String inst = (String) o;
                resultList.add(inst);
            }
        }
        return resultList;
    }


    public List<String> getAllStockSymbols(long watchListDescId) {
        List<Object> list = watchListTickersDao.findByWatchListId(watchListDescId);
        List<String> resultList = new ArrayList<String>();
        if(list != null && list.size()>0) {
            for(Object o : list) {
                String inst = (String) o;
                resultList.add(inst);
            }
        }
        return resultList;
    }

    public List<WatchListDesc> getDataSetsAttachedToAcc(int accId) {
        long id = accId;
        List<WatchListDesc> list = watchListDescDao.findByAccountId(id);
        list.forEach(p -> p.setStockSymbolsList(getAllStockSymbols(p.getId().getId())));
       /* SqlParameterSource params = new MapSqlParameterSource("accountId", accId);
        final String getDataFromDataSetTable = "SELECT * FROM data_set WHERE account_fk_id = :accountId";
        List<IWatchListDesc> watchListDescs = namedParamJbd.query(getDataFromDataSetTable,
                params, new WatchListDescRowMapper());
        watchListDescs.forEach(p -> p.setStockSymbolsList(getAllStockSymbols(p.getWatchListId())));
        return watchListDescs;*/
        return list;
    }

    public WatchListDesc getWatchListDesc(int dsId, int accId) {
        long dataSetId = dsId;
        long accountId = accId;
        return watchListDescDao.findOne(new DataSetId(dataSetId, accountId));
      /*  return entityManager.createQuery("select w from WatchListDesc w where w.id.id = :dsId and w.id.account.id = :accId", WatchListDesc.class)
                .setParameter("dsId", dsId)
                .setParameter("accId", accId)
                .getSingleResult();
*/
        /*Map<String, Object> parameters = new HashMap<>();
        parameters.put("accId", accId);
        parameters.put("dsId", dsId);
        final String sqlQuery =
                "SELECT * FROM data_set WHERE account_fk_id = :accId AND data_set_id = :dsId";
        return namedParamJbd.queryForObject(sqlQuery, parameters, new WatchListDescRowMapper());*/
    }

    @Transactional
    public boolean addTicker(int watchlistId, String tickerName) {
        boolean add = false;
        try {
            WatchListTickers watchListTickers = new WatchListTickers();
            long id = watchlistId;
            watchListTickers.setId(new WatchListTickersId(tickerName, id));
            watchListTickersDao.saveAndFlush(watchListTickers);
            add = true;
        } catch (Exception e) {
            e.printStackTrace();
            add = false;
        }
        return add;

        /*Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("watchlistId", watchlistId);
        paramMap.put("tickerName", tickerName);
        final String INSERT_QUERY =
                "INSERT INTO wl_tickers (`watchlist_id_fk`, `instId`) VALUES (:watchlistId, :tickerName);";
        return namedParamJbd.update(INSERT_QUERY, paramMap) == 1;*/
    }

    @Transactional
    public boolean deleteWatchListDesc(int dsId, int accId) {
        boolean delete = false;
        try {
            long dataSetId = dsId;
            long accountId = accId;
            watchListDescDao.delete(new DataSetId(dataSetId, accountId));
            delete = true;
        } catch (Exception e) {
            e.printStackTrace();
            delete = false;
        }
        return delete;
        /*SqlParameterSource beanParams = new BeanPropertySqlParameterSource(dataSet);
        String sqlQuery = "	DELETE FROM data_set WHERE data_set_id = :watchListId" +
                " AND account_fk_id = :accountId;";
        return namedParamJbd.update(sqlQuery, beanParams) == 1;*/
    }

    @Transactional()
    public boolean deleteWatchListDesc(WatchListDesc watchListDesc) {
        boolean delete = false;
        try {
            watchListDescDao.delete(watchListDesc);
            delete = true;
        } catch (Exception e) {
            e.printStackTrace();
            delete = false;
        }
       /* SqlParameterSource beanParams = new BeanPropertySqlParameterSource(watchListDesc);
        String sqlQuery = "	DELETE FROM data_set WHERE data_set_id = :watchListId" +
                " AND account_fk_id = :accountId;";
        return namedParamJbd.update(sqlQuery, beanParams) == 1;*/
        return  delete;
    }

    @Transactional
    public boolean createWatchListDesc(WatchListDesc watchListDesc) {
       if (createWatchListDescFields(watchListDesc)) {
            List<String> tickers = watchListDesc.getStockSymbolsList();
            if (tickers != null && tickers.size() > 0) {
                for (String ticker : tickers) {
                    if (!setWatchListTickers(ticker)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean createWatchListDescFields(WatchListDesc watchListDesc) {
        boolean createWatchList = false;
        try {
            watchListDescDao.saveAndFlush(watchListDesc);
            createWatchList = true;
        } catch (Exception e) {
           e.printStackTrace();
            createWatchList = false;
        }


     /*   MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accId", watchListDesc.getAccountId(), Types.INTEGER);
        parameters.addValue("data_set_name", watchListDesc.getWatchListName());
        parameters.addValue("market_data_frequency", watchListDesc.getMarketDataFrequency());
        parameters.addValue("data_set_description", watchListDesc.getWatchListDetails());
        parameters.addValue("data_providers", watchListDesc.getDataProviders());
        String sqlQuery = " INSERT INTO data_set ( account_fk_id, data_set_name, " +
                "data_set_description, market_data_frequency, data_providers ) " +
                "VALUES ( :accId, :data_set_name, :data_set_description," +
                " :market_data_frequency, :data_providers);";
        return namedParamJbd.update(sqlQuery, parameters) == 1;*/
        return createWatchList;
    }

    @Transactional
    public boolean setWatchListTickers(String ticker) {
        boolean set = false;
        try {
            watchListTickersDao.storeData(ticker);
        } catch (Exception e) {
            e.printStackTrace();
            set = false;
        }
       /* MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("instId", ticker);
        String sqlQuery = " INSERT INTO wl_tickers (instId, watchlist_id_fk)" +
                "VALUES ( :instId, LAST_INSERT_ID()) ;";
        return namedParamJbd.update(sqlQuery, parameters) == 1;*/
        return set;
    }

    @Transactional
    public boolean updateWatchListDesc(WatchListDesc watchListDesc) {
        boolean update = false;
        try {
            watchListDescDao.saveAndFlush(watchListDesc);
            update = true;
        } catch(Exception e) {
            e.printStackTrace();
            update = false;
        }
       /* Map<String, Object> parameters = new HashMap<>();
        parameters.put("data_set_id", watchListDesc.getWatchListId());
        parameters.put("account_fk_id", watchListDesc.getAccountId());
        parameters.put("data_set_name", watchListDesc.getWatchListName());
        String sqlQuery = "UPDATE data_set SET data_set_name = :data_set_name " +
                "WHERE data_set_id = :data_set_id AND account_fk_id = :account_fk_id";
        return namedParamJbd.update(sqlQuery, parameters) == 1;*/
        return update;
    }
}
