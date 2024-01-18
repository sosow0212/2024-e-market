package com.market.board.domain.board;

import com.market.board.domain.image.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardUpdateResult {

    private final List<Image> addedImages;
    private final List<Image> deletedImages;

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
