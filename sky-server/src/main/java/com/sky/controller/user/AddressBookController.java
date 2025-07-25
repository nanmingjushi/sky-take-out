package com.sky.controller.user;

/* 
    @author nanchao 
    @date 2025/7/3
*/



import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//地址簿管理
@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {


    @Autowired
    private AddressBookService addressBookService;


    //查询当前登录用户的所有地址信息
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list=addressBookService.list(addressBook);
        return Result.success(list);

    }


    //新增地址
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook){
        addressBookService.save(addressBook);
        return Result.success();
    }


    //根据Id查询地址
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id){
        AddressBook addressBook=addressBookService.getById(id);
        return Result.success(addressBook);
    }


    //根据Id修改地址
    @PutMapping
    public Result update(@RequestBody AddressBook addressBook){
        addressBookService.update(addressBook);
        return Result.success();
    }


    //设置默认地址
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);
        return Result.success();
    }


    //根据Id删除地址
    @DeleteMapping
    public Result deleteById(Long id){
        addressBookService.deleteById(id);
        return Result.success();
    }


    //查询默认地址
    @GetMapping("/default")
    public Result<AddressBook> getDefault(){
        AddressBook addressBook=new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());

        List<AddressBook> list = addressBookService.list(addressBook);

        if(list!=null && list.size()==1){
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }

}
