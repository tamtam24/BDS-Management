package com.javaweb.controller.admin;



import com.javaweb.enums.District;
import com.javaweb.enums.TypeCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.service.IBuildingService;
import com.javaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value="buildingControllerOfAdmin")
public class BuildingController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IBuildingService IBuildingService;



    @GetMapping (value="admin/building-list")
        public ModelAndView builldingList(@ModelAttribute BuildingSearchRequest buildingSearchRequest, HttpServletRequest request){
            ModelAndView nav = new ModelAndView("admin/building/list");
            nav.addObject("modelSearch",buildingSearchRequest);

        List<BuildingSearchResponse>responseList = IBuildingService.findAll(buildingSearchRequest);
        nav.addObject("buildingList",responseList);
        nav.addObject("ListStaffs",userService.getStaffs());
        nav.addObject("districts", District.type());
        nav.addObject("typeCodes", TypeCode.type());


            return  nav;

        }

    @GetMapping (value="admin/building-edit")
    public ModelAndView buildingEdit(@ModelAttribute("buildingEdit") BuildingDTO buildingDTO , HttpServletRequest request){
        ModelAndView nav = new ModelAndView("admin/building/edit");
        nav.addObject("districts", District.type());
        nav.addObject("typeCodes", TypeCode.type());

        return  nav;

    }

    @GetMapping (value="admin/building-edit-{id}")
    public ModelAndView buildingEdit(@PathVariable("id") Long Id , HttpServletRequest request){
        ModelAndView nav = new ModelAndView("admin/building/edit");
        BuildingDTO buildingDTO = IBuildingService.findBuildingById(Id);
        nav.addObject("buildingEdit",buildingDTO);
        nav.addObject("districts", District.type());
        nav.addObject("typeCodes", TypeCode.type());

        return  nav;

    }



}
