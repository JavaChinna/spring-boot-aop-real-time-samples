package com.javachinna.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "example")
public class ExampleEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}