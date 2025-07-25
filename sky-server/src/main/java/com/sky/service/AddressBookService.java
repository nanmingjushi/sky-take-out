package com.sky.service;

/* 
    @author nanchao 
    @date 2025/7/3
*/

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    List<AddressBook> list(AddressBook addressBook);

    void save(AddressBook addressBook);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);
}
