package com.bridgeinvest.service;

import com.bridgeinvest.entity.User;
import com.bridgeinvest.helper.Helper;
import com.bridgeinvest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private UserRepository repo;

    public ByteArrayInputStream getActualData() throws IOException {
        List<User>all = repo.findAll();

       ByteArrayInputStream byteArrayInputStream = Helper.dataToExcel(all);
       return byteArrayInputStream;
    }
}
