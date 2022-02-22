package com.epam.esm.controller.api;

//import com.epam.esm.entity.dto.TagDto;
import com.epam.esm.controller.util.Message;
import com.epam.esm.controller.api.exception.TagAlreadyExistsException;
import com.epam.esm.controller.api.exception.TagNotFoundException;
import com.epam.esm.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    static {
        System.out.println("TagsController.static initializer");
    }

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public  @ResponseBody
    List<String> getAllTags() {
        return tagsService.getAllTags();
    }

    @RequestMapping(value="/{name}", method=RequestMethod.GET, produces="application/json")
    public  @ResponseBody String getTag(@PathVariable String name) {
        return tagsService.getTag(name);
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    public  @ResponseBody Message createTag(String name) {
        boolean created = tagsService.createTag(name);
        if (!created) {
            throw new TagAlreadyExistsException(name);
        }

        return new Message(HttpStatus.OK, String.format("Tag '%s' has been created", name));
    }

    @RequestMapping(value="/{name}", method=RequestMethod.DELETE, produces="application/json")
    public  @ResponseBody Message deleteTag(@PathVariable String name) {
        boolean deleted = tagsService.deleteTag(name);
        if (!deleted) {
            throw new TagNotFoundException(name);
        }

        return new Message(HttpStatus.OK, String.format("Tag '%s' has been deleted", name));
    }



    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Message tagNotFound(TagNotFoundException ex) {
        String tagName = ex.getTagName();
        return new Message(HttpStatus.NOT_FOUND, String.format("Tag '%s' can not be found", tagName));
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Message tagAlreadyExist(TagAlreadyExistsException ex) {
        String tagName = ex.getTagName();
        return new Message(HttpStatus.CONFLICT, String.format("Tag '%s' already exists", tagName));
    }
}
