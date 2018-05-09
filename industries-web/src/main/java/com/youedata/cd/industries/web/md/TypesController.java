package com.youedata.cd.industries.web.md;

import com.youedata.cd.industries.service.md.ITypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/16.
 */
@Controller
@RequestMapping("types")
public class TypesController {
    @Autowired
    private ITypesService service;

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity list() {
        Map<Object, Object> list = service.getList();

        ResponseEntity<List> entity = new ResponseEntity(list, HttpStatus.OK);
        return entity;
    }
}
