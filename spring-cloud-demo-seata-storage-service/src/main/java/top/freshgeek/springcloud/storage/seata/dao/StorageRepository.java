package top.freshgeek.springcloud.storage.seata.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.freshgeek.springcloud.storage.entity.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

}