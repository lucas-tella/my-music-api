//package br.inatel.mymusicapi.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.MapsId;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "profiles")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Profile{
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id", nullable = false, length = 11)
//	private Long id;
//	
//	@OneToOne
//	@MapsId							 //dar uma lida
//	@JoinColumn(name = "user_id")
//	private User user;
//
//	@Column(name = "name", nullable = false)
//	@Getter @Setter
//	private String name;
//}