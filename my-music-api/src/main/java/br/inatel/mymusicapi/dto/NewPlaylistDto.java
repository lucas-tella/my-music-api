package br.inatel.mymusicapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NewPlaylistDto {
	@NotNull
	@NotEmpty
	@Length(min = 4)
	private String title;
	private String description;
	private Long userId;
	public NewPlaylistDto(String title, String description, Long userId) {
		this.title = title;
		this.description = description;
		this.userId = userId;
	}
}