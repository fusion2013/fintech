package com.gjj.igden.dao.test;

import com.gjj.igden.dao.WatchListDescDao;
import com.gjj.igden.dao.daoimpl.WatchListDescDaoImpl;
import com.gjj.igden.model.IWatchListDesc;
import com.gjj.igden.model.WatchListDesc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/*
@Configuration
@ComponentScan(basePackageClasses = {WatchListDescDao.class,
  WatchListDescStub.class})
class WatchListDaoTestConfig {
}
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPATestConfig.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db-init-sql-script/fintech.sql")
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WatchListDaoTest {
  @Autowired
  private WatchListDescDaoImpl watchListDescDao;


  @Test
  public void testAddTicker01() {
    boolean resultFlag = watchListDescDao.addTicker(15, "C@NASDAQ");
    Assert.assertTrue(resultFlag);
    Assert.assertEquals("C@NASDAQ", watchListDescDao.getAllStockSymbols(15).get(2));
  }

  @Test
  public void testGetAllStockSymbols() {
    List<String> tickerList = watchListDescDao.getAllStockSymbols(2);
    final int expectedDataSetsAmount = 18;
    System.out.println(tickerList);
    Assert.assertEquals(expectedDataSetsAmount, tickerList.size());
  }

  @Test
  public void test01Read() throws Exception {
    List<WatchListDesc> watchListDescs = watchListDescDao.getDataSetsAttachedToAcc(1);
    final int stockSymbolNumAttachedToWatchedList17th = 18;
    Assert.assertEquals(stockSymbolNumAttachedToWatchedList17th, watchListDescs.get(1).getStockSymbolsList().size());
  }

  @Test
  public void testGetDataSetsAttachedToAcc() {
    List<WatchListDesc> dataSetList = watchListDescDao.getDataSetsAttachedToAcc(1);
    final int expectedDataSetsAmount = 9;
    Assert.assertEquals(expectedDataSetsAmount, dataSetList.size());
  }

  @Test
  public void testReturnBarList() {
    WatchListDesc dataSet = watchListDescDao.getWatchListDesc(1, 1);
    System.out.println(dataSet.getWatchListName());
    Assert.assertNotNull(dataSet);
    Assert.assertEquals("test-aapl-5minBar-preMarketdata", dataSet.getWatchListName());
  }

  @Test
  public void testDelete02() throws Exception {
    List<WatchListDesc> dataSetList = watchListDescDao.getDataSetsAttachedToAcc(1);
    final int expectedDataSetsAmount = 9;
    System.out.println(" again ");
   dataSetList.forEach(p -> System.out.println(p.getId().getId()));
    Assert.assertEquals(expectedDataSetsAmount, dataSetList.size());
   boolean deleteResultFlag = watchListDescDao.deleteWatchListDesc(dataSetList.get(0));
    Assert.assertTrue(deleteResultFlag);
    System.out.println("after deletion ");
    dataSetList = watchListDescDao.getDataSetsAttachedToAcc(1);
    final int expectedDataSetsAmountAfterDeletion = 8;
   dataSetList.forEach(p -> System.out.println(p.getId().getId()));
    Assert.assertEquals(expectedDataSetsAmountAfterDeletion, dataSetList.size());
  }

  @Test
  public void testCreateDataSet() throws Exception {
    WatchListDesc dataSet = watchListDescDao.getWatchListDesc(1, 1);
    List<WatchListDesc> dataSetList = watchListDescDao.getDataSetsAttachedToAcc(1);
    dataSetList.forEach(p -> System.out.print(p.getId().getId() + " ; "));
    int expectedDataSetsAmountAfterDeletion = 9;
    Assert.assertEquals(expectedDataSetsAmountAfterDeletion, dataSetList.size());
    Assert.assertNotNull(dataSet);
    //dataSet.setWatchListId(111);
    dataSet.setWatchListName("just testing around");
    watchListDescDao.createWatchListDesc(dataSet);
  }

  @Test
  public void testUpdateDesc() throws Exception {
    WatchListDesc dataSet = watchListDescDao.getWatchListDesc(1, 1);
    dataSet.setWatchListName("test update");
    watchListDescDao.updateWatchListDesc(dataSet);
    final String dataSetNameDirect = watchListDescDao.getWatchListDesc(1, 1).getWatchListName();
    Assert.assertEquals("test update", dataSetNameDirect);
  }
}