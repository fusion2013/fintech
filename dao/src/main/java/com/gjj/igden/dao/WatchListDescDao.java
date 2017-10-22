package com.gjj.igden.dao;

import com.gjj.igden.model.DataSetId;
import com.gjj.igden.model.WatchListDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WatchListDescDao extends JpaRepository<WatchListDesc, DataSetId> {

  @Query("SELECT w FROM WatchListDesc w WHERE w.id.accountId = :id")
  public List<WatchListDesc> findByAccountId(@Param("id") long id);

}
