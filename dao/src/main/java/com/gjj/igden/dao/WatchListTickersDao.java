package com.gjj.igden.dao;

import com.gjj.igden.model.WatchListTickers;
import com.gjj.igden.model.WatchListTickersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by murtaza8t8 on 10/14/2017.
 */
public interface WatchListTickersDao extends JpaRepository<WatchListTickers, WatchListTickersId> {

    @Query("SELECT w.id.inst FROM WatchListTickers w WHERE w.id.dataSet = :id")
    public List<Object> findByWatchListId(@Param("id") long id);

    @Query(nativeQuery = true, value = "INSERT INTO wl_tickers (instId, watchlist_id_fk) VALUES ( :instId, LAST_INSERT_ID()) ;")
    public void storeData(@Param("instId") String ticker);

}
