package br.inatel.mymusicapi.dto;

import br.inatel.mymusicapi.model.Playlist;

public class PlaylistDtoUpdate {

	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Playlist updatePlaylist(Playlist playlist) {
		if (title != null)
				playlist.setTitle(title);

		if (description != null)
				playlist.setDescription(description);

		return playlist;
	}
}
