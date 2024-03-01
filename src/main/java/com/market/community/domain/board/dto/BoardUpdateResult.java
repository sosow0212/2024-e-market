package com.market.community.domain.board.dto;

import com.market.community.domain.board.Image;

import java.util.ArrayList;
import java.util.List;

public record BoardUpdateResult(List<Image> addedImages, List<Image> deletedImages) {

    public BoardUpdateResult(final List<Image> addedImages, final List<Image> deletedImages) {
        this.addedImages = handleAddedImages(addedImages);
        this.deletedImages = handleDeletedImages(deletedImages);
    }

    private List<Image> handleAddedImages(final List<Image> addedImages) {
        if (addedImages == null) {
            return new ArrayList<>();
        }

        return addedImages;
    }

    private List<Image> handleDeletedImages(final List<Image> deletedImages) {
        if (deletedImages == null) {
            return new ArrayList<>();
        }

        return deletedImages;
    }
}
