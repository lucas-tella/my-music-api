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
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "likes")
//@NoArgsConstructor
//@Getter
//public class Like {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "LIKE_ID", nullable = false, length = 11)
//	private Long likeId;
//	
//	@ManyToOne
//	private Long trackId;
//	
//	@ManyToOne
//	private Long userId;
//
//	public Like(Long trackId, String userId) {
//		this.trackId = trackId;
//		this.userId = userId;
//	}
//}