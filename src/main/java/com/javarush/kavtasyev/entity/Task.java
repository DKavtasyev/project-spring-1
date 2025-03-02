package com.javarush.kavtasyev.entity;

import com.javarush.kavtasyev.constants.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "todo", name = "task")
@Getter
@Setter
public class Task
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private Status status;
}
