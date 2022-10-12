package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("新增套餐信息 {}",setmealDto);
        setmealService.savaWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

//    @GetMapping("/page")
//    public R<Page> page(int page,int pageSize,String name){
//        //添加分页构造器
//        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
//        Page<SetmealDto> dtoPage = new Page<>();
//        //添加条件查询构造器
//        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like(name != null,Setmeal::getName,name);
//        queryWrapper.orderByAsc(Setmeal::getCreateTime);
//        setmealService.page(pageInfo,queryWrapper);
//        //对象拷贝
//        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
//        List<Setmeal> records = pageInfo.getRecords();
//        List<SetmealDto> list = records.stream().map((item) ->{
//            SetmealDto setmealDto = new SetmealDto();
//            //对象拷贝
//            BeanUtils.copyProperties(item,setmealDto);
//            //分类ID
//            Long categoryId = item.getCategoryId();
//            //根据分类ID查询分类对象
//            Category category = categoryService.getById(categoryId);
//            if (category != null){
//                //分类名称
//                String categoryName = category.getName();
//                setmealDto.setCategoryName(categoryName);
//            }
//            return setmealDto;
//        }).collect(Collectors.toList());
//        dtoPage.setRecords(list);
//        return R.success(dtoPage);
//    }

//    @GetMapping("/page")
//    public R<Page> page(int page,int pageSize,String name){
//        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
//        Page<SetmealDto> dtoPage = new Page<>();
//        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
//        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
//        queryWrapper.like(name != null,Setmeal::getName,name);
//        setmealService.page(pageInfo,queryWrapper);
//
//        List<Setmeal> records = pageInfo.getRecords();
//        List<SetmealDto> list = records.stream().map((item) ->{
//            SetmealDto setmealDto = new SetmealDto();
//            Long categoryId = item.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//            String categoryName = category.getName();
//            setmealDto.setCategoryName(categoryName);
//            BeanUtils.copyProperties(item,setmealDto);
//            return setmealDto;
//        }).collect(Collectors.toList());
//        dtoPage.setRecords(list);
//
//        return R.success(dtoPage);
//    }

//    @GetMapping("/page")
//    public R<Page> page( int page, int pageSize, String name){
//        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
//        Page<SetmealDto> dtoPage = new Page<>();
//        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
//        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
//        queryWrapper.like(name != null,Setmeal::getName,name);
//        setmealService.page(pageInfo,queryWrapper);
//        List<Setmeal> records = pageInfo.getRecords();
//        List<SetmealDto> list = records.stream().map((item) ->{
//            SetmealDto setmealDto = new SetmealDto();
//            Long categoryId = item.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//            String categoryName = category.getName();
//            setmealDto.setCategoryName(categoryName);
//            BeanUtils.copyProperties(item,setmealDto);
//            return setmealDto;
//        }).collect(Collectors.toList());
//        dtoPage.setRecords(list);
//        return R.success(dtoPage);
//    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //添加分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        //添加查询条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        queryWrapper.like(name != null,Setmeal::getName,name);
        setmealService.page(pageInfo,queryWrapper);

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            BeanUtils.copyProperties(item,setmealDto);
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }


    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){

        //构造条件查询
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

}
