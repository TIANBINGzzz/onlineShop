package com.njfu.controller;

import com.njfu.controller.viewobject.ItemVO;
import com.njfu.error.*;
import com.njfu.response.CommonReturnType;
import com.njfu.service.ItemService;
import com.njfu.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.njfu.error.BusinessException;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    ItemService itemService;


    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl,
                                       @RequestParam(name = "description") String description) throws BusinessException {
        // 封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        itemModel.setDescription(description);
//
        //System.out.println("title:  "+title+"price:     "+price);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);
       // System.out.println("开始转");
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);
        //System.out.println("转成功");
//        System.out.println(itemVO.getId()+itemVO.getDescription());

        return CommonReturnType.create(itemVO);
    }





    @RequestMapping(value = "/destroy",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType destroyItem(@RequestParam(name = "id")int id,
                                        @RequestParam(name = "title") String title){
        ItemModel itemModel = new ItemModel();
        if(id == -1){
            itemModel.setId(itemService.getItemByTitle(title).getId());
        }
        else {
            itemModel.setId(id);
        }
//        System.out.println("set id"+itemModel.getId());
        itemService.destroyItem(itemModel);
//        System.out.println("des");
        return CommonReturnType.create(null);
    }



                                       //商品详情浏览
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        //System.out.println("xx");
        ItemModel itemModel = itemService.getItemById(id);
        //System.out.println("wuwu");
        ItemVO itemVo = this.convertVOFromModel(itemModel);

        return CommonReturnType.create(itemVo);
    }


    //商品列表页浏览
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){

        List<ItemModel> itemModelList = itemService.listItem();

        //用stream (api)将list内的itemodel转换为ItemVO
        List<ItemVO> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVo = convertVOFromModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVoList);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if(itemModel == null) {
            return null;
        }
        ItemVO itemVo = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVo);

        return itemVo;
    }

}
