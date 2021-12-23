package br.inatel.mymusicapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.mymusicapi.model.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String>{
	
	Optional<Playlist> findByTitle(String title);
}
