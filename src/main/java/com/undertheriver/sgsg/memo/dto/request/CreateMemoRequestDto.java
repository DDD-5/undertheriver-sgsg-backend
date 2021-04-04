package com.undertheriver.sgsg.memo.dto.request;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class CreateMemoRequestDto {
	@Nullable
	Long folderId;
	@NotNull
	String content;
}
