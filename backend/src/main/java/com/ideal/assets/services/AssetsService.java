package com.ideal.assets.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ideal.assets.dto.AssetsDTO;
import com.ideal.assets.entities.Assets;
import com.ideal.assets.repositories.AssetsRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Service
public class AssetsService {
	
	@Autowired
	private AssetsRepository repository;

	private Stock stock;

	private AssetsDTO assetDTO = new AssetsDTO();

	public AssetsDTO findByAssetName(String assetName) throws IOException {
		stock = YahooFinance.get(assetName);
		assetDTO.setName(assetName);
		assetDTO.setPrice(stock.getQuote().getPrice());
		return assetDTO;
	}

	@Transactional
	public AssetsDTO insert(AssetsDTO dto) {
		if (repository.exists(Example.of(new Assets(dto.getName())))) {
			throw new IllegalArgumentException("Record already exists with name " + dto.getName());
		}
		Assets entity = new Assets();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new AssetsDTO(entity);
	}

	@Transactional
	public List<AssetsDTO> findAll() throws IOException {
		List<Assets> list = repository.findAll();
		List<AssetsDTO> listDTO = new ArrayList<>();

		for (Assets assets : list) {
			stock = YahooFinance.get(assets.getName());
			AssetsDTO assetDTO = new AssetsDTO();
			assetDTO.setName(assets.getName());
			assetDTO.setId(assets.getId());
			assetDTO.setPrice(stock.getQuote().getPrice());
			listDTO.add(assetDTO);
		}
		return listDTO;
	}

	public void deleteById(Long assetId) {
		try {
			repository.deleteById(assetId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Asset with id " + assetId + " not found");
		}
	}
}
