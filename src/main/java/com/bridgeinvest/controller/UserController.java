package com.bridgeinvest.controller;

import com.bridgeinvest.service.ExcelService;
import com.bridgeinvest.service.PdfService;
import com.bridgeinvest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bridgeinvest.dto.LoginRequest;
import com.bridgeinvest.entity.User;
import com.bridgeinvest.repository.UserRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	    @Autowired
	    private UserRepository userRepository;
        @Autowired
		private ExcelService excelService;

		@Autowired
		private UserService userService;
        @Autowired
		private PdfService pdfService;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	        User user = userRepository.findByUsername(loginRequest.getUsername());
	        if (user == null) {
	            return ResponseEntity.badRequest().body("Invalid username or password");
	        }
	        if (!user.getPassword().equals(loginRequest.getPassword())) {
	            return ResponseEntity.badRequest().body("Invalid username or password");
	        }
	        return ResponseEntity.ok("Login successful");
	    }
       @RequestMapping("/excel")
		public ResponseEntity<Resource>download() throws IOException {
			String filename="users.xlsx";
			ByteArrayInputStream actualData = excelService.getActualData();
		   InputStreamResource file =new InputStreamResource(actualData);

		   ResponseEntity<Resource>body=ResponseEntity.ok()
				   .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+filename)
		   .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				   .body(file);
		   return body;
	   }

	@PostMapping("/users/csv")
	public ResponseEntity<String> convertToCsv(@RequestBody List<User> users) {
		String filename="users.csv";
		userService.convertToCsv(users,filename);
		return ResponseEntity.ok("CSV file saved successfully");
	}
   @GetMapping("/createPdf")
   public ResponseEntity<InputStreamResource> createPdf(){
   ByteArrayInputStream pdf = pdfService.createPdf();
   HttpHeaders httpHeaders = new HttpHeaders();
   httpHeaders.add("Content-Disposition","inline; file=lcwd.pdf");
   return ResponseEntity
		   .ok()
		   .headers(httpHeaders)
		   .contentType(MediaType.APPLICATION_PDF)
		   .body(new InputStreamResource(pdf));
   }

	@PostMapping("/users/pdf")
	public ResponseEntity<String> convertToPdf(@RequestBody List<User> users, @RequestParam String fileName) {
		userService.convertToPdf(users, fileName);
		return ResponseEntity.ok("PDF file saved successfully");
	}

	@PostMapping("/users/word")
	public ResponseEntity<String> convertToWord(@RequestBody List<User> users, @RequestParam String fileName) {
		userService.convertToWord(users, fileName);
		return ResponseEntity.ok("Word document saved successfully");
	}

}

