package sbd.domain;

import sbd.beans.EAlbumType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by benoit on 12/08/15.
 */
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String name;

    @NotNull
    LocalDate creationDate;

    @NotNull
    @ManyToOne
    Band author;

    @ManyToOne
    Category category;

    @ManyToOne
    SubCategory subCategory;

    String label;

    boolean numericVersion=false;

    boolean cdVersion=false;

    EAlbumType type=EAlbumType.ALBUM;

    public Album() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Band getAuthor() {
        return author;
    }

    public void setAuthor(Band author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isNumericVersion() {
        return numericVersion;
    }

    public void setNumericVersion(boolean numericVersion) {
        this.numericVersion = numericVersion;
    }

    public boolean isCdVersion() {
        return cdVersion;
    }

    public void setCdVersion(boolean cdVersion) {
        this.cdVersion = cdVersion;
    }

    public EAlbumType getType() {
        return type;
    }

    public void setType(EAlbumType type) {
        this.type = type;
    }
}
