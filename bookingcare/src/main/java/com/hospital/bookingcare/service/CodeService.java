package com.hospital.bookingcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hospital.bookingcare.model.Allcodes;
import com.hospital.bookingcare.repository.CodeRepository;
import com.hospital.bookingcare.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeService implements ICodeService {
	
	private final CodeRepository codeRepository;
	private final UserRepository userRepository;
	@Autowired
    public CodeService(CodeRepository codeRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
	}

	@Override
	public List<Allcodes> getAllcodes() {
		return codeRepository.findAll();
	}

	@Override
	public List<Allcodes> getType(String type) {
		// TODO Auto-generated method stub
		return codeRepository.findByType(type);
	}

}
