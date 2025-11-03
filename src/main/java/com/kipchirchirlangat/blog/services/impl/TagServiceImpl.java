package com.kipchirchirlangat.blog.services.impl;

import com.kipchirchirlangat.blog.domain.entities.Tag;
import com.kipchirchirlangat.blog.repositories.TagRepository;
import com.kipchirchirlangat.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }
}
