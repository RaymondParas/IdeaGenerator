package com.IdeaGenerator.IdeaGenerator.services;

import com.IdeaGenerator.IdeaGenerator.Repository.IdeaRepository;
import com.IdeaGenerator.IdeaGenerator.exceptionHandle.RequestException;
import com.IdeaGenerator.IdeaGenerator.models.Idea;
import com.IdeaGenerator.IdeaGenerator.models.IdeaType;
import com.IdeaGenerator.IdeaGenerator.utilities.DamerauLevenshtein;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdeaGeneratorService {
    private final IdeaRepository repo;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public IdeaGeneratorService(IdeaRepository repo){
        this.repo = repo;
    }

    public Idea GetIdea(String name) {
        return repo.findByName(name);
    }

    public List<Idea> GetAllIdeas() {
        return repo.findAll();
    }

    public List<Idea> AddIdeas(List<Idea> ideas, boolean filter){
        List<Idea> newIdeas = filter ? FilterExistingIdeas(ideas) : ideas;
        if (newIdeas.size() == 0) {
            log.warn("No new ideas remained after filtering out already existing ideas");
            return null;
        }

        return repo.saveAll(newIdeas);
    }

    public boolean DeleteIdeas(List<String> names) {
        List<String> existingNames = FilterNonexistentNames(names);
        if (existingNames.size() == 0) {
            log.warn("No names remained after filtering out non-existent names");
            return false;
        }

        return repo.deleteAllByName(existingNames) > 0;
    }

    public Idea GetRandomIdea(String type) {
        if (type == null)
            return repo.findRandom();

        IdeaType ideaType = null;
        try{
            ideaType = IdeaType.valueOf(type);
        } catch (IllegalArgumentException e) {
            log.error(String.format("'%s' idea type does not exist", type), e);
            throw new RequestException(String.format("'%s' idea type does not exist", type), e);
        }

        return repo.findRandomByType(type);
    }

    public List<Idea> IdeaMatch(String name) {
        List<Idea> allIdeas = GetAllIdeas();
        return allIdeas.stream().filter(idea -> {
            String ideaName = idea.getName().toUpperCase().replace(" ", "");
            return name.contains(ideaName) || ideaName.contains(name)
                    || DamerauLevenshtein.matchPercentage(name, ideaName) >= 75;
        }).collect(Collectors.toList());
    }

    public boolean IdeaExists(String name){
        return repo.existsByName(name) == 1;
    }

    public List<Idea> FilterExistingIdeas(List<Idea> ideas){
        return ideas.stream().filter(i -> !IdeaExists(i.getName())).collect(Collectors.toList());
    }

    public List<String> FilterNonexistentNames(List<String> names){
        return names.stream().filter(n -> IdeaExists(n)).collect(Collectors.toList());
    }
}
