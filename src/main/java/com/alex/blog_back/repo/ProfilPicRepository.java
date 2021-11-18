package com.alex.blog_back.repo;

import com.alex.blog_back.model.ProfilPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfilPicRepository extends JpaRepository<ProfilPic, String> {

}
