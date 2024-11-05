package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
