package com.dalila.flow_track.repository;

import com.dalila.flow_track.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository  extends JpaRepository<Task, UUID> {

    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN t.assignedUsers assignedUsers " +
            "WHERE (assignedUsers.id = :userId OR t.createdBy = :userName) " +
            "AND LOWER(t.title) LIKE %:keyword%")
    List<Task> findByKeywordAndUser(UUID userId, String userName, String keyword);

    List<Task> findByCreatedBy(String createdBy);

    @Query("SELECT DISTINCT t FROM Task t WHERE LOWER(t.title) LIKE %:keyword% OR LOWER(t.description) LIKE %:keyword%")
    List<Task> findByKeywordInTitleOrDescription(String keyword);

    @Query("SELECT t FROM Task t WHERE t.id NOT IN (SELECT DISTINCT t.id FROM Task t JOIN t.assignedUsers u)")
    List<Task> findTaskWithoutAssignedUser();







}
