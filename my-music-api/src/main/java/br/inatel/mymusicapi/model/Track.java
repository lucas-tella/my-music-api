package br.inatel.mymusicapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "track")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Track implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 11)
	private Long id;

//	@Column(name = "like")
//	private boolean like;
	
//	private Long trackIdDeezer;
}
