package br.inatel.mymusicapi.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.mymusicapi.dto.NewPlaylistDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.repository.PlaylistRepository;
import br.inatel.mymusicapi.service.PlaylistService;
import br.inatel.mymusicapi.service.UserService;


@SpringBootTest @ActiveProfiles("test")
class PlaylistServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	PlaylistService playlistService;
	@Autowired
	PlaylistRepository repository;
	
	@Test
	void shouldSavePlaylist() {
		NewPlaylistDto dto = new NewPlaylistDto("title", "description", (long) 1);
		Playlist playlist = new Playlist(dto);
		repository.save(playlist);
		Optional<Playlist> opPlaylist = repository.findByTitle(playlist.getTitle());
		assertFalse(opPlaylist.isEmpty());
	}
}