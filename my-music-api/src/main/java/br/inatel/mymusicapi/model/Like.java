package br.inatel.mymusicapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@Getter
public class Like {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String likeId;
	
	@ManyToOne
	private String trackId;
	
	@ManyToOne
	private String userId;

	public Like(String trackId, String userId) {
		this.trackId = trackId;
		this.userId = userId;
	}
}