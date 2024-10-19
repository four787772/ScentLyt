package com.four.scentLyt.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.four.scentLyt.service.ICategoriesService;

@Validated
@RestController
//@CrossOrigin("*")
@RequestMapping(value = "api/v1/categories")
public class CategoriesController {
	
	@Autowired
	private ICategoriesService servie;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@GetMapping()
	public ResponseEntity<?> getAllCategories(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
		
	}
	

}
