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
@RequestMapping(value = "/asset")
public class AssetsResource {
	
	@Autowired
	AssetsService service;

	//Retorna qualquer ativo "url http://localhost:8080/asset/NOMEdoAtivo"
	@GetMapping("/{assetName}")
	public ResponseEntity<AssetsDTO> findByAssetName(@PathVariable String assetName) throws IOException {
		return ResponseEntity.ok().body(service.findByAssetName(assetName));
	}

	//Salva os ativos, eles são salvos pelo postman e ficam salvos no banco h2
	@PostMapping
	public ResponseEntity<AssetsDTO> addAsset(@RequestBody AssetsDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	//Retorna a lista dos seus ativos "url http://localhost:8080/asset/seeList"
	@GetMapping("/seeList")
	public ResponseEntity<List<AssetsDTO>> seeList() throws IOException {
		return ResponseEntity.ok().body(service.findAll());
	}

	//Deleta o ativo a partir do id "url http://localhost:8080/asset/delete/IDdoATIVOqueVAIdeletar"
	@DeleteMapping("/delete/{assetId}")
	public ResponseEntity<Object> deleteAsset(@PathVariable Long assetId) {
		service.deleteById(assetId);
		return ResponseEntity.noContent().build();
	}
}
