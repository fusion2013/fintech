
package com.gjj.igden.service.test;

import com.gjj.igden.model.DataSetId;
import com.gjj.igden.model.WatchListDesc;
import com.gjj.igden.service.watchlist.WatchListDescServiceService;
import com.gjj.igden.service.test.daostub.WatchListDescDaoStub;
import com.gjj.igden.model.IWatchListDesc;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPATestConfig.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db-init-sql-script/fintech.sql")
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WatchListDescServiceTest {
  @Autowired
  private WatchListDescServiceService watchListDescService;

  @Test
  public void simpleReadTest() throws Exception {
      //watchListDescService.getAllStockSymbols(1).forEach(System.out::println);
    watchListDescService.getStockSymbolsList(1).forEach(System.out::println);
  }

  @Test
  public void testCreateH2DataBaseTest() {
    List<WatchListDesc> dataSetList = watchListDescService.getDataSetsAttachedToAcc(1);
    final int expectedDataSetsAmount = dataSetList.size();
    Assert.assertEquals(expectedDataSetsAmount, dataSetList.size());
  }

  @Test
  public void testReturnBarList() {
    WatchListDesc dataSet = watchListDescService.getDataSetsAttachedToAcc(2).get(0);
    System.out.println(dataSet.getWatchListName());
    Assert.assertNotNull(dataSet);
    Assert.assertEquals("test-aapl-5minBar-preMarketdata", dataSet.getWatchListName());
  }

  @Test
  public void testDelete02() throws Exception {
    List<WatchListDesc> dataSetList = watchListDescService.getDataSetsAttachedToAcc(1);
    final int expectedDataSetsAmount = dataSetList.size();
//    dataSetList.forEach(p -> System.out.println(p.getWatchListId()));
    boolean deleteResultFlag = watchListDescService.delete(dataSetList.get(0));
    Assert.assertTrue(deleteResultFlag);
    System.out.println("after deletion ");
    List<WatchListDesc> dataSetList1 = watchListDescService.getDataSetsAttachedToAcc(1);
  //  dataSetList.forEach(p -> System.out.println(p.getWatchListId()));
    Assert.assertNotEquals(dataSetList.size(), dataSetList1.size());
  }

  @Test
  public void testCreateDataSet() throws Exception {
    int accId = 1;
    WatchListDesc newWatchList = watchListDescService.getWatchListDesc(1, accId);
    List<WatchListDesc> dataSetList = watchListDescService.getDataSetsAttachedToAcc(1);
    //dataSetList.forEach(p -> System.out.print(p.getWatchListId() + " ; "));
    int expectedDataSetsAmountAfterDeletion = dataSetList.size();
    Assert.assertEquals(expectedDataSetsAmountAfterDeletion, dataSetList.size());
    Assert.assertNotNull(newWatchList);
    newWatchList.setId(new DataSetId(111L, 1l));
    newWatchList.setWatchListName("just testing around");
    Assert.assertTrue(watchListDescService.create(newWatchList));
    dataSetList = watchListDescService.getDataSetsAttachedToAcc(1);
//    dataSetList.forEach(p -> System.out.print(p.getWatchListId() + " ; "));
    expectedDataSetsAmountAfterDeletion = expectedDataSetsAmountAfterDeletion+1;
    Assert.assertEquals(expectedDataSetsAmountAfterDeletion, dataSetList.size());
  }

  @Test
  public void testUpdate() throws Exception {
    final int accId = 1;
    WatchListDesc dataSet = watchListDescService.getWatchListDesc(1, accId);
    dataSet.setWatchListName("test update");
    dataSet.getId().setAccountId(1l);
    watchListDescService.update(dataSet);
    final String dataSetNameDirect = watchListDescService.getWatchListDesc(1, 1).getWatchListName();
    Assert.assertEquals("test update", dataSetNameDirect);
  }

  @Test
  public void test01Read() throws Exception {
    List<WatchListDesc> watchListDescs = watchListDescService.getDataSetsAttachedToAcc(1);
    final int size = 4;
    Assert.assertEquals(watchListDescs.size(),
      watchListDescs.size());
  }













}

