package com.njfu.service.impl;

import com.njfu.dataobject.ItemDO;
import com.njfu.dao.*;
import com.njfu.dataobject.ItemStockDO;
import com.njfu.error.BusinessException;
import com.njfu.error.EmBusinessError;
import com.njfu.service.ItemService;
import com.njfu.service.model.ItemModel;
import com.njfu.validator.ValidationResult;
import com.njfu.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.beans.Beans;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

//    @Autowired
//    private PromoService promoService;


    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }


    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }


        //1.校验
        //2.转换
        //3.写入数据库
        //4.返回创建完成的对象
        @Override
        @Transactional
        public ItemModel createItem(ItemModel itemModel) throws BusinessException {
            //校验入参
            ValidationResult result = validator.validate(itemModel);
            if(result.isHasErrors()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
            }
            //转化itemMode->dataobject
            ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);

            //写入数据库
            itemDOMapper.insertSelective(itemDO);
            itemModel.setId(itemDO.getId());

            ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
            itemStockDOMapper.insertSelective(itemStockDO);

            //返回创建完成的对象
            return this.getItemById(itemModel.getId());
        }

    @Override
    public void destroyItem(ItemModel itemModel) {

        ItemDO itemDO = new ItemDO();
        if (itemModel.getId()!=null){

        }
        itemDO.setId(itemModel.getId());
        //写入数据库
//        System.out.println("service写入数据库的id"+itemDO.getId());
        itemDOMapper.deleteByPrimaryKey(itemDO.getId());
        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.deleteByPrimaryKey(itemDO.getId());
    }


    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();

        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        //System.out.println("IN getid");
        if(itemDO == null) {
            //System.out.println("null");
            return null;
        }
        //操作获得库存数量
        //System.out.println("id"+itemDO.getId());
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        //将dataobject转换成model
        ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);

        return itemModel;


    }

    @Override
    public ItemModel getItemByTitle(String title) {

        ItemDO itemDO = itemDOMapper.selectByTitle(title);
        if(itemDO == null) {
//            System.out.println("item service item == null");
            return null;
        }
        //操作获得库存数量
        //System.out.println("id"+itemDO.getId());
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        //将dataobject转换成model
        ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
//        System.out.println("itemService ok");
        return itemModel;
    }


    //相当于拼接
    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));

        //调试用

        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        //DOmapper里的sql语句的影响条目如果大于0那就是执行成功
        int affectedRow = itemStockDOMapper.decreaseStock(itemId,amount);

        if (affectedRow > 0){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.increaseSales(itemId,amount);
    }
}
