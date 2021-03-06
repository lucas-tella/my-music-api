package br.inatel.mymusicapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.mymusicapi.model.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	Optional<Playlist> findByTitle(String title);
	Optional<Playlist> findByUserId(Long id);
	Optional<Playlist> findById(Long id);
	Optional<Page<Playlist>> findByIdContaining (Long id, Pageable pageable);
}