package com.example.warehouse.repository;

import com.example.warehouse.model.FashionBrand;
import com.example.warehouse.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.brand = :brand AND i.yearOfCreation = 2022")
    List<Item> findByBrandAndYear2022(@Param("brand") FashionBrand brand);
}