package com.undertheriver.sgsg.memo.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

@Getter
public class ReadMemoResponseDto {
	Long id;
	String content;
	Integer contentLength;
	String createdAt;
	Boolean isSecret;
	Boolean isFavorite;
	String ThumbnailUrl;
}
