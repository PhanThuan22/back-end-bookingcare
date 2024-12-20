package com.hospital.bookingcare.service;

import com.hospital.bookingcare.model.Markdown;

public interface IMarkdownService {
	Markdown saveMarkdown(Markdown markdown);
    Markdown getMarkdownById(Long id);
	Markdown getMarkdownByDoctorId(Long doctorId);

}
