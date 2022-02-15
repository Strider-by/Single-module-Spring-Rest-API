package com.epam.esm.controller.api;

//import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public  @ResponseBody boolean createTag(String name) {
        return tagsService.createTag(name);
    }

    @RequestMapping(value="/{name}", method=RequestMethod.DELETE, produces="application/json")
    public  @ResponseBody boolean deleteTag(@PathVariable String name) {
        // todo: return json with error if certificate wasn't found
        return tagsService.deleteTag(name);
    }

}
