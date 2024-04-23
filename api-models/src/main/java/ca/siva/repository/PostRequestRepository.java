package ca.siva.repository;


import ca.siva.model.PostRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRequestRepository extends JpaRepository<PostRequest, String> {
}
