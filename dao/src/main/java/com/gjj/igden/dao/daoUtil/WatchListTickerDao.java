package com.gjj.igden.dao.daoUtil;

import com.gjj.igden.model.WatchListTickers;
import com.gjj.igden.model.WatchListTickersId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by murtaza8t8 on 10/14/2017.
 */
public interface WatchListTickerDao extends JpaRepository<WatchListTickers, WatchListTickersId> {
}
