package com.guo.mapper;


import com.guo.po.ItemsCustom;
import com.guo.po.ItemsVO;

import java.util.List;

public interface ItemsMapperCustom
{
    public List<ItemsCustom> queryItemsByName(ItemsVO itemsVO) throws Exception;

    public void deleteItemsByIds(Integer[] ids) throws Exception;

    public void updateBatch(List<ItemsCustom> itemsCustomList)throws Exception;
}