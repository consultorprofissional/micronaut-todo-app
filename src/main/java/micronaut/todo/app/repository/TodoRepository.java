package micronaut.todo.app.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import micronaut.todo.app.domain.Todo;

@Repository
public interface TodoRepository extends PageableRepository<Todo, Long> {
}
