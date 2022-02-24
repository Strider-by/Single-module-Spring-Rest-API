package com.epam.esm.controller.api;

import com.epam.esm.controller.util.Message;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface TagsController {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    List<String> getAllTags();

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    String getTag(@PathVariable String name);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    Message createTag(String name);

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    Message deleteTag(@PathVariable String name);

}
