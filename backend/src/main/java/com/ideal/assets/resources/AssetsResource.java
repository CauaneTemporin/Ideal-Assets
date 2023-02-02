package com.ideal.assets.resources;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ideal.assets.dto.AssetsDTO;
import com.ideal.assets.services.AssetsService;

@RestController
@RequestMapping(value = "/assets")
public class AssetsResource {
	
	@Autowired
	AssetsService service;

	//Returns any asset "url http://localhost:8080/assets/ASSETNAME"
	@GetMapping("/{assetsName}")
	public ResponseEntity<AssetsDTO> findByAssetName(@PathVariable String assetName) throws IOException {
		return ResponseEntity.ok().body(service.findByAssetName(assetName));
	}

	//Save the assets, they are saved by the postman and are saved in the Bank h2
	@PostMapping
	public ResponseEntity<AssetsDTO> addAsset(@RequestBody AssetsDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	//Returns the list of your assets "url http://localhost:8080/assets/seeList"
	@GetMapping("/seeList")
	public ResponseEntity<List<AssetsDTO>> seeList() throws IOException {
		return ResponseEntity.ok().body(service.findAll());
	}

	//Delete asset from id "url http://localhost:8080/assets/delete/id"
	@DeleteMapping("/delete/{assetsId}")
	public ResponseEntity<Object> deleteAsset(@PathVariable Long assetsId) {
		service.deleteById(assetsId);
		return ResponseEntity.noContent().build();
	}
}
