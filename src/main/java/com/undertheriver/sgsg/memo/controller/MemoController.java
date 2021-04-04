package com.undertheriver.sgsg.memo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.undertheriver.sgsg.memo.dto.request.CreateMemoRequestDto;
import com.undertheriver.sgsg.memo.dto.response.ReadMemoResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/memos")
@Api(value = "memo")
public class MemoController {

	@ApiOperation(value = "메모 전체 조회")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "메모 전체 조회", response = ReadMemoResponseDto.class, responseContainer = "List")
	})
	@GetMapping
	public void readAll() {

	}


	@ApiOperation(value = "특정 폴더 메모 전체 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "folderId", value = "폴더 ID", required = true, dataType = "Long", paramType = "path variable")
	})
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "노션 서버Wiki에 메모 read 부분 참고", response = ReadMemoResponseDto.class, responseContainer = "List")
	})
	@GetMapping("/folders/{id}")
	public void readAllByFolder(@PathVariable Long folderId) {
	}

	@ApiOperation(value = "메모 단일 조회", response = ReadMemoResponseDto.class)
	@GetMapping("/{id}")
	public void read(@PathVariable Long id) {

	}

	@ApiOperation(value = "메모 저장")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Location: /api/v1/memos/1")
	})
	@PostMapping
	public void save(@RequestBody CreateMemoRequestDto request) {
	}

	@ApiOperation(value = "메모 내용 수정")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "메모 ID", required = true, dataType = "Long", paramType = "path variable"),
		@ApiImplicitParam(name = "content", value = "메모 내용", required = true, dataType = "String", paramType = "body")
	})
	@PutMapping("/{id}/content")
	public void update(@PathVariable Long id, @RequestBody String content) {
	}

	@ApiOperation(value = "메모 상태 수정 (토글? 명시?)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "메모 ID", required = true, dataType = "Long", paramType = "path variable"),
		@ApiImplicitParam(name = "status", value = "비밀 여부", required = true, dataType = "Boolean", paramType = "body")
	})
	@PutMapping("/{id}/status")
	public void updateStatus(@PathVariable Long id, @RequestBody Boolean status) {
	}

}
