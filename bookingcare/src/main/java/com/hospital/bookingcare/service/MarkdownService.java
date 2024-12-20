package com.hospital.bookingcare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.Markdown;

import com.hospital.bookingcare.repository.MarkdownRepository;

@Service
public class MarkdownService implements IMarkdownService {
	
	private MarkdownRepository markdownRepository;
	
	@Autowired
    public MarkdownService(MarkdownRepository markdownRepository) {
        this.markdownRepository = markdownRepository;
       
	}
	 
	 public Markdown saveMarkdown(Markdown markdown) {
	     return markdownRepository.save(markdown);
	    }

	 @Override
	 public Markdown getMarkdownByDoctorId(Long doctorId) {
	     // Thay đổi từ id thành doctorId trong phương thức gọi repository
	     return markdownRepository.findByDoctorId(doctorId).orElse(null);
	 }

	@Override
	public Markdown getMarkdownById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
