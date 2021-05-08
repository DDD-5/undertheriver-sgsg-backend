package com.undertheriver.sgsg.memo.domain.dto;

import javax.validation.constraints.NotNull;

import com.undertheriver.sgsg.foler.domain.Folder;
import com.undertheriver.sgsg.memo.domain.Memo;

import org.springframework.lang.Nullable;

import com.undertheriver.sgsg.foler.domain.FolderColor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemoDto {
	@Setter
	@Getter
	@NoArgsConstructor
	public static class CreateMemoReq {
		@Nullable
		private Long folderId;
		@NotNull
		private String folderTitle;
		@NotNull
		private FolderColor folderColor;
		@NotNull
		private String memoContent;

		@Builder
		public CreateMemoReq(Long folderId, String folderTitle, FolderColor folderColor, String memoContent) {
			this.folderId = folderId;
			this.folderTitle = folderTitle;
			this.folderColor = folderColor;
			this.memoContent = memoContent;
		}

		public Memo toMemoEntity() {
			return Memo.builder()
				.content(memoContent)
				.build();
		}

		public Folder toFolderEntity() {
			return Folder.builder()
				.title(folderTitle)
				.color(folderColor)
				.build();
		}

		public boolean hasFolderId() {
			return folderId != null;
		}
	}

	public static class ReadMemoRes {
		private Long id;
		private String content;
		private String createdAt;
		private Boolean favorite;
		private String thumbnailUrl;
	}

	@Getter
	public static class UpdateMemoReq {
		@NotNull
		private Long memoId;
		@NotNull
		private String content;
		@Nullable
		private Boolean favorite;
		@Nullable
		private String thumbnailUrl;
		@NotNull
		private Long folderId;

		@Builder
		public UpdateMemoReq(
			Long memoId, String content, Boolean favorite, String thumbnailUrl, Long folderId) {
			this.memoId = memoId;
			this.content = content;
			this.favorite = favorite;
			this.thumbnailUrl = thumbnailUrl;
			this.folderId = folderId;
		}
	}
}
