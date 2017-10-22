package com.gjj.igden.dao;

import com.gjj.igden.model.Bar;
import com.gjj.igden.model.BarSetId;
import com.gjj.igden.model.DataSetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BarDao extends JpaRepository<Bar, BarSetId> {


  @Query("Select b from Bar b where b.id.instId = :instId")
  public List<Bar> findByInstId(@Param("instId") String instId);

  @Query("SELECT DISTINCT b.id.instId FROM Bar b WHERE b.id.instId LIKE %:tickerNamePart%")
  public List<Object> findByTicketName(@Param("tickerNamePart") String ticketNamePart);

  @Query("Select b from Bar b where b.ticker = :ticker")
  public List<Bar> findByTickerName(@Param("ticker") String ticker);



}
