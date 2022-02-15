package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateDao {

    long create(Certificate certificate);

    Certificate getById(long id);

    List<Certificate> getAll();

    Certificate update(Map<String, Object> nameValuePairs);

    boolean delete(long id);

}
