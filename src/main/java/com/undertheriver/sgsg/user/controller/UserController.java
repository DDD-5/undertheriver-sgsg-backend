package com.undertheriver.sgsg.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.undertheriver.sgsg.common.annotation.LoginUser;
import com.undertheriver.sgsg.common.dto.CurrentUser;
import com.undertheriver.sgsg.user.controller.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@Api(value = "User")
@RequiredArgsConstructor
public class UserController {

	@ApiOperation(value = "회원 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Bearer sgsg-token-value", required = true, dataType = "String", paramType = "header")
	})
	@ApiResponse()
	@GetMapping("/me")
	public ResponseEntity user(@LoginUser CurrentUser currentUser) {
		UserDto.DetailResponse userDetail = new UserDto.DetailResponse(currentUser.getName(),
			currentUser.getEmail(), currentUser.getProfileImageUrl());

		return ResponseEntity.ok(userDetail);
	}

	@ApiOperation(value = "회원 탈퇴")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Bearer sgsg-token-value", required = true, dataType = "String", paramType = "header")
	})
	@DeleteMapping
	public void deleteUser() {
	}

	@ApiOperation(value = "메모비밀번호 변경")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Bearer sgsg-token-value", required = true, dataType = "String", paramType = "header"),
		@ApiImplicitParam(name = "password", value = "1234", required = true, dataType = "String", paramType = "body")
	})
	@PatchMapping
	public void updateMemoPassword(@RequestBody String password) {
	}

	@ApiOperation(value = "회원가입")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "github-provided-code", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "password", value = "1234", required = true, dataType = "String", paramType = "body")
	})
	@ApiResponses(value = {
		@ApiResponse(code = 408, message = "회원가입 시간이 초과되었습니다.")
	})
	@PostMapping
	public void signUp(@RequestBody String code, @RequestBody String password) {
	}
}
