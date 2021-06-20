package org.example.application.database;

import org.example.domain.entities.taxpayer.TaxPayer;

import java.util.LinkedHashMap;
import java.util.Map;

public class fakeDB {
    public static final Map<Integer, TaxPayer> db = new LinkedHashMap<>();
}
