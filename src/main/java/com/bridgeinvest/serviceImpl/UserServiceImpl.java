package com.bridgeinvest.serviceImpl;

import com.bridgeinvest.helper.Helper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeinvest.dto.LoginRequest;
import com.bridgeinvest.entity.User;
import com.bridgeinvest.repository.UserRepository;
import com.bridgeinvest.service.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
	public class UserServiceImpl implements UserService {
	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
		private Helper helper;

	    @Override
	    public boolean login(LoginRequest loginRequest) {
	        User user = userRepository.findByUsername(loginRequest.getUsername());
	        if (user == null) {
	            return false;
	        }
	        if (!user.getPassword().equals(loginRequest.getPassword())) {
	            return false;
	        }
	        return true;
	    }

	@Override
	public void convertToCsv(List<User> users, String fileName) {
		try {
			helper.writeToCsv(users, fileName);
		} catch (IOException e) {
			throw new RuntimeException("Error while converting to CSV: " + e.getMessage(), e);
		}
	}
	public void convertToPdf(List<User> users, String fileName) {
		try {
			Helper.generatePdf(users, fileName);
		} catch (Exception e) {
			throw new RuntimeException("Error while converting to PDF: " + e.getMessage(), e);
		}
	}

	@Override
	public void convertToWord(List<User> users, String fileName) {
		try{
			XWPFDocument document = new XWPFDocument();

			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();

			for (User user : users) {
				run.setText(user.getId() + " - " + user.getUsername() + " - " + user.getPassword());
				run.addBreak();
			}

			FileOutputStream out = new FileOutputStream(fileName);
			document.write(out);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException("Error while converting to Word: " + e.getMessage(), e);
		}
	}
		}





