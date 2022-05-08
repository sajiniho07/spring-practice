package com.example.springpractice.controller;

import com.example.springpractice.bean.Exam;
import com.example.springpractice.bean.SampleResponse;
import com.example.springpractice.bean.User;
import com.example.springpractice.repository.ExamRepository;
import com.example.springpractice.repository.UserRepository;
import com.example.springpractice.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExamRepository examRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/simpleResponse")
    public SampleResponse simpleResponse(
            @RequestParam(value = "name", defaultValue = "unknown") String name) {
        SampleResponse response = new SampleResponse();
        response.setId(1);
        response.setMessage("Your name is " + name);
        return response;
    }

    @GetMapping("/users")
    public ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {
        Stream<User> stream = StreamSupport.stream(userRepository.findAll().spliterator(), false);
        Stream<EntityModel<User>> entityModelStream = stream.map(EntityModel::of);
        List<EntityModel<User>> employees = entityModelStream.collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(employees));
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findOne(@PathVariable long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new UserNotFoundException("user id: " + id);
        }
        EntityModel<User> resource = EntityModel.of(byId.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAll());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userRepository.save(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            throw new UserNotFoundException("user-" + user);
        }
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("user id: " + id);
        }
        try {
            userRepository.save(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            throw new UserNotFoundException("user id: " + id);
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/exams")
    public List<Exam> getUsersExams(@PathVariable long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("user id: " + id);
        }
        return userById.get().getExam();
    }

    @PostMapping("/users/{id}/exams")
    public ResponseEntity<Object> createExam(@PathVariable Long id, @RequestBody Exam exam) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("id-" + id);
        }
        User user = userOptional.get();
        exam.setUser(user);
        examRepository.save(exam);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(exam.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
