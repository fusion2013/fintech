package com.gjj.igden.dao.test;

import com.gjj.igden.dao.BarDao;
import com.gjj.igden.dao.daoimpl.BarDaoImpl;
import com.gjj.igden.model.Bar;
import com.gjj.igden.model.BarSetId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPATestConfig.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db-init-sql-script/fintech.sql")
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BarDaoTest {

  @Autowired
  private BarDaoImpl barDaoImpl;


  @Test
  public void testGetSingleBar() {
    Bar bar = barDaoImpl.getSingleBar(1, "AAPL@NASDAQ");
    System.out.println(bar);
    Assert.assertNotNull(bar);
  }

  @Test
  public void testReturnBarList() {
    List<Bar> bars = barDaoImpl.getBarList( "AAPL@NASDAQ");
    System.out.println(bars);
    Assert.assertNotNull(bars);
  }

  @Test
  public void testSearch() {
    List<String> bars = barDaoImpl.searchTickersByChars( "AAP");
    System.out.println(bars);
    Assert.assertNotNull(bars);
  }



  @Test
  public void testCreateBar() throws Exception {
    Bar bar = barDaoImpl.getSingleBar(1,  "AAPL@NASDAQ");
    System.out.println(bar);
    Assert.assertNotNull(bar);
    BarSetId id = bar.getId();
    id.setId(111l);
    bar.setId(id);
    System.out.println(bar);
    Assert.assertTrue(barDaoImpl.createBar(bar));
    List<Bar> barList = barDaoImpl.getBarList( "AAPL@NASDAQ");
    List<Bar> newBar = barList.stream()
      .filter(p -> Objects.equals(p.getHigh(), 296.25))
      .collect(Collectors.toList());
    List<Bar> newBar_id = barList.stream()
      .filter(p -> Objects.equals(p.getId().getId(), 111L))
      .collect(Collectors.toList());
    System.out.println(newBar);
    Assert.assertEquals(3, newBar.size());
    Assert.assertEquals(1, newBar_id.size());
  }

  @Test
  public void testDelete() throws Exception {
    Bar bar = barDaoImpl.getSingleBar(1,  "AAPL@NASDAQ");
    System.out.println(bar);
    Assert.assertNotNull(bar);
    BarSetId id = bar.getId();
    id.setId(111l);
    bar.setId(id);
    System.out.println(bar);
    Assert.assertTrue(barDaoImpl.createBar(bar));
    List<Bar> barList = barDaoImpl.getBarList( "AAPL@NASDAQ");
    List<Bar> result = barList.stream().filter(p -> Objects.equals(p.getHigh(), 296.25))
      .collect(Collectors.toList());
    System.out.println(result);
    Assert.assertEquals(3, result.size());
    Assert.assertTrue(barDaoImpl.deleteBar(111L,  "AAPL@NASDAQ"));
    barList = barDaoImpl.getBarList( "AAPL@NASDAQ");
    result = barList.stream().filter(p -> Objects.equals(p.getHigh(), 296.25)).collect(Collectors.toList());
    Assert.assertEquals(2, result.size());
  }

  @Test
  public void testUpdate() throws Exception {
    Bar bar = barDaoImpl.getSingleBar(1,  "AAPL@NASDAQ");
    bar.setLogInfo("test update");
    barDaoImpl.updateBar(bar);
    final String additionalInfo = barDaoImpl.getSingleBar(1,  "AAPL@NASDAQ").getLogInfo();
    Assert.assertEquals("test update", additionalInfo);
  }
}
