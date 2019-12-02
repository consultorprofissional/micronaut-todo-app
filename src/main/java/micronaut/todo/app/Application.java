package micronaut.todo.app;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.scheduling.annotation.Async;
import io.reactivex.Flowable;
import micronaut.todo.app.domain.Todo;
import micronaut.todo.app.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application implements ApplicationEventListener<ServiceStartedEvent> {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    protected TodoRepository todoRepository;

    public Application(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @Async
    @Override
    public void onApplicationEvent(ServiceStartedEvent event) {
        log.info("Remove old data");
        todoRepository.deleteAll();

        log.info("Loading data at startup");
        Flowable.just(new Todo("Learn Spring", "Yes"),
                new Todo("Learn Driving", "No"),
                new Todo("Go for a Walk", "No"),
                new Todo("Cook health Lunch", "No"),
                new Todo("Cook Dinner", "Yes"))
                .forEach(todoRepository::save);
    }
}