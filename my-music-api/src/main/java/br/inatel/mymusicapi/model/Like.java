//package br.inatel.mymusicapi.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "like")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Like {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id_like", nullable = false, length = 11)
//	private Long likeId;
//	
//	@ManyToOne
//	
//	private Long trackId;
//	
//	@ManyToOne
//	private Long userId;
//
//	public Like(Long trackId, Long userId) {
//		this.trackId = trackId;
//		this.userId = userId;
//	}
//}