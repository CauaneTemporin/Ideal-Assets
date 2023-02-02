package com.ideal.assets.dto;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ideal.assets.entities.Assets;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetsDTO  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Stock stock;
	private BigDecimal price;

	public AssetsDTO() {
	}

	public AssetsDTO(String name) {
		this.name = name;
	}

	public AssetsDTO(String name, Long id) {
		this.name = name;
		this.id = id;
	}

	public AssetsDTO(Assets entity) {
		this.name = entity.getName();
		this.id = entity.getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		try {
			stock = YahooFinance.get(name);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return "AssetsDTO [id: " + id + ",name: " + name + ",price: " + stock.getQuote().getPrice() + "]";
	}
}
