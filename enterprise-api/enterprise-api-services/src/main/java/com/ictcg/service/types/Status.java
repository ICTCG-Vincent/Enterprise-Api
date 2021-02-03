package com.ictcg.service.types;

public enum Status {

    Freelance("Freelance"),
    Employee("Employee");

    String status;

    Status(String value) {
        this.status = value;
    }
}
