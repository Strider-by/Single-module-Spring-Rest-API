package com.epam.esm.controller.api;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.controller.util.Message;
import com.epam.esm.entity.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tags")
public interface TagsController {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    List<Tag> getAllTags();

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    Tag getTag(@PathVariable String name);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    Message createTag(TagDownstreamDto tag);

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    Message deleteTag(@PathVariable String name);

}
