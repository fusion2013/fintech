package com.gjj.igden.controller;

import com.gjj.igden.model.Bar;
import com.gjj.igden.model.WatchListDesc;
import com.gjj.igden.model.com.gjj.igden.model.dto.BarDTO;
import com.gjj.igden.service.barService.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by murtaza8t8 on 10/14/2017.
 */
@RestController
public class StockController {

    @Autowired
    BarService barService;

    @RequestMapping(value = "/getStock", method = RequestMethod.GET)
    public @ResponseBody
    List<BarDTO> getStock(@RequestParam("ticker") String ticker) {
        List<Bar> barList = barService.findByTicker(ticker);
        System.out.println("byTicker " + barList.size());
        List<BarDTO> list = new ArrayList<>();
        if(barList != null && barList.size()>0) {
            for(Bar bar : barList) {
                list.add(new BarDTO(bar));
            }
        }
        return  list;
    }
}
