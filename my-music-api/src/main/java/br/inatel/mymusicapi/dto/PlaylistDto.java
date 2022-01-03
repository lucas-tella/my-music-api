package br.inatel.mymusicapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.inatel.mymusicapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistDto {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	@Length(min = 4)
	private String title;
	
	private String description;
	private User user;
}
