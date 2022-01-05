package br.inatel.mymusicapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlist_track")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistTracks implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 11)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_playlist", referencedColumnName = "id", nullable = false)
	private Playlist playlist;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_track", referencedColumnName = "id", nullable = false)
	private Track track;
	
}
