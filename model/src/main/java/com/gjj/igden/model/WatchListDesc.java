package com.gjj.igden.model;

import com.google.common.base.Objects;
import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.list.LazyList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "data_set")
public class WatchListDesc implements Serializable{


  private DataSetId id;
  private Account account;
  private String watchListName;
  private String watchListDetails;
  private int marketDataFrequency;
  private String dataProviders;
  private List<String> stockSymbolsList;
  private List operationParameterses = LazyList.lazyList(new ArrayList<>(),
          FactoryUtils.instantiateFactory(OperationParameters.class));


  @EmbeddedId
  public DataSetId getId() {
    return id;
  }

  public void setId(DataSetId id) {
    this.id = id;
  }

  @ManyToOne
  @JoinColumn(name = "account_fk_id", insertable = false, updatable = false)
  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }


  @Column(name = "data_set_name")
  public String getWatchListName() {
    return watchListName;
  }

  public void setWatchListName(String watchListName) {
    this.watchListName = watchListName;
  }

  @Column(name = "data_set_description")
  public String getWatchListDetails() {
    return watchListDetails;
  }

  public void setWatchListDetails(String watchListDetails) {
    this.watchListDetails = watchListDetails;
  }

  @Column(name = "market_data_frequency")
  public int getMarketDataFrequency() {
    return marketDataFrequency;
  }

  public void setMarketDataFrequency(int marketDataFrequency) {
    this.marketDataFrequency = marketDataFrequency;
  }

  @Column(name = "data_providers")
  public String getDataProviders() {
    return dataProviders;
  }

  public void setDataProviders(String dataProviders) {
    this.dataProviders = dataProviders;
  }



  @Transient
  public List<String> getStockSymbolsList() {
    return stockSymbolsList;
  }

  public void setStockSymbolsList(List<String> stockSymbolsList) {
    this.stockSymbolsList = stockSymbolsList;
  }

  public void setStockSymbolsListFromOperationList(List<OperationParameters> stockSymbolsList) {
    List<String> stringList = stockSymbolsList
            .stream()
            .map(OperationParameters::getName)
            .collect(Collectors.toList());
    this.stockSymbolsList = stringList;
  }

  @Transient
  public List<OperationParameters> getOperationParameterses() {
    return operationParameterses;
  }

  public void setOperationParameterses(
          List<OperationParameters> operationParameterses) {
    this.operationParameterses = operationParameterses;
  }


  @Override
  public String toString() {
    return String.valueOf(" account id =  " + "" + "\n " +
      "data set id = " + this.id + "\n " +
      "market data freq = " + this.getMarketDataFrequency() + "\n " +
      "data set name = " + this.getWatchListName() + "\n " +
      "data set description = " + this.getWatchListDetails() + "\n ");
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WatchListDesc watchListDesc = (WatchListDesc) o;
    return Objects.equal(id, watchListDesc.id) &&
            Objects.equal(watchListName, watchListDesc.watchListName) &&
            Objects.equal(watchListDetails, watchListDesc.watchListDetails) &&
            Objects.equal(marketDataFrequency, watchListDesc.marketDataFrequency) &&
            Objects.equal(dataProviders, watchListDesc.dataProviders);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, watchListName, watchListDetails, marketDataFrequency, dataProviders);
  }


}
