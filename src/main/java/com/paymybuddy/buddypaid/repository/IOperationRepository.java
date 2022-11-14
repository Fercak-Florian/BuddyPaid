package com.paymybuddy.buddypaid.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.buddypaid.model.Operation;

@Repository
public interface IOperationRepository extends CrudRepository<Operation, Integer>{

}
