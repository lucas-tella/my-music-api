package br.inatel.mymusicapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.mymusicapi.model.Track;
import br.inatel.mymusicapi.model.User;

@Repository
public interface LikeRepository extends JpaRepository<Track, Long>{
	
	Optional<?> findTrackAndUser (Track track, User user);
}