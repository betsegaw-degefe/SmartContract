package com.gebeya.smartcontract.model.data.model;

import android.support.annotation.Nullable;
import com.gebeya.smartcontract.model.data.dto.ToDTO;

public class To {
    private String id;
    private String firstName;
    private String lastName;
    private String publicId;
    private String createdAt;

    public To(ToDTO toDTO) {
        this.id = toDTO.getId();
        this.firstName = toDTO.getFirstName();
        this.lastName = toDTO.getLastName();
        this.publicId = toDTO.getPublicId();
        this.createdAt = toDTO.getCreatedAt();
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
        if (obj instanceof To) {
            To other = (To)obj;
            return other.getId().equals(id);
        }
        return false;
    }
}
