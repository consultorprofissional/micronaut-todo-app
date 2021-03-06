package micronaut.todo.app.controller;

import io.micronaut.context.annotation.Context;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import io.micronaut.views.rocker.RockerWritable;
import micronaut.todo.app.domain.Todo;
import micronaut.todo.app.repository.TodoRepository;
import views.index;
import views.todos;

import java.net.URI;
import java.net.URISyntaxException;

import static io.micronaut.http.MediaType.TEXT_HTML;

@Controller
public class TodoController {

    protected TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Get("/")
    @Produces(TEXT_HTML)
    public HttpResponse index(){
        return HttpResponse.ok(new RockerWritable(index.template()));
    }

    @Get("/todos")
    @Produces(TEXT_HTML)
    public HttpResponse todos(){
        return HttpResponse.ok(new RockerWritable(todos.template(todoRepository.findAll())));
    }

    @Context()
    @Post(value = "/todoNew", consumes = MediaType.MULTIPART_FORM_DATA )
    public HttpResponse<String> add(String todoItem, String status) throws URISyntaxException {
        Todo todo = new Todo(todoItem, status);
        todoRepository.save(todo);
        return HttpResponse.redirect(new URI("/todos"));
    }


    @Post(value = "todoDelete/{id}", consumes = MediaType.MULTIPART_FORM_DATA )
    public HttpResponse<String> delete(long id) throws URISyntaxException {
        todoRepository.deleteById(id);
        return HttpResponse.redirect(new URI("/todos"));
    }

    @Post(value = "/todoUpdate/{id}", consumes = MediaType.MULTIPART_FORM_DATA )
    public HttpResponse<String> update(long id) throws URISyntaxException {
        Todo todo = todoRepository.findById(id).get();
        todo.setCompleted("Yes".equals(todo.getCompleted()) ? "No": "Yes");
        todoRepository.update(todo);
        return HttpResponse.redirect(new URI("/todos"));
    }
    
}