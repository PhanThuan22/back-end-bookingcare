package com.hospital.bookingcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.bookingcare.model.Allcodes;
import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.service.CodeService;
import com.hospital.bookingcare.service.ICodeService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class CodeController {
	private final ICodeService codeService;
	
	@Autowired
    public CodeController(ICodeService codeService) {
        this.codeService = codeService;
	}
	
	@GetMapping("/all-codes")
    public ResponseEntity<List<Allcodes>> getAllRoles(){
        return new ResponseEntity<>(codeService.getAllcodes(), HttpStatus.FOUND);
    }
	
	@GetMapping("type/{type}")
	public ResponseEntity<?> getCodeByType(@PathVariable("type") String type){
        try{
            List<Allcodes> theCode = codeService.getType(type);
            return ResponseEntity.ok(theCode);
        //}catch (UsernameNotFoundException e){
          //  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }
	

}
