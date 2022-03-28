package com.cfm.sacc.util.pagination;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public final class Pagination {
	
	private Pagination() {}
	
	private static final String ATTRIBUTE_PAGE = "page";
	private static final int PAGE_SIZE = 5;
	
	public static void buildPagination(int page, List<?> lista, String urlRender, String attribute, Model model) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), lista.size());
		Page<?> pageData = new PageImpl<>(lista.subList(start, end), pageable, lista.size());
		
		PageRender<?> pageRender = new PageRender<>(urlRender, pageData);
		model.addAttribute(attribute, pageData);
		model.addAttribute(ATTRIBUTE_PAGE, pageRender);
	}

}
