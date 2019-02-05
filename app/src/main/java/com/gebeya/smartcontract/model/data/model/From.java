package com.gebeya.smartcontract.model.data.model;

import android.support.annotation.Nullable;

import com.gebeya.smartcontract.model.data.dto.FromDTO;

public class From {
    private String id;
    private String firstName;
    private String lastName;
    private String publicId;
    private String createdAt;

    public From(FromDTO fromDTO) {
        this.id = fromDTO.getId();
        this.firstName = fromDTO.getFirstName();
        this.lastName = fromDTO.getLastName();
        this.createdAt = fromDTO.getCreatedAt();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof From) {
            From other = (From) obj;
            return other.getId().equals(id);
        }
        return false;
    }
}
