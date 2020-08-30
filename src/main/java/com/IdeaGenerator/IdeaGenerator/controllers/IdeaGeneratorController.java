package com.IdeaGenerator.IdeaGenerator.controllers;

import com.IdeaGenerator.IdeaGenerator.exceptionHandle.RecordNotFoundException;
import com.IdeaGenerator.IdeaGenerator.exceptionHandle.RequestException;
import com.IdeaGenerator.IdeaGenerator.models.Idea;
import com.IdeaGenerator.IdeaGenerator.services.IdeaGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/idea")
public class IdeaGeneratorController {
    private final IdeaGeneratorService service;

    @Autowired
    public IdeaGeneratorController(IdeaGeneratorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Idea> GetAllIdeas() {
        return service.GetAllIdeas();
    }

    @PostMapping
    public Idea GetIdea(@RequestBody Map<String, String> input) {
        String name = input == null ? null : input.get("name");
        if (name == null || name.isEmpty() || name.trim().isEmpty())
            throw new RequestException("No valid idea name");

        Idea result = service.GetIdea(name);
        if (result == null)
            throw new RecordNotFoundException(String.format("Idea '%s' could not be found", name));

        return result;
    }

    @DeleteMapping
    public String DeleteIdea(@RequestBody List<String> names){
        if (names.stream().anyMatch(n -> n == null || n.isEmpty() || n.trim().isEmpty())
            || !areAllUnique((names)))
            throw new RequestException("No unique valid idea names entered");

        boolean result = service.DeleteIdeas(names);
        if (!result)
            throw new RecordNotFoundException("Ideas entered could not be found");

        return "Successfully deleted ideas";
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/add")
    public List<Idea> AddIdeas(@RequestBody List<Idea> ideas,
                               @RequestHeader(value = "Filter", defaultValue = "true", required = false) boolean filter){
        if (ideas == null || ideas.isEmpty())
            throw new RequestException("No ideas found in request");

        List<String> names = ideas.stream().map(Idea::getName).collect(Collectors.toList());
        if (!areAllUnique(names))
            throw new RequestException("Duplicate ideas found in request");

        List<Idea> result = service.AddIdeas(ideas, filter);
        if (result == null)
            throw new RequestException("Ideas entered already exist");

        return result;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping(value = {"/random", "/random/{ideaType}"})
    public Idea GetRandomIdea(@PathVariable(required = false) String ideaType) {
        Idea result = service.GetRandomIdea(ideaType);
        if (result == null)
            throw new RequestException(String.format("No ideas of '%s' idea type available", ideaType));

        return result;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/exists/{ideaName}")
    public boolean IdeaExists(@PathVariable String ideaName) {
        return service.IdeaExists(ideaName);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/matches/{ideaName}")
    public List<Idea> IdeaMatches(@PathVariable String ideaName) {
        return service.IdeaMatch(ideaName.toUpperCase().replace(" ", ""));
    }

    private static <T> boolean areAllUnique(List<T> list){
        return list.stream().allMatch(new HashSet<>()::add);
    }
}
