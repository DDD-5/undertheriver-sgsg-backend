package com.undertheriver.sgsg.foler.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.undertheriver.sgsg.common.annotation.LoginUserId;
import com.undertheriver.sgsg.common.dto.PageRequest;
import com.undertheriver.sgsg.foler.domain.dto.FolderDto;
import com.undertheriver.sgsg.foler.service.FolderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/folders")
@Api(value = "folder")
public class FolderController {
	private final FolderService folderService;

	@ApiOperation(value = "폴더 생성")
	@PostMapping
	public ApiResult<?> save(
		@LoginUserId Long userId,
		@RequestBody FolderDto.CreateFolderReq dto){
		long id = folderService.save(userId, dto);
		URI location = URI.create("/api/folders/" + id);
		return ApiResult.OK(location);
	}

	@ApiOperation("폴더 조회")
	@GetMapping
	public ApiResult<List<FolderDto.ReadFolderRes>> read(
		@LoginUserId Long userId,
		final PageRequest pageable
	) {
		List<FolderDto.ReadFolderRes> folders = folderService.readAll(userId, pageable);
		return ApiResult.OK(folders);
	}

    @ApiOperation("폴더 이름 수정")
    @PutMapping("/{id}/title")
    public ApiResult<FolderDto.ReadFolderRes> update(
            @PathVariable Long id, @RequestBody FolderDto.UpdateFolderTitleReq body) {

		FolderDto.ReadFolderRes res = folderService.update(id, body);

		return ApiResult.OK(res);
	}

	@ApiOperation("다음 폴더 색상 조회")
	@GetMapping("/color")
	public ApiResult<FolderDto.GetNextFolderColorRes> getNextFolderColor(@LoginUserId Long userId) {

		FolderDto.GetNextFolderColorRes res = folderService.getNextColor(userId);

		return ApiResult.OK(res);
	}
}
