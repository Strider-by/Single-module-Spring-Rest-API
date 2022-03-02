package com.epam.esm.service.impl;

import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CertificatesServiceImplTest {

    @Mock
    private CertificateDao dao;
    @Autowired
    private CertificatesService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CertificatesServiceImpl(dao);
    }

    @Test
    void createCertificate() {

        Certificate expected = createCertificateDummy();
        CertificateCreateDto dto = createCertificateDownstreamDtoDummy();

        when(dao.createCertificate(any(), any())).thenReturn(expected);
        Certificate actual = service.createCertificate(dto);
        assertEquals(expected, actual);

    }

    @Test
    void getCertificate() {
        long id = 7;
        Certificate expected = createCertificateDummy();

        CertificatesService service = new CertificatesServiceImpl(dao);
        when(dao.getCertificateById(id)).thenReturn(expected);

        Certificate actual = service.getCertificate(id);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCertificates() {
        List<Certificate> expected = createListOfCertificateDummy();
        when(dao.getAllCertificates()).thenReturn(expected);

        List<Certificate> actual = service.getAllCertificates();
        assertEquals(actual, expected);
    }

    @Test
    void updateCertificate() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        long id = 4;
        Certificate expected = createCertificateDummy();
        when(dao.update(any())).thenReturn(expected);

        Certificate actual = service.updateCertificate(id, parameters);
        assertEquals(expected, actual);
    }

    @Test
    void deleteCertificate_positive() {
        long id = 4;
        boolean expected = true;
        when(dao.delete(id)).thenReturn(expected);

        boolean actual = service.deleteCertificate(id);
        assertEquals(expected, actual);
    }

    @Test
    void deleteCertificate_negative() {
        long id = 4;
        boolean expected = false;
        when(dao.delete(id)).thenReturn(expected);

        boolean actual = service.deleteCertificate(id);
        assertEquals(expected, actual);
    }

    @Test
    void searchCertificates() {
        List<Certificate> expected = createListOfCertificateDummy();
        Map<String, String> parameters = new HashMap<>();
        when(dao.searchCertificates(parameters)).thenReturn(expected);

        List<Certificate> actual = service.searchCertificates(parameters);
        assertEquals(expected, actual);
    }


    private Certificate createCertificateDummy() { // todo: to class
        String name = "name";
        int price = 1000;
        int duration = 12;

        Certificate dummy = new Certificate();
        dummy.setName(name);
        dummy.setPrice(price);
        dummy.setDuration(duration);

        return dummy;
    }

    private List<Certificate> createListOfCertificateDummy() { // todo: to class
        List<Certificate> certificates = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String name = "certificate " + (i + 1);
            int price = (i + 2) * 1000;
            int duration = i * 2 + 4;

            Date date = new Date();


            Certificate dummy = new Certificate();
            dummy.setName(name);
            dummy.setPrice(price);
            dummy.setDuration(duration);
            dummy.setCreateDate(date);
            dummy.setLastUpdateDate(date);
        }

        return certificates;
    }

    private CertificateCreateDto createCertificateDownstreamDtoDummy() { // todo: to class
        String name = "name";
        int price = 1000;
        int duration = 12;

        CertificateCreateDto dto = new CertificateCreateDto();
        dto.setName(name);
        dto.setDuration(duration);
        dto.setPrice(price);
        return dto;
    }

}