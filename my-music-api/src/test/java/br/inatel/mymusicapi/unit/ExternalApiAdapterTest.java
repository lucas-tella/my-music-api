package br.inatel.mymusicapi.unit;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.mymusicapi.adapter.ExternalApiAdapter;
import br.inatel.mymusicapi.dto.AlbumDto;
import br.inatel.mymusicapi.dto.ArtistDto;
import br.inatel.mymusicapi.dto.TrackExtendedDto;

@SpringBootTest @ActiveProfiles("test")
public class ExternalApiAdapterTest {
	
	@Autowired
	ExternalApiAdapter adapter;
    @Value("${deezer.api.url}")
    private String url;
    @Value("${deezer.api.key}")
    private String key;
	
	@Test
	void shouldRetrieveTrackDataFromDeezerApi(){
		TrackExtendedDto expectedDto = new TrackExtendedDto() ;
		ArtistDto artist = new ArtistDto();
		artist.setName("Justin Bieber");
		AlbumDto album = new AlbumDto();
		album.setTitle("Justice");
		expectedDto.setId("1280165222");
		expectedDto.setTitle("Peaches");
		expectedDto.setDuration("198");
		expectedDto.setArtist(artist);
		expectedDto.setAlbum(album);
		
		TrackExtendedDto trackMono = adapter.getTrackById("1280165222");
		
		assertEquals(expectedDto, trackMono);
	}
}