package ru.feduncov.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.feduncov.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends PagingAndSortingRepository<Message, Integer> {


    @Query("SELECT m FROM Message m")
    Page<Message> findMessages(Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.user.userId =:userId")
    Optional<List<Message>> findMessageByUserId(Integer userId);
}
