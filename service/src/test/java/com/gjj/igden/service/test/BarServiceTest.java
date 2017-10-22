
package com.gjj.igden.service.test;

import com.gjj.igden.dao.daoimpl.BarDaoImpl;
import com.gjj.igden.model.Bar;
import com.gjj.igden.service.barService.BarService;
import com.gjj.igden.service.test.daostub.BarDaoStub;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPATestConfig.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db-init-sql-script/fintech.sql")
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarServiceTest {

  @Autowired
  private BarService barService;

  @Test
  public void simpleReadTest01() {
    List<Bar> accounts = barService.getBarList("AAPL@NASDAQ");
    accounts.forEach(System.out::println);
  }

  @Test
  public void testCreateH2DataBaseTest() {
    Bar bar = barService.getSingleBar(1, "AAPL@NASDAQ");
    Assert.assertNotNull(bar);
    Assert.assertEquals("AAPL@NASDAQ", bar.getId().getInstId().toString());
  }

  @Test
  public void testUpdate() {
    Bar bar = barService.getSingleBar(1, "AAPL@NASDAQ");
    Bar barCopy = new Bar(bar);
    barCopy.setLogInfo("test update");
    Assert.assertTrue(barService.update(barCopy));
    bar = barService.getSingleBar(1, "AAPL@NASDAQ");
    Assert.assertEquals("test update", bar.getLogInfo());
  }

  @Test
  public void testCreateBar() throws Exception {
    Bar bar = barService.getSingleBar(2, "AAPL@NASDAQ");
    System.out.println(bar);
    Assert.assertNotNull(bar);
    bar.getId().setId(111l);
    //bar.setMdId(111L);
    System.out.println(bar);
    Assert.assertTrue(barService.createBar(bar));
    List<Bar> barList = barService.getBarList("AAPL@NASDAQ");
    Bar newBar = barList.stream()
      .filter(p -> Objects.equals(p.getId().getId(), 111L)).findAny().get();
    System.out.println(newBar);
    Assert.assertEquals(Long.valueOf(111), newBar.getId().getId());
  }

  @Test
  public void testDelete() throws Exception {
    Bar bar = barService.getSingleBar(111l, "AAL@NASDAQ");
    System.out.println(bar);
    Assert.assertNotNull(bar);
    Assert.assertTrue(barService.deleteBar(bar));
    try {
      Bar bar1 = barService.getSingleBar(111l, "AAL@NASDAQ");
      bar1.getId();
      Assert.fail();
    } catch (NullPointerException e) {
      Assert.assertTrue(true);
    }
  }
}

