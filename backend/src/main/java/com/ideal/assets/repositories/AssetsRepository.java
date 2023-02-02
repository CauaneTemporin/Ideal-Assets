package com.ideal.assets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideal.assets.entities.Assets;

public interface AssetsRepository extends JpaRepository<Assets, Long>{

}
