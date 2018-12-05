package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GeneralInfo.
 */
@Entity
@Table(name = "general_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "generalinfo")
public class GeneralInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "short_name", nullable = false)
    private String shortName;

    @NotNull
    @Column(name = "logo", nullable = false)
    private Long logo;

    @NotNull
    @Column(name = "background_image", nullable = false)
    private Long backgroundImage;

    @NotNull
    @Column(name = "instruction_information", nullable = false)
    private String instructionInformation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public GeneralInfo shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getLogo() {
        return logo;
    }

    public GeneralInfo logo(Long logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public Long getBackgroundImage() {
        return backgroundImage;
    }

    public GeneralInfo backgroundImage(Long backgroundImage) {
        this.backgroundImage = backgroundImage;
        return this;
    }

    public void setBackgroundImage(Long backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getInstructionInformation() {
        return instructionInformation;
    }

    public GeneralInfo instructionInformation(String instructionInformation) {
        this.instructionInformation = instructionInformation;
        return this;
    }

    public void setInstructionInformation(String instructionInformation) {
        this.instructionInformation = instructionInformation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralInfo generalInfo = (GeneralInfo) o;
        if (generalInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), generalInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeneralInfo{" +
            "id=" + getId() +
            ", shortName='" + getShortName() + "'" +
            ", logo=" + getLogo() +
            ", backgroundImage=" + getBackgroundImage() +
            ", instructionInformation='" + getInstructionInformation() + "'" +
            "}";
    }
}
