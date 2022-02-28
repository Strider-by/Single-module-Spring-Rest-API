//package com.epam.esm.controller.api;
//
//import com.epam.esm.controller.api.impl.TagsControllerImpl;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.service.TagsService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
//@ContextConfiguration
//@WebAppConfiguration
//class TagsControllerTest {
//
//    @Mock
//    private TagsService service;
//
//    @org.junit.jupiter.api.BeforeEach
//    void setUp() {
//    }
//
//
//
//    @AfterEach
//    void tearDown() {
//    }
//
////    @org.junit.jupiter.api.Test
////    void getAllTags() throws Exception{
////        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
////    }
//
//    @Test
//    void testTest() {
////        Mockito.when(service.getAllCertificates()).thenReturn(new ArrayList<>());
////        assertEquals(0, service.getAllCertificates().size());
//    }
//
//    @Test
//    void testGetAllTags() {
//        TagsController controller = new TagsControllerImpl(service);
//        List<Tag> expected = Arrays.asList(new Tag(), new Tag(), new Tag());
//        Mockito.when(service.getAllTags()).thenReturn(expected);
//        assertEquals(expected, controller.getAllTags());
//    }
//
//    @Test
//    void getTag() {
//    }
//
//    @Test
//    void createTag() {
//    }
//
//    @Test
//    void deleteTag() {
//    }
//
//    @Test
//    void tagNotFound() {
//    }
//
//    @Test
//    void tagAlreadyExist() {
//    }
//
//}